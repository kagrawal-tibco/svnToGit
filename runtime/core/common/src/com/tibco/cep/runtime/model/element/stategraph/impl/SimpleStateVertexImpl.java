package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.SimpleStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 1:20:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleStateVertexImpl extends StateVertexImpl implements SimpleStateVertex{
    TransitionLinkImpl [] toTransitions;
    TransitionLinkImpl [] fromTransitions;
    int curTo=0, curFrom=0;

    public SimpleStateVertexImpl(String name,
                                 CompositeStateVertexImpl superState) {
        super(name,superState);
    }

    public TransitionLink[] getToTransitions() {
        return toTransitions;
    }

    public TransitionLink[] getFromTransitions() {
        return fromTransitions;
    }

    protected void setToTransitions(TransitionLinkImpl [] toTransitions) {
        this.toTransitions=toTransitions;
    }

    protected void setFromTransitions(TransitionLinkImpl [] toTransitions) {
        this.fromTransitions=toTransitions;
    }

    public void leave(StateMachineContext ctx, TransitionLink t, byte status) {
        super.leave(ctx, t, status);
        _leave(ctx, t, status);
    }
}
