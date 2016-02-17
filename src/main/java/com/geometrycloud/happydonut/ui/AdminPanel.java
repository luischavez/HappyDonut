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
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.Context.*;

/**
 * Panel de administracion.
 *
 * @author Luis Chavez Bustamante
 */
public class AdminPanel extends JPanel implements ActionListener {

    private final JButton categoriesButton = new JButton(message("categories"));
    private final JButton productsButton = new JButton(message("products"));
    private final JButton providersButton = new JButton(message("providers"));

    public AdminPanel() {
        initComponents();
    }

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
        add(categoriesButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(productsButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(providersButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        UiUtils.launch("Admin", new AdminPanel());
    }
}
