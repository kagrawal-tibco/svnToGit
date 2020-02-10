package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.GroupTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 8:24:24 PM
 * To change this template use File | Settings | File Templates.
 */

public class FromAndToGroupTransitionLinkImpl extends TransitionLinkImpl implements GroupTransitionLink{

    public FromAndToGroupTransitionLinkImpl(String name,
                                            CompositeStateVertexImpl source,
                                            CompositeStateVertexImpl target,
                                            boolean isCompletion, boolean emptyCondition,
                                            int timeout) {
        super(name,source, target, isCompletion, emptyCondition, timeout);
    }

    protected void leaveSource(StateMachineContext ctx, Object[] x) {
        super.leaveSource(ctx, x);
        if (!isLambda()) {
            leaveSubStates(ctx,(CompositeStateVertex)source());
        }
    }

    protected void enterTarget(StateMachineContext ctx, Object[] x) {
        CompositeStateVertexImpl target = (CompositeStateVertexImpl) target();
        target.enter(ctx,x,this);
        // Instantiate the default transition that takes it to the start or history state.

        //target.getDefaultTransition().trigger(ctx,x);
        if (target.isConcurrent()) {
            ctx.concurrentEntered(target);
            for (int jj=0; jj<target.subStates.length; jj++) {
                CompositeStateVertexImpl region = (CompositeStateVertexImpl) target.subStates[jj];
                SimpleStateVertexImpl sv = (SimpleStateVertexImpl)region.getDefaultState();
                sv.enter(ctx, x, this);

            }
            return;
        }

        SimpleStateVertexImpl sv = (SimpleStateVertexImpl)target.getDefaultState(); //Could be a StartState or History State
        sv.enter(ctx, x, this );
        
    }

    protected void enterTargetSilently(StateMachineContext ctx, Object[] x) {
        CompositeStateVertexImpl target = (CompositeStateVertexImpl) target();
        target.enterSilently(ctx,x,this);
        // Instantiate the default transition that takes it to the start or history state.

        //target.getDefaultTransition().trigger(ctx,x);
        if (target.isConcurrent()) {
            ctx.concurrentEntered(target);
            for (int jj=0; jj<target.subStates.length; jj++) {
                CompositeStateVertexImpl region = (CompositeStateVertexImpl) target.subStates[jj];
                region.enterSilently(ctx, x, this);
                SimpleStateVertexImpl sv = (SimpleStateVertexImpl)region.getDefaultState();
                sv.enterSilently(ctx, x, this);

            }
            return;
        }

        target.enterSilently(ctx, x, this);
        SimpleStateVertexImpl sv = (SimpleStateVertexImpl)target.getDefaultState(); //Could be a StartState or History State
        sv.enterSilently(ctx, x, this );

    }


    protected void leaveSubStates(StateMachineContext ctx, CompositeStateVertex source) {
        StateVertex [] substates=source.subStates();
        for (int i=0; i < substates.length;i++) {
            if (substates[i] instanceof SimpleStateVertexImpl) {
                SimpleStateVertexImpl kid= (SimpleStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
                    kid.leave(ctx,null, StateVertex.STATUS_LEAVE_PARENT);
                }
            } else if (substates[i] instanceof CompositeStateVertex) {
                CompositeStateVertexImpl kid= (CompositeStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
                    leaveSubStates(ctx,kid);
                    kid.leave(ctx,null,StateVertex.STATUS_LEAVE_PARENT);
                }
            }
        }
    }
}

