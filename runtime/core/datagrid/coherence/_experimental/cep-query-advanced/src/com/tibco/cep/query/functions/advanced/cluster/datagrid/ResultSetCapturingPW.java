package com.tibco.cep.query.functions.advanced.cluster.datagrid;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
* Author: Ashwin Jayaprakash / Date: 3/16/11 / Time: 12:21 PM
*/
public class ResultSetCapturingPW extends PrintWriter {
    protected LinkedList<Object[]> rows = new LinkedList<Object[]>();

    protected ArrayList<Object> currentRow = new ArrayList<Object>();

    public ResultSetCapturingPW(Writer out) {
        super(out);
    }

    public ResultSetCapturingPW(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public ResultSetCapturingPW(OutputStream out) {
        super(out);
    }

    public ResultSetCapturingPW(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public ResultSetCapturingPW(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public ResultSetCapturingPW(String fileName, String csn)
            throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public ResultSetCapturingPW(File file) throws FileNotFoundException {
        super(file);
    }

    public ResultSetCapturingPW(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    protected void closeRow() {
        if (!currentRow.isEmpty()) {
            rows.add(currentRow.toArray());
            currentRow.clear();
        }
    }

    public List<Object[]> getRows() {
        closeRow();

        return rows;
    }

    //-----------


    @Override
    public void close() {
        super.close();

        closeRow();
    }

    @Override
    public void print(boolean b) {
        currentRow.add(b);
    }

    @Override
    public void print(char c) {
        currentRow.add(c);
    }

    @Override
    public void print(int i) {
        currentRow.add(i);
    }

    @Override
    public void print(long l) {
        currentRow.add(l);
    }

    @Override
    public void print(float f) {
        currentRow.add(f);
    }

    @Override
    public void print(double d) {
        currentRow.add(d);
    }

    @Override
    public void print(char[] s) {
        currentRow.add(new String(s));
    }

    @Override
    public void print(String s) {
        currentRow.add(s);
    }

    @Override
    public void print(Object obj) {
        currentRow.add(obj);
    }

    @Override
    public void println() {
        closeRow();
    }

    @Override
    public void println(boolean x) {
        print(x);
        println();
    }

    @Override
    public void println(char x) {
        print(x);
        println();
    }

    @Override
    public void println(int x) {
        print(x);
        println();
    }

    @Override
    public void println(long x) {
        print(x);
        println();
    }

    @Override
    public void println(float x) {
        print(x);
        println();
    }

    @Override
    public void println(double x) {
        print(x);
        println();
    }

    @Override
    public void println(char[] x) {
        print(x);
        println();
    }

    @Override
    public void println(String x) {
        print(x);
        println();
    }

    @Override
    public void println(Object x) {
        print(x);
        println();
    }
}
