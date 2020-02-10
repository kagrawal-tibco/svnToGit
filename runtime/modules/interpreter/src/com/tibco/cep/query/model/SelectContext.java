package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 4, 2007
 * Time: 4:00:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SelectContext extends QueryContext, Aliased, Limited {

    /**
     * Returns the DISTINCT clause context from the Select Context if any
     * @return DistinctClause of the modelcontext if any, else null
     */
    DistinctClause getDistinctClause();

    /**
     * Returns the GROUP BY clause from the Select context
     * @return GroupClause
     */
    GroupClause getGroupClause();

    /**
     * Returns the ORDER BY clause from the Select context
     * @return GroupClause
     */
    OrderClause getOrderClause();

    /**
     * Returns the ProjectionAttributes context from the Select Context
     * @return ProjectionAttributes the modelcontext
     */
    ProjectionAttributes getProjectionAttributes();


    /**
     * Returns true if the tuples are distinct else false
     * @return boolean true or false
     */
    boolean isDistinct();

    /**
     * Returns true if the select is part of of an EXISTS clause
     * @return boolean true or false
     */
    boolean isPartOfExists();
}
