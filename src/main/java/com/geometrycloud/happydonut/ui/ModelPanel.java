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

import com.geometrycloud.happydonut.Main;
import com.geometrycloud.happydonut.swing.FormPanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Panel para la admninistracion de los modelos.
 *
 * @author Luis Chávez Bustamante
 */
public class ModelPanel extends JPanel implements ActionListener {

    private final String tableName;
    private final String[] requiredFields;
    private final Class<? extends FormPanel> formPanelClass;

    private final JButton addButton = new JButton("Agregar");

    public ModelPanel(String tableName, String[] requiredFields,
            Class<? extends FormPanel> formPanelClass) {
        this.tableName = tableName;
        this.requiredFields = requiredFields;
        this.formPanelClass = formPanelClass;
        initComponents();
    }

    private void initComponents() {
        addButton.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(addButton, constraints);
    }

    private FormPanel createForm() {
        try {
            return formPanelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addButton == e.getSource()) {
            FormPanel form = createForm();
            UiUtils.form("Agregar", form, this, requiredFields);
        }
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        UiUtils.launch("Model Panel",
                new ModelPanel("categories",
                        CATEGORIES_REQUIRED_FIELDS, CategoryFormPanel.class));
    }
}
