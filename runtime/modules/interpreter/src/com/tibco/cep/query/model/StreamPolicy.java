package com.tibco.cep.query.model;


/**
 *
 */
public interface StreamPolicy extends QueryContext{

    /**
     * Gets the BY clause used by the policy.
     * 
     * @return StreamPolicyBy
     */
    StreamPolicyBy getByClause();

    /**
     * Gets the WHERE clause used by the policy.
     * 
     * @return WhereClause.
     */
    WhereClause getWhereClause();

    /**
     * Gets the Window specification.
     * 
     * @return Window
     */
    Window getWindow();

}
