package com.tibco.be.migration;

/*
 * Author: Ashwin Jayaprakash Date: Feb 25, 2008 Time: 1:16:29 PM
 */

/**
 * <p>
 * Use this class to read all the related CSV file fragments.
 * </p>
 * <p>
 * The programming model is similar to the <code>java.util.Enumeration</code>
 * class.
 * 
 * <pre>
 * CSVReader reader = new &lt;Reader implementation&gt;
 * 
 * String[] columns = null;
 * while( (columns = reader.nextRow()) != null ){
 *      //Do something .. 
 *      columns[0].. ..
 *      ...
 * }
 * reader.close();
 * </pre>
 * 
 * </p>
 */
public interface CSVReader {
    /**
     * @return Array of columns if a new row was available. <code>null</code>
     *         to mark the end of the read sequence - "Reached the end".
     * @throws Exception
     */
    public String[] nextRow() throws Exception;

    public void close() throws Exception;
}
