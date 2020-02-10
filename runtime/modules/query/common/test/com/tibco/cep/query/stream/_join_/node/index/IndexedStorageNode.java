package com.tibco.cep.query.stream._join_.node.index;

import com.tibco.cep.query.stream._join_.index.Index;
import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.storage.StorageNode;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream.util.ReusableIterator;

public class IndexedStorageNode implements IndexedNode<Tuple>, StorageNode<Tuple> {
    protected final IndexExtractor indexValueExtractor;

    protected final SourceToMapTransformer<Tuple> sourceToMapXformer;

    protected final FixedKeyHashMap<String, Tuple> map;

    protected final Index<Comparable, Tuple, Number> index;

    protected Node<Tuple> previous;

    protected Node<Tuple> next;

    public IndexedStorageNode(IndexExtractor indexValueExtractor,
                              SourceToMapTransformer<Tuple> sourceToMapXformer,
                              Index<Comparable, Tuple, Number> index) {
        this.indexValueExtractor = indexValueExtractor;
        this.sourceToMapXformer = sourceToMapXformer;
        this.index = index;
        this.map = new FixedKeyHashMap<String, Tuple>(sourceToMapXformer.getKeys());
        this.sourceToMapXformer.init(this.map);
    }

    public SourceToMapTransformer<Tuple> getSourceToMapXformer() {
        return sourceToMapXformer;
    }

    public IndexExtractor getIndexValueExtractor() {
        return indexValueExtractor;
    }

    public Index<Comparable, Tuple, Number> getIndex() {
        return index;
    }

    public void setPrevious(Node<Tuple> previousNode) {
        previous = previousNode;
    }

    public Node<Tuple> getPrevious() {
        return previous;
    }

    public void setNext(Node<Tuple> nextNode) {
        next = nextNode;
    }

    public Node<Tuple> getNext() {
        return next;
    }

    public void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple i) {
        sourceToMapXformer.copyTransform(i, map);

        Comparable key = indexValueExtractor.evaluate(globalContext, queryContext, map);

        map.clear();

        index.record(key, i, i.getId());

        next.acceptNew(globalContext, queryContext, i);
    }

    public void acceptDelete(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
        //todo
    }

    public ReusableIterator<Tuple> fetchAll(GlobalContext globalContext,
                                            QueryContext queryContext) {
        Object results = index.fetch(globalContext, queryContext, Operator.ALL, null);

        return (ReusableIterator<Tuple>) results;
    }

    public Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator,
                        Comparable key) {
        return index.fetch(globalContext, queryContext, operator, key);
    }
}
