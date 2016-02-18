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

import com.geometrycloud.happydonut.swing.DatabaseTableModel;
import com.geometrycloud.happydonut.swing.FormPanel;
import com.geometrycloud.happydonut.swing.JTableButton;
import com.geometrycloud.happydonut.util.DatabaseUtils;
import com.geometrycloud.happydonut.util.UiUtils;

import com.github.luischavez.database.query.Query;
import com.github.luischavez.database.link.Row;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static com.geometrycloud.happydonut.Context.*;

/**
 * Panel para la admninistracion de los modelos.
 *
 * @author Luis Chávez Bustamante
 */
public class ModelPanel extends JPanel implements ActionListener, KeyListener {

    // Nombre de la tabla en la base de datos.
    private final String tableName;

    // Nombre de la llave primaria.
    private final String primaryKeyName;

    // Campos a mostrar en la tabla.
    private final String[] displayFields;

    // Campos requeridos.
    private final String[] requiredFields;

    // Clase del formulario del modelo.
    private final Class<? extends FormPanel> formPanelClass;

    // Indica si el formulario agregara los campos de created_at y updated_at.
    private final boolean timestamps;

    /* Etiquetas. */
    private final JLabel filterLabel = new JLabel(message("filter"));

    // Boton para agregar un nuevo modelo.
    private final JButton addButton = new JButton(message("add"));

    // Listado de columnas a filtrar.
    private final JComboBox<String> filterColumn = new JComboBox<>();

    // Campo de texto para filtrar los resultados.
    private final JTextField filter = new JTextField();

    // Tabla donde se listan los modelos.
    private final JTable table = new JTable();

    // Scroll para la tabla de modelos.
    private final JScrollPane scroll = new JScrollPane(table);

    // Modelo de la tabla.
    private DatabaseTableModel model = null;

    /**
     * Constructor principal.
     *
     * @param tableName nombre de la tabla.
     * @param primaryKeyName nombre de la llave primaria.
     * @param displayFields campos a mostrar.
     * @param requiredFields campos requeridos.
     * @param formPanelClass clase del formulario.
     * @param timestamps indica si se usaran campos de fecha.
     */
    public ModelPanel(String tableName, String primaryKeyName,
            String[] displayFields, String[] requiredFields,
            Class<? extends FormPanel> formPanelClass,
            boolean timestamps) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.displayFields = displayFields;
        this.requiredFields = requiredFields;
        this.formPanelClass = formPanelClass;
        this.timestamps = timestamps;
        initComponents();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        filter.addKeyListener(this);
        addButton.addActionListener(this);

        for (String displayField : displayFields) {
            filterColumn.addItem(message(displayField));
        }

        model = new DatabaseTableModel(
                DATABASE.table(tableName),
                displayFields);
        model.setExtra(new String[]{
            message("edit"), message("delete")});
        model.loadData();

        table.setModel(model);

        table.getTableHeader().setReorderingAllowed(false);

        JTableButton.apply(table, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Integer.valueOf(e.getActionCommand());
                Row row = model.row(rowIndex);
                FormPanel form = createForm();
                form.fill(DatabaseUtils.toMap(row));
                Map<String, Object> map
                        = UiUtils.form(message("edit"), form,
                                ModelPanel.this, requiredFields);
                if (null != map) {
                    if (timestamps) {
                        map.put("updated_at", LocalDateTime.now());
                    }
                    DATABASE.where(primaryKeyName, "=",
                            row.value(primaryKeyName))
                            .update(tableName,
                                    DatabaseUtils.columns(map),
                                    DatabaseUtils.values(map));
                    model.loadData();
                    UiUtils.repaint(table);
                }
            }
        }, model.indexOfExtra(1));
        JTableButton.apply(table, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Integer.valueOf(e.getActionCommand());
                if (UiUtils.confirm(
                        message("warning.title"),
                        message("warning.delete"),
                        ModelPanel.this)) {
                    Row row = model.row(rowIndex);
                    DATABASE.where(primaryKeyName, "=",
                            row.value(primaryKeyName))
                            .delete(tableName);
                    model.loadData();
                    UiUtils.repaint(table);
                }
            }
        }, model.indexOfExtra(2));

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridwidth = 1;
        add(filterLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(filterColumn, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(filter, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add(addButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(scroll, constraints);
    }

    /**
     * Crea una nueva instancia del formulario.
     *
     * @return nueva instancia del formulario.
     */
    private FormPanel createForm() {
        try {
            return formPanelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addButton == e.getSource()) {
            FormPanel form = createForm();
            Map<String, Object> map
                    = UiUtils.form(message("add"), form, this, requiredFields);
            if (null != map) {
                if (timestamps) {
                    map.put("created_at", LocalDateTime.now());
                    map.put("updated_at", map.get("created_at"));
                }
                DATABASE.insert(tableName,
                        DatabaseUtils.columns(map),
                        DatabaseUtils.values(map));
                model.loadData();
                UiUtils.repaint(table);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String column = displayFields[filterColumn.getSelectedIndex()];
        String find = filter.getText().toLowerCase();
        Query query = DATABASE.table(tableName);
        if (!find.isEmpty()) {
            query.where(
                    String.format("lower(%s)", column), "like",
                    String.format("%%%s%%", find));
        }
        model.setQuery(query);
        model.loadData();
        UiUtils.repaint(table);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
