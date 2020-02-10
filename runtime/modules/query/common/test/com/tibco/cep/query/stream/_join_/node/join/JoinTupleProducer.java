package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 6:53:09 PM
*/
public interface JoinTupleProducer extends ExpressionEvaluator {
    void init(FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);

    Tuple evaluate(GlobalContext globalContext, QueryContext queryContext,
                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);
}
