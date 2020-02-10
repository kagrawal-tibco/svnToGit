package com.tibco.be.ws.rt.model.builder.ast.impl;

import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConstantValueLinkDescriptor<F extends IFilterLinkDescriptor> extends AbstractFilterLinkDescriptor<F> {

    public ConstantValueLinkDescriptor(RulesASTNode wrappedNode) {
        super(wrappedNode);
    }
}
