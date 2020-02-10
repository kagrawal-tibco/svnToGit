package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.visitor.FilteredHierarchicalContextVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 12:32:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextValidationVisitor extends FilteredHierarchicalContextVisitor {

    public ContextValidationVisitor() {
        super();
        addFilter(new ValidatableFilter());;
        addOperator(new ValidationOperator());
    }
}
