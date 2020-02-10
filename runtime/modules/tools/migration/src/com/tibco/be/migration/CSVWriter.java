package com.tibco.be.migration;

import java.io.IOException;

/*
 * Author: Ashwin Jayaprakash Date: Feb 25, 2008 Time: 1:36:34 PM
 */

/**
 * Interface to generate CSV Files.
 */
public interface CSVWriter {
    public CSVWriter write(String s) throws IOException;

    public CSVWriter writeln() throws IOException;

    /**
     * Write string followed by a new-line.
     * 
     * @param s
     * @return
     * @throws IOException
     */
    public CSVWriter writeln(String s) throws IOException;

    public CSVWriter writeln(String[] line) throws IOException;

    /**
     * Write string as a comment followed by a new-line.
     * 
     * @param text
     * @return
     * @throws IOException
     */
    public CSVWriter writeCommentln(String s) throws IOException;

    public CSVWriter write(char c) throws IOException;

    public CSVWriter write(int i) throws IOException;

    public CSVWriter write(short s) throws IOException;

    public CSVWriter write(boolean b) throws IOException;

    public CSVWriter write(long l) throws IOException;

    public CSVWriter write(float f) throws IOException;

    public CSVWriter write(double d) throws IOException;

    public CSVWriter write(Object j) throws IOException;

    /**
     * Close and discard all resources.
     * 
     * @return Generate report of the generation.
     * @throws IOException
     */
    public Report close() throws IOException;
}
