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
import com.geometrycloud.happydonut.filter.MaxSizeFilter;
import com.geometrycloud.happydonut.swing.Fillable;
import com.geometrycloud.happydonut.swing.FillablePanel;
import com.geometrycloud.happydonut.swing.ImagePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import org.apache.commons.io.IOUtils;

/**
 * Formulario para la tabla de categorias.
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryFormPanel extends FillablePanel implements ActionListener {

    // Tamano maximo del campo nombre.
    public static final int MAX_NAME_LENGTH = 128;

    /*
     * Etiquetas.
     */
    private final JLabel nameLabel = new JLabel("Nombre");

    // Boton para la busqueda de imagenes.
    private final JButton searchImageButton = new JButton("Buscar..");

    // Campo imagen.
    @Fillable(name = "image")
    private final ImagePanel image = new ImagePanel();

    // Campo nombre.
    @Fillable(name = "name")
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

        AbstractDocument document = (AbstractDocument) name.getDocument();
        document.setDocumentFilter(new MaxSizeFilter(MAX_NAME_LENGTH));

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

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
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
            }
        }
    }

    public static void main(String... args) throws FileNotFoundException, IOException {
        Main.loadLookAndFeel();
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Juan");
        values.put("image",
                IOUtils.toByteArray(
                        new FileInputStream(new File("c:/avatar.jpg"))));
        CategoryFormPanel form = new CategoryFormPanel();
        form.fill(values);
        Map<String, Object> map = UiUtils.form("Category Form", form, null);
        if (null != map) {
            map.forEach((k, v) -> System.out.printf("%s = %s\n", k, v));
        }
    }
}
