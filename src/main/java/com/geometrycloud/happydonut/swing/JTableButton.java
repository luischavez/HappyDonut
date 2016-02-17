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
package com.geometrycloud.happydonut.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * Permite agregar botones dentro de las tablas.
 *
 * @author Luis Chávez Bustamante
 */
public class JTableButton extends AbstractCellEditor implements
        TableCellRenderer, TableCellEditor, ActionListener, MouseListener {

    // Instancia de la tabla.
    private final JTable table;

    // Boton para mostrar.
    private final JButton renderButton;

    // Boton para editar.
    private final JButton editButton;

    // Borde original.
    private final Border originalBorder;

    // Borde de seleccion.
    private Border focusBorder;

    // Accion a realizar.
    private Action action;

    // Valor del editor.
    private Object editorValue;

    // Indica si el boton se esta editando.
    private boolean isButtonColumnEditor;

    /**
     * Constructor principal.
     *
     * @param table instancia de la tabla.
     * @param action accion a realizar.
     * @param column columna.
     */
    private JTableButton(JTable table, Action action) {
        this.table = table;
        this.action = action;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
        originalBorder = editButton.getBorder();
    }

    /**
     * Obtiene el borde del boton cuando esta seleccionado.
     *
     * @return borde.
     */
    public Border getFocusBorder() {
        return focusBorder;
    }

    /**
     * Establece el borde del boton cuando esta seleccionado.
     *
     * @param focusBorder borde.
     */
    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder(focusBorder);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null) {
            editButton.setText("");
            editButton.setIcon(null);
        } else if (value instanceof Icon) {
            editButton.setText("");
            editButton.setIcon((Icon) value);
        } else {
            editButton.setText(value.toString());
            editButton.setIcon(null);
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus) {
            renderButton.setBorder(focusBorder);
        } else {
            renderButton.setBorder(originalBorder);
        }

        if (value == null) {
            renderButton.setText("");
            renderButton.setIcon(null);
        } else if (value instanceof Icon) {
            renderButton.setText("");
            renderButton.setIcon((Icon) value);
        } else {
            renderButton.setText(value.toString());
            renderButton.setIcon(null);
        }

        return renderButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        fireEditingStopped();

        ActionEvent event = new ActionEvent(table,
                ActionEvent.ACTION_PERFORMED, "" + row);
        action.actionPerformed(event);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (table.isEditing() && table.getCellEditor() == this) {
            isButtonColumnEditor = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnEditor && table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        isButtonColumnEditor = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Agrega el boton a la tabla.
     *
     * @param table tabla.
     * @param action accion a realizar.
     * @param column columna de la tabla.
     * @return boton.
     */
    public static JTableButton apply(JTable table, Action action, int column) {
        JTableButton button = new JTableButton(table, action);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(button);
        columnModel.getColumn(column).setCellEditor(button);
        table.addMouseListener(button);
        return button;
    }
}
