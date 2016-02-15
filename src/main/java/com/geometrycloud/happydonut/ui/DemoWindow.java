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
package com.geometrycloud.happydonut.ui;

import com.geometrycloud.happydonut.Main;

import javax.swing.JFrame;

/**
 * Interfaz de ejemplo para ver las caracteristicas del look and feel.
 *
 * @author Luis Chavez Bustamante
 */
public class DemoWindow {

    // Componentes.
    private final JFrame frame = new JFrame("DEMO");

    /**
     * Constructor por defecto.
     */
    public DemoWindow() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        frame.setSize(480, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ejecuta el programa.
     *
     * @param args argumentos.
     */
    public static void main(String... args) {
        // Carga la apariencia.
        Main.loadLookAndFeel();
        // Inicializa la ventana.
        DemoWindow window = new DemoWindow();
    }
}
