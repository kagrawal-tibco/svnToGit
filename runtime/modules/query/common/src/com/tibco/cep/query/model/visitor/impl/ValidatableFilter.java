package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.validation.Validatable;
import com.tibco.cep.query.model.visitor.ContextFilter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 12:22:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidatableFilter implements ContextFilter {
    public boolean canVisit(ModelContext node) {
        return node instanceof Validatable;
    }
}
