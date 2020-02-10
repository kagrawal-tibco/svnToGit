package com.tibco.cep.runtime.model.element.impl.property.simple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 13, 2006
 * Time: 12:46:07 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyStateMachineReferencedState extends PropertyStateMachineCompositeState{

    protected PropertyStateMachineReferencedState(Object owner) {
        super(owner);
    }

    protected PropertyStateMachineReferencedState(Object owner, int defaultValue) {
        super(owner, defaultValue);
    }

    public boolean isComplete() {
        return false; // TODO:    
    }

    protected void _exit(Object[] args, boolean executeExitMethods, boolean notifyParent, boolean timedOut, boolean selfTransition) {
        super._exit(args, executeExitMethods, notifyParent, timedOut, selfTransition);
        retractReferenced();
    }

    public void complete(Object[] args, boolean selfTransition) {
        super.complete(args, selfTransition);
        retractReferenced();
    }

    public void timeout(Object[] args) {
        super.timeout(args);
        retractReferenced();

    }
}
