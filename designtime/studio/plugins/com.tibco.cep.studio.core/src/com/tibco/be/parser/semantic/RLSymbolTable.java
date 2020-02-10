package com.tibco.be.parser.semantic;

import java.util.Iterator;

import com.tibco.be.parser.tree.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 30, 2004
 * Time: 4:27:39 PM
 * NodeTypes used as arguments must have names in the rules language format
 * for example, folder1.folder2.ConceptName
 */
public interface RLSymbolTable {
    /**
     * @param id an identifer in the rules language
     * @return the type of this identifier in the context of this symbol table's scope or NodeType.UNKNOWN if id isn't found in the scope
     */ 
    public NodeType getDeclaredIdentifierType(String id);
    
    /**
     * 
     * @return a NodeType, or null if the return type is void.
     */ 
    public NodeType getReturnType();
    
    public void addLocalDeclaration(String id, NodeType type);
    public void pushScope();
    public void popScope();
    /**
     * clears the local variables but not any scope variables 
     * (variables that are visible in both conditions and actions in a rule
     *  or the variables declared in the signature of a rule function for example) 
     */ 
    public void clearLocals();
    //all the variable identifiers that are currently visible
    public Iterator visibleIds();
}
