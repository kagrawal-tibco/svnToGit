package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.InternalTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 5:28:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalTransitionLinkImpl extends TransitionLinkImpl implements InternalTransitionLink{

    public InternalTransitionLinkImpl(String name,StateVertexImpl state) {
        super(name,state, state, false, false);
    }

    /*
     * @param statemachine
     * @param event
     */

    public void trigger(StateMachineContext ctx, Object[] x) {
        ((StateVertexImpl) target()).stay(ctx, x, this);
    }

    public String toString() {
        return source.getName() + "-(i)->" + target.getName();
    }
}
