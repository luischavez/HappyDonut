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
package com.geometrycloud.happydonut;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.configuration.ProjectSource;
import com.github.luischavez.database.configuration.XMLBuilder;
import com.github.luischavez.database.link.Affecting;
import com.github.luischavez.database.link.RowList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryTest {

    // Instancia de la base de datos.
    private Database database;

    /**
     * Se carga la configuracion de la base de datos de prueba.
     */
    @BeforeClass
    public static void setUpClass() {
        Database.load(new XMLBuilder(), new ProjectSource("/database.xml"));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Se obtiene la instancia de la base de datos, se abre la conexion y se
     * ejecutan las migraciones para crear las tablas.
     */
    @Before
    public void setUp() {
        database = Database.use(DATABASE_NAME);
        database.open();
        database.migrate();
    }

    /**
     * Se revierten las migraciones para eliminar las tablas de la base de datos
     * y al finalizar se cierra la conexion.
     */
    @After
    public void tearDown() {
        database.rollback();
        database.close();
    }

    /**
     * Verifica si la conexion existe y funciona.
     */
    @Test
    public void testConnection() {
        boolean exist = database.exists(CATEGORIES_TABLE_NAME);
        assertTrue(exist);
    }

    /**
     * Inserta una categoria en la base de datos.
     */
    @Test
    public void testCategoryInsert() {
        Affecting insert = database
                .insert(CATEGORIES_TABLE_NAME, CATEGORIES_NAME, "test");
        assertTrue(insert.success());
    }

    /**
     * Actualiza una categoria en la base de datos.
     */
    @Test
    public void testCategoryUpdate() {
        Affecting insert = database
                .insert(CATEGORIES_TABLE_NAME, CATEGORIES_NAME, "test");
        Object id = insert.getGeneratedKeys()[0];
        Affecting update = database.where(CATEGORIES_PRIMARY_KEY, "=", id)
                .update(CATEGORIES_TABLE_NAME, CATEGORIES_NAME, "other");
        assertTrue(update.success());
    }

    /**
     * Eliina una categoria de la base de datos.
     */
    @Test
    public void testCategoryDelete() {
        Affecting insert = database
                .insert(CATEGORIES_TABLE_NAME, CATEGORIES_NAME, "test");
        Object id = insert.getGeneratedKeys()[0];
        Affecting delete = database.where(CATEGORIES_PRIMARY_KEY, "=", id)
                .delete(CATEGORIES_TABLE_NAME);
        assertTrue(delete.success());
    }

    /**
     * Consulta la tabla de categorias.
     */
    @Test
    public void testCategoryQuery() {
        database.insert(CATEGORIES_TABLE_NAME, CATEGORIES_NAME, "test");
        RowList rows = database.table(CATEGORIES_TABLE_NAME).get();
        assertTrue(0 < rows.size());
    }
}
