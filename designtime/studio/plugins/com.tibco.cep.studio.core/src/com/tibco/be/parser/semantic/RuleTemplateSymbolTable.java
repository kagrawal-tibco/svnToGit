package com.tibco.be.parser.semantic;

import java.util.HashMap;
import java.util.Map;

import com.tibco.be.parser.impl.RuleTemplateInfo;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.model.Ontology;


/**
 * User: nprade
 * Date: 1/31/12
 * Time: 2:32 PM
 */
public class RuleTemplateSymbolTable
        extends RuleInfoSymbolTable {

    protected Map<String, NodeType> bindingNameToType = new HashMap<String, NodeType>();


    public RuleTemplateSymbolTable(
            RuleTemplateInfo rtInfo,
            Ontology ontology) {
        super(rtInfo, ontology);
    }


    public void addBindingType(
            String id,
            NodeType nodeType) {

        if((null != id) && (null != nodeType)) {
            this.bindingNameToType.put(id, nodeType);
        }
    }


//    public void addLocalsToBindings() {
//
//        for (final Iterator i = this.visibleLocalIds(); i.hasNext();) {
//            final String id = (String) i.next();
//            this.addBindingType(id, getLocalType(id));
//        }
//    }


    public NodeType getBindingType(
            String id) {

        NodeType result = this.bindingNameToType.get(id);
        if (null == result) {
            result = NodeType.UNKNOWN;
        }
        return result;
    }

//    @Override
//    protected NodeType getLocalType(
//            String id) {
//
//        NodeType result = this.getBindingType(id);
//        if(NodeType.UNKNOWN == result) {
//            result = super.getLocalType(id);
//        }
//        return result;
//    }

}
