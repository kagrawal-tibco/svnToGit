package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 13, 2008
 * Time: 8:36:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryContext extends ModelContext {
    /**
     * Returns the string associated with the modelcontext
     * @return String
     */
    String getSourceString();

    /* (non-Javadoc)
    * @see com.tibco.cep.query.ast.ModelContext#getLine()
    */
    int getLine();

    /* (non-Javadoc)
    * @see com.tibco.cep.query.ast.ModelContext#getCharPosition()
    */
    int getCharPosition();

     /**
     * Returns the From Clause context from the Context
     * @return FromClause the modelcontext
     */
    FromClause getFromClause();

    /**
     * Returns the WHERE clause from the context
     * @return WhereClause
     */
    WhereClause getWhereClause();    
}
