package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * Parent of all Window types.
 */
public interface Window
    extends QueryContext, Validatable {


    /**
     * @return WhereClause or null.
     */
    WhereClause getWhereClause();

    
}
