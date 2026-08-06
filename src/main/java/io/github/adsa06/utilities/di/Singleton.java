package io.github.adsa06.utilities.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca una clase como "gestionada" por el {@link Injector}: el contenedor
 * podrá instanciarla y resolverle las dependencias automáticamente.
 *
 * Dos cosas clave al definir una anotación propia:
 *
 * - @Retention(RUNTIME): sin esto, el compilador descarta la anotación al
 *   generar el .class y el Injector jamás podría verla vía reflection en
 *   tiempo de ejecución (todo fallaría en silencio, sin ningún error).
 *
 * - @Target(TYPE): restringe dónde se puede poner esta anotación. TYPE
 *   significa "sobre una clase o interfaz". Si alguien intenta ponerla
 *   sobre un método o un campo, no compila.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Solo se aplica a clases/interfaces
public @interface Singleton {
}
