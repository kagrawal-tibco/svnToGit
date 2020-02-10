package com.tibco.cep.query.stream.tuple;

import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.Flags;

/*
 * Author: Ashwin Jayaprakash Date: Feb 20, 2008 Time: 10:13:07 AM
 */

public class HeavyTuple implements Tuple {
    protected final Number id;

    protected Object[] columns;

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
    public HeavyTuple(Number id, Object[] columns) {
        this.id = id;

        if (columns != null) {
            setColumns(columns);
        }

        //todo Is this always required?
        this.timestamp = Clock.getCurrentTimeMillis();
    }

    /**
     * <p> For sub-Classes. </p> <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     */
    public HeavyTuple(Number id) {
        this(id, null);
    }

    //-----------

    public void incrementRefCount() {
    }

    /**
     * Invoke {@link #discard()} if the count reaches 0.
     */
    public void decrementRefCount() {
    }

    /**
     * @return {@link Integer#MAX_VALUE}.
     */
    protected int getRefCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * Does nothing if {@link #getRefCount()} is above 0.
     */
    protected void discard() {
        if (Flags.TRACK_TUPLE_REFS) {
            if (getRefCount() <= 0) {
                columns = null;
                timestamp = null;
            }
        }
    }

    //-----------

    public final Number getId() {
        return id;
    }

    public final int getTotalColumns() {
        return (columns != null) ? columns.length : 0;
    }

    /**
     * @return The columns as they are. Might even be {@link com.tibco.cep.query.stream.cache.SharedPointer}s.
     */
    public final Object[] getRawColumns() {
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
     * @return {@link com.tibco.cep.query.stream.cache.SharedPointer}s will be extracted and only
     *         their contents will be returned.
     */
    public Object getColumn(int index) {
        Object column = columns[index];

        if (column != null && column instanceof SharedPointer) {
            SharedPointer sharedPointer = (SharedPointer) column;
            column = sharedPointer.getObject();
        }

        return column;
    }

    public int getColumnAsInteger(int index) {
        Number n = (Number) getColumn(index);

        return n.intValue();
    }

    public long getColumnAsLong(int index) {
        Number n = (Number) getColumn(index);

        return n.longValue();
    }

    public float getColumnAsFloat(int index) {
        Number n = (Number) getColumn(index);

        return n.floatValue();
    }

    public double getColumnAsDouble(int index) {
        Number n = (Number) getColumn(index);

        return n.doubleValue();
    }

    public final Long getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Uses {@link #getId()} only.
     *
     * @param o
     * @return
     */
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HeavyTuple)) {
            return false;
        }

        HeavyTuple that = (HeavyTuple) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    /**
     * Uses {@link #getId()} only.
     *
     * @return
     */
    public final int hashCode() {
        return id.hashCode();
    }
}
