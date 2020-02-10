package com.tibco.cep.query.client.console.swing.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ksubrama
 */
public class QueryResultModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private String[] types;
    private String[] names;
    private String[] batchRecord;
    private final List<String[]> data;
    private AtomicInteger rowCount = new AtomicInteger(0);

    public QueryResultModel(String[] columns) {
        super();
        names = columns;
        types = new String[names.length];
        String stringClassName = String.class.getName();
        for (int i = 0; i < names.length; i++) {
            types[i] = stringClassName;
        }
        data = Collections.synchronizedList(new LinkedList<String[]>());
    }

    public void updateMetadata(String[] columnNames) {
        names = columnNames;
        types = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            types[i] = String.class.getName();
        }
        batchRecord = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            batchRecord[i] = "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    public String[] getHeader() {
        return names;
    }

    public void insertBatchRecord() {
        data.add(0, batchRecord);
        fireTableRowsInserted(0, 0);
    }

    public void addRows(List<String[]> rows) {
        for (String[] row : rows) {
            insertRowAtTop(row);
        }
        int size = rows.size();
        if (size != 0) {
            fireTableRowsInserted(0, size - 1);
        }
    }

    private String[] getModifiedRow(String[] row, int rowIndex) {
        String[] newRow = new String[row.length + 1];
        newRow[0] = String.valueOf(rowIndex);
        System.arraycopy(row, 0, newRow, 1, row.length);
        return newRow;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeAllRows() {
        int count = data.size();
        data.clear();
        rowCount.set(0);
        if (count != 0) {
            fireTableRowsDeleted(0, count - 1);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(data.size() < rowIndex + 1) {
            System.err.println("Possible error here.");
            System.err.println(new Throwable());
        }
        String[] columns = data.get(rowIndex);
        if (columns == null) {
            return "";
        }
        return columns[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String[] columns = data.get(rowIndex);
        columns[columnIndex] = String.valueOf(aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addRow(String[] row) {
        insertRowAtTop(row);
        fireTableRowsInserted(0, 0);
    }

    private void insertRowAtTop(String[] row) {
        int rowIndex = rowCount.incrementAndGet();
        String[] modifiedRow = getModifiedRow(row, rowIndex);
        data.add(0, modifiedRow);
    }
}
