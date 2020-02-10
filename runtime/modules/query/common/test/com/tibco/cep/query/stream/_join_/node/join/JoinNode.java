package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream.tuple.Tuple;

import java.util.List;

/**
 * No generics because it doesn't have a single source type does not apply - different for left and
 * right.
 */
public interface JoinNode extends Node<Tuple> {
    /**
     * @return List containing elements from left to right.
     */
    List<? extends Node<? extends Tuple>> getPrevious();
}
