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

/**
 * Constantes de la base de datos.
 *
 * @author Luis Chavez Bustamante
 */
public class DatabaseConstants {

    // Archivo de configuracion de la base de datos.
    public static final String DATABASE_CONFIG_FILE = "/database.xml";

    // Nombre de la conexion.
    public static final String DATABASE_NAME = "default";

    // Nombre de la tabla de proveedores.
    public static final String PROVIDERS_TABLE_NAME = "providers";

    // Nombre de la tabla de categorias.
    public static final String CATEGORY_TABLE_NAME = "categories";

    // Nombre de la tabla de productos.
    public static final String PRODUCTS_TABLE_NAME = "products";

    // Nombre de la tabla de ventas.
    public static final String SALES_TABLE_NAME = "sales";

    // Nombre de la tabla de datalles de venta.
    public static final String SALE_DETAILS_TABLE_NAME = "sale_details";
}
