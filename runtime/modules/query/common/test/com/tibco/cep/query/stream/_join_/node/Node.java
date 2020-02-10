package com.tibco.cep.query.stream._join_.node;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

public interface Node<I extends Tuple> {
    /**
     * User covariant return types to change to a collection if there are more than 1.
     *
     * @return
     */
    Object getPrevious();

    Node<Tuple> getNext();

    void acceptNew(GlobalContext globalContext, QueryContext queryContext, I input);

    void acceptDelete(GlobalContext globalContext, QueryContext queryContext, I input);
}
