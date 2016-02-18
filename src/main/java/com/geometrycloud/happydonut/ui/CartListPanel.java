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
import com.geometrycloud.happydonut.Main;
import com.geometrycloud.happydonut.util.DatabaseUtils;
import com.geometrycloud.happydonut.util.UiUtils;

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Lista de objetos en el carrito de compras.
 *
 * @author Luis Chávez Bustamante
 */
public class CartListPanel extends JPanel implements ActionListener {

    // Lista de observadores.
    private final List<CartListener> listeners = new ArrayList<>();

    // Lista de objetos en el carrito.
    private RowList cartItems = null;

    /**
     * Constructor vacio.
     */
    public CartListPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        setLayout(new GridBagLayout());
    }

    /**
     * Agrega un nuevo observador.
     *
     * @param listener observador.
     */
    public void addCartListener(CartListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Elimina un observador.
     *
     * @param listener observador.
     */
    public void removeCartListener(CartListener listener) {
        listeners.remove(listener);
    }

    /**
     * Carga la informacion de la base de datos.
     */
    public void loadData() {
        cartItems = DATABASE.table(CART_TABLE_NAME).get();
        removeAll();
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(2, 2, 2, 2);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1;
        constraints.weighty = 0;

        int y = 0;
        for (Row cartItem : cartItems) {
            Row product = DATABASE.table(PRODUCTS_TABLE_NAME)
                    .where(PRODUCTS_PRIMARY_KEY, "=",
                            cartItem.value(CART_PRODUCT))
                    .first();
            String display = String.format("%s ($%.2f) x%d",
                    product.string(PRODUCTS_NAME),
                    product.decimal(PRODUCTS_PRICE),
                    cartItem.number(CART_QUANTITY));
            CartItem item = new CartItem(display, cartItem);
            y++;
            constraints.gridx = 0;
            constraints.gridy = y;
            if (y == cartItems.size()) {
                constraints.weighty = 1;
            }
            add(item, constraints);
        }

        UiUtils.repaint(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean confirm = UiUtils.confirm(
                message("warning.title"),
                message("warning.delete"), this);
        if (confirm) {
            Object cartItemId = e.getActionCommand();

            DATABASE.where(CART_PRIMARY_KEY, "=", cartItemId)
                    .delete(CART_TABLE_NAME);
            loadData();
            for (CartListener listener : listeners) {
                listener.onProductRemoved();
            }
        }
    }

    /**
     * Representa un objeto en el carrito.
     */
    public class CartItem extends JPanel {

        // etiqueta a mostrar.
        private final JLabel display = new JLabel();

        // Boton para remover el objeto del carrito.
        private final JButton remove = new JButton(message("remove"));

        // Objeto en la base de datos.
        private final Row item;

        /**
         * Constructor principal.
         *
         * @param display cadena a mostrar.
         * @param item objeto de la base de datos.
         */
        public CartItem(String display, Row item) {
            this.display.setText(display);
            this.item = item;
            initComponents();
        }

        /**
         * Inicializa los componentes.
         */
        private void initComponents() {
            display.setFont(Fonts.TEXT_FONT);

            remove.setFont(Fonts.TEXT_FONT);
            remove.setActionCommand(item.value(CART_PRIMARY_KEY).toString());
            remove.addActionListener(CartListPanel.this);

            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();

            constraints.insets.set(5, 5, 5, 5);

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(display, constraints);

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(remove, constraints);
        }
    }

    /**
     * Interface para la escucha de eventos del carrito.
     */
    public interface CartListener {

        /**
         * Se lanza cuando se remueve un producto del carrito.
         */
        void onProductRemoved();
    }

    public static void main(String... args) {
        RowList products = DATABASE.table(PRODUCTS_TABLE_NAME).get();
        for (Row product : products) {
            Object productId = product.value(PRODUCTS_PRIMARY_KEY);
            DATABASE.insert(CART_TABLE_NAME,
                    DatabaseUtils.columns(CART_PRODUCT, CART_QUANTITY),
                    productId, 1);
        }
        Main.loadLookAndFeel();
        CartListPanel cart = new CartListPanel();
        cart.loadData();
        UiUtils.launch("CART", cart);
    }
}
