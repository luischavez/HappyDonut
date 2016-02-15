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
 * Migracion para la tabla de detalles de venta, en esta tabla se almacenara
 * la informacion de los detalles de cada venta.
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
        database.create(DatabaseConstants.SALE_DETAILS_TABLE_NAME, (table) -> {
            table.integer("sale_detail_id").unsigned().incremented();
            table.string("name", 128);
            table.decimal("price", 8, 2);
            table.integer("quantity").unsigned();
            table.integer("sale_id").unsigned();
            
            table.primary("sale_detail_id");
            table.foreign("sale_id", 
                    DatabaseConstants.SALES_TABLE_NAME, "sale_id", 
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
        database.drop(DatabaseConstants.SALE_DETAILS_TABLE_NAME);
    }
}
