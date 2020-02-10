package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 1:49:45 PM
 */

public class ReteEntitySourceImpl extends SourceImpl implements ReteEntitySource {
    protected final Class reteEntityClass;

    protected final ReteEntityFilter reteEntityFilter;

    /**
     * @param id
     * @param outputInfo
     * @param reteEntityClass The Concept/Event/POJO that comes from the "main" Rete which will get
     *                        wrapped in the {@link ReteEntityInfo#getContainerClass()} when it
     *                        enters the Stream.
     */
    public ReteEntitySourceImpl(ResourceId id, ReteEntityInfo outputInfo, Class reteEntityClass) {
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
    public ReteEntitySourceImpl(ResourceId id, ReteEntityInfo outputInfo, Class reteEntityClass,
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
