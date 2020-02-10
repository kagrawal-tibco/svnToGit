package com.tibco.cep.query.model.visitor;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 8, 2007
 * Time: 6:15:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextFilter {
    /**
     * evaluate the criteria to find if the node can be visited
     * @param node
     * @return boolean
     */
    boolean canVisit( ModelContext node );
}
