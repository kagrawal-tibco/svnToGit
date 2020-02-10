package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.validation.Resolvable;
import com.tibco.cep.query.model.visitor.ContextOperator;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 11:29:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResolutionOperator implements ContextOperator {

    public void visit(ModelContext node) throws Exception {
        if (node instanceof Resolvable) {
           final Resolvable rsv = (Resolvable) node;
            if (!rsv.isResolved()) {
                rsv.resolveContext();
            }
        } else  {
            throw new IllegalArgumentException("Node is not resolvable :"+ node.toString());
        }
    }

}//class
