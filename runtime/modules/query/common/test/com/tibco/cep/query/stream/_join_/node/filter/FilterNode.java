package com.tibco.cep.query.stream._join_.node.filter;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

public class FilterNode implements Node<Tuple> {
    public static final String FIXED_KEY_IN_INTERNAL_MAP = "$_key_$";

    protected final ExpressionEvaluator filter;

    protected final FixedKeyHashMap<String, Tuple> internalMap;

    protected final int keyPositionInMap;

    protected Node<Tuple> previous;

    protected Node<Tuple> next;

    protected FilterNode(ExpressionEvaluator filter) {
        this.filter = filter;

        this.internalMap = new FixedKeyHashMap<String, Tuple>(FIXED_KEY_IN_INTERNAL_MAP);
        this.keyPositionInMap = this.internalMap.getInternalPosition(FIXED_KEY_IN_INTERNAL_MAP);
    }

    public ExpressionEvaluator getFilter() {
        return filter;
    }

    public Node<Tuple> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<Tuple> previous) {
        this.previous = previous;
    }

    public Node<Tuple> getNext() {
        return next;
    }

    public void setNext(Node<Tuple> next) {
        this.next = next;
    }

    public void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
        internalMap.setValueAtInternalPosition(input, keyPositionInMap);

        boolean pass = filter.evaluateBoolean(globalContext, queryContext, internalMap);
        if (pass) {
            next.acceptNew(globalContext, queryContext, input);
        }

        internalMap.clear();
    }

    public void acceptDelete(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
        //todo
    }
}
