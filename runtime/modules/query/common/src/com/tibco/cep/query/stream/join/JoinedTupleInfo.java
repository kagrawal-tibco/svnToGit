package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Jan 7, 2008 Time: 3:41:54 PM
 */

public interface JoinedTupleInfo extends TupleInfo {
    public Class<? extends JoinedTuple> getContainerClass();

    public JoinedTuple createTuple(Number id);
}
