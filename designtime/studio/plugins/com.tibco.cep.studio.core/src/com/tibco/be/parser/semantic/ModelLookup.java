package com.tibco.be.parser.semantic;

import com.tibco.be.parser.tree.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 1, 2004
 * Time: 7:27:53 PM
 * NodeTypes used as arguments must have names in the rules language format
 * for example, folder1.folder2.ConceptName
 */
public interface ModelLookup {
    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getPropertyType(NodeType parentType, String propertyName);

    public boolean isA(NodeType a, NodeType b);
    
    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getAttributeType(NodeType parentType, String attributeName);
    
    //name is in dotted format
    //search order is primitive names, then generic names, then model names
    public NodeType getTypeFromName(String name, boolean isMutable, boolean isProperty, int numBrackets);
}