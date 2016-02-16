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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * Panel con la capacidad para establecer sus propiedades a partir de un objeto,
 * ademas de poder obtenerlas automaticamente.
 *
 * @author Luis Chavez Bustamante
 */
public class FillablePanel extends JPanel {

    /**
     * Obtiene los campos con la anotacion Fillable.
     *
     * @param object objeto del cual se buscara.
     * @return lista con los campos encontrados.
     */
    private List<Field> fillableFields(Object object) {
        List<Field> fields = new ArrayList<>();
        Class<? extends Object> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Fillable.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Obtiene el nombre de un campo con la anotacion Fillable.
     *
     * @param field campo.
     * @return nombre del campo.
     */
    private String fillableName(Field field) {
        Fillable annotation = field.getAnnotation(Fillable.class);
        if (null != annotation) {
            return annotation.name();
        }
        throw new InvalidFieldException(field.getName());
    }

    /**
     * Obtiene el valor del campo.
     *
     * @param field campo.
     * @param object objeto donde esta el campo.
     * @return valor del campo.
     */
    private Object value(Field field, Object object) {
        Object value = null;
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            Accesor accesor = Accesor.forClass(fieldValue.getClass());
            if (null == accesor) {
                throw new InvalidFieldException("invalid accesor for field");
            }
            value = accesor.get(fieldValue);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new InvalidFieldException("cant access field", ex);
        }
        return value;
    }

    /**
     * Actualiza el valor de un campo.
     *
     * @param field campo.
     * @param value valor.
     * @param object objeto donde esta el campo.
     */
    private void update(Field field, Object value, Object object) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            Accesor accesor = Accesor.forClass(fieldValue.getClass());
            if (null == accesor) {
                throw new InvalidFieldException("invalid accesor for field");
            }
            accesor.set(fieldValue, value);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new InvalidFieldException("cant set field value", ex);
        }
    }

    /**
     * Almacena la clave y el valor de una lista de campos en un mapa.
     *
     * @param fields lista de campos.
     * @param object objeto donde estan los campos.
     * @return mapa.
     */
    private Map<String, Object> mapFields(List<Field> fields, Object object) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : fields) {
            String name = fillableName(field);
            Object value = value(field, object);
            map.put(name, value);
        }
        return map;
    }

    /**
     * Establece los valores del panel a partir de un mapa.
     *
     * @param map mapa.
     */
    public void fill(Map<String, Object> map) {
        List<Field> fields = fillableFields(this);
        for (Field field : fields) {
            String name = fillableName(field);
            if (map.containsKey(name)) {
                update(field, map.get(name), this);
            }
        }
    }

    /**
     * Obtiene un mapa con las claves y valores del panel.
     *
     * @return mapa.
     */
    public Map<String, Object> get() {
        List<Field> fields = fillableFields(this);
        Map<String, Object> map = mapFields(fields, this);
        return map;
    }
}
