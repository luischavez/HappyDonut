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
package com.geometrycloud.happydonut.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel con un teclado numerico virtual.
 *
 * @author Luis Chavez Bustamante
 */
public class VirtualNumPad extends JPanel implements ActionListener {

    // Botonoes numericos.
    private final JButton number0 = new JButton("0");
    private final JButton number1 = new JButton("1");
    private final JButton number2 = new JButton("2");
    private final JButton number3 = new JButton("3");
    private final JButton number4 = new JButton("4");
    private final JButton number5 = new JButton("5");
    private final JButton number6 = new JButton("6");
    private final JButton number7 = new JButton("7");
    private final JButton number8 = new JButton("8");
    private final JButton number9 = new JButton("9");
    private final JButton dot = new JButton(".");
    private final JButton delete = new JButton("<<");

    private final JTextField field;

    /**
     * Constructor principal.
     *
     * @param field campo donde escribir.
     */
    public VirtualNumPad(JTextField field) {
        this.field = field;
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        field.setEnabled(false);

        number0.addActionListener(this);
        number1.addActionListener(this);
        number2.addActionListener(this);
        number3.addActionListener(this);
        number4.addActionListener(this);
        number5.addActionListener(this);
        number6.addActionListener(this);
        number7.addActionListener(this);
        number8.addActionListener(this);
        number9.addActionListener(this);
        dot.addActionListener(this);
        delete.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipadx = 20;
        constraints.ipady = 20;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        add(delete, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number7, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number8, constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number9, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number4, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number5, constraints);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number6, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number2, constraints);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(number3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        add(number0, constraints);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(dot, constraints);
    }

    /**
     * Lanza una pulsacion de la tecla indicada.
     *
     * @param keycode codigo de la tecla.
     */
    private void fireKey(char character) {
        String text = field.getText();
        if (text.contains(".")) {
            int indexOfDot = text.indexOf(".");
            System.out.println(text.length() - indexOfDot);
            if (text.length() - indexOfDot == 3) {
                return;
            }
        }
        String value;
        if (text.endsWith(".0")) {
            value = text.substring(0, text.length() - 1)
                    .concat(Character.toString(character));
        } else {
            value = text.concat(Character.toString(character));
        }
        try {
            if (!value.contains(".")) {
                field.setText(new Integer(value).toString());
            } else {
                field.setText(new Double(value).toString());
            }
        } catch (NumberFormatException ex) {
            // IGNORE.
        }
    }

    /**
     * Activa o desactiva el uso de decimales.
     *
     * @param enable bandera que indica el estado.
     */
    public void decimals(boolean enable) {
        dot.setEnabled(enable);
    }

    /**
     * Obtiene el resultado.
     *
     * @return resultado.
     */
    public double result() {
        return Double.valueOf(field.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (number0 == source) {
            fireKey('0');
        } else if (number1 == source) {
            fireKey('1');
        } else if (number2 == source) {
            fireKey('2');
        } else if (number3 == source) {
            fireKey('3');
        } else if (number4 == source) {
            fireKey('4');
        } else if (number5 == source) {
            fireKey('5');
        } else if (number6 == source) {
            fireKey('6');
        } else if (number7 == source) {
            fireKey('7');
        } else if (number8 == source) {
            fireKey('8');
        } else if (number9 == source) {
            fireKey('9');
        } else if (dot == source) {
            fireKey('.');
        } else if (delete == source) {
            String text = field.getText();
            if (!text.isEmpty()) {
                String substring = text.substring(0, text.length() - 1);
                if (substring.endsWith(".")) {
                    substring = substring.replaceFirst("\\.", "");
                }
                field.setText(substring);
            }
        }
    }
}
