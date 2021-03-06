/* 
 * Copyright (C) 2016 Luis Chávez Bustamante
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

import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    // Carga los accesores por defecto al cargar la clase.
    static {
        registerDefaults();
    }

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
     * Transforma un objeto en el valor apropiado.
     *
     * @param object objeto.
     * @return objeto transformado.
     */
    public Object cast(Object object) {
        return object;
    }

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

    /**
     * Registra los accesores por defecto.
     */
    public static void registerDefaults() {
        register(JTextField.class, new JTextFieldAccesor());
        register(JPasswordField.class, new JPasswordFieldAccesor());
        register(ImagePanel.class, new ImagePanelAccesor());
        register(DatabaseComboBox.class, new DatabaseComboBoxAccesor());
    }
}
