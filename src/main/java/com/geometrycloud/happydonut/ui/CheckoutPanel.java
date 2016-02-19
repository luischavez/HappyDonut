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
import com.geometrycloud.happydonut.util.UiUtils;

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
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
public class CheckoutPanel extends JPanel implements ActionListener {

    // Observadores.
    private final List<CheckoutListener> listeners = new ArrayList<>();

    // Items en el carrito.
    private RowList cartItems;

    // Etiqueta donde se mostrara el total.
    private final JLabel total = new JLabel(message("total").concat(" $0.00"));

    // Boton para terminar la compra.
    private final JButton checkout = new JButton(message("checkout"));

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
        checkout.addActionListener(this);

        total.setFont(Fonts.TITLE_FONT);
        checkout.setFont(Fonts.TITLE_FONT);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(total, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(checkout, constraints);
    }

    /**
     * Agrega un observador.
     *
     * @param listener observador.
     */
    public void addCheckoutListener(CheckoutListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Elimina un observador.
     *
     * @param listener observador.
     */
    public void removeCheckoutListener(CheckoutListener listener) {
        listeners.remove(listener);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        double totalAmount = calculate();
        if (0 == totalAmount) {
            return;
        }
        InputNumberForm input = new InputNumberForm(
                message("amount"), message("amount.description"), true);
        boolean confirm = UiUtils.plain(
                message("amount"),
                input, (JComponent) this.getParent());
        if (confirm) {
            double result = input.result();
            if (result < totalAmount) {
                UiUtils.warning(
                        message("warning.title"),
                        message("warning.amount"), (JComponent) this.getParent());
            } else {
                if (result > totalAmount) {
                    JLabel exchange
                            = new JLabel(String.valueOf(result - totalAmount));
                    exchange.setFont(Fonts.TITLE_FONT);
                    UiUtils.display(message("exchange"),
                            exchange, (JComponent) this.getParent());
                }
                UiUtils.display(
                        message("thanks.title"),
                        message("thanks.message"), (JComponent) this.getParent());
                for (CheckoutListener listener : listeners) {
                    listener.onCheckout();
                }
            }
        }
    }

    /**
     * Interface para la escucha de eventos de compra.
     */
    public interface CheckoutListener {

        /**
         * Se lanza cuando la compra se confirma.
         */
        void onCheckout();
    }
}
