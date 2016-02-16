/* 
 * Copyright (C) 2015 GeometryCloud <http://www.geometrycloud.com>
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

/**
 * Representa al estado actual del programa en ejecucion.
 *
 * @author Luis Chávez Bustamante
 */
public final class Context {

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
}
