package com.tibco.cep.query.client.console.swing.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ksubrama
 */
public class PropertiesTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null || value.getClass().equals(HashMap.class) == false) {
            return table.getDefaultRenderer(value != null ? value.getClass() : String.class).
                    getTableCellRendererComponent(table, value == null? "" : value,
                    isSelected, hasFocus, row, column);
        }
        @SuppressWarnings("unchecked")
        Map<String, String> properties = (Map<String, String>) value;
        if(properties.size() == 0) {
            return new JLabel();
        }
        // Calculate row height
        int rowHeight = getRowHeight(table, properties);
        table.setRowHeight(row, rowHeight);
        JTable innerTable = new JTable(new DefaultTableModel(getData(properties),
                new String[]{"Name", "Value"}), getColumnModel());
        innerTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        innerTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(innerTable);
        scrollPane.validate();
        return scrollPane;
    }

    private int getRowHeight(JTable table, Map<String, String> properties) {
        int rowHeight = table.getRowHeight();
        if (rowHeight == -1) {
            rowHeight = 16;
        }
        return rowHeight * ( 2 + properties.size());
    }

    private Object[][] getData(Map<String, String> properties) {
        Object[][] data = new Object[properties.size()][2];
        int i = 0;
        for (String key : properties.keySet()) {
            data[i][0] = key;
            data[i++][1] = properties.get(key);
        }
        return data;
    }

    private JTableHeader getTableHeader() {
        JTableHeader header = new JTableHeader(getColumnModel());
        return header;
    }

    private TableColumnModel getColumnModel() {
        DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
        // Name Column
        TableColumn nameColumn = new TableColumn();
        nameColumn.setHeaderValue("Name");
        nameColumn.setModelIndex(0);
        nameColumn.setResizable(true);
        columnModel.addColumn(nameColumn);
        // Value Column
        TableColumn valueColumn = new TableColumn();
        valueColumn.setHeaderValue("Value");
        valueColumn.setModelIndex(1);
        valueColumn.setResizable(true);
        columnModel.addColumn(valueColumn);
        return columnModel;
    }
}
