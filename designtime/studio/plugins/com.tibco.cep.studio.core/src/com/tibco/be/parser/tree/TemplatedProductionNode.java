package com.tibco.be.parser.tree;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.parser.Token;


/**
 * User: nprade
 * Date: 1/31/12
 * Time: 6:48 PM
 */
public class TemplatedProductionNode
        extends ProductionNode
        implements TemplatedNode {


    public enum Mode {
        MODIFY,
    }


    private Mode mode;
    private List<String> accessibleSymbolNames = new ArrayList<String>(0);


    public TemplatedProductionNode(
            Token token,
            int reln,
            Mode mode) {
        super(token, reln);
        this.mode = mode;
    }


    @Override
    public Object accept(NodeVisitor v) {
        return v.visitTemplatedProductionNode(this);
    }


    @Override
    public List<String> getAccessibleSymbolNames() {
        return this.accessibleSymbolNames;
    }


    public Mode getMode() {
        return this.mode;
    }


    public static TemplatedProductionNode newNode(
            ParseSyntaxTree parseTree,
            Token t,
            int relation,
            Mode mode) {
        final TemplatedProductionNode node = new TemplatedProductionNode(t, relation, mode);
        if (null != parseTree) {
            parseTree.pushNode(node);
        }
        return node;
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
