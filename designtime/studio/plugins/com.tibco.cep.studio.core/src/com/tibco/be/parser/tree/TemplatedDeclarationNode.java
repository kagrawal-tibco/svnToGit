package com.tibco.be.parser.tree;

import java.util.ArrayList;
import java.util.List;


/**
 * User: nprade
 * Date: 1/31/12
 * Time: 6:29 PM
 */
public class TemplatedDeclarationNode
        extends DeclarationNode
        implements TemplatedNode {
    
    
    public enum Mode {
        CALL,
        CREATE,
    }


    private Mode mode;
    private List<String> accessibleSymbolNames = new ArrayList<String>(0);


    public TemplatedDeclarationNode(
            Mode mode) {
        this.mode = mode;
    }


    @Override
    public Object accept(NodeVisitor v) {
        return v.visitTemplatedDeclarationNode(this);
    }


    @Override
    public List<String> getAccessibleSymbolNames() {
        return this.accessibleSymbolNames;
    }


    @Override
    public TypeNode getDeclarationType() {
        if (Mode.CALL == this.mode) {
            return null;
        }

        return super.getDeclarationType();
    }


    public Mode getMode() {
        return this.mode;
    }


    @Override
    public void setAccessibleSymbolNames(
            List<String> accessibleSymbolNames) {
        this.accessibleSymbolNames = accessibleSymbolNames;
    }


    public void setMode(
            Mode mode) {
        this.mode = mode;
    }
}
