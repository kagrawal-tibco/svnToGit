package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * PURGE clause used in window definitions.
 */
public interface PurgeClause
        extends QueryContext, Validatable {


    /**
     * @return Expression
     */
    Expression getFirst();

    /**
     * @return Expression
     */
    Expression getWhen();

}
