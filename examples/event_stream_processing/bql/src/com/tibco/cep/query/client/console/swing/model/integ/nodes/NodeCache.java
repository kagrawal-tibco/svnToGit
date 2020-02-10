package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.element.Concept;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
* Author: Karthikeyan Subramanian / Date: Jul 8, 2010 / Time: 12:28:02 PM
*/
public class NodeCache {
    private final ConcurrentMap<String, ConceptNode> nodeMap = new ConcurrentHashMap<String, ConceptNode>();

    public ConceptNode getConceptNode(Concept concept) {
        ConceptNode node = nodeMap.get(concept.getFullPath());
        if(node == null) {
            node = new ConceptNode(concept);
            ConceptNode tempNode = nodeMap.putIfAbsent(concept.getFullPath(), node);
            if(tempNode != null) {
                return tempNode;
            }
        }
        return node;
    }
}
