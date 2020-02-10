package com.tibco.cep.query.api;

public interface QueryResultSet {
    /**
     * Closes this ResultSet.
     */
    void close() throws Exception;


    /**
     * Finds the index of the column with the given name.
     *
     * @param name String name of a column.
     * @return the index of the column with the given name.
     */
    int findColumn(String name);


    /**
     * Gets the Object value of the column at the given index.
     *
     * @param columnIndex int index of the column value to return.
     * @return Object value of the column at the given index.
     * @throws IndexOutOfBoundsException if the index is not valid.
     */
    Object getObject(int columnIndex) throws IndexOutOfBoundsException;

    /**
     * Gets the row count if possible (>= 0). Otherwise -1.
     *
     * @return
     */
    int getRowCountIfPossible();

    /**
     * Gets the QueryStatement owning this object, if it is known, else null.
     *
     * @return QueryStatement owning this object, if it is known, else null.
     */
    QueryStatement getStatement();


    /**
     * Checks if the cursor is located on a batch end instead of a row.
     *
     * @return boolean true if the cursor is located on a batch end instead of a row.
     */
    boolean isBatchEnd();


    /**
     * Moves the cursor to the next row (or batch end).
     *
     * @return boolean true if the new current row (or batch end) is valid; false if there are no
     *         more rows (or batch ends).
     */
    boolean next();
}
 
