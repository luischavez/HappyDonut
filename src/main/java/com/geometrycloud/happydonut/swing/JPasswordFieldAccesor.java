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

import javax.swing.JPasswordField;

/**
 * Accesor para los campos de tipo password.
 *
 * @author Luis Chávez Bustamante
 */
public class JPasswordFieldAccesor extends Accesor<JPasswordField, String> {

    @Override
    public String get(JPasswordField object) {
        return object.getText();
    }

    @Override
    public void set(JPasswordField object, String value) {
        object.setText(value);
    }

    @Override
    public Object cast(Object object) {
        return object.toString();
    }
}
