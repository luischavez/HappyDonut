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
package com.geometrycloud.happydonut.ui;

import com.geometrycloud.happydonut.swing.FillablePanel;
import com.geometrycloud.happydonut.util.UiUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextField;

/**
 *
 * @author Luis Chavez Bustamante
 */
public class CategoryFormPanel extends FillablePanel {

    private final JTextField name = new JTextField();

    public CategoryFormPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 1;
        constraints.weightx = 1;
        add(name, constraints);
    }

    public static void main(String... args) {
        UiUtils.launch("Category Form", new CategoryFormPanel());
    }
}
