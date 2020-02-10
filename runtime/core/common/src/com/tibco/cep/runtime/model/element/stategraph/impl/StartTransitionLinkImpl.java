package com.tibco.cep.runtime.model.element.stategraph.impl;

import java.util.Iterator;
import java.util.LinkedList;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.SimpleStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.SimpleTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StartTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 8:49:04 PM
 * To change this template use File | Settings | File Templates.
 */

/*
 * A stub transition from the group transition to the default start state of the composite state
 */

public class StartTransitionLinkImpl extends TransitionLinkImpl implements StartTransitionLink{
    CompositeStateVertexImpl root;

    public StartTransitionLinkImpl(String name,CompositeStateVertexImpl root, StartStateVertexImpl target) {
        super(name,root,target,false, false, 0);
        this.root=root;
        this.root.setDefaultTransition(this);
    }

    protected void leaveSource(StateMachineContext ctx, Event x) {
        // Start Transition is a transition with no source
    }

    public void disable(StateMachineContext ctx) {
        // Start Transition is a pseudo transition dependent on a group transition or an initial event
    }

    public void enable(StateMachineContext ctx) {
       // Start Transition is a pseudo transition dependent on a group transition or an initial event
    }

    protected void enterTarget (StateMachineContext ctx,Object[] x) {
        StateVertexImpl t= (StateVertexImpl) target();

        if (root.hasHistoryState()) {
            // Get the History State
            SimpleTransitionLink nextTransition =(SimpleTransitionLink) root.historyTransition(ctx);
            t=(StateVertexImpl)nextTransition.source();
            jumpTo(ctx,(SimpleStateVertex) t);
            t.enter(ctx, x, this);
            return;
        } else {
            t.enter(ctx,x,this);
            return;
        }
    }

    protected void enterTargetSilently(StateMachineContext ctx, Object[] x) {
        StateVertexImpl t= (StateVertexImpl) target();

        if (root.hasHistoryState()) {
            // Get the History State
            SimpleTransitionLink nextTransition =(SimpleTransitionLink) root.historyTransition(ctx);
            t=(StateVertexImpl)nextTransition.source();
            jumpToSilently(ctx,(SimpleStateVertex) t);
            t.enterSilently(ctx, x, this);
            return;
        } else {
            t.enterSilently(ctx,x,this);
            return;
        }

    }

    public void jumpTo (StateMachineContext ctx, SimpleStateVertex newTarget) {
        CompositeStateVertex ta=newTarget.getSuperState();
        LinkedList steps = new LinkedList();


        while (!ta.equals(root)) {
            steps.add(ta);
            ta=ta.getSuperState();
        }

        for (Iterator it = steps.iterator(); it.hasNext();){
            CompositeStateVertexImpl cv = (CompositeStateVertexImpl) it.next();
            cv.enter(ctx,null,null);
        }
    }

    public void jumpToSilently (StateMachineContext ctx, SimpleStateVertex newTarget) {
        CompositeStateVertex ta=newTarget.getSuperState();
        LinkedList steps = new LinkedList();


        while (!ta.equals(root)) {
            steps.add(ta);
            ta=ta.getSuperState();
        }

        for (Iterator it = steps.iterator(); it.hasNext();){
            CompositeStateVertexImpl cv = (CompositeStateVertexImpl) it.next();
            cv.enterSilently(ctx,null,null);
        }
    }
}

