package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Feb 13, 2008 Time: 11:31:46 AM
 */

public class ReteEntityWithdrawableSourceImpl extends WithdrawableSourceImpl implements
        ReteEntityWithdrawableSource {
    protected final Class reteEntityClass;

    protected final ReteEntityFilter reteEntityFilter;

    /**
     * @param id
     * @param outputInfo
     * @param reteEntityClass The Concept/Event/POJO that comes from the "main" Rete which will get
     *                        wrapped in the {@link ReteEntityInfo#getContainerClass()} when it
     *                        enters the Stream.
     */
    public ReteEntityWithdrawableSourceImpl(ResourceId id, ReteEntityInfo outputInfo,
                                            Class reteEntityClass) {
        this(id, outputInfo, reteEntityClass, null);
    }

    /**
     * @param id
     * @param outputInfo
     * @param reteEntityClass  The Concept/Event/POJO that comes from the "main" Rete which will get
     *                         wrapped in the {@link ReteEntityInfo#getContainerClass()} when it
     *                         enters the Stream.
     * @param reteEntityFilter Optional.
     */
    public ReteEntityWithdrawableSourceImpl(ResourceId id, ReteEntityInfo outputInfo,
                                            Class reteEntityClass,
                                            ReteEntityFilter reteEntityFilter) {
        super(id, outputInfo);

        this.reteEntityClass = reteEntityClass;
        this.reteEntityFilter = reteEntityFilter;
    }

    public Class getReteEntityClass() {
        return reteEntityClass;
    }

    @Override
    public ReteEntityInfo getOutputInfo() {
        return (ReteEntityInfo) super.getOutputInfo();
    }

    public ReteEntityFilter getReteEntityFilter() {
        return reteEntityFilter;
    }
}
