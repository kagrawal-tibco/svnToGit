package com.tibco.cep.query.stream._join_.node.index;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 11:28:30 AM
*/
public interface IndexExtractor extends ExpressionEvaluator {
    Comparable evaluate(GlobalContext globalContext, QueryContext queryContext,
                        FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples);
}
