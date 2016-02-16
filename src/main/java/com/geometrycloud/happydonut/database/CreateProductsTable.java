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
 * Migracion para la tabla de productos, en esta tabla se almacenaran todos los
 * productos.
 *
 * @author Luis Chavez Bustamante
 */
public class CreateProductsTable implements Migration {

    /**
     * Se crea la estructura de la tabla.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void up(Database database) {
        database.create(PRODUCTS_TABLE_NAME, (table) -> {
            table.integer(PRODUCTS_PRIMARY_KEY).unsigned().incremented();
            table.string(PRODUCTS_NAME, PRODUCTS_NAME_SIZE);
            table.text(PRODUCTS_DESCRIPTION).nullable();
            table.text(PRODUCTS_INGREDIENTS);
            table.decimal(PRODUCTS_PRICE,
                    PRODUCTS_PRICE_SIZE, PRODUCTS_PRICE_ZEROS);
            table.integer(PRODUCTS_STOCK);
            table.binary(PRODUCTS_IMAGE);
            table.integer(PRODUCTS_CATEGORY).unsigned();
            table.dateTime(PRODUCTS_CREATED_AT);
            table.dateTime(PRODUCTS_UPDATED_AT);

            table.primary(PRODUCTS_PRIMARY_KEY);
            table.foreign(PRODUCTS_CATEGORY,
                    CATEGORY_TABLE_NAME, CATEGORY_PRIMARY_KEY,
                    "CASCADE", "CASCADE");
        });
    }

    /**
     * Se elimina la tabla de la base de datos.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void down(Database database) {
        database.drop(PRODUCTS_TABLE_NAME);
    }
}
