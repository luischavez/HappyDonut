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

import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.io.IOUtils;

/**
 * Panel con la capacidad de contener una imagen.
 *
 * @author Luis Chávez Bustamante
 */
public class ImagePanel extends JPanel {

    // Arreglo con los datos de la imagen.
    private byte[] bytes;

    // Imagen cargada en memoria.
    private BufferedImage image;

    /**
     * Constructor vacio, se utiliza cuando no existe una imagen previa que
     * cargar.
     */
    public ImagePanel() {
    }

    /**
     * Constructor para cargar una imagen por defecto.
     *
     * @param bytes arreglo con los datos de la imagen.
     */
    public ImagePanel(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Obtiene la informacion de la imagen.
     *
     * @return arreglo con los datos de la imagen.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Establece una nueva imagen en el panel.
     *
     * @param bytes arreglo con la informacion de la imagen.
     */
    public void setBytes(byte[] bytes) {
        if (null == bytes || 0 == bytes.length) {
            this.bytes = null;
            image = null;
            return;
        }
        this.bytes = bytes;
        image = toImage();
        UiUtils.repaint(this);
    }

    /**
     * Obtiene la imagen actual.
     *
     * @return imagen actual.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Obtiene una imagen a partir del arreglo cargado.
     *
     * @return imagen en memoria.
     */
    public BufferedImage toImage() {
        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            throw new InvalidImageException("cant decode image", ex);
        }
        return image;
    }

    /**
     * Carga una imagen a partir de un archivo.
     *
     * @param file archivo.
     */
    public void load(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            setBytes(byteArray);
        } catch (IOException ex) {
            throw new InvalidImageException("cant load image from file", ex);
        }
    }

    @Override
    public int getWidth() {
        if (null != image) {
            return image.getWidth();
        }
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        if (null != image) {
            return image.getHeight();
        }
        return super.getHeight();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public Border getBorder() {
        return new EtchedBorder(EtchedBorder.LOWERED);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (null != image) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
