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
package com.geometrycloud.happydonut.util;

import com.github.luischavez.database.link.Row;

import java.util.HashMap;
import java.util.Map;

/**
 * Funciones de utilidad para la base de datos.
 *
 * @author Luis Chavez Bustamante
 */
public class DatabaseUtils {

    /**
     * Crea un mapa con las claves y valores de un resultado.
     *
     * @param row resultado.
     * @return mapa.
     */
    public static Map<String, Object> toMap(Row row) {
        HashMap<String, Object> map = new HashMap<>();
        for (String key : row.keys()) {
            map.put(key, row.value(key));
        }
        return map;
    }
}
