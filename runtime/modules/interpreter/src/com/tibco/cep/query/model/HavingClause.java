package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 3:20:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HavingClause extends QueryContext {

    /**
     * @return Expression that specifies the HAVING condition.
     */
    Expression getExpression();

}
