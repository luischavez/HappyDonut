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
 * Listado de categorias de la base de datos.
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryListPanel extends JPanel implements MouseListener {

    // Numero maximo de item por fila.
    public static final int MAX_ITEM_PER_ROW = 4;

    // Listado de observadores.
    private final List<CategorySelectListener> listeners = new ArrayList<>();

    private RowList categories = null;

    /**
     * Constructor vacio.
     */
    public CategoryListPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        addMouseListener(this);

        setLayout(new GridBagLayout());
    }

    /**
     * Carga la informacion desde la base de datos.
     */
    public void loadData() {
        categories = DATABASE.table(CATEGORIES_TABLE_NAME).get();
        removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        int x = 0;
        int y = 0;
        int count = 0;
        for (Row category : categories) {
            if (MAX_ITEM_PER_ROW == count++) {
                x = 0;
                y++;
                count = 0;
            }
            CategoryItem item = new CategoryItem(category);
            constraints.gridx = x++;
            constraints.gridy = y;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weightx = 1;
            constraints.weighty = 1;
            add(item, constraints);
        }
        UiUtils.repaint(this);
    }

    /**
     * Agrega un nuevo observador.
     *
     * @param listener observador.
     */
    public void addCategorySelectListener(CategorySelectListener listener) {
        listeners.add(listener);
    }

    /**
     * Elimina un observador.
     *
     * @param listener observador.
     */
    public void removeCategorySelectListener(CategorySelectListener listener) {
        listeners.remove(listener);
    }

    /**
     * Obtiene el item ubicado en el punto especificado.
     *
     * @param point punto.
     * @return item o null si no existe.
     */
    public CategoryItem item(Point point) {
        Component component = getComponentAt(point);
        if (null != component && component instanceof CategoryItem) {
            return CategoryItem.class.cast(component);
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CategoryItem item = item(e.getPoint());
        if (null != item) {
            for (CategorySelectListener listener : listeners) {
                listener.onSelectCategory(item.category);
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
     * Item que representa una categoria.
     */
    public class CategoryItem extends JPanel {

        // Imagen de la categoria.
        private final ImagePanel image = new ImagePanel();

        // Nombre de la categoria.
        private final JLabel name = new JLabel();

        // Categoria.
        private final Row category;

        /**
         * Constructor principal.
         *
         * @param category categoria.
         */
        public CategoryItem(Row category) {
            this.category = category;
            initComponents();
        }

        /**
         * Inicializa los componentes.
         */
        private void initComponents() {
            image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);

            image.setBytes(category.bytes(CATEGORIES_IMAGE).array());
            name.setText(category.string(CATEGORIES_NAME));

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
    public interface CategorySelectListener {

        /**
         * Se lanza cuando ocurre una seleccion de categoria.
         *
         * @param category categoria.
         */
        void onSelectCategory(Row category);
    }
}
