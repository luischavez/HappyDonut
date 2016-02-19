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
package com.geometrycloud.happydonut.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class IntegerFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {
        if (string.matches("^\\d+$")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {
        if (text.matches("^\\d+$")) {
            super.insertString(fb, offset, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        super.remove(fb, offset, length);
    }
}
