package com.tibco.cep.query.stream._join_;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.storage.StorageNode;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 7:01:11 PM
*/
public class Graph {
    protected FixedKeyHashMap<String, StorageNode> startNodes;

    protected Node endNode;

    public Graph() {
        //todo Support deletes
    }
}
