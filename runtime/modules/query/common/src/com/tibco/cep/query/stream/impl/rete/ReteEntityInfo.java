package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 1:54:26 PM
 */

public interface ReteEntityInfo extends TupleInfo {
    public Class<? extends ReteEntity> getContainerClass();

    public ReteEntity createTuple(Number id);
}
