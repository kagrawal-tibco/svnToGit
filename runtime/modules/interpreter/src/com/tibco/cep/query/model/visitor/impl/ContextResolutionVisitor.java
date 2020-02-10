package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.visitor.FilteredHierarchicalContextVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 11:42:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextResolutionVisitor
    extends FilteredHierarchicalContextVisitor {

    public ContextResolutionVisitor() {
        super();
        this.init();
    }

    public ContextResolutionVisitor(int flags) {
        super(flags);
        this.init();
    }

    private void init() {
        this.addFilter(new ResolvableFilter());
        this.addOperator(new ResolutionOperator());
    }

}
