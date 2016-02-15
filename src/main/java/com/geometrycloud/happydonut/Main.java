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

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class Main {

    // Aspecto de la aplicacion.
    public static final String LOOK_AND_FEEL = "com.jtattoo.plaf.luna.LunaLookAndFeel";

    /**
     * Establece el aspecto de la aplicacion.
     */
    public static void loadLookAndFeel() {
        try {
            UIManager.setLookAndFeel(LOOK_AND_FEEL);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String... args) {
        // Se carga la configuracion de la base de datos.
        Database.load(new XMLBuilder(), new ProjectSource("/database.xml"));
        // Se obtiene una instancia de la base de datos.
        Database database = Database.use("default");
        /*
         * Se abre la conexion, posteriormente se ejecutan las migraciones
         * y finalmente se cierra la conexion.
         */
        database.open();
        database.migrate();
        database.close();

        // Carga el aspecto seleccionado.
        loadLookAndFeel();
    }
}
