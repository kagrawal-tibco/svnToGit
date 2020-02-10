package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.SortCriterion;
import com.tibco.cep.query.model.validation.validators.OrderClauseValidator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 22, 2007 Time: 3:37:26 PM To
 * change this template use File | Settings | File Templates.
 */
public class OrderClauseImpl extends AbstractQueryContext implements OrderClause {
    protected SortCriterion[] criteria;

    /**
     * ctor
     * 
     * @param parentContext
     * @param tree
     */
    public OrderClauseImpl(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ORDER_CLAUSE;
    }

    /**
     * Returns the name of the named context
     * 
     * @return String
     */
    public NamedContextId getContextId() {
        return OrderClause.CTX_ID;
    }

    public void setSortCriteria(SortCriterion[] criteria) {
        this.criteria = criteria;
    }

    public SortCriterion[] getSortCriteria() {
        return criteria;
    }

    public void validate() throws Exception {
        new OrderClauseValidator().validate(this);
    }
}
