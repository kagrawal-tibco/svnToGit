/**
 * 
 */
package com.tibco.cep.query.rule;

import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.query.service.QueryStatement;


/**
 * @author pdhar
 *
 */
public interface QueryRule extends Rule,Action,Condition {
     /**
     * get the attached query statement
     * @return QueryStatement
     */
     public QueryStatement getQueryStatement();

    /**
     * attach a query statement
     * @param queryStatement
     */
    public void setQueryStatement(QueryStatement queryStatement);
}
