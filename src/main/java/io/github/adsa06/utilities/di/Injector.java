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

    private final Map<Class<?>, Object> instances = new HashMap<>();
    // Registra qué clases se están construyendo ahora mismo, para detectar
    // dependencias circulares en vez de reventar con un StackOverflowError.
    private final Set<Class<?>> onCreation = new HashSet<>();

    /**
     * Registra un objeto YA CONSTRUIDO (p.ej. cargado de una base de datos,
     * un fichero de configuración, etc.) para que el Injector lo entregue
     * a partir de ahora como si fuera un @Component más. No hace falta que
     * `tipo` esté anotado con @Component: aquí no hay reflection, solo se
     * guarda la referencia. Debe llamarse ANTES de que algo pida ese tipo.
     */
    public <T> void registerInstance(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    public <T> T getInstance(Class<T> type) {
        if (instances.containsKey(type)) {
            return type.cast(instances.get(type));
        }

        if (!type.isAnnotationPresent(Singleton.class)) {
            throw new IllegalStateException(
                "La clase " + type.getName() + " no está anotada con @Singleton");
        }

        if (!onCreation.add(type)) { // Si en creation ya existe ese tipo da excepcion
            throw new IllegalStateException(
                "Dependencia circular detectada creando " + type.getName()
                + ". Este Injector no la resuelve automáticamente: rompe el "
                + "ciclo usando inyección por campo en uno de los dos lados, "
                + "o rediseña para eliminar la dependencia circular.");
        }

        try {
            T instance = type.cast(build(type));
            // Se cachea ANTES de  cinyectarampos, por si otro campo del
            // grafo vuelve a pedir este mismo tipo (reutiliza la misma
            // instancia en vez de crear una segunda).
            instances.put(type, instance);
            injectFields(instance);
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("No se pudo crear una instancia de " + type.getName(), e);
        } finally {
            onCreation.remove(type);
        }
    }

    private Object build(Class<?> type) throws ReflectiveOperationException {
        Constructor<?> constructor = selectConstructor(type);
        constructor.setAccessible(true);

        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            arguments[i] = getInstance(parameterTypes[i]); // resolución recursiva
        }
        return constructor.newInstance(arguments);
    }

    private Constructor<?> selectConstructor(Class<?> type) {
        Constructor<?>[] constructors = type.getDeclaredConstructors();

        // 1) Si hay un constructor anotado con @Inject, se usa ese.
        for (Constructor<?> c : constructors) {
            if (c.isAnnotationPresent(Inject.class)) {
                return c;
            }
        }
        // 2) Si solo hay un constructor, se usa directamente (igual que
        //    hace Spring desde la 4.3: con un único constructor no hace
        //    falta anotarlo).
        if (constructors.length == 1) {
            return constructors[0];
        }
        throw new IllegalStateException(
            "La clase " + type.getName() + " tiene varios constructores; "
            + "anota con @Inject el que debe usar el Injector");
    }

    private void injectFields(Object instancie) throws IllegalAccessException {
        for (Field field : instancie.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }
            if (Modifier.isFinal(field.getModifiers())) {
                throw new IllegalStateException(
                    "El campo '" + field.getName() + "' en " + instancie.getClass().getSimpleName()
                    + " es final: la inyección por campo ocurre DESPUÉS de construir "
                    + "el objeto, así que no puede ser final. Usa inyección por "
                    + "constructor para ese campo.");
            }
            field.setAccessible(true);
            field.set(instancie, getInstance(field.getType()));
        }
    }
}