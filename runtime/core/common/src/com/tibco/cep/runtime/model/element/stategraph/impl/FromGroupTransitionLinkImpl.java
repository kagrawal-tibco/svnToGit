package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.GroupTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 6:14:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class FromGroupTransitionLinkImpl extends TransitionLinkImpl implements GroupTransitionLink{

    public FromGroupTransitionLinkImpl(String name,CompositeStateVertexImpl source, SimpleStateVertexImpl target, boolean isCompletion, boolean emptyCondition, int timeout) {
        super(name,source, target, isCompletion, emptyCondition, timeout);
    }


    /*
     * This method is called when the group transition is fired
     * The transition checks for all the active transitions within this group
     * and disables all the transitions
     * The internal transitions are owned by the composite state and they are diabled by default when the state
     * is exited
     */

    protected void leaveSource(StateMachineContext ctx, Object[] x) {
        if (!isLambda() ) {
            leaveSubStates(ctx,(CompositeStateVertex)source());
        }
        super.leaveSource(ctx, x);
    }


    protected void leaveSubStates(StateMachineContext ctx, CompositeStateVertex source) {
        StateVertex [] substates=source.subStates();
        for (int i=0; i < substates.length;i++) {
            if (substates[i] instanceof SimpleStateVertexImpl) {
                SimpleStateVertexImpl kid= (SimpleStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
                    kid.leave(ctx,null, StateVertex.STATUS_LEAVE_PARENT);
                }
            } else if (substates[i] instanceof CompositeStateVertexImpl) {
                CompositeStateVertexImpl kid= (CompositeStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
                    leaveSubStates(ctx,kid);
                    kid.leave(ctx,null, StateVertex.STATUS_LEAVE_PARENT);
                }
            }
        }
    }
}
