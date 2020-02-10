package com.tibco.be.parser.semantic;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable;
import com.tibco.cep.designtime.model.rule.Symbol;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 30, 2004
 * Time: 4:37:42 PM
 * 
 * An RLSymbolTable implementation backed by a Compilable object
 */
public class CompilableDeclSymbolTable extends BaseSymbolTable {
    Compilable c;
    Ontology o;
    
    public CompilableDeclSymbolTable(Compilable compilable, Ontology ontology) {
        super();
        c = compilable;
        o = ontology;
    }

    public void reset(Compilable compilable, Ontology ontology) {
        c = compilable;
        o = ontology;
        clearLocals();
    }
    
    public NodeType getDeclaredIdentifierType(String id){
        if(id == null) return NodeType.UNKNOWN;
        final Symbol symbol = c.getScope().getSymbol(id);
        String declType;
        if (null == symbol) {
            declType = null;
        } else {
            declType = symbol.getType();
        }
        if(declType != null) {
            int numBrackets = symbol.isArray() ? 1 : 0;
            return TypeNameUtil.typeNameToNodeType(declType, o, false, false, numBrackets);
        } else {
            return getLocalType(id);
            //returns unknown if id not found
        }
    }
    
    public NodeType getReturnType() {
        String returnTypeName = c.getReturnType();
        if(returnTypeName == null || "".equals(returnTypeName)) {
        	return NodeType.VOID;
        } else {
        	int numBrackets = 0;
        	if (returnTypeName.endsWith("[]")) {
        		numBrackets = 1;
        		returnTypeName = returnTypeName.substring(0, returnTypeName.length()-2); 
        	}
        	return TypeNameUtil.typeNameToNodeType(returnTypeName, o, false, false, numBrackets);
        }
    }
    
    public Iterator visibleIds() {
        return new Iterator() {
            Iterator declIds = c.getScope().keySet().iterator();
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
