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

import com.geometrycloud.happydonut.Main;
import com.geometrycloud.happydonut.filter.MaxSizeFilter;
import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FormPanel;
import com.geometrycloud.happydonut.swing.ImagePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.Dimension;
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
import javax.swing.text.AbstractDocument;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Formulario para la tabla de categorias.
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryFormPanel extends FormPanel implements ActionListener {

    /* Etiquetas. */
    private final JLabel nameLabel = new JLabel(message("add"));

    // Boton para la busqueda de imagenes.
    private final JButton searchImageButton = new JButton(message("search"));

    // Campo imagen.
    @Fillable(name = CATEGORIES_IMAGE)
    private final ImagePanel image = new ImagePanel();

    // Campo nombre.
    @Fillable(name = CATEGORIES_NAME)
    private final JTextField name = new JTextField();

    /**
     * Contructor por defecto.
     */
    public CategoryFormPanel() {
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        searchImageButton.addActionListener(this);

        image.setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

        AbstractDocument document = (AbstractDocument) name.getDocument();
        document.setDocumentFilter(
                new MaxSizeFilter(CATEGORY_NAME_SIZE));

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (searchImageButton == e.getSource()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(
                    new FileNameExtensionFilter("Image Files", "jpg"));
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

    public static void main(String... args) {
        Main.loadLookAndFeel();
        CategoryFormPanel form = new CategoryFormPanel();
        UiUtils.form("Category Form", form, null, CATEGORIES_REQUIRED_FIELDS);
    }
}
