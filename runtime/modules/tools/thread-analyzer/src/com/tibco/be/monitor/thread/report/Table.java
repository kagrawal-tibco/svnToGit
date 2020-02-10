/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: Karthikeyan Subramanian / Date: Dec 8, 2009 / Time: 4:20:59 PM
 * @author ksubrama
 */
public class Table {

    private Map<Integer, List<String>> tableValues =
            new HashMap<Integer, List<String>>();
    private List<Integer> sizes;
    private final int columns;
    private int rowCount;
    private String name;
    private final List<Row> rows = new LinkedList<Row>();
    private Row header;

    public Table(String name, int columns) {
        this.name = name;
        this.columns = columns;
        this.sizes = new ArrayList<Integer>(columns);
        // initialize the columns & max values
        for (int i = 0; i < columns; i++) {
            tableValues.put(i, new LinkedList<String>());
            this.sizes.add(0);
        }
    }

    public Table(String name, String[] header, int columns) throws TableException {
        this(name, columns);
        setHeader(header);
    }

    public void setHeader(String... entries) throws TableException {
        if (entries.length != columns) {
            throw new TableException("Column size mismatch.");
        }
        header = getRow(entries);
        header.setCenter(true);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Row getHeader() {
        return header;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void addRow(String... entries) throws TableException {
        if (entries.length != columns) {
            throw new TableException("Column size mismatch.");
        }
        rowCount++;
        Row row = getRow(entries);
        rows.add(row);
    }

    public final void printTable(PrintStream stream) throws Exception {
        printTable(stream, true, true);
    }

    public final void printTable(PrintStream stream, boolean firstLine, boolean lastLine)
            throws Exception {
        // print header line.
        int max = 0;
        for (int value : sizes) {
            max += value;
        }
        max = (max + (columns * 4)) - (columns - 1);
        if (name != null) {
            if (max < (name.length() + 4)) {
                max = name.length() + 4;
            }
        }
        String headerLine = OutputFormatter.getHeaderLine(max);
        max = headerLine.length();
        if(firstLine) {
            stream.printf("%s", headerLine);
        }
        if (name != null) {
            stream.printf("%s%n", OutputFormatter.formatCenter(name, max, true));
            stream.printf("%s", headerLine);
        }
        if(header != null) {
            header.print(stream, sizes);
            stream.printf("%n");
            printHeaderSeperator(stream, sizes);
            stream.printf("%n");
        }
        for (Row row : rows) {
            row.print(stream, sizes);
            stream.printf("%n");
        }
        if(lastLine) {
            stream.printf("%s", headerLine);
        }
        stream.flush();
    }

    private Row getRow(String... entries) throws TableException {
        int i = 0;
        List<String> values = new ArrayList<String>(columns);
        for (String entry : entries) {
            tableValues.get(i).add(entry);
            int max = sizes.get(i);
            // adjust the max value.
            if (entry.length() > max) {
                sizes.set(i, entry.length());
            }
            i++;
            values.add(entry);
        }
        return new Row(columns, values);
    }

    private void printHeaderSeperator(PrintStream stream, List<Integer> sizes) {
        for (int i = 0; i < sizes.size(); i++) {
            int maxValue = sizes.get(i);
            String value = OutputFormatter.getString("-", maxValue);
            if (i == 0) {
                stream.printf("| %-" + maxValue + "s |", value);
            } else {
                stream.printf(" %-" + maxValue + "s |", value);
            }
        }
        stream.flush();
    }

    public class Row {

        private final int columns;
        private final List<String> values;
        private boolean center;

        public Row(int columns) {
            this.columns = columns;
            values = new ArrayList<String>(columns);
        }

        public Row(int columns, List<String> values) throws TableException {
            this.columns = columns;
            this.values = new ArrayList<String>(columns);
            setValues(values);
        }

        public void setCenter(boolean isCenter) {
            this.center = isCenter;
        }

        public void setValues(List<String> values) throws TableException {
            if (values.size() != columns) {
                throw new TableException("Columns mismatch.");
            }
            this.values.clear();
            this.values.addAll(values);
        }

        public List<String> getValues() {
            return values;
        }

        public void print(PrintStream stream, List<Integer> size) throws TableException {
            if (size.size() != columns) {
                throw new TableException("Column mismatch");
            }
            for (int j = 0; j < columns; j++) {
                String value = values.get(j);
                if(center) {
                    value = OutputFormatter.formatCenter(value, size.get(j), false);
                }
                printValue(j, stream, size.get(j), value);
            }
            stream.flush();
        }

        private void printValue(int j, PrintStream stream, Integer size, String value) {
            if (j == 0) {
                stream.printf("| %-" + size + "s |", value);
            } else {
                stream.printf(" %-" + size + "s |", value);
            }
        }
    }
}
