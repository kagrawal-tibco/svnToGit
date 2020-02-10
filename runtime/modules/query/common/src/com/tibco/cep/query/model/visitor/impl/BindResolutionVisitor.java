package com.tibco.cep.query.model.visitor.impl;

import com.tibco.cep.query.model.visitor.FilteredHierarchicalContextVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 15, 2008
 * Time: 3:11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BindResolutionVisitor
    extends FilteredHierarchicalContextVisitor {


    public BindResolutionVisitor() {
        super();
        this.init();
    }

    public BindResolutionVisitor(int flags) {
        super(flags);
        this.init();
    }

    private void init() {
        this.addFilter(new BindVariableFilter());
        this.addOperator(new BindResolutionOperator());
    }



}
