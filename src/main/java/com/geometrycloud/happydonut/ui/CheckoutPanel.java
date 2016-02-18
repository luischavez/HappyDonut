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

import com.geometrycloud.happydonut.Fonts;

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Informacion del total del carrito, ademas ofrece la opcion para realizar la
 * compra.
 *
 * @author Luis Chávez Bustamante
 */
public class CheckoutPanel extends JPanel {

    // Items en el carrito.
    private RowList cartItems;

    // Etiqueta donde se mostrara el total.
    private final JLabel total = new JLabel(message("total").concat(" $0.00"));

    /**
     * Constructor vacio.
     */
    public CheckoutPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        total.setFont(Fonts.TITLE_FONT);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(total, constraints);
    }

    /**
     * Calcula el total del carrito.
     *
     * @return total.
     */
    private double calculate() {
        double totalAmount = 0;
        for (Row cartItem : cartItems) {
            Row product = DATABASE.table(PRODUCTS_TABLE_NAME)
                    .where(PRODUCTS_PRIMARY_KEY, "=",
                            cartItem.value(CART_PRODUCT))
                    .first();
            BigDecimal price = product.decimal(PRODUCTS_PRICE);
            Long quantity = cartItem.number(CART_QUANTITY);
            totalAmount += price.doubleValue() * quantity;
        }
        return totalAmount;
    }

    /**
     * Carga la informacion de la base de datos.
     */
    public void loadData() {
        cartItems = DATABASE.table(CART_TABLE_NAME).get();
        double totalAmount = calculate();
        total.setText(String.format("%s $%.2f",
                message("total"), totalAmount));
    }
}
