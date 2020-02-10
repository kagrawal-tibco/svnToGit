package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.validation.Validatable;
import com.tibco.cep.query.model.visitor.ContextOperator;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Sep 9, 2007 Time: 12:24:29 PM To
 * change this template use File | Settings | File Templates.
 */
public class ValidationOperator implements ContextOperator {
    public void visit(ModelContext node) throws Exception {
        if (node instanceof Validatable) {
            Validatable v = (Validatable) node;
            v.validate();
        }
    }
}
