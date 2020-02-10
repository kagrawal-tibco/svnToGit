package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.cache.SharedPointer;

/*
 * Author: Ashwin Jayaprakash Date: Nov 16, 2007 Time: 5:21:21 PM
 */

public class ReteEntityHandle implements GenericReteEntityHandle {
    protected final Long reteId;

    protected final Class reteClass;

    protected final ReteEntityHandleType type;

    /**
     * Non-<code>null</code> if {@link #type} is {@link ReteEntityHandleType#NEW}.
     */
    protected volatile SharedPointer sharedPointer;

    protected volatile boolean warm;

    /**
     * @param reteClass
     * @param reteId
     * @param status
     */
    public ReteEntityHandle(Class reteClass, Long reteId, ReteEntityHandleType status) {
        this.reteClass = reteClass;
        this.reteId = reteId;
        this.type = status;
    }

    public ReteEntityHandleType getType() {
        return type;
    }

    public Long getReteId() {
        return reteId;
    }

    public Class getReteClass() {
        return reteClass;
    }

    public SharedPointer getSharedPointer() {
        return sharedPointer;
    }

    public void setSharedPointer(SharedPointer sharedPointer) {
        this.sharedPointer = sharedPointer;
    }

    public boolean isWarm() {
        return warm;
    }

    public void warmUp() {
        warm = true;
    }

    public void coolDown() {
        warm = false;
    }
}