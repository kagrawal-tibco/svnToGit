package com.tibco.cep.query.stream.tuple;

import com.tibco.cep.query.stream.misc.Clock;

/*
 * Author: Ashwin Jayaprakash Date: Feb 19, 2008 Time: 6:27:47 PM
 */

public class LiteTuple implements Tuple {
    protected Object[] columns;

    protected final Number id;

    /**
     * This is the default timestamp. May or may not be used by the Kernel.
     */
    protected Long timestamp;

    /**
     * <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     * @param columns Can be <code>null</code>.
     */
    public LiteTuple(Number id, Object[] columns) {
        this.id = id;
        if (columns != null) {
            setColumns(columns);
        }
        this.timestamp = Clock.getCurrentTimeMillis();
    }

    /**
     * <p> For sub-Classes. </p> <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     */
    public LiteTuple(Number id) {
        this(id, null);
    }

    public Number getId() {
        return id;
    }

    public int getTotalColumns() {
        return (columns != null) ? columns.length : 0;
    }

    /**
     * @return The columns as they are.
     */
    public Object[] getRawColumns() {
        return columns;
    }

    /**
     * @param columns
     */
    public void setColumns(Object[] columns) {
        this.columns = columns;
    }

    /**
     * @param index
     * @return
     */
    public Object getColumn(int index) {
        return columns[index];
    }

    public int getColumnAsInteger(int index) {
        return (Integer) columns[index];
    }

    public long getColumnAsLong(int index) {
        return (Long) columns[index];
    }

    public float getColumnAsFloat(int index) {
        return (Float) columns[index];
    }

    public double getColumnAsDouble(int index) {
        return (Double) columns[index];
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void decrementRefCount() {
    }

    public void incrementRefCount() {
    }

    /**
     * Uses {@link #getId()}.
     *
     * @param o
     * @return
     */
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiteTuple)) {
            return false;
        }

        LiteTuple liteTuple = (LiteTuple) o;

        if (!id.equals(liteTuple.id)) {
            return false;
        }

        return true;
    }

    /**
     * Uses {@link #getId()}.
     *
     * @return
     */
    public final int hashCode() {
        return id.hashCode();
    }
}
