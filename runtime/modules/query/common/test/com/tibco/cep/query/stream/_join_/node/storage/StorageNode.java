package com.tibco.cep.query.stream._join_.node.storage;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.ReusableIterator;

public interface StorageNode<I extends Tuple> extends Node<I> {
    /**
     * @param globalContext
     * @param queryContext
     * @return No <code>null</code>s are returned unless the mapping itself contains a
     *         <code>null</code>.
     */
    ReusableIterator<I> fetchAll(GlobalContext globalContext, QueryContext queryContext);
}
