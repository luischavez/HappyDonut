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

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
public class ProductPickerPanel extends JPanel
        implements CategoryListPanel.CategorySelectListener,
        ProductListPanel.ProductSelectListener {

    // Numero maximo de item por fila.
    public static final int MAX_ITEMS_PER_ROW = 4;

    // Panel contenedor de las categorias y los productos.
    private final JScrollPane scroll = new JScrollPane();

    // Panel con la lista de categorias.
    private final CategoryListPanel categoryListPanel
            = new CategoryListPanel(MAX_ITEMS_PER_ROW);

    // Panel con la lista de productos.
    private final ProductListPanel productListPanel
            = new ProductListPanel(MAX_ITEMS_PER_ROW);

    /**
     * Constructor vacio.
     */
    public ProductPickerPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        categoryListPanel.loadData();

        categoryListPanel.addCategorySelectListener(this);
        productListPanel.addCategorySelectListener(this);

        scroll.setViewportView(categoryListPanel);
        Dimension size = scroll.getPreferredSize();
        size.width += IMAGE_WIDTH / 2;
        size.height += IMAGE_HEIGHT / 2;
        scroll.setPreferredSize(size);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        add(scroll, constraints);
    }

    @Override
    public void onSelectCategory(Row category) {
        System.out.printf("Categoria: %s\n", category.string(CATEGORIES_NAME));
        RowList products = DATABASE.table(PRODUCTS_TABLE_NAME)
                .where(PRODUCTS_CATEGORY, "=",
                        category.value(CATEGORIES_PRIMARY_KEY))
                .get();
        if (products.empty()) {
            UiUtils.warning(
                    message("warning.title"),
                    message("warning.empty"), this);
        } else {
            // TODO: HANDLE CART
            productListPanel.setCategory(category);
            productListPanel.loadData();
            scroll.setViewportView(productListPanel);
            UiUtils.repaint(this);
        }
    }

    @Override
    public void onSelectProduct(Row product) {
        System.out.printf("Producto: %s\n", product.string(PRODUCTS_NAME));
        scroll.setViewportView(categoryListPanel);
        UiUtils.repaint(this);
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        UiUtils.launch("MAIN", new ProductPickerPanel());
    }
}
