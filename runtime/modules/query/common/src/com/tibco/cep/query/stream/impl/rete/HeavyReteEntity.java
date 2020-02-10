package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Feb 20, 2008 Time: 6:33:50 PM
 */

public class HeavyReteEntity implements Tuple, ReteEntity {
    protected final Number id;

    protected SharedPointer pointer;

    /**
     * This is the default timestamp. May or may not be used by the Kernel.
     */
    protected Long timestamp;

    /**
     * @param id <p> Default {@link #timestamp} is the time of instantiation. </p>
     */
    public HeavyReteEntity(Number id) {
        this.id = id;
        this.timestamp = Clock.getCurrentTimeMillis();
    }

    //----------

    public void setPointer(SharedPointer pointer) {
        this.pointer = pointer;
    }

    public SharedPointer getPointer() {
        return pointer;
    }

    public final Long getReteId() {
        Long theId = (Long) pointer.getKey();

        return theId;
    }

    public final void setReteId(Long reteId) {
        //Do nothing. Already present in the pointer.
    }

    //----------


    public final void incrementRefCount() {
    }

    public final void decrementRefCount() {
    }

    public final Number getId() {
        return id;
    }

    public final int getTotalColumns() {
        return 1;
    }

    /**
     * @return The columns as they are. Might even be {@link com.tibco.cep.query.stream.cache.SharedPointer}s.
     */
    public final Object[] getRawColumns() {
        return new Object[]{pointer};
    }

    /**
     * Use {@link #setPointer(com.tibco.cep.query.stream.cache.SharedPointer)} instead.
     *
     * @param columns Picks the 0th element and expects it to be a {@link com.tibco.cep.query.stream.cache.SharedPointer}.
     */
    public void setColumns(Object[] columns) {
        this.pointer = (SharedPointer) columns[0];
    }

    /**
     * @param index Ignored.
     * @return {@link com.tibco.cep.query.stream.cache.SharedPointer}s will be extracted and only
     *         their contents will be returned.
     */
    public Object getColumn(int index) {
        return pointer.getObject();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    public final int getColumnAsInteger(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    public final long getColumnAsLong(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    public final float getColumnAsFloat(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throw java.lang.UnsupportedOperationException
     */
    public final double getColumnAsDouble(int index) {
        throw new UnsupportedOperationException();
    }

    public final Long getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    protected final void discard() {
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
        if (!(o instanceof HeavyReteEntity)) {
            return false;
        }

        HeavyReteEntity that = (HeavyReteEntity) o;

        if (!id.equals(that.id)) {
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
