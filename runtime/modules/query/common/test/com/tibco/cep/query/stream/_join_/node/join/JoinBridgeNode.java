package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.storage.StorageNode;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Aug 7, 2008 Time: 1:32:13 PM
*/

public interface JoinBridgeNode extends Node<Tuple> {
    StorageNode<Tuple> getPrevious();

    JoinNode getNext();
}
