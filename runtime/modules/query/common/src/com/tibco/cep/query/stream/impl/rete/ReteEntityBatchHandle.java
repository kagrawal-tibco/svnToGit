package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.cache.SharedPointer;

/*
 * Author: Ashwin Jayaprakash Date: Mar 14, 2008 Time: 2:33:49 PM
 */

public class ReteEntityBatchHandle implements GenericReteEntityHandle {
    protected Class reteClass;

    protected ReteEntityHandleType type;

    protected volatile Long[] reteIds;

    protected volatile SharedPointer[] sharedPointers;

    protected volatile boolean warm;

    /**
     * @param reteClass
     * @param type
     * @param reteIds        Can be <code>null</code> of "sharedPointers" is not.
     * @param sharedPointers Can be <code>null</code> if "reteIds" is not.
     */
    protected ReteEntityBatchHandle(Class reteClass, ReteEntityHandleType type, Long[] reteIds,
                                    SharedPointer[] sharedPointers) {
        this.reteClass = reteClass;
        this.type = type;
        this.reteIds = reteIds;
        this.sharedPointers = sharedPointers;
    }

    public Class getReteClass() {
        return reteClass;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Long[] getReteIds() {
        return reteIds;
    }

    public ReteEntityHandleType getType() {
        return type;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public SharedPointer[] getSharedPointers() {
        return sharedPointers;
    }

    /**
     * If some one has already done the pointers, then replace it for the rest of the users to avoid
     * rework.
     *
     * @param pointers
     */
    public void replaceIdsWithPointers(SharedPointer[] pointers) {
        this.sharedPointers = pointers;
        this.reteIds = null;
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

    //------------

    /**
     * @param reteClass
     * @param reteIds
     * @return
     * @see #replaceIdsWithPointers(com.tibco.cep.query.stream.cache.SharedPointer[])
     */
    public static ReteEntityBatchHandle createAsTypeNew(Class reteClass, Long[] reteIds) {
        return new ReteEntityBatchHandle(reteClass, ReteEntityHandleType.NEW, reteIds, null);
    }

    public static ReteEntityBatchHandle createAsTypeNew(Class reteClass,
                                                        SharedPointer[] sharedPointers) {
        return new ReteEntityBatchHandle(reteClass, ReteEntityHandleType.NEW, null, sharedPointers);
    }

    public static ReteEntityBatchHandle createAsTypeDeletion(Class reteClass, Long[] reteIds) {
        return new ReteEntityBatchHandle(reteClass, ReteEntityHandleType.DELETION, reteIds, null);
    }
}
