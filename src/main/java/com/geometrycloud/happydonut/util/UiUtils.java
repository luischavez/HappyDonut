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
package com.geometrycloud.happydonut.util;

import com.geometrycloud.happydonut.swing.FillablePanel;

import java.awt.EventQueue;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Clase con funciones utilies para el manejo de la interfaz de usuario.
 *
 * @author Luis Chavez Bustamante
 */
public class UiUtils {

    /**
     * Muestra un componente envuelto en un JFrame.
     *
     * @param title titulo.
     * @param component componente.
     */
    public static void launch(String title, JComponent component) {
        JFrame frame = new JFrame(title);
        frame.add(component);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Muestra un formulario en forma de mensaje desplegable, bloquea el acceso
     * al padre mientras esta en primer plano.
     *
     * @param title titulo.
     * @param form formulario.
     * @param parent padre.
     * @return mapa con la informacion del formulario o null si se cancela.
     */
    public static Map<String, Object> form(String title, FillablePanel form,
            JComponent parent) {
        int option = JOptionPane.showConfirmDialog(parent, form, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (JOptionPane.OK_OPTION == option) {
            return form.get();
        }
        return null;
    }

    /**
     * Repinta un componente utilizando el hilo apropiado.
     *
     * @param component componente a repintar.
     */
    public static void repaint(final JComponent component) {
        EventQueue.invokeLater(() -> {
            component.invalidate();
            component.revalidate();
            component.repaint();
        });
    }

    /**
     * Muestra un dialogo de advertencia.
     *
     * @param title titulo.
     * @param message mensaje.
     * @param parent componente padre.
     */
    public static void warning(String title, Object message, JComponent parent) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
