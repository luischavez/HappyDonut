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
package com.geometrycloud.happydonut.database;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 * Migracion para la tabla de proveedores, en esta tabla se almacenara la
 * informacion de los proveedores de ingredientes.
 * 
 * @author Luis Chavez Bustamante
 */
public class CreateProvidersTable implements Migration {

    /**
     * Se crea la estructura de la tabla.
     * 
     * @param database instancia de la base de datos.
     */
    @Override
    public void up(Database database) {
        database.create(DatabaseConstants.PROVIDERS_TABLE_NAME, (table) -> {
            table.integer("provider_id").unsigned().incremented();
            table.string("first_name", 128);
            table.string("last_name", 128);
            table.string("phone", 32);
            table.string("email", 128);
            table.text("ingredients");
            
            table.primary("provider_id");
        });
    }

    /**
     * Se elimina la tabla de la base de datos.
     * 
     * @param database instancia de la base de datos.
     */
    @Override
    public void down(Database database) {
        database.drop(DatabaseConstants.PROVIDERS_TABLE_NAME);
    }
}
