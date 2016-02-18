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
 * Migracion para la tabla del carrito, aqui se almacenara la informacion de
 * forma temporal de la venta actual.
 *
 * @author Luis Chavez Bustamante
 */
public class CreateCartTable implements Migration {

    @Override
    public void up(Database database) {
        database.create(CART_TABLE_NAME, (table) -> {
            table.integer(CART_PRIMARY_KEY).unsigned().incremented();
            table.integer(CART_PRODUCT).unsigned();

            table.primary(CART_PRIMARY_KEY);
            table.foreign(CART_PRODUCT,
                    PRODUCTS_TABLE_NAME, PRODUCTS_PRIMARY_KEY,
                    "CASCADE", "CASCADE");
        });
    }

    @Override
    public void down(Database database) {
        database.drop(CART_TABLE_NAME);
    }
}
