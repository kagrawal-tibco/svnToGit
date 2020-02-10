package com.tibco.cep.query.stream.tuple;

import java.util.HashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 2:05:01 PM
 */

public abstract class AbstractTupleInfo implements TupleInfo {
    /**
     * Order is important. See {@link #columnTypes}.
     */
    protected final String[] columnAliases;

    protected final Class[] columnTypes;

    protected final HashMap<String, Integer> columnAliasPositions;

    /**
     * The Tuple sub-class that holds the column values for the Tuple.
     */
    protected final Class<? extends Tuple> containerClass;

    /**
     * @param containerClass
     * @param columnNames
     * @param columnTypes
     */
    public AbstractTupleInfo(Class<? extends Tuple> containerClass, String[] columnNames,
                             Class[] columnTypes) {
        this.containerClass = containerClass;
        this.columnAliases = columnNames;
        this.columnTypes = columnTypes;

        // Use twice the size of the array length to avoid collisions.
        this.columnAliasPositions = new HashMap<String, Integer>(this.columnAliases.length * 2);
        for (int i = 0; i < this.columnAliases.length; i++) {
            this.columnAliasPositions.put(this.columnAliases[i], i);
        }
    }

    public Class<? extends Tuple> getContainerClass() {
        return containerClass;
    }

    public final Integer getColumnPosition(String alias) {
        return columnAliasPositions.get(alias);
    }

    public final String[] getColumnAliases() {
        return columnAliases;
    }

    public final Class[] getColumnTypes() {
        return columnTypes;
    }
}