package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;

/*
 * Author: Ashwin Jayaprakash Date: Feb 8, 2008 Time: 5:06:50 PM
 */

public interface WithdrawableSource extends Source {
    public void sendModifiedTuple(Context context, Tuple oldTuple, Tuple newTuple) throws Exception;

    public void sendDeadTuple(Context context, Tuple tuple) throws Exception;

    public void sendDeadTuples(Context context, CustomCollection<? extends Tuple> tuples)
            throws Exception;
}
