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
package com.geometrycloud.happydonut.swing;

/**
 * Accesor para manejar imagenes.
 *
 * @author Luis Chávez Bustamante
 */
public class ImagePanelAccesor extends Accesor<ImagePanel, byte[]> {

    @Override
    public byte[] get(ImagePanel object) {
        return object.getBytes();
    }

    @Override
    public void set(ImagePanel object, byte[] value) {
        object.setBytes(value);
    }
}
