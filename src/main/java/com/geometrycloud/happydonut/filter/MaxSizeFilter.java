/* 
 * Copyright (C) 2015 GeometryCloud <http://www.geometrycloud.com>
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
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Filtro para limitar el tamano maximo de un campo.
 *
 * @author Luis Chávez Bustamante
 */
public class MaxSizeFilter extends DocumentFilter {

    // Tamano maximo del campo.
    private final int max;

    /**
     * Constructor del filtro.
     *
     * @param max tamano maximo.
     */
    public MaxSizeFilter(int max) {
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset,
            String string, AttributeSet attr) throws BadLocationException {
        Document document = fb.getDocument();
        if (max >= document.getLength() + string.length()) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        Document document = fb.getDocument();
        if (max >= document.getLength() + text.length()) {
            super.insertString(fb, offset, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }
}
