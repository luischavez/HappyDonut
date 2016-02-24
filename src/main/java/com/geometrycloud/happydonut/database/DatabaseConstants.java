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

    // Ancho de las imagenes.
    public static final int IMAGE_WIDTH = 100;

    // Alto de las imagenes.
    public static final int IMAGE_HEIGHT = 100;

    // Definicion de la tabla de proveedores.
    public static final String PROVIDERS_TABLE_NAME = "providers";
    public static final String PROVIDERS_PRIMARY_KEY = "provider_id";
    public static final String PROVIDERS_FIRST_NAME = "first_name";
    public static final String PROVIDERS_LAST_NAME = "last_name";
    public static final String PROVIDERS_PHONE = "phone";
    public static final String PROVIDERS_EMAIL = "email";
    public static final String PROVIDERS_INGREDIENTS = "ingredients";

    public static final int PROVIDERS_FIRST_NAME_SIZE = 128;
    public static final int PROVIDERS_LAST_NAME_SIZE = 128;
    public static final int PROVIDERS_PHONE_SIZE = 32;
    public static final int PROVIDERS_EMAIL_SIZE = 128;

    public static final String[] PROVIDERS_DISPLAY_FIELDS = {
        PROVIDERS_FIRST_NAME, PROVIDERS_LAST_NAME,
        PROVIDERS_PHONE, PROVIDERS_EMAIL,
        PROVIDERS_INGREDIENTS
    };

    public static final String[] PROVIDERS_REQUIRED_FIELDS = {
        PROVIDERS_FIRST_NAME, PROVIDERS_LAST_NAME,
        PROVIDERS_PHONE, PROVIDERS_EMAIL,
        PROVIDERS_INGREDIENTS
    };

    // Definicion de la tabla de categorias.
    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String CATEGORIES_PRIMARY_KEY = "category_id";
    public static final String CATEGORIES_NAME = "name";

    public static final int CATEGORY_NAME_SIZE = 128;

    public static final String[] CATEGORIES_DISPLAY_FIELDS = {
        CATEGORIES_NAME
    };

    public static final String[] CATEGORIES_REQUIRED_FIELDS = {
        CATEGORIES_NAME
    };

    // Definicion de la tabla de productos.
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String PRODUCTS_PRIMARY_KEY = "product_id";
    public static final String PRODUCTS_NAME = "name";
    public static final String PRODUCTS_DESCRIPTION = "description";
    public static final String PRODUCTS_INGREDIENTS = "ingredients";
    public static final String PRODUCTS_PRICE = "price";
    public static final String PRODUCTS_STOCK = "stock";
    public static final String PRODUCTS_IMAGE = "image";
    public static final String PRODUCTS_CATEGORY = "category_id";
    public static final String PRODUCTS_CREATED_AT = "created_at";
    public static final String PRODUCTS_UPDATED_AT = "updated_at";

    public static final int PRODUCTS_NAME_SIZE = 128;
    public static final int PRODUCTS_PRICE_SIZE = 8;
    public static final int PRODUCTS_PRICE_ZEROS = 2;

    public static final String[] PRODUCTS_DISPLAY_FIELDS = {
        PRODUCTS_NAME,
        PRODUCTS_PRICE, PRODUCTS_STOCK
    };

    public static final String[] PRODUCTS_REQUIRED_FIELDS = {
        PRODUCTS_NAME, PRODUCTS_INGREDIENTS,
        PRODUCTS_PRICE, PRODUCTS_STOCK,
        PRODUCTS_IMAGE, PRODUCTS_CATEGORY
    };

    // Definicion de la tabla de ventas.
    public static final String SALES_TABLE_NAME = "sales";
    public static final String SALES_PRIMARY_KEY = "sale_id";
    public static final String SALES_SALE_DATE = "sale_date";

    // Definicion de la tabla de datalles de venta.
    public static final String SALE_DETAILS_TABLE_NAME = "sale_details";
    public static final String SALE_DETAILS_PRIMARY_KEY = "sale_detail_id";
    public static final String SALE_DETAILS_NAME = "name";
    public static final String SALE_DETAILS_PRICE = "price";
    public static final String SALE_DETAILS_QUANTITY = "quantity";
    public static final String SALE_DETAILS_SALE = "sale_id";

    public static final int SALE_DETAILS_NAME_SIZE = 128;
    public static final int SALE_DETAILS_PRICE_SIZE = 8;
    public static final int SALE_DETAILS_PRICE_ZEROS = 2;

    public static final String[] SALE_DETAILS_DISPLAY_FIELDS = {
        SALE_DETAILS_NAME, SALE_DETAILS_PRICE,
        SALE_DETAILS_QUANTITY
    };

    public static final String[] SALE_DETAILS_REQUIRED_FIELDS = {
        SALE_DETAILS_NAME, SALE_DETAILS_PRICE,
        SALE_DETAILS_QUANTITY, SALE_DETAILS_SALE
    };

    // Definicion de la tabla del carrito.
    public static final String CART_TABLE_NAME = "cart";
    public static final String CART_PRIMARY_KEY = "item_id";
    public static final String CART_QUANTITY = "quantity";
    public static final String CART_PRODUCT = "product_id";
}
