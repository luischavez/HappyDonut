/* 
 * Copyright (C) 2015 GeometryCloud <http://www.geometrycloud.com>
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
import com.geometrycloud.happydonut.swing.FillablePanel;
import com.geometrycloud.happydonut.swing.ImagePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Formulario para los productos.
 *
 * @author Luis Chávez Bustamante
 */
public class ProductFormPanel extends FillablePanel {

    // Ancho de la imagen.
    public static final int IMAGE_WIDTH = 240;
    // Alto de la imagen.
    public static final int IMAGE_HEIGHT = 240;

    // Etiquetas.
    private final JLabel nameLabel = new JLabel("Nombre");
    private final JLabel descriptionLabel = new JLabel("Descripcion");
    private final JLabel ingredientsLabel = new JLabel("Ingredientes");
    private final JLabel priceLabel = new JLabel("Precio");
    private final JLabel stockLabel = new JLabel("Existencia");
    private final JLabel categoryLabel = new JLabel("Categoria");

    // Campo para la imagen.
    private final ImagePanel image = new ImagePanel();
    private final JButton searchImageButton = new JButton("Buscar..");

    // Campo para el nombre.
    private final JTextField name = new JTextField();

    // Campo para la descripcion.
    private final JTextField description = new JTextField();

    // Campo para los ingredientes.
    private final JTextField ingredients = new JTextField();

    // Campo para el precio.
    private final JTextField price = new JTextField();

    // Campo para las existencias.
    private final JTextField stock = new JTextField();

    // Listado de categorias.
    private final JComboBox<Object> category = new JComboBox<>();

    /**
     * Constructor por defecto.
     */
    public ProductFormPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(image, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(searchImageButton, constraints);

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(name, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(descriptionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(description, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(ingredientsLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(ingredients, constraints);
    }

    public static void main(String... args) {
        Main.loadLookAndFeel();
        ProductFormPanel form = new ProductFormPanel();
        UiUtils.form("Product form", form, null);
    }
}
