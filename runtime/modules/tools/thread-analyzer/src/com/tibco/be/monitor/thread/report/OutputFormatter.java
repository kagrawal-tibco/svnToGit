/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 20, 2009 / Time: 11:47:05 AM
 */
public class OutputFormatter {

    private OutputFormatter() {
    }

    static final String getHeaderString(List<String> entries, int length) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        printHeaderString(entries, length, printStream);
        return outStream.toString();
    }

    static final void printHeaderString(List<String> entries,
            int padding, PrintStream stream) {
        int length = 0;
        for (String entry : entries) {
            length = (length < entry.length() ? entry.length() : length);
        }
        length += padding;
        String headerLine = getHeaderLine(length + 2);
        stream.printf("%s", headerLine);
        for (String entry : entries) {
            stream.printf("%" + length + "s%n",
                    formatCenter(entry, length, true));
        }
        stream.printf("%s%n", headerLine);
        stream.flush();
    }

    final static String formatCenter(String string, int len, boolean lineMarker) {
        if(lineMarker) {
            len -= 2;
        }
        // find the remaining length
        int remainLen = len - (string.length() + 2);
        // Divide the remaining length into two.
        int leftLen = remainLen / 2;
        int rightLen = remainLen - leftLen;
        StringBuilder str = new StringBuilder();
        if(lineMarker) {
            str.append("|");
        }
        str.append(padSpace(string, leftLen, true));
        str.append(padSpace("", rightLen, true));
        if(lineMarker) {
            str.append("|");
        }
        return str.toString();
    }

    final static void printHeaderLine(PrintStream stream, int length) {
        StringBuilder str = new StringBuilder("+");
        for (int i = 0; i < length - 2; i++) {
            str.append("-");
        }
        str.append("+");
        stream.printf("%s%n", str.toString());
    }

    final static String getHeaderLine(int length) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        printHeaderLine(printStream, length);
        return outStream.toString();
    }

    final static void createHeaderLine(PrintStream stream, int length) {
        StringBuilder str = new StringBuilder("+");
        for (int i = 0; i < length - 2; i++) {
            str.append("-");
        }
        str.append("+");
        stream.printf("%s%n", str.toString());
    }

    final static String padSpace(String str, int len, boolean left) {
        StringBuilder paddedStr = new StringBuilder();
        for(int i = 0; i < len; i++) {
            paddedStr.insert(0, " ");
        }
        if (left) {
            return String.format(paddedStr + "%s", str);
        } else {
            return String.format("%s" + paddedStr, str);
        }
    }

    final static String getString(String str, int len) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < len; i++) {
            string.append(str);
        }
        return string.toString();
    }
}
