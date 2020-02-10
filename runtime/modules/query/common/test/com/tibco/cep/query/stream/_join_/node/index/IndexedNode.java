package com.tibco.cep.query.stream._join_.node.index;

import com.tibco.cep.query.stream._join_.index.Index;
import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Aug 6, 2008 Time: 6:27:56 PM
*/

public interface IndexedNode<I extends Tuple> extends Node<I> {
    Index<Comparable, I, Number> getIndex();

    /**
     * @param globalContext
     * @param queryContext
     * @param operator
     * @param key
     * @return
     * @see com.tibco.cep.query.stream._join_.index.Index#fetch(com.tibco.cep.query.stream.context.GlobalContext,
     *      com.tibco.cep.query.stream.context.QueryContext, com.tibco.cep.query.stream._join_.index.Operator,
     *      Comparable)
     */
    Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator,
                 Comparable key);
}
