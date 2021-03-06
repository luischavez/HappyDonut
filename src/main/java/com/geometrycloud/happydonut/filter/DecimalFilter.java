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
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class DecimalFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {
        if (string.matches("^\\d+\\.\\d+$")
                || string.matches("^\\.$")
                || string.matches("^\\d+$")) {
            Document document = fb.getDocument();
            String actualText = document.getText(0, document.getLength());
            String append = string;
            if (actualText.contains(".") && append.contains(".")) {
                return;
            }
            if (actualText.endsWith(".0")) {
                offset--;
            } else if (append.endsWith(".")) {
                append = append.concat("0");
            }
            fb.insertString(offset, append, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {
        if (text.matches("^\\d+\\.\\d+$")
                || text.matches("^\\.$")
                || text.matches("^\\d+$")) {
            Document document = fb.getDocument();
            String actualText = document.getText(0, document.getLength());
            String append = text;
            if (actualText.contains(".") && append.contains(".")) {
                return;
            }
            if (actualText.endsWith(".0")) {
                offset--;
                length++;
            } else if (append.endsWith(".")) {
                append = append.concat("0");
            }
            fb.replace(offset, length, append, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document document = fb.getDocument();
        if (0 < offset) {
            String prev = document.getText(offset - 1, 1);
            if (prev.equals(".")) {
                offset--;
                length++;
            }
        }
        fb.remove(offset, length);
    }
}
