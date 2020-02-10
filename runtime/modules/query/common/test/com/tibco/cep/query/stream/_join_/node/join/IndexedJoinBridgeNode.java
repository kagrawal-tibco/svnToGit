package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.index.IndexedStorageNode;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

public class IndexedJoinBridgeNode implements JoinBridgeNode {
    protected final boolean bridgeLeft;

    protected IndexedStorageNode previous;

    protected IndexedJoinNode next;

    /**
     * @param bridgeLeft <code>true</code> for left. <code>false</code> for right.
     */
    public IndexedJoinBridgeNode(boolean bridgeLeft) {
        this.bridgeLeft = bridgeLeft;
    }

    public boolean isBridgeLeft() {
        return bridgeLeft;
    }

    public IndexedStorageNode getPrevious() {
        return previous;
    }

    public void setPrevious(IndexedStorageNode previous) {
        this.previous = previous;
    }

    public IndexedJoinNode getNext() {
        return next;
    }

    public void setNext(IndexedJoinNode nextNode) {
        next = nextNode;
    }

    public Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator,
                        Comparable key) {
        return previous.fetch(globalContext, queryContext, operator, key);
    }

    public void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        if (bridgeLeft) {
            next.acceptLeft(globalContext, queryContext, tuple);

            return;
        }

        next.acceptRight(globalContext, queryContext, tuple);
    }

    public void acceptDelete(GlobalContext globalContext, QueryContext queryContext, Tuple tuple) {
        //todo.
    }
}
