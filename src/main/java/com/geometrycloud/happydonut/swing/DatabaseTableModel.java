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

    @Override
    public int getRowCount() {
        return null != rows ? rows.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return null != displayFields ? displayFields.length : 0;
    }

    @Override
    public String getColumnName(int column) {
        return displayFields[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return row(0).value(getColumnName(columnIndex)).getClass();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return row(row).value(getColumnName(column));
    }
}
