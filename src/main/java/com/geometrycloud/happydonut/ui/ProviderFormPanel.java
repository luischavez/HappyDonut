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
import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FormPanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Formulario para la tabla de proveedores.
 *
 * @author Luis Chavez Bustamante
 */
public class ProviderFormPanel extends FormPanel {

    /*
    * Etiquetas.
     */
    private final JLabel firstNameLabel
            = new JLabel(message("first_name"));
    private final JLabel lastNameLabel
            = new JLabel(message("last_name"));
    private final JLabel phoneLabel
            = new JLabel(message("phone"));
    private final JLabel emailLabel
            = new JLabel(message("email"));
    private final JLabel ingredientsLabel
            = new JLabel(message("ingredients"));

    // Campo para el nombre.
    @Fillable(name = PROVIDERS_FIRST_NAME)
    private final JTextField firstName = new JTextField();

    // Campo para el apellido.
    @Fillable(name = PROVIDERS_LAST_NAME)
    private final JTextField lastName = new JTextField();

    // Campo para el numero de telefono.
    @Fillable(name = PROVIDERS_PHONE)
    private final JTextField phone = new JTextField();

    // Campo para el correo electronico.
    @Fillable(name = PROVIDERS_EMAIL)
    private final JTextField email = new JTextField();

    // Campo para los ingredientes.
    @Fillable(name = PROVIDERS_INGREDIENTS)
    private final JTextField ingredients = new JTextField();

    /**
     * Constructor por defecto.
     */
    public ProviderFormPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(firstNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(firstName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(lastNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(lastName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(phoneLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(phone, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(email, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(ingredientsLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(ingredients, constraints);
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        UiUtils.form("Provider form", new ProviderFormPanel(), null,
                PROVIDERS_REQUIRED_FIELDS);
    }
}
