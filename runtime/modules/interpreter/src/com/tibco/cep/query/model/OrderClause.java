package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 22, 2007 Time: 3:37:00 PM To
 * change this template use File | Settings | File Templates.
 */
public interface OrderClause extends QueryContext, NamedContext, Validatable {

    NamedContextId CTX_ID = new NamedContextId() {
        public String toString() {
            return "ORDER_CLAUSE";
        }
    };

    void setSortCriteria(SortCriterion[] criteria);
    
    SortCriterion[] getSortCriteria();
}
