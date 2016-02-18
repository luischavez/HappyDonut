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
import com.geometrycloud.happydonut.swing.VirtualNumPad;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana para la introduccion de valores numericos.
 *
 * @author Luis Chavez Bustamante
 */
public class InputNumberForm extends JPanel {

    /* Etiquetas. */
    private final JLabel nameLabel = new JLabel();
    private final JLabel descriptionLabel = new JLabel();

    // Campo de texto donde mostrar el resultado.
    private final JTextField field = new JTextField();

    // Teclado numerico virtual.
    private final VirtualNumPad numPad = new VirtualNumPad(field);

    /**
     * Constructor principal.
     *
     * @param title titulo.
     * @param description descripcion.
     * @param decimals indica si permite decimales.
     */
    public InputNumberForm(String title, String description, boolean decimals) {
        nameLabel.setText(title);
        descriptionLabel.setText(description);
        numPad.decimals(decimals);
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
        constraints.anchor = GridBagConstraints.NORTH;
        add(nameLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        add(field, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        add(descriptionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        add(numPad, constraints);
    }

    /**
     * Obtiene el resultado.
     *
     * @return resultado.
     */
    public double result() {
        return numPad.result();
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        UiUtils.launch("Input",
                new InputNumberForm("Cantidad", "Ingresa la cantidad", true));
    }
}
