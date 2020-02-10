package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.InternalTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 1:12:26 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class StateVertexImpl extends ModelElementImpl implements StateVertex {
    String designTimeConceptPath;
    CompositeStateVertex superState;
    boolean hasSuperState;
    InternalTransitionLink internalTransition;
    protected byte m_timeoutPolicy = NO_ACTION_TIMEOUT_POLICY;
    protected StateVertexImpl m_timeoutState;

    protected long m_timeoutMultiplier = 1L;


    
    public StateVertexImpl (String name, CompositeStateVertexImpl superState) {
        super(name);
        setIndex(-1);
        this.superState=superState;
        if (superState != null) {
            this.hasSuperState=true;
        }
    }

    public long getTimeoutMultiplier() {
        return m_timeoutMultiplier;
    }

    public void setTimeoutMultiplier(long multiplier) {
        m_timeoutMultiplier = multiplier;
    }

    public long getTimeout(Object[] args) {
        return 0L;
    }

    public byte getTimeoutPolicy() {
        return m_timeoutPolicy;
    }

    public StateVertex getTimeoutState() {
        return m_timeoutState;
    }

    public void setTimeoutState(StateVertexImpl timeoutState) {
        m_timeoutState = timeoutState;
    }

    /* @return true if this state is contained in a composite state */
    public boolean hasSuperState() {
        return hasSuperState;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* @return parent state */
    public CompositeStateVertex getSuperState() {
        return superState;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void enter(StateMachineContext ctx, Object[] evt, TransitionLink t) {
        // Enable all the internal transitions
        if (internalTransition != null) {
            internalTransition.enable(ctx);
        }

        addToCurrentStates(ctx);

        _enter(ctx, evt, t);
//        ctx.stateIn(this,evt);
    }

    public void _enter(StateMachineContext ctx, Object[] evt, TransitionLink t) {
///todo SS: tracing        if (mTrace.hasDebugRole()) {
//            mTrace.trace(Trace.DEBUG, "Entering State=" + getName());
//        }

        preEnableNext(ctx);

        /** Invoke the entry action */
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        Object[] args = new Object[1];
        args[0] = ctxImpl.getSubject();
        onEntry(args);

        enableNext(ctx);

        //++ SS Added for Timeout
        long timeout = 0L;
        try {
            timeout = getTimeout(args) * getTimeoutMultiplier();
        } catch(Exception e) {
            System.err.println("Exception occurred while calculating Timeout for: " + getName());
            System.err.println("Using timeout of zero.");
            e.printStackTrace();
        }

        if (timeout > 0) {
            ctx.watchState(this, timeout);
        }
        //-- SS Added for Timeout
    }

    protected void preEnableNext(StateMachineContext ctx) {
        TransitionLink[] tls = getFromTransitions();
        for(int i = 0; i < tls.length; i++) {
            ctx.setTransitionStatus(tls[i], TransitionLink.STATUS_WILL_BE_READY);
        }
    }

    protected void enableNext(StateMachineContext ctx) {
        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            transitions[j].enable(ctx);
            if(transitions[j].isLambda()) return; // only go through one lambda
        }
    }

    public void leave(StateMachineContext ctx, TransitionLink t, byte status) {

        // Disable all the internal transitions
        if (internalTransition != null) {
            internalTransition.disable(ctx);
        }

//        ctx.stateOut(this);
    }

    public void _leave(StateMachineContext ctx, TransitionLink t, byte status) {
/// todo SS        if (mTrace.hasDebugRole()) {
//            mTrace.trace(Trace.DEBUG, "Leaving State=" + getName() + ":" + status);
//        }

        /** Invoke the exit action */
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        Object[] args = new Object[1];
        args[0] = ctxImpl.getSubject();
        onExit(args);

        removeFromCurrentStates(ctx);

        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if ((t == null) || (!transitions[j].equals(t))) {
                ctx.setTransitionStatus(transitions[j], TransitionLink.STATUS_SKIPPED);
            }
        }
    }

    public boolean currentState(StateMachineContext ctx) {
//        return ctx.currentState(this);
        return ctx.isStateActive(this);
    }

    public void stay(StateMachineContext ctx, Object[] evt, InternalTransitionLink t) {
//        ctx.stateStay(this,evt);
    }

    public InternalTransitionLink getInternalTransition() {
        return internalTransition;
    }

    public void onEntry(Object[] args) {}

    public void onExit(Object[] args) {}

    public void onTimeout(Object[] args) {}

    public void setInternalTransition(InternalTransitionLink t) {
        this.internalTransition=t;
    }

//    public long getTimeout() {
//        return m_timeoutMS;
//    }
//
//    /**
//     * This is an internal function, not to be used. The timeout is always specified at the transition. The source of
//     * the transition is this state. There can be N-arcs(transitions) from this state. The state's timeout value is the max of
//     * the transition(s) timeout emerging from this state.
//     * Note this timeout is corelation timeout, and has nothing to do with actual timeout that the target system sends
//     * @param timeout
//     */
//    public void setTimeout(long timeout) {
//        m_timeoutMS = timeout;
//    }

    public void setTimeoutPolicy(byte policy) {
        m_timeoutPolicy = policy;
    }

    protected void disableNext (StateMachineContext ctx, TransitionLink t) {
        TransitionLink [] transitions = getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if ((t == null) || (!transitions[j].equals(t))) {
                if (!transitions[j].isLambda()) {
//                    transitions[j].disable(ctx);
                    ctx.setTransitionStatus(transitions[j], TransitionLink.STATUS_SKIPPED);
                }
            }
        }
    }

    public void
            enterSilently(StateMachineContext ctx, Object[] args, TransitionLinkImpl transitionLink) {
///        if (mTrace.hasDebugRole()) {
//            mTrace.trace(Trace.DEBUG, "Ambiguously Entering State =" + getName());
//        }

        addToCurrentStates(ctx);

        preEnableNext(ctx);

        // no actions

        enableNext(ctx);

        //++ SS Added for Timeout
        long timeout = 0L;
        try {
            timeout = getTimeout(args) * getTimeoutMultiplier();
        } catch(Exception e) {
            System.err.println("Exception occurred while calculating Timeout for: " + getName());
            System.err.println("Using timeout of zero.");
            e.printStackTrace();
        }

        if (timeout > 0) {
            ctx.watchState(this, timeout);
        }
        //-- SS Added for Timeout
    }

    public String getDesignTimeConceptPath() {
        return designTimeConceptPath;
    }

    public void setDesignTimeConceptPath(String path) {
        designTimeConceptPath = path;
    }

    protected void addToCurrentStates(StateMachineContext ctx) {
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        ctxImpl.m_currStates.add(this);
    }

    protected void removeFromCurrentStates(StateMachineContext ctx) {
        StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
        ctxImpl.m_currStates.remove(this);
    }
}