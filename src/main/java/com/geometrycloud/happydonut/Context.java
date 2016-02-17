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
package com.geometrycloud.happydonut;

import com.geometrycloud.happydonut.database.DatabaseConstants;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.configuration.ProjectSource;
import com.github.luischavez.database.configuration.XMLBuilder;

import java.util.ResourceBundle;

/**
 * Representa al estado actual del programa en ejecucion.
 *
 * @author Luis Chávez Bustamante
 */
public final class Context {

    // Mensajes por defecto.
    public static final ResourceBundle messages
            = ResourceBundle.getBundle("lang/messages");

    // Conexion con la base de datos.
    public static final Database DATABASE;

    // Carga la configuracion de la base de datos y ejecuta las migraciones.
    static {
        Database.load(new XMLBuilder(),
                new ProjectSource(DatabaseConstants.DATABASE_CONFIG_FILE));
        DATABASE = Database.use(DatabaseConstants.DATABASE_NAME);
        DATABASE.open();
        DATABASE.migrate();
    }

    /**
     * Obtiene un mensaje desde el archivo apropiado si existe, de otra manera
     * retorna la llave.
     *
     * @param key llave.
     * @return mensaje o llave si el mensaje no existe.
     */
    public static String message(String key) {
        if (messages.containsKey(key)) {
            return messages.getString(key);
        }
        return key;
    }
}
