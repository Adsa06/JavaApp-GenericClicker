package io.github.adsa06.utilities.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca un constructor o un campo para que el {@link Injector} le resuelva
 * las dependencias. @Target acepta un array, así que esta anotación puede
 * ir en dos sitios distintos, con dos comportamientos distintos:
 *
 * - Sobre un CONSTRUCTOR: el Injector llama a ese constructor pasándole
 *   las dependencias ya resueltas como argumentos. Como la asignación
 *   ocurre dentro del propio constructor (this.campo = parametro), el
 *   campo SÍ puede ser final.
 *
 * - Sobre un FIELD (campo): el Injector primero construye el objeto y
 *   DESPUÉS le asigna el valor al campo por reflection. Como la asignación
 *   ocurre después de construir el objeto, el campo NO puede ser final
 *   (Injector.java lo comprueba y lanza un error claro si lo es).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface Inject {
}
