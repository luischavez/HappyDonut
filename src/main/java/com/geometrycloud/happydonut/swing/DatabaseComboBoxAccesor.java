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

/**
 * Accesor para los campos desplegables de la base de datos.
 *
 * @author Luis Chávez Bustamante
 */
public class DatabaseComboBoxAccesor
        extends Accesor<DatabaseComboBox, DatabaseComboBoxModel.Item> {

    @Override
    public DatabaseComboBoxModel.Item get(DatabaseComboBox object) {
        return DatabaseComboBoxModel.Item.class.cast(object.getSelectedItem());
    }

    @Override
    public void set(DatabaseComboBox object,
            DatabaseComboBoxModel.Item value) {
        int count = object.getItemCount();
        for (int i = 0; i < count; i++) {
            DatabaseComboBoxModel.Item item = object.getItemAt(i);
            if (item.id.equals(value.id)) {
                object.setSelectedIndex(i);
            }
        }
    }

    @Override
    public Object cast(Object object) {
        return new DatabaseComboBoxModel.Item(object, null, null);
    }
}
