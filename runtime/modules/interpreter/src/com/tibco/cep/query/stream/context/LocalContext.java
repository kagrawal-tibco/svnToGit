package com.tibco.cep.query.stream.context;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 11:48:57 AM
 */

public final class LocalContext {
    protected final boolean nullMode;

    protected CustomCollection<? extends Tuple> newTuples;

    protected CustomCollection<? extends Tuple> deadTuples;

    /**
     * Same as {@link #LocalContext(boolean)} with <code>false</code>.
     */
    public LocalContext() {
        this(false);
    }

    /**
     * @param nullMode If <code>true</code>, then this context cannot be modified.
     */
    public LocalContext(boolean nullMode) {
        this.nullMode = nullMode;
    }

    public boolean isNullMode() {
        return nullMode;
    }

    /**
     * @return Can be <code>null</code>. If not, this collection must not be modified in place.
     */
    public CustomCollection<? extends Tuple> getDeadTuples() {
        return deadTuples;
    }

    public void setDeadTuples(CustomCollection<? extends Tuple> deadTuples) {
        if (nullMode) {
            return;
        }

        this.deadTuples = deadTuples;
    }

    /**
     * @return Can be <code>null</code>. If not, this collection must not be modified in place.
     */
    public CustomCollection<? extends Tuple> getNewTuples() {
        return newTuples;
    }

    public void setNewTuples(CustomCollection<? extends Tuple> newTuples) {
        if (nullMode) {
            return;
        }

        this.newTuples = newTuples;
    }

    /**
     * Must be invoked only by the owner. Does not clear the collections. It just sets the
     * references to <code>null</code>.
     */
    public void clear() {
        newTuples = null;
        deadTuples = null;
    }
}
