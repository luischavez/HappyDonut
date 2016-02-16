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
import com.geometrycloud.happydonut.filter.MaxSizeFilter;
import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FillablePanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 * Formulario para la tabla de categorias.
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryFormPanel extends FillablePanel {

    // Tamano maximo del campo nombre.
    public static final int MAX_NAME_LENGTH = 128;

    private final JLabel nameLabel = new JLabel("Nombre");

    // Campo nombre.
    @Fillable(name = "name")
    private final JTextField name = new JTextField();

    /**
     * Contructor por defecto.
     */
    public CategoryFormPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        AbstractDocument document = (AbstractDocument) name.getDocument();
        document.setDocumentFilter(new MaxSizeFilter(MAX_NAME_LENGTH));

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(name, constraints);
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        CategoryFormPanel form = new CategoryFormPanel();
        int option = JOptionPane.showConfirmDialog(null, form, "Category Form",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (JOptionPane.OK_OPTION == option) {
            Map<String, Object> map = form.get();
            map.forEach((k, v) -> System.out.printf("%s = %s\n", k, v));
        }
        //UiUtils.launch("Category Form", new CategoryFormPanel());
    }
}
