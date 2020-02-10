package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 3:22:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class HavingClauseImpl extends AbstractQueryContext implements HavingClause {

    
    /**
     * ctor
     *
     * @param parentContext
     * @param tree
     */
    public HavingClauseImpl(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
    }


    public Expression getExpression() {
        return (Expression) this.getChild(0);
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_HAVING_CLAUSE;
    }


}
