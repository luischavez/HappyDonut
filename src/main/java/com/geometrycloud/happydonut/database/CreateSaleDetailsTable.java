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
package com.geometrycloud.happydonut.database;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Migracion para la tabla de detalles de venta, en esta tabla se almacenara la
 * informacion de los detalles de cada venta.
 *
 * @author Luis Chavez Bustamante
 */
public class CreateSaleDetailsTable implements Migration {

    /**
     * Se crea la estructura de la tabla.
     *
     * @param database instancia de la base de datos.
     */
    @Override
    public void up(Database database) {
        database.create(SALE_DETAILS_TABLE_NAME, (table) -> {
            table.integer(SALE_DETAILS_PRIMARY_KEY).unsigned().incremented();
            table.string(SALE_DETAILS_NAME, SALE_DETAILS_NAME_SIZE);
            table.decimal(SALE_DETAILS_PRICE,
                    SALE_DETAILS_PRICE_SIZE, SALE_DETAILS_PRICE_ZEROS);
            table.integer(SALE_DETAILS_QUANTITY).unsigned();
            table.integer(SALE_DETAILS_SALE).unsigned();

            table.primary(SALE_DETAILS_PRIMARY_KEY);
            table.foreign(SALE_DETAILS_SALE,
                    SALES_TABLE_NAME, SALES_PRIMARY_KEY,
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
        database.drop(SALE_DETAILS_TABLE_NAME);
    }
}
