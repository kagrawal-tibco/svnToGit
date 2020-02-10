package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLinkAction;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 5:15:11 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class TransitionLinkImpl extends ModelElementImpl implements TransitionLink{
    boolean enabled=false;
    protected StateVertexImpl source;
    protected StateVertexImpl target;
    CompositeStateVertexImpl [] targetAncestors = new CompositeStateVertexImpl[0];
    CompositeStateVertexImpl [] sourceAncestors = new CompositeStateVertexImpl[0];
    boolean isLambda=false;
    boolean emptyCondition = false;
    int timeout;



    protected TransitionLinkImpl(String name,StateVertexImpl source, StateVertexImpl target, boolean isCompletion, boolean emptyCondition) {
        this(name,source,target,isCompletion,emptyCondition,0);
    }
    protected TransitionLinkImpl(String name,StateVertexImpl source, StateVertexImpl target, boolean isCompletion, boolean emptyCondition, int timeout) {
        super(name);
        this.source=source;
        this.target=target;
        this.isLambda=isCompletion;
        this.emptyCondition = emptyCondition;
        this.timeout = timeout;
    }

    public void disable(StateMachineContext ctx) {
        ctx.setTransitionInActive(this);
    }

    public boolean isEnabled(StateMachineContext ctx) {
        return ctx.isTransitionActive(this);
    }


    public void enable(StateMachineContext ctx) {
        ctx.setTransitionActive(this);
        if (isLambda) {
            trigger(null, ctx, new Object[] {ctx.getSubject()});
        }

    }

    protected void leaveSource (StateMachineContext ctx, Object[] x) {
        source.leave(ctx,this,StateVertex.STATUS_LEAVE_NEXT);

        for (int i=0; i < sourceAncestors.length ;i++) {
            CompositeStateVertexImpl sa= (CompositeStateVertexImpl) sourceAncestors[i];
            sa.leave(ctx,null,StateVertex.STATUS_LEAVE_CHILD);
        }
    }

    protected void enterTarget (StateMachineContext ctx, Object[] x) {

        for (int i=0; i < targetAncestors.length ;i++) {
            CompositeStateVertexImpl ta= (CompositeStateVertexImpl) targetAncestors[i];
            ta.enter(ctx,x,this);
        }
        target.enter(ctx, x, this);
        return;
    }

    protected void enterTargetSilently(StateMachineContext ctx, Object[] args) {
        for(int i = 0; i < targetAncestors.length; i++) {
            CompositeStateVertexImpl ta = (CompositeStateVertexImpl) targetAncestors[i];
            ta.enterSilently(ctx,args,this);
        }

        target.enterSilently(ctx, args, this);
    }

    /*
     * Leave source, disable yourself and enable target
     * @param statemachine
     * @param event
     */

    public void trigger(TransitionLinkAction actionImpl, StateMachineContext ctx, Object[] x) {
        trigger(actionImpl, ctx,x,TransitionLink.STATUS_COMPLETE);
    }

    public boolean emptyCondition() {
        return emptyCondition;
    }

    public void triggerSilently(StateMachineContext ctx, Object[] args) {
        // no real concept of "leaving silently" so no leaveSourceSilently()
        ctx.setTransitionStatus(this, STATUS_AMBIGUOUS);
        enterTargetSilently(ctx, args);
    }

    public void trigger(TransitionLinkAction linkAction, StateMachineContext ctx, Object[] x, byte status) {

        // Leaves the source
        leaveSource(ctx, x);

/// todo SS        if (tracer.hasDebugRole()) {
//            tracer.trace(BETrace.DEBUG, "Transition["+toString() + "] action firing");
//        }
        ctx.setTransitionStatus(this, status);

        if(status == STATUS_COMPLETE || status == STATUS_TIMEOUT || status == STATUS_LAMBDA) {
            StateMachineContextImpl ctxImpl = (StateMachineContextImpl) ctx;
            ctxImpl.invalidateTransitionsFor(source);
            ctxImpl.invalidateTimersFor(source);
        }

        if (linkAction != null) {
            // for fwd correlations this does nothing
            // for other transitions this fires the action, and also adds the correlation objects
            // adding the correlation objects sets the correlated status to true
            linkAction.action(x);
        }

        if(ctx.forwardCorrelates()) {
            ctx.setCorrelationStatus(this, false);
        }

        // Enter the target, Target can be calculated runtime by a transition
        enterTarget(ctx,x);
    }

    public StateVertex source() {
        return source;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StateVertex target() {
        return target;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isLambda() {
        return isLambda;
    }

    protected void setSourcePath(CompositeStateVertexImpl [] sourceAncestors) {
        this.sourceAncestors=sourceAncestors;
    }

    protected void setTargetPath(CompositeStateVertexImpl [] targetAncestors) {
        this.targetAncestors=targetAncestors;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public String toString() {
        return source.getName() + "--->" + target.getName();
    }
}
