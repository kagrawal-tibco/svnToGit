package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryTypeInfo;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 9, 2009 Time: 2:05:15 PM
*/
public class LiteReteEntity implements Tuple, ReteEntity, SharedPointer {
    protected final Entity entity;

    protected final SharedObjectSource sharedObjectSource;

    /**
     * This is the default timestamp. May or may not be used by the Kernel.
     */
    protected Long timestamp;

    public LiteReteEntity(Entity e, SharedObjectSource sharedObjectSource) {
        this.entity = e;
        this.sharedObjectSource = sharedObjectSource;
        this.timestamp = Clock.getCurrentTimeMillis();
    }

    //----------

    public void setPointer(SharedPointer pointer) {
        //Do nothing. We are the pointer.
    }

    public SharedPointer getPointer() {
        return this;
    }

    public final Long getReteId() {
        return entity.getId();
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
        return getReteId();
    }

    public final int getTotalColumns() {
        return 1;
    }

    /**
     * @return The columns as they are. Might even be {@link com.tibco.cep.query.stream.cache.SharedPointer}s.
     */
    public final Object[] getRawColumns() {
        return new Object[]{this};
    }

    public void setColumns(Object[] columns) {
        //Ignore.
    }

    public Object getColumn(int index) {
        return entity;
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

    //----------

    public SharedObjectSource getSource() {
        return sharedObjectSource;
    }

    public Number getKey() {
        return getId();
    }

    public Object getObject() {
        if (sharedObjectSource instanceof QueryTypeInfo) {
            ((QueryTypeInfo) sharedObjectSource).handleDownloadedEntity(entity, entity.getId());
        }

        return entity;
    }

    //----------

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiteReteEntity)) {
            return false;
        }

        LiteReteEntity that = (LiteReteEntity) o;

        if (!entity.equals(that.entity)) {
            return false;
        }

        return true;
    }

    public final int hashCode() {
        return entity.hashCode();
    }
}
