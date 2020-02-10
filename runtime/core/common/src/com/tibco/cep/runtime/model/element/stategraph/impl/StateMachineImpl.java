package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateGraphDefinition;
import com.tibco.cep.runtime.model.element.stategraph.StateMachine;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLinkAction;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 19, 2004
 * Time: 4:39:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class StateMachineImpl implements StateMachine{
    public AbstractStateGraphDefinitionImpl model;
    Concept instance;
    public StateMachineContextImpl ctx;
    PropertyArrayInt mStatuses;

    public StateMachineImpl (Concept assInstance,  StateGraphDefinition model) {
        instance = assInstance;
        this.model = (AbstractStateGraphDefinitionImpl) model;
        ctx = new StateMachineContextImpl(model, (ConceptImpl)instance);
        //mStatuses = statuses;
    }

    public Concept getAssociatedInstance() {
        return instance;
    }

    /*
    public boolean isInState(StateVertex state) {
        return ctx.currentState(state);
    }

    public int countInState(StateVertex state) {
        return ctx.stateInCount(state);
    }
    */

    public boolean isComplete() {
        return ctx.isMachineClosed();
    }

    public Concept getHistory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StateGraphDefinition getGraphDefinition() {
        return model;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected StateMachineContext getContext() {
        return ctx;
    }

    /*
    public void fire(TransitionLink t, Event e) {
        t.trigger(ctx,e);
    }
    */

    public boolean isActivated(TransitionLink t) {
        return ctx.isTransitionActive(t);
    }

    /**
     *
     * @param idx - The Transition Index.
     * @param closure - The closure object array that was associated to firing this transition
     */
    public void fire(TransitionLinkAction actionImpl, int idx, Object[] closure) {
        TransitionLink tl = model.getTransitionLink(idx);
        tl.trigger(actionImpl, ctx, closure);
    }

    public void start() {
        CompositeStateVertex csv = model.getRoot();
        if (!ctx.isRecovered) {

            SimpleStateVertexImpl sv = (SimpleStateVertexImpl) csv.getDefaultState(); //Could be a StartState or History State
            sv.addToCurrentStates(ctx);
            TransitionLink[] tis = sv.getFromTransitions();
            for (int i=0; i < tis.length; i++) {
                TransitionLink tl = tis[i];
                tl.enable(ctx);
                if(tl.isLambda()) {
                    break; // only go through at most one lambda
                }
                //tl.trigger(ctx, null);
            }
        }
        ctx.isRecovered = false;

        //TODO - need to ask PUNEET - what is timeout for machine on recovery
        Object args[] = new Object[] { getAssociatedInstance() };
        long machineTimeout = 0L;
        try {
            machineTimeout = csv.getTimeout(args) * csv.getTimeoutMultiplier();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(machineTimeout > 0)
            ctx.watchState(csv, machineTimeout);
    }
}
