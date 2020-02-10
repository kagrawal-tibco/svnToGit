package com.tibco.be.parser.semantic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Symbol;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 2, 2004
 * Time: 12:42:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleInfoSymbolTable extends BaseSymbolTable {
    protected HashMap declMap = new HashMap();
    protected NodeType returnType;
    
    /**
     * The table will not reflect changes made to rinfo after construction.
     * @param rinfo declarations used to construct the table
     * @param ontology
     */ 
    public RuleInfoSymbolTable(RuleInfo rinfo, Ontology ontology) {
        for(Iterator it = rinfo.getDeclarations().values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            //todo need array (numBrackets) support here?
            NodeType type = TypeNameUtil.typeNameToNodeType(symbol.getType(), ontology, false, false, 0);
            
            if(type != null) declMap.put(symbol.getName(), type);
        }

        String returnTypeName = rinfo.getReturnTypeName();
        if(returnTypeName == null || "".equals(returnTypeName)) {
            returnType = NodeType.VOID;
        } else {
            //todo need array (numBrackets) support here?
            NodeType temp = TypeNameUtil.typeNameToNodeType(returnTypeName, ontology, false, false, 0); 
            if(temp != null) returnType = temp;
            else returnType = NodeType.UNKNOWN;
        }
    }
    
    public NodeType getDeclaredIdentifierType(String id) {
        if(id == null) return NodeType.UNKNOWN;
        NodeType type = (NodeType)declMap.get(id);
        if(type != null) {
            return type;
        } else {
            return getLocalType(id);
            //returns unknown if id not found
        }
    }
    
    public NodeType getReturnType() {
        return returnType;
    }
    
    public Iterator visibleIds() {
        return new Iterator() {
            Iterator declIds = declMap.keySet().iterator();
            Iterator localIds = visibleLocalIds();
            
            public boolean hasNext() {
                return declIds.hasNext() || localIds.hasNext();
            }

            public Object next() {
                if(declIds.hasNext()) return declIds.next();
                else if(localIds.hasNext()) return localIds.next();
                else throw new NoSuchElementException();
            }
            
            public void remove() {}
        };
    }
}
