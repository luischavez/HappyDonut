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
package com.geometrycloud.happydonut;

import com.github.luischavez.database.Database;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class Main {

    // Aspecto de la aplicacion.
    public static final String LOOK_AND_FEEL = "com.jtattoo.plaf.texture.TextureLookAndFeel";

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
        // Se obtiene la instancia de la base de datos.
        Database database = Context.DATABASE;

        // Carga el aspecto seleccionado.
        loadLookAndFeel();
    }
}
