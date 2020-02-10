package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.visitor.ContextOperator;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 15, 2008
 * Time: 3:16:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BindResolutionOperator implements ContextOperator {


    public void visit(ModelContext node) throws Exception {
        if (node instanceof BindVariable) {
           final BindVariable v = (BindVariable) node;
            if (!v.isBindingResolved()) {
                v.resolveBinding();
            }
        } else  {
            throw new IllegalArgumentException("Cannot resolve binding for:"+ node.toString());
        }
    }


}
