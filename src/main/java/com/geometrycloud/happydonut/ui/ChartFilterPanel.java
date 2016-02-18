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

import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import static com.geometrycloud.happydonut.Context.*;
import static com.geometrycloud.happydonut.database.DatabaseConstants.*;

/**
 * Panel para filtrar el top de ventas por fechas.
 *
 * @author Luis Chavez Bustamante
 */
public class ChartFilterPanel extends JPanel {

    // Fabrica para generar campos de fecha.
    private final JDateComponentFactory datePickerFactory
            = new JDateComponentFactory();

    /* Etiquetas */
    private final JLabel fromLabel = new JLabel(message("from"));
    private final JLabel toLabel = new JLabel(message("to"));

    // Campo para filtrar desde.
    private final JDatePanel fromPicker
            = datePickerFactory.createJDatePicker();

    // Campo para filtrar hasta.
    private final JDatePanel toPicker
            = datePickerFactory.createJDatePicker();

    // Grafico con el top de productos vendidos.
    private ChartPanel chart = null;

    /**
     * Constructor vacio.
     */
    public ChartFilterPanel() {
        initComponents();
        createChart();
    }

    /**
     * Inicializa los componentes.
     */
    private void initComponents() {
        chart = new ChartPanel(createChart());

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets.set(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridwidth = 1;
        add(fromLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add((JComponent) fromPicker, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridwidth = 1;
        add(toLabel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        add((JComponent) toPicker, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(chart, constraints);
    }

    /**
     * Carga la informacion para generar la grafica con el top de ventas.
     *
     * @param from desde donde buscar.
     * @param to hasta donde buscar.
     * @return informacion de la grafica.
     */
    private PieDataset chartDataset(LocalDateTime from, LocalDateTime to) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        RowList sales = DATABASE.table(SALES_TABLE_NAME)
                .where(SALES_SALE_DATE, ">=", from)
                .where(SALES_SALE_DATE, "<=", to)
                .get(SALES_PRIMARY_KEY, SALES_SALE_DATE);
        HashMap<String, Long> top = new HashMap<>();
        for (Row sale : sales) {
            RowList details = DATABASE.table(SALE_DETAILS_TABLE_NAME)
                    .where(SALE_DETAILS_SALE, "=",
                            sale.value(SALES_PRIMARY_KEY))
                    .get(SALE_DETAILS_NAME, SALE_DETAILS_QUANTITY);
            String name;
            Long quantity;
            for (Row detail : details) {
                name = detail.string(SALE_DETAILS_NAME);
                quantity = detail.number(SALE_DETAILS_QUANTITY);
                if (top.containsKey(name)) {
                    quantity += top.get(name);
                }
                top.put(name, quantity);
            }
        }
        top.forEach((k, v) -> dataset.setValue(k, v));
        return dataset;
    }

    /**
     * Crea un nuevo grafico con la informacion del top de ventas.
     *
     * @return grafico.
     */
    private JFreeChart createChart() {
        LocalDateTime from = null, to = null;
        return ChartFactory
                .createPieChart(message("TOP"), chartDataset(from, to));
    }
}
