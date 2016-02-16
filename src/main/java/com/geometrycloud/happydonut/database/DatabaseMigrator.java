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

import com.github.luischavez.database.Migrator;

/**
 * Migracion inicial de la base de datos, contiene la informacion para
 * crear las tablas necesarias y eliminarlas en caso de ser necesario.
 * 
 * @author Luis Chavez Bustamante
 */
public class DatabaseMigrator extends Migrator {

    /**
     * Configura las migraciones que se realizaran.
     */
    @Override
    public void setup() {
        register(new CreateProvidersTable());
        register(new CreateCategoriesTable());
        register(new CreateProductsTable());
        register(new CreateSalesTable());
        register(new CreateSaleDetailsTable());
    }
}
