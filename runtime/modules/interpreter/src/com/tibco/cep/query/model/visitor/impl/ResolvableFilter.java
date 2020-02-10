package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.validation.Resolvable;
import com.tibco.cep.query.model.visitor.ContextFilter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 9:44:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResolvableFilter implements ContextFilter {

    /**
     * return status if this node is resolvable and has not been resolved
     * @param node
     * @return boolean
     */
     public boolean canVisit(ModelContext node) {
        return ((node instanceof Resolvable) && !((Resolvable)node).isResolved());
    }
}
