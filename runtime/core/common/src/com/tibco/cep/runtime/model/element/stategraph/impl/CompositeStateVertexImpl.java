package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.GroupTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.SimpleStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.SimpleTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StartTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 2:07:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class CompositeStateVertexImpl extends StateVertexImpl implements CompositeStateVertex{
    boolean hasHistoryState, isConcurrent;
    StateVertexImpl [] subStates;
    StartTransitionLinkImpl defaultTransition;
    GroupTransitionLink [] toTransitions;
    GroupTransitionLink [] fromTransitions;
    boolean isTop=false;
//    int curTo=0, curFrom=0, curSub=0;
    SimpleStateVertex mDefaultState = null;
    int mConcurrentIndex = -1;

    /*
    * Constructor for top-level composite states
    *
    */

    public CompositeStateVertexImpl(String name) {
        super(name, null);
        isTop=true;
        isConcurrent=false;
    }

    public CompositeStateVertexImpl(String name,
                                    CompositeStateVertexImpl superState,
                                    boolean hasHistoryState,
                                    boolean isConcurrent) {
        super(name, superState);
        this.hasHistoryState=hasHistoryState;
        this.isConcurrent=isConcurrent;
        if (superState == null) {
            this.isTop=true;
        }
    }

    public boolean isConcurrent() {
        return isConcurrent;
    }

    /* Returns if the composite state has a shallow history state */
    public boolean hasHistoryState() {
        return hasHistoryState;
    }

    /* Return the child states */
    public StateVertex[] subStates() {
        return subStates;
    }

    public boolean isRegion() {
        return false;
    }

    public StartTransitionLink getDefaultTransition() {
        return defaultTransition;
    }

    public void setDefaultTransition(StartTransitionLinkImpl start) {
        defaultTransition=start;
    }

    /*
    * Gets the history transition if enabled
    */

    public SimpleTransitionLink historyTransition(StateMachineContext ctx) {
        return null;
    }


    protected void preEnableNext(StateMachineContext ctx) {
        // pre-enable non-empty condition rules
        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if (!(transitions[j].isLambda() || transitions[j].emptyCondition())) {
                ctx.setTransitionStatus(transitions[j], TransitionLink.STATUS_WILL_BE_READY);
            }
        }
    }

    protected void enableNext (StateMachineContext ctx) {
        // Enable all the non-completion group transitions
        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if (!(transitions[j].isLambda() || transitions[j].emptyCondition())) {
                transitions[j].enable(ctx);
            }
        }
    }

    protected void enableCompletion (StateMachineContext ctx, TransitionLink t) {
        // Enable all the non-completion group transitions
        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if (transitions[j].isLambda() || transitions[j].emptyCondition()) {
                transitions[j].enable(ctx);
                if(transitions[j].isLambda()) return; // only take the first lambda
            }
        }
    }

    public void leave(StateMachineContext context, TransitionLink t, byte status) {
        StateMachineContextImpl ctx = (StateMachineContextImpl) context;
        switch (status) {
            case StateVertex.STATUS_LEAVE_NEXT :
                // If the state is exited because of a non-completion group transition being fired
                // then disable all the active states
                disableNext(ctx,t);
                super.leave(ctx, t, status);
                _leave(ctx, t, status);
                break;

            case StateVertex.STATUS_LEAVE_CHILD_FINAL:
                // If this is a region, decrement the superstate
                if (isRegion()) {
                    ((CompositeStateVertexImpl) getSuperState()).leave(ctx,t,StateVertex.STATUS_LEAVE_CHILD_FINAL);
                    super.leave(ctx, t, status);
                    _leave(ctx, t, status);
                }

                // If this is a concurrent state, a child region must have ended,
                // so check if all regions are completed
                else if (isConcurrent()) {
                    // Check the counter, if all substates are done then exit the concurrent state
                    if(ctx.regionEnded(this) == subStates.length) {

                        // In this case, t is the transition belogin
                        enableCompletion(ctx, t);
                    }
                }

                // If the state is a regular composite, and it exited because of
                // reaching the final state, then just enable the completion transition
                else {
                    // There should be only one transition and that should be a completion transition
                    // The constraint is that if there is finalstate substate within a composite state
                    // then one of the transitions from the group should be a completion transition
                    if (isTop) {
                        ctx.closeMachine();
                        return;
                    }

                    else {
                        CompositeStateVertexImpl superState = (CompositeStateVertexImpl) getSuperState();
                        // if this is a concurrent's region , notify the concurrent that a region has ended
                        // todo as a result of code gen, there is an extraneous root state for each submachine invocation
                        // todo so we must notify the parent of that root state.  This should be removed from code generation.
                        if((superState != null && (superState.isConcurrent() || superState.isSubMachine()))) {
                            superState.leave(ctx, t, status);
                        }

                        // otherwise we enable the completion transition, whether it's a submachine
                        // or just a regular composite
                        else {
                            enableCompletion(ctx, t);
                        }
                    }
                }
                break;

            case StateVertex.STATUS_LEAVE_PARENT:
            case StateVertex.STATUS_LEAVE_CHILD:
                // If the state is exited because of a parent group transition being fired
                super.leave(ctx, t, status);
                disableNext(ctx,t);
                break;
            case StateVertex.STATUS_TIMEOUT:
                /**
                 * A timeout caused this state to be left.  Mark all sub-transitions appropriately.
                 */
                super.leave(ctx, t, status);
                _leave(ctx, t, status);
//                if(!isConcurrent() && (subStates != null)) {
//                    for(int i = 0; i < subStates.length; i++) {
//                        StateVertexImpl subState = subStates[i];
//                        ctx.timeoutState(subState, null);
//                    }
//                }
                leaveSubStatesOnTimeout(ctx);
                break;
            case StateVertex.STATUS_TIMEOUT_CHILD_FINAL:
                // If the state is exited because of reaching the final state, then just enable the
                // completion transition
                // If this composite state is part of a region then decrement the superstate
                // This is the same as a transition being taken, except no entry/exit actions are fired
                if (isRegion()) {
                    ((CompositeStateVertexImpl) getSuperState()).leave(ctx,t,StateVertex.STATUS_TIMEOUT_CHILD_FINAL);
                }

                else if (isConcurrent()) {
                    // Check the counter, if all substates are done then exit the concurrent state

                    if (ctx.regionEnded(this) == subStates.length) {
                        // Join successful, We're done
//                        super.leave(ctx, t, status);
//                        _leave(ctx, t, status);
                        enableCompletion(ctx,t);
                    }
                }

                else {
                    // There should  be only one transition and that should be a completion transition
                    // The constraint is that if there is finalstate substate within a composite state
                    // then one of the transitions from the group should be a completion transition
//                    super.leave(ctx, t, status);
//                    _leave(ctx,t,status);

                    if (isTop) {
                        ctx.closeMachine();
                        return;
                    }

                    else {
                        CompositeStateVertexImpl superState = (CompositeStateVertexImpl) getSuperState();
                        // if this is a concurrent's region , notify the concurrent that a region has ended
                        // todo as a result of code gen, there is an extraneous root state for each submachine invocation
                        // todo so we must notify the parent of that root state.  This should be removed from code generation.
                        if((superState != null && (superState.isConcurrent() || superState.isSubMachine()))) {
                            superState.leave(ctx, t, status);
                        }

                        // otherwise we enable the completion transition, whether it's a submachine
                        // or just a regular composite
                        else {
                            enableCompletion(ctx, t);
                        }
                    }

                }
                break;

        }
    }

    protected void leaveSubStatesOnTimeout(StateMachineContext ctx) {
        StateVertex [] substates= subStates();
        if(substates == null) return;
        for (int i=0; i < substates.length;i++) {
            if (substates[i] instanceof SimpleStateVertexImpl) {
                SimpleStateVertexImpl kid= (SimpleStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
//                    kid.leave(ctx,null, StateVertex.STATUS_LEAVE_PARENT);
                    kid.disableNext(ctx, null);
                }
            } else if (substates[i] instanceof CompositeStateVertexImpl) {
                CompositeStateVertexImpl kid= (CompositeStateVertexImpl) substates[i];
                if (kid.currentState(ctx)) {
                    kid.leaveSubStatesOnTimeout(ctx);
//                    kid.leave(ctx,null, StateVertex.STATUS_LEAVE_PARENT);
                    kid.disableNext(ctx, null);
                }
            }
        }
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

        // If we are not the source of the non-null transition, then it must have been on
        // of our sub-states, so don't skip our transitions
        if((t != null) && !this.equals(t.source())) return;

        TransitionLink [] transitions= getFromTransitions();
        for (int j=0; j < transitions.length;j++) {
            if ((t == null) || (!transitions[j].equals(t))) {
                ctx.setTransitionStatus(transitions[j], TransitionLink.STATUS_SKIPPED);
            }
        }
    }

    protected void setSubStates(StateVertexImpl [] subStates){
        this.subStates=subStates;
    }

    protected void setFromTransitions(GroupTransitionLink [] t) {
        fromTransitions=t;
    }

    protected void setToTransitions(GroupTransitionLink [] t) {
        toTransitions=t;
    }

    public TransitionLink[] getToTransitions() {
        return toTransitions;
    }

    public TransitionLink[] getFromTransitions() {
        return fromTransitions;
    }


    public boolean isTop() {
        return isTop;
    }

    public SimpleStateVertex getDefaultState() {
        return mDefaultState;
    }

    public void setDefaultState(SimpleStateVertex vertex) {
        mDefaultState = vertex;
    }

    public void printGraph () {
        System.out.println("Composite State: " + getName());
        System.out.println("Num Transitions In " +  getToTransitions().length);
        System.out.println("Num Transitions Out " + getFromTransitions().length);
        System.out.println("Num Sub States " + subStates().length);
        StateVertex [] kids = subStates();
        for (int i=0; i < kids.length;i++) {
            if (kids[i] instanceof CompositeStateVertex) {
                ((CompositeStateVertexImpl)kids[i]).printGraph();
            } else {
                SimpleStateVertex kid = (SimpleStateVertex) kids[i];
                System.out.println("Child State: " + kid.getName());
                System.out.println("Num Transitions In " + kid.getToTransitions().length);
                System.out.println("Num Transitions Out " + kid.getFromTransitions().length);
            }
        }
    }

    public boolean isSubMachine() {
        return false;
    }

    public int getConcurrentIndex() {
        return mConcurrentIndex;
    }

    public void setConcurrentIndex(int index) {
        mConcurrentIndex = index;
    }

    // don't allow composite states to be returned by the function
    protected void addToCurrentStates(StateMachineContext ctx) {}

    protected void removeFromCurrentStates(StateMachineContext ctx) {
        super.removeFromCurrentStates(ctx);
        if(subStates == null) {
            return;
        }
        for(int i = 0; i < subStates.length; i++) {
            subStates[i].removeFromCurrentStates(ctx);
        }
    }
}
