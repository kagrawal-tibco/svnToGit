package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.FinalStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 7, 2004
 * Time: 11:17:30 PM
 * To change this template use File | Settings | File Templates.
 */

public class FinalStateVertexImpl extends SimpleStateVertexImpl implements FinalStateVertex{

    public FinalStateVertexImpl (String name,
                                 CompositeStateVertexImpl superState) {
        super(name, superState);
    }

    public void enter(StateMachineContext ctx, Object[] evt, TransitionLink t) {
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        ctxImpl.removeTimeout(getSuperState(), false);
        super.enter(ctx, evt, t);
        ((CompositeStateVertexImpl) this.getSuperState()).leave(ctx,t, StateVertex.STATUS_LEAVE_CHILD_FINAL);
    }

    public void enterSilently(StateMachineContext ctx, Object[] args, TransitionLinkImpl transitionLink) {
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        ctxImpl.removeTimeout(getSuperState(), false);

        super.enterSilently(ctx, args, transitionLink);
        ((CompositeStateVertexImpl) this.getSuperState()).leave(ctx, null, StateVertex.STATUS_TIMEOUT_CHILD_FINAL);

    }
}
