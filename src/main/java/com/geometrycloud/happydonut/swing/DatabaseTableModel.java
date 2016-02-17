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

import com.geometrycloud.happydonut.Context;

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;
import com.github.luischavez.database.query.Query;

import javax.swing.table.DefaultTableModel;

/**
 * Modelo de tabla para los elementos de la base de datos.
 *
 * @author Luis Chávez Bustamante
 */
public class DatabaseTableModel extends DefaultTableModel {

    // Consulta desde donde se obtendra la informacion de la tabla.
    private Query query;

    // Campos a mostrar.
    private String[] displayFields;

    // Campos adicionales.
    private String[] extra = null;

    // Resultados de la consulta.
    private RowList rows = null;

    /**
     * Constructor vacio.
     */
    public DatabaseTableModel() {
    }

    /**
     * Constructor principal.
     *
     * @param query consulta.
     * @param displayFields campos a mostrar.
     */
    public DatabaseTableModel(Query query, String[] displayFields) {
        this.query = query;
        this.displayFields = displayFields;
    }

    /**
     * Obtiene la consulta actual.
     *
     * @return consulta actual.
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Establece la consulta.
     *
     * @param query consulta.
     */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * Obtiene los campos a mostrar.
     *
     * @return campos a mostrar.
     */
    public String[] getDisplayFields() {
        return displayFields;
    }

    /**
     * Establece los campos a mostrar.
     *
     * @param displayFields campos a mostrar.
     */
    public void setDisplayFields(String[] displayFields) {
        this.displayFields = displayFields;
    }

    /**
     * Obtiene los campos adicionales.
     *
     * @return campos adicionales.
     */
    public String[] getExtra() {
        return extra;
    }

    /**
     * Establece los campos adicionales.
     *
     * @param extra campos adicionales.
     */
    public void setExtra(String[] extra) {
        this.extra = extra;
    }

    /**
     * Obtiene el indice donde comienzan las columnas adicionales.
     *
     * @param i indice de la columna.
     * @return indice de las columnas adicionales.
     */
    public int indexOfExtra(int i) {
        return displayFields.length - 1 + i;
    }

    /**
     * Carga la informacion de la base de datos.
     */
    public void loadData() {
        rows = query.get();
    }

    /**
     * Obtiene el resultado especificado.
     *
     * @param index indice del resultado.
     * @return resultado.
     */
    public Row row(int index) {
        return rows.getRow(index);
    }

    /**
     * Obtiene el nombre original de la columna.
     *
     * @param column indice de la columna.
     * @return nombre original.
     */
    public String getOriginalColumnName(int column) {
        if (displayFields.length <= column) {
            return "";
        }
        return displayFields[column];
    }

    @Override
    public int getRowCount() {
        return null != rows ? rows.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return null != displayFields ? displayFields.length
                + (null != extra ? extra.length : 0) : 0;
    }

    @Override
    public String getColumnName(int column) {
        if (displayFields.length <= column) {
            return "";
        }
        return Context.message(displayFields[column]);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (displayFields.length <= columnIndex) {
            return String.class;
        }
        return row(0).value(getOriginalColumnName(columnIndex)).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return displayFields.length <= column;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (displayFields.length <= column) {
            return extra[column - displayFields.length];
        }
        return row(row).value(getOriginalColumnName(column));
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
    }
}
