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

import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

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
        database.create(PROVIDERS_TABLE_NAME, (table) -> {
            table.integer(PROVIDERS_PRIMARY_KEY).unsigned().incremented();
            table.string(PROVIDERS_FIRST_NAME, PROVIDERS_FIRST_NAME_SIZE);
            table.string(PROVIDERS_LAST_NAME, PROVIDERS_LAST_NAME_SIZE);
            table.string(PROVIDERS_PHONE, PROVIDERS_PHONE_SIZE);
            table.string(PROVIDERS_EMAIL, PROVIDERS_EMAIL_SIZE);
            table.text(PROVIDERS_INGREDIENTS);

            table.primary(PROVIDERS_PRIMARY_KEY);
        });
    }

    /**
     * Se elimina la tabla de la base de datos.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void down(Database database) {
        database.drop(PROVIDERS_TABLE_NAME);
    }
}
