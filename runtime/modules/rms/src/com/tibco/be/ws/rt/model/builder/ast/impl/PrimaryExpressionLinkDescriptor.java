package com.tibco.be.ws.rt.model.builder.ast.impl;

import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrimaryExpressionLinkDescriptor<F extends IFilterLinkDescriptor> extends AbstractFilterLinkDescriptor<F> {

    public PrimaryExpressionLinkDescriptor(RulesASTNode wrappedNode) {
        super(wrappedNode);
    }
}
