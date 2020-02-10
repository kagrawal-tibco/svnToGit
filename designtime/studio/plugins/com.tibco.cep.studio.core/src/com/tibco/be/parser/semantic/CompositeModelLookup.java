package com.tibco.be.parser.semantic;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.model.Ontology;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 8, 2004
 * Time: 4:54:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompositeModelLookup implements ModelLookup {
    protected ModelLookup[] lookups;

    public CompositeModelLookup(ModelLookup[] lookups) {
        this.lookups = lookups;
    }

    public static CompositeModelLookup getDefaultLookupSet(Ontology model) {
        return new CompositeModelLookup(new ModelLookup[]{new BuiltinLookup(), new OntologyModelLookup(model)});
    }

    public NodeType getPropertyType(NodeType parentType, String propertyName) {
        for(int ii = 0; ii < lookups.length; ii++) {
            NodeType propType = lookups[ii].getPropertyType(parentType, propertyName);
            if(propType != null && propType != NodeType.UNKNOWN) {
                return propType;
            }
        }
        return NodeType.UNKNOWN;
    }

    public boolean isA(NodeType a, NodeType b) {
        for(int ii = 0; ii < lookups.length; ii++) {
            if(lookups[ii].isA(a, b)) {
                return true;
            }
        }
        return false;
    }

    //Returns NodeType.UNKNOWN if the lookup failed
    public NodeType getAttributeType(NodeType parentType, String attributeName) {
        for(int ii = 0; ii < lookups.length; ii++) {
            NodeType attrType = lookups[ii].getAttributeType(parentType, attributeName);
            if(attrType != null && attrType != NodeType.UNKNOWN) {
                return attrType;
            }
        }
        return NodeType.UNKNOWN;
    }

    public NodeType getTypeFromName(String name, boolean isMutable, boolean isProperty, int numBrackets) {
        for(int ii = 0; ii < lookups.length; ii++) {
            NodeType type = lookups[ii].getTypeFromName(name, isMutable, isProperty, numBrackets);
            if(type != null && type!= NodeType.UNKNOWN) {
                return type;
            }
        }
        return NodeType.UNKNOWN;
    }
}