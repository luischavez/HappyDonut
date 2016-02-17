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

import javax.swing.DefaultComboBoxModel;

/**
 * Modelo para los campos desplegables, la informacion se obtiene de una
 * consulta de la base de datos.
 *
 * @author Luis Chávez Bustamante
 */
public class DatabaseComboBoxModel
        extends DefaultComboBoxModel<DatabaseComboBoxModel.Item> {

    // Nombre del campo de la llave primaria.
    private String keyName;

    // Nombre de la la columna a mostrar.
    private String displayColumn;

    // Consulta.
    private Query query;

    /**
     * Constructor principal.
     *
     * @param keyName nombre del campo de la llave primaria.
     * @param displayColumn nombre de la columna a mostrar.
     * @param query consulta.
     */
    public DatabaseComboBoxModel(String keyName, String displayColumn, Query query) {
        this.keyName = keyName;
        this.displayColumn = displayColumn;
        this.query = query;
    }

    /**
     * Obtiene el nombre de la llave primaria.
     *
     * @return nombre de la llave primaria.
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Establece el nombre de la llave primaria.
     *
     * @param keyName nombre de la llave primaria.
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * Obtiene el nombre de la columna a mostrar.
     *
     * @return nombre de la columna a mostrar.
     */
    public String getDisplayColumn() {
        return displayColumn;
    }

    /**
     * Establece el nombre de la columna a mostrar.
     *
     * @param displayColumn nombre de la columna a mostrar.
     */
    public void setDisplayColumn(String displayColumn) {
        this.displayColumn = displayColumn;
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
     * Establece la consulta que se utilizara para obtener los resultados.
     *
     * @param query consulta.
     */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * Carga la informacion de la base de datos.
     */
    public void loadData() {
        RowList rows = query.get();
        for (Row row : rows) {
            addElement(
                    new Item(row.value(keyName),
                            row.value(displayColumn).toString(),
                            row));
        }
    }

    /**
     * Representa un objeto de la base de datos.
     */
    public static class Item {

        // Identificador.
        public final Object id;

        // Campo a mostrar.
        public final String display;

        // Objeto original.
        public final Object binding;

        /**
         * Constructor por defecto.
         *
         * @param id identificador.
         * @param display campo a mostrar.
         * @param binding objeto original.
         */
        public Item(Object id, String display, Object binding) {
            this.id = id;
            this.display = display;
            this.binding = binding;
        }

        @Override
        public String toString() {
            return display;
        }
    }
}
