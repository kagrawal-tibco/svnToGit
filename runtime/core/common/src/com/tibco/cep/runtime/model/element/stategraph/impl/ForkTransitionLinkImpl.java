package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.stategraph.ForkTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StartStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 18, 2004
 * Time: 7:49:54 PM
 * To change this template use File | Settings | File Templates.
 */

public class ForkTransitionLinkImpl extends StartTransitionLinkImpl implements ForkTransitionLink{
    StartStateVertexImpl [] targets;

    public ForkTransitionLinkImpl(String name,
                                  CompositeStateVertexImpl superState,
                                  StartStateVertexImpl [] targets) {
        super(name, superState, null);
        this.targets=targets;
    }

    protected void leaveSource(StateMachineContext ctx, Event x) {
        super.leaveSource(ctx, x);
    }

    public void disable(StateMachineContext ctx) {
        // Do Nothing
    }

    public void enable(StateMachineContext ctx) {
        // Do Nothing
    }

    protected void enterTarget(StateMachineContext ctx, Object[] x) {
        for (int i=0; i < targets.length; i++) {
            targets[i].enter(ctx,x,null);
        }
    }

    protected void enterTargetSilently(StateMachineContext ctx, Object[] x) {
        for (int i=0; i < targets.length; i++) {
            targets[i].enterSilently(ctx,x,null);
        }
    }

    public StartStateVertex[] getTargets() {
        return targets;
    }
}
