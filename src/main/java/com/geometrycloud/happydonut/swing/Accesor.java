/*
 * Copyright (C) 2016 Luis Chavez Bustamante
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.geometrycloud.happydonut.swing;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de registrar los distintos accesores de la aplicacion.
 *
 * @param <T> tipo de objeto a accesar.
 * @param <V> tipo de valor.
 * @author Luis Chavez Bustamante
 */
public abstract class Accesor<T, V> {

    // Contiene todos los accesores de la aplicacion.
    private static final Map<Class<?>, Accesor> ACCESORS = new HashMap<>();

    /**
     * Establece el valor del objeto.
     *
     * @param object objeto.
     * @param value valor.
     */
    public abstract void set(T object, V value);

    /**
     * Obtiene el valor del objeto.
     *
     * @param object objeto.
     * @return valor del objeto.
     */
    public abstract V get(T object);

    /**
     * Obtiene el accesor de la clase especificada.
     *
     * @param <T> tipo de objeto a accesar.
     * @param <V> tipo de valor.
     * @param clazz clase del objeto.
     * @return accesor.
     */
    public static <T, V> Accesor<T, V> forClass(Class<T> clazz) {
        if (ACCESORS.containsKey(clazz)) {
            return ACCESORS.get(clazz);
        }
        return null;
    }

    /**
     * Registra un nuevo accesor para la clase indicada.
     *
     * @param <T> tipo de objeto a accesar.
     * @param <V> tipo de valor.
     * @param clazz clase del objeto.
     * @param accesor accesor.
     */
    public static <T, V> void register(Class<T> clazz, Accesor<T, V> accesor) {
        ACCESORS.put(clazz, accesor);
    }
}
