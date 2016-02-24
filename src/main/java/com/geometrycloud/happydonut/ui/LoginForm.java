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
package com.geometrycloud.happydonut.ui;

import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FormPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static com.geometrycloud.happydonut.Context.*;

/**
 * Formulario para el inicio de sesion.
 *
 * @author Luis Chávez Bustamante
 */
public class LoginForm extends FormPanel {

    public static final String PASSWORD = "password";

    /* Etiquetas. */
    private final JLabel passwordLabel = new JLabel(message("password"));

    // Campo contrasena.
    @Fillable(name = PASSWORD)
    private final JTextField password = new JPasswordField();

    /**
     * Constructor por defecto.
     */
    public LoginForm() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridwidth = GridBagConstraints.REMAINDER;

        constraints.gridwidth = 1;
        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(password, constraints);
    }
}
