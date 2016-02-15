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
 * Migracion para la tabla de categorias, en esta tabla se almacenaran las
 * categorias de los productos.
 *
 * @author Luis Chavez Bustamante
 */
public class CreateCategoriesTable implements Migration {

    /**
     * Se crea la estructura de la tabla.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void up(Database database) {
        database.create(DatabaseConstants.CATEGORY_TABLE_NAME, (table) -> {
            table.integer("category_id").unsigned().incremented();
            table.string("name", 128);

            table.primary("category_id");
        });
    }

    /**
     * Se elimina la tabla de la base de datos.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void down(Database database) {
        database.drop(DatabaseConstants.CATEGORY_TABLE_NAME);
    }
}
