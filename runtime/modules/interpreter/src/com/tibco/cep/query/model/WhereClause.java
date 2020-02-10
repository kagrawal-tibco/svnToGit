package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * Represents a WHERE clause.
 */
public interface WhereClause
        extends QueryContext, NamedContext, Validatable {

    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "WHERE_CLAUSE"; }
    };

	public static final String ID_GEN_PREFIX = "WC$";


    /**
     * @return Expression
     */
    Expression getExpression();


}
