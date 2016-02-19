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

import com.geometrycloud.happydonut.Context;
import com.geometrycloud.happydonut.swing.DatabaseComboBox;
import com.geometrycloud.happydonut.swing.DatabaseComboBoxModel;
import com.geometrycloud.happydonut.filter.DecimalFilter;
import com.geometrycloud.happydonut.filter.IntegerFilter;
import com.geometrycloud.happydonut.filter.MaxSizeFilter;
import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FormPanel;
import com.geometrycloud.happydonut.swing.ImagePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Formulario para los productos.
 *
 * @author Luis Chávez Bustamante
 */
public class ProductFormPanel extends FormPanel implements ActionListener {

    /* Etiquetas. */
    private final JLabel nameLabel = new JLabel(message("name"));
    private final JLabel descriptionLabel = new JLabel(message("description"));
    private final JLabel ingredientsLabel = new JLabel(message("ingredients"));
    private final JLabel priceLabel = new JLabel(message("price"));
    private final JLabel stockLabel = new JLabel(message("stock"));
    private final JLabel categoryLabel = new JLabel(message("category"));

    // Campo para la imagen.
    @Fillable(name = PRODUCTS_IMAGE)
    private final ImagePanel image = new ImagePanel();
    private final JButton searchImageButton = new JButton(message("search"));

    // Campo para el nombre.
    @Fillable(name = PRODUCTS_NAME)
    private final JTextField name = new JTextField();

    // Campo para la descripcion.
    @Fillable(name = PRODUCTS_DESCRIPTION)
    private final JTextField description = new JTextField();

    // Campo para los ingredientes.
    @Fillable(name = PRODUCTS_INGREDIENTS)
    private final JTextField ingredients = new JTextField();

    // Campo para el precio.
    @Fillable(name = PRODUCTS_PRICE)
    private final JTextField price = new JTextField();

    // Campo para las existencias.
    @Fillable(name = PRODUCTS_STOCK)
    private final JTextField stock = new JTextField();

    // Listado de categorias.
    @Fillable(name = PRODUCTS_CATEGORY)
    private final DatabaseComboBox category = new DatabaseComboBox();

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
        searchImageButton.addActionListener(this);

        setFilter(name.getDocument(), new MaxSizeFilter(PRODUCTS_NAME_SIZE));
        setFilter(price.getDocument(), new DecimalFilter());
        setFilter(stock.getDocument(), new IntegerFilter());

        DatabaseComboBoxModel model
                = new DatabaseComboBoxModel(CATEGORIES_PRIMARY_KEY, CATEGORIES_NAME,
                        Context.DATABASE.table(CATEGORIES_TABLE_NAME));
        model.loadData();

        category.setModel(model);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridwidth = GridBagConstraints.REMAINDER;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(image, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(searchImageButton, constraints);

        constraints.gridwidth = 1;
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

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(priceLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(price, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(stockLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(stock, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(categoryLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(category, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (searchImageButton == e.getSource()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(
                    new FileNameExtensionFilter("Image Files", "jpg", "png"));
            int option = chooser.showOpenDialog(this);
            if (JFileChooser.APPROVE_OPTION == option) {
                File file = chooser.getSelectedFile();
                image.load(file);
                if (IMAGE_WIDTH < image.getImage().getWidth()
                        || IMAGE_HEIGHT < image.getImage().getHeight()) {
                    image.setBytes(null);
                    UiUtils.warning(
                            message("warning.title"),
                            message("warning.image"), this);
                }
            }
        }
    }
}
