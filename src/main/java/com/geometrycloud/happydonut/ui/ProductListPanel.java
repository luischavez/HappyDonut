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

import com.geometrycloud.happydonut.Fonts;
import com.geometrycloud.happydonut.swing.ImagePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Listado de productos de la base de datos, filtrados por la categoria
 * especificada.
 *
 * @author Luis Chavez Bustamante
 */
public class ProductListPanel extends JPanel implements MouseListener {

    // Listado de observadores.
    private final List<ProductSelectListener> listeners = new ArrayList<>();

    // Titulo del listado.
    private final JLabel title = new JLabel(message(PRODUCTS_TABLE_NAME));

    // Numero maximo de items por fila.
    private final int maxItemsPerRow;

    // Categoria de los productos.
    private Row category;

    // Listado de productos.
    private RowList products;

    /**
     * Constructor principal.
     *
     * @param maxItemsPerRow numero maximo de items por fila.
     */
    public ProductListPanel(int maxItemsPerRow) {
        this.maxItemsPerRow = maxItemsPerRow;
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        addMouseListener(this);

        title.setFont(Fonts.TITLE_FONT);

        setLayout(new GridBagLayout());
    }

    /**
     * Obtiene la categoria.
     *
     * @return categoria.
     */
    public Row getCategory() {
        return category;
    }

    /**
     * Establece la categoria.
     *
     * @param category categoria.
     */
    public void setCategory(Row category) {
        this.category = category;
    }

    /**
     * Carga la informacion desde la base de datos.
     */
    public void loadData() {
        if (null == category) {
            return;
        }
        products = DATABASE.table(PRODUCTS_TABLE_NAME)
                .where(PRODUCTS_CATEGORY, "=",
                        category.value(CATEGORIES_PRIMARY_KEY))
                .get();
        removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(title, constraints);
        constraints.gridwidth = 1;
        int x = 0;
        int y = 1;
        int count = 0;
        for (Row product : products) {
            if (maxItemsPerRow == count++) {
                x = 0;
                y++;
                count = 0;
            }
            ProductItem item = new ProductItem(product);
            constraints.gridx = x++;
            constraints.gridy = y;
            add(item, constraints);
        }
        UiUtils.repaint(this);
    }

    /**
     * Agrega un nuevo observador.
     *
     * @param listener observador.
     */
    public void addCategorySelectListener(ProductSelectListener listener) {
        listeners.add(listener);
    }

    /**
     * Elimina un observador.
     *
     * @param listener observador.
     */
    public void removeCategorySelectListener(ProductSelectListener listener) {
        listeners.remove(listener);
    }

    /**
     * Obtiene el item ubicado en el punto especificado.
     *
     * @param point punto.
     * @return item o null si no existe.
     */
    public ProductItem item(Point point) {
        Component component = getComponentAt(point);
        if (null != component && component instanceof ProductItem) {
            return ProductItem.class.cast(component);
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ProductItem item = item(e.getPoint());
        if (null != item) {
            for (ProductSelectListener listener : listeners) {
                listener.onSelectProduct(item.product);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Item que representa un producto.
     */
    public class ProductItem extends JPanel {

        // Imagen del producto.
        private final ImagePanel image = new ImagePanel();

        // Nombre del producto.
        private final JLabel name = new JLabel();

        // producto.
        private final Row product;

        /**
         * Constructor principal.
         *
         * @param product producto.
         */
        public ProductItem(Row product) {
            this.product = product;
            initComponents();
        }

        /**
         * Inicializa los componentes.
         */
        private void initComponents() {
            image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
            name.setFont(Fonts.TEXT_FONT);

            image.setBytes(product.bytes(PRODUCTS_IMAGE).array());
            name.setText(product.string(PRODUCTS_NAME));

            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.anchor = GridBagConstraints.CENTER;
            add(name, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(image, constraints);
        }
    }

    /**
     * Interface para la escucha de eventos de seleccion.
     */
    public interface ProductSelectListener {

        /**
         * Se lanza cuando ocurre una seleccion de producto.
         *
         * @param product producto.
         */
        void onSelectProduct(Row product);
    }
}
