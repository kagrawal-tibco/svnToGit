package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.StreamPolicyBy;

/**
 * Represents a BY clause.
 */
public class StreamPolicyByImpl
        extends AbstractQueryContext
        implements StreamPolicyBy {


    public StreamPolicyByImpl(ModelContext parent, CommonTree tree) {
        super(parent, tree);
    }


    /**
     * Gets the Expression's specified in the BY clause.
     *
     * @return Expression array.
     */
    public Expression[] getExpressions() {
        ModelContext[] contexts = getChildren();
        Expression[] expressions = new Expression[contexts.length];
        for (int i = 0; i < expressions.length; i++) {
            expressions[i] = (Expression) contexts[i];
        }

        return expressions;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_BY_CLAUSE;
    }


}
