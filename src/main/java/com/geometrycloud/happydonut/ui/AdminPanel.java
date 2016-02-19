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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Panel de administracion.
 *
 * @author Luis Chavez Bustamante
 */
public class AdminPanel extends JPanel implements ActionListener {

    // Boton para cargar la informacion de las categorias.
    private final JButton categoriesButton = new JButton(message("categories"));

    // Boton para cargar la informacion de los productos.
    private final JButton productsButton = new JButton(message("products"));

    // Boton para cargar la informacion de los proveedores.
    private final JButton providersButton = new JButton(message("providers"));

    // Grafico.
    private final ChartFilterPanel chart = new ChartFilterPanel();

    /**
     * Constructor vacio.
     */
    public AdminPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        categoriesButton.addActionListener(this);
        productsButton.addActionListener(this);
        providersButton.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(categoriesButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(productsButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(providersButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(chart, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String title = "";
        ModelPanel modelPanel = null;
        if (categoriesButton == source) {
            title = CATEGORIES_TABLE_NAME;
            modelPanel = new ModelPanel(CATEGORIES_TABLE_NAME, CATEGORIES_PRIMARY_KEY,
                    CATEGORIES_DISPLAY_FIELDS, CATEGORIES_REQUIRED_FIELDS,
                    CategoryFormPanel.class, false);
        } else if (productsButton == source) {
            title = PRODUCTS_TABLE_NAME;
            modelPanel = new ModelPanel(PRODUCTS_TABLE_NAME, PRODUCTS_PRIMARY_KEY,
                    PRODUCTS_DISPLAY_FIELDS, PRODUCTS_REQUIRED_FIELDS,
                    ProductFormPanel.class, true);
        } else if (providersButton == source) {
            title = PROVIDERS_TABLE_NAME;
            modelPanel = new ModelPanel(PROVIDERS_TABLE_NAME, PROVIDERS_PRIMARY_KEY,
                    PROVIDERS_DISPLAY_FIELDS, PROVIDERS_REQUIRED_FIELDS,
                    ProviderFormPanel.class, false);
        }
        if (null != modelPanel) {
            UiUtils.display(message(title), modelPanel, this);
        }
    }
}
