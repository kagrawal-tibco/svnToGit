package com.tibco.be.ws.rt.model.builder.ast;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 11:20 AM
 * AST types should extend this interface
 */
public interface IFilterLinkDescriptor<F extends IFilterLinkDescriptor> {

    /**
     * Name of the descriptor.
     * @return
     */
    public String getName();
}
