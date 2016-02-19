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

import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import static com.geometrycloud.happydonut.Context.*;

/**
 * Menu principal de la aplicacion.
 *
 * @author Luis Chavez Bustamante
 */
public class MainMenu extends JMenuBar implements ActionListener {

    // Menu ver.
    private final JMenu view = new JMenu(message("view"));

    // Menu para abrir el panel de administracion.
    private final JMenuItem admin = new JMenuItem(message("admin"));

    /**
     * Constructor vacio.
     */
    public MainMenu() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        admin.addActionListener(this);

        view.add(admin);
        add(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (admin == source) {
            UiUtils.display(message("admin"),
                    new AdminPanel(), (JComponent) this.getParent());
        }
    }
}
