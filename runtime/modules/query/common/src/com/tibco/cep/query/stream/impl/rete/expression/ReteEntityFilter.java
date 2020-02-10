package com.tibco.cep.query.stream.impl.rete.expression;

import java.io.Serializable;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Jun 16, 2008 Time: 2:42:02 PM
*/
public interface ReteEntityFilter extends Serializable {
    boolean allow(GlobalContext globalContext, QueryContext queryContext, Tuple reteEntity);

    /*
    todo Provide some sort of hints: selectivity, histogram, order/priority when other
    filters are present.
    */
}
