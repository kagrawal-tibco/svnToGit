package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.validation.validators.WhereClauseValidator;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 18, 2007
 * Time: 12:04:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class WhereClauseImpl extends AbstractQueryContext implements WhereClause {

    /**
     * Constructor
     * @param context
     * @param tree
     */
    public WhereClauseImpl(ModelContext context, CommonTree tree) {
        super(context, tree);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_WHERE;
    }

    /**
     * @return Expression
     */
    public Expression getExpression() {
        return (Expression) getChildren()[0];
    }

    /**
     * Returns the name of the named context
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }

    public void validate() throws Exception {
        new WhereClauseValidator().validate(this);
    }
}
