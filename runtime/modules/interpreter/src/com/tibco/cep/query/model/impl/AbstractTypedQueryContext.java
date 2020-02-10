package com.tibco.cep.query.model.impl;

import java.util.Map;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.WhereClause;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 25, 2007
 * Time: 6:03:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTypedQueryContext extends AbstractTypedContext implements QueryContext {
    protected CommonTree tree;
    protected FromClause from;
    protected WhereClause where;

    /**
     * @param parentContext
     * @param tree
     */
    public AbstractTypedQueryContext(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
        if(this instanceof QueryContext && null == tree) {
            throw new RuntimeException("QueryContext token must have a AST node");
        }
        if (null == tree) {
            this.tree = new CommonTree(Token.INVALID_TOKEN);
        } else {
            this.tree = tree;
        }
    }


        /**
     * @return Map of name to ModelContext valid in this context
     */
    public Map<Object,ModelContext> getContextMap() {
        return this.getOwnerContext().getContextMap();
    }


    public String getSourceString() {
        return tree.getText();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.query.ast.ModelContext#getLine()
     */
    public int getLine() {
        return this.tree.getLine();
    }


    /* (non-Javadoc)
     * @see com.tibco.cep.query.ast.ModelContext#getCharPosition()
     */
    public int getCharPosition() {
        return this.tree.getCharPositionInLine();
    }


    @Override
    public WhereClause getWhereClause() {
        return where;
    }

    @Override
    public FromClause getFromClause() {
        return from;
    }
}
