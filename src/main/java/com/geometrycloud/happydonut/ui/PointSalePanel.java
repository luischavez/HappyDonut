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

import com.geometrycloud.happydonut.util.DatabaseUtils;
import com.geometrycloud.happydonut.util.UiUtils;

import com.github.luischavez.database.link.Affecting;
import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Ventana principal de la aplicacion, desde aqui se podran realizar las ventas
 * de productos.
 *
 * @author Luis Chavez Bustamante
 */
public class PointSalePanel extends JPanel
        implements ProductListPanel.ProductSelectListener,
        CartListPanel.CartListener,
        CheckoutPanel.CheckoutListener,
        ModelPanel.ModelListener {

    // Numero maximo de item por fila.
    public static final int MAX_ITEMS_PER_ROW = 8;

    // Panel contenedor de las categorias y los productos.
    private final JScrollPane pickerScroll = new JScrollPane();

    // Panel contenedor del carrito.
    private final JScrollPane cartScroll = new JScrollPane();

    // Panel con la lista de productos.
    private final ProductListPanel productListPanel
            = new ProductListPanel(MAX_ITEMS_PER_ROW);

    // Panel con la informacion del carrito.
    private final CartListPanel cartListPanel = new CartListPanel();

    // Panel con la informacion del total del carrito.
    private final CheckoutPanel checkoutPanel = new CheckoutPanel();

    /**
     * Constructor vacio.
     */
    public PointSalePanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        ModelPanel.MODEL_LISTENERS.add(this);
        productListPanel.loadData();
        cartListPanel.loadData();
        checkoutPanel.loadData();

        productListPanel.addCategorySelectListener(this);
        cartListPanel.addCartListener(this);
        checkoutPanel.addCheckoutListener(this);

        pickerScroll.setViewportView(productListPanel);
        Dimension pickerSize = pickerScroll.getPreferredSize();
        pickerSize.width += IMAGE_WIDTH * MAX_ITEMS_PER_ROW;
        pickerSize.height += IMAGE_HEIGHT * 4;
        pickerScroll.setPreferredSize(pickerSize);

        cartScroll.setViewportView(cartListPanel);
        Dimension cartSize = cartScroll.getPreferredSize();
        cartSize.width = 200;
        cartScroll.setPreferredSize(cartSize);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        add(pickerScroll, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(checkoutPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        add(cartScroll, constraints);
    }

    private void refreshAll() {
        productListPanel.loadData();
        cartListPanel.loadData();
        checkoutPanel.loadData();
        UiUtils.repaint(this);
    }

    @Override
    public void onSelectProduct(Row product) {
        InputNumberForm input = new InputNumberForm(
                message("quantity"),
                message("quantity.description"), false);
        Object productId = product.value(PRODUCTS_PRIMARY_KEY);
        Long stock = product.number(PRODUCTS_STOCK);
        Row cartItem = DATABASE.table(CART_TABLE_NAME)
                .where(CART_PRODUCT, "=", productId)
                .first();
        Long alreadyAdded = null != cartItem ? cartItem.number(CART_QUANTITY) : 0;
        input.getNumPad().setMax(stock - alreadyAdded);
        boolean confirm = UiUtils.plain(message("quantity"), input, this);
        if (confirm) {
            double quantity = input.result();
            if (1 > quantity) {
                return;
            }
            if (null == cartItem) {
                DATABASE.insert(CART_TABLE_NAME,
                        DatabaseUtils.columns(CART_PRODUCT, CART_QUANTITY),
                        productId, quantity);
            } else {
                DATABASE.where(CART_PRIMARY_KEY, "=",
                        cartItem.number(CART_PRIMARY_KEY))
                        .update(CART_TABLE_NAME,
                                CART_QUANTITY, alreadyAdded + quantity);
            }
            cartListPanel.loadData();
            checkoutPanel.loadData();
        }
        productListPanel.loadData();
        UiUtils.repaint(this);
    }

    @Override
    public void onProductRemoved() {
        checkoutPanel.loadData();
    }

    @Override
    public void onCheckout() {
        RowList cartItems = DATABASE.table(CART_TABLE_NAME).get();
        Affecting insert = DATABASE.insert(SALES_TABLE_NAME,
                SALES_SALE_DATE,
                LocalDateTime.now());
        Object saleId = insert.getGeneratedKeys()[0];
        for (Row cartItem : cartItems) {
            Row product = DATABASE.table(PRODUCTS_TABLE_NAME)
                    .where(PRODUCTS_PRIMARY_KEY, "=",
                            cartItem.value(CART_PRODUCT))
                    .first();
            String name = product.string(PRODUCTS_NAME);
            BigDecimal price = product.decimal(PRODUCTS_PRICE);
            Long quantity = cartItem.number(CART_QUANTITY);
            DATABASE.insert(SALE_DETAILS_TABLE_NAME,
                    DatabaseUtils.columns(SALE_DETAILS_NAME, SALE_DETAILS_PRICE,
                            SALE_DETAILS_QUANTITY, SALE_DETAILS_SALE),
                    name, price, quantity, saleId);
        }
        DATABASE.delete(CART_TABLE_NAME);
        cartListPanel.loadData();
        checkoutPanel.loadData();
    }

    @Override
    public void onInsert() {
        refreshAll();
    }

    @Override
    public void onUpdate() {
        refreshAll();
    }

    @Override
    public void onDelete() {
        refreshAll();
    }
}
