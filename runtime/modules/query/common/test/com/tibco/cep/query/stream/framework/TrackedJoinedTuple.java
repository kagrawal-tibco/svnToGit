package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Dec 19, 2007 Time: 5:10:30 PM
 */

public abstract class TrackedJoinedTuple extends TrackedTuple implements JoinedTuple {
    public TrackedJoinedTuple(Number id) {
        super(id);
    }

    public TrackedJoinedTuple(Number id, Object[] columns) {
        super(id, columns);
    }

    @Override
    public final void setColumns(Object[] newColumns) {
        Object[] oldColumns = columns;
        if (oldColumns != null) {
            for (int i = 0; i < oldColumns.length; i++) {
                if (oldColumns[i] != null) {
                    ((Tuple) oldColumns[i]).decrementRefCount();
                }
            }
        }

        columns = newColumns;

        if (newColumns != null) {
            for (int i = 0; i < newColumns.length; i++) {
                if (newColumns[i] != null) {
                    ((Tuple) newColumns[i]).incrementRefCount();
                }
            }
        }
    }

    @Override
    public Tuple getColumn(int index) {
        return (Tuple) super.getColumn(index);
    }

    @Override
    protected void discard() {
        if (getRefCount() <= 0) {
            if (columns != null) {
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i] != null) {
                        ((Tuple) columns[i]).decrementRefCount();
                    }
                }
            }
        }

        super.discard();
    }
}
