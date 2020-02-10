package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.node.storage.StorageNode;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.ReusableIterator;

public class NestedLoopJoinBridgeNode implements JoinBridgeNode {
    protected StorageNode<Tuple> previous;

    protected NestedLoopJoinNode next;

    protected int positionInJoin;

    public NestedLoopJoinBridgeNode() {
    }

    public StorageNode<Tuple> getPrevious() {
        return previous;
    }

    public void setPrevious(StorageNode<Tuple> previous) {
        this.previous = previous;
    }

    public NestedLoopJoinNode getNext() {
        return next;
    }

    public void setNext(NestedLoopJoinNode next) {
        this.next = next;
    }

    public int getPositionInJoin() {
        return positionInJoin;
    }

    public void setPositionInJoin(int positionInJoin) {
        this.positionInJoin = positionInJoin;
    }

    public ReusableIterator<Tuple> fetchAll(GlobalContext globalContext,
                                            QueryContext queryContext) {
        return previous.fetchAll(globalContext, queryContext);
    }

    public void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
        next.acceptNew(globalContext, queryContext, input, positionInJoin);
    }

    public void acceptDelete(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
        next.acceptDelete(globalContext, queryContext, input, positionInJoin);
    }
}
