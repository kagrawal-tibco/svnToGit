package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Feb 20, 2008 Time: 1:11:35 PM
 */

public class HeavyJoinedTuple extends HeavyTuple implements JoinedTuple {
    /**
     * <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     * @param columns Can be <code>null</code>.
     */
    public HeavyJoinedTuple(Number id, Object[] columns) {
        super(id, columns);
    }

    /**
     * <p> For sub-Classes. </p> <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     */
    public HeavyJoinedTuple(Number id) {
        this(id, null);
    }

    /**
     * <p> {@inheritDoc} </p> <p> Elements have to be {@link Tuple}s. </p>
     */
    public final void setColumns(Object[] newColumns) {
        if (Flags.TRACK_TUPLE_REFS) {
            Object[] oldColumns = columns;
            if (oldColumns != null) {
                for (int i = 0; i < oldColumns.length; i++) {
                    if (oldColumns[i] != null) {
                        ((Tuple) oldColumns[i]).decrementRefCount();
                    }
                }
            }
        }

        columns = newColumns;

        if (Flags.TRACK_TUPLE_REFS) {
            if (newColumns != null) {
                for (int i = 0; i < newColumns.length; i++) {
                    if (newColumns[i] != null) {
                        ((Tuple) newColumns[i]).incrementRefCount();
                    }
                }
            }
        }
    }

    @Override
    public final Tuple getColumn(int index) {
        return (Tuple) super.getColumn(index);
    }


    /**
     * @throw java.lang.UnsupportedOperationException
     */
    @Override
    public final int getColumnAsInteger(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    @Override
    public final long getColumnAsLong(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    @Override
    public final float getColumnAsFloat(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    @Override
    public final double getColumnAsDouble(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void discard() {
        if (Flags.TRACK_TUPLE_REFS) {
            if (getRefCount() <= 0) {
                if (columns != null) {
                    for (int i = 0; i < columns.length; i++) {
                        if (columns[i] != null) {
                            ((Tuple) columns[i]).decrementRefCount();
                        }
                    }
                }
            }
        }

        super.discard();
    }
}
