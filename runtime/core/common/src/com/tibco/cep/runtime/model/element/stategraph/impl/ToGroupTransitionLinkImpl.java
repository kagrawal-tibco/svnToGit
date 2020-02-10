package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.GroupTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 6:23:41 PM
 * To change this template use File | Settings | File Templates.
 */


public class ToGroupTransitionLinkImpl extends TransitionLinkImpl implements GroupTransitionLink{

    public ToGroupTransitionLinkImpl(String name, SimpleStateVertexImpl source, CompositeStateVertexImpl target, boolean isCompletion, boolean emptyCondition, int timeout) {
        super(name,source, target, isCompletion, emptyCondition, timeout);
    }

    protected void leaveSource(StateMachineContext ctx, Object[] x) {
        super.leaveSource(ctx, x);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void enterTarget(StateMachineContext ctx, Object[] x) {
        CompositeStateVertexImpl target = (CompositeStateVertexImpl) target();
        target.enter(ctx,x,this);
        // Instantiate the default transition that takes it to the start or history state.
        
        //target.getDefaultTransition().trigger(ctx,x);
        if (target.isConcurrent()) {
            ctx.concurrentEntered(target);
            //add the states to the set of current states before hand
            for(int jj = 0; jj < target.subStates.length; jj++) {
                CompositeStateVertexImpl region = (CompositeStateVertexImpl) target.subStates[jj];
                SimpleStateVertexImpl sv = (SimpleStateVertexImpl)region.getDefaultState();
                sv.addToCurrentStates(ctx);
            }

            for (int jj=0; jj<target.subStates.length; jj++) {
                CompositeStateVertexImpl region = (CompositeStateVertexImpl) target.subStates[jj];
                SimpleStateVertexImpl sv = (SimpleStateVertexImpl)region.getDefaultState();
                sv.enter(ctx, x, this);

            }
            return;
        }

        SimpleStateVertexImpl sv = (SimpleStateVertexImpl)target.getDefaultState(); //Could be a StartState or History State
        sv.enter(ctx, x, this );
        /*
        TransitionLink[] tis = sv.getFromTransitions();
        for (int i=0; i < tis.length; i++) {
            TransitionLink tl = tis[i];
            tl.enable(ctx);
            //tl.trigger(ctx, null);
        }
        */
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

        SimpleStateVertexImpl sv = (SimpleStateVertexImpl)target.getDefaultState(); //Could be a StartState or History State
        sv.enterSilently(ctx, x, this );
        /*
        TransitionLink[] tis = sv.getFromTransitions();
        for (int i=0; i < tis.length; i++) {
        TransitionLink tl = tis[i];
        tl.enable(ctx);
        //tl.trigger(ctx, null);
        }
        */
    }
}

