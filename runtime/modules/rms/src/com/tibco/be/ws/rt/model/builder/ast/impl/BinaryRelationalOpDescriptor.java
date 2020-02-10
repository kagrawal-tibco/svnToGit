package com.tibco.be.ws.rt.model.builder.ast.impl;

import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryRelationalOpDescriptor<F extends IFilterLinkDescriptor> extends AbstractFilterLinkDescriptor<F> {

    public BinaryRelationalOpDescriptor(RulesASTNode wrappedNode) {
        super(wrappedNode);
    }
}
