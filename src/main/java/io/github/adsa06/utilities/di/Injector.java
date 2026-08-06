package io.github.adsa06.utilities.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contenedor de dependencias mínimo: dado un Class, lo instancia resolviendo
 * primero sus dependencias de forma recursiva. Todas las instancias son
 * singleton (una sola por tipo, cacheada).
 */
public class Injector {

    private final Map<Class<?>, Object> instancias = new HashMap<>();
    // Registra qué clases se están construyendo ahora mismo, para detectar
    // dependencias circulares en vez de reventar con un StackOverflowError.
    private final Set<Class<?>> enCreacion = new HashSet<>();

    /**
     * Registra un objeto YA CONSTRUIDO (p.ej. cargado de una base de datos,
     * un fichero de configuración, etc.) para que el Injector lo entregue
     * a partir de ahora como si fuera un @Component más. No hace falta que
     * `tipo` esté anotado con @Component: aquí no hay reflection, solo se
     * guarda la referencia. Debe llamarse ANTES de que algo pida ese tipo.
     */
    public <T> void registerInstance(Class<T> tipo, T instancia) {
        instancias.put(tipo, instancia);
    }

    public <T> T getInstance(Class<T> tipo) {
        if (instancias.containsKey(tipo)) {
            return tipo.cast(instancias.get(tipo));
        }

        if (!tipo.isAnnotationPresent(Singleton.class)) {
            throw new IllegalStateException(
                "La clase " + tipo.getName() + " no está anotada con @Component");
        }

        if (!enCreacion.add(tipo)) {
            throw new IllegalStateException(
                "Dependencia circular detectada creando " + tipo.getName()
                + ". Este Injector no la resuelve automáticamente: rompe el "
                + "ciclo usando inyección por campo en uno de los dos lados, "
                + "o rediseña para eliminar la dependencia circular.");
        }

        try {
            T instancia = tipo.cast(construir(tipo));
            // Se cachea ANTES de inyectar campos, por si otro campo del
            // grafo vuelve a pedir este mismo tipo (reutiliza la misma
            // instancia en vez de crear una segunda).
            instancias.put(tipo, instancia);
            inyectarCampos(instancia);
            return instancia;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("No se pudo crear una instancia de " + tipo.getName(), e);
        } finally {
            enCreacion.remove(tipo);
        }
    }

    private Object construir(Class<?> tipo) throws ReflectiveOperationException {
        Constructor<?> constructor = elegirConstructor(tipo);
        constructor.setAccessible(true);

        Class<?>[] tiposParametros = constructor.getParameterTypes();
        Object[] argumentos = new Object[tiposParametros.length];
        for (int i = 0; i < tiposParametros.length; i++) {
            argumentos[i] = getInstance(tiposParametros[i]); // resolución recursiva
        }
        return constructor.newInstance(argumentos);
    }

    private Constructor<?> elegirConstructor(Class<?> tipo) {
        Constructor<?>[] constructores = tipo.getDeclaredConstructors();

        // 1) Si hay un constructor anotado con @Inject, se usa ese.
        for (Constructor<?> c : constructores) {
            if (c.isAnnotationPresent(Inject.class)) {
                return c;
            }
        }
        // 2) Si solo hay un constructor, se usa directamente (igual que
        //    hace Spring desde la 4.3: con un único constructor no hace
        //    falta anotarlo).
        if (constructores.length == 1) {
            return constructores[0];
        }
        throw new IllegalStateException(
            "La clase " + tipo.getName() + " tiene varios constructores; "
            + "anota con @Inject el que debe usar el Injector");
    }

    private void inyectarCampos(Object instancia) throws IllegalAccessException {
        for (Field campo : instancia.getClass().getDeclaredFields()) {
            if (!campo.isAnnotationPresent(Inject.class)) {
                continue;
            }
            if (Modifier.isFinal(campo.getModifiers())) {
                throw new IllegalStateException(
                    "El campo '" + campo.getName() + "' en " + instancia.getClass().getSimpleName()
                    + " es final: la inyección por campo ocurre DESPUÉS de construir "
                    + "el objeto, así que no puede ser final. Usa inyección por "
                    + "constructor para ese campo.");
            }
            campo.setAccessible(true);
            campo.set(instancia, getInstance(campo.getType()));
        }
    }
}