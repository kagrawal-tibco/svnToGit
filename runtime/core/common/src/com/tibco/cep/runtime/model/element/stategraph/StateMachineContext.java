package com.tibco.cep.runtime.model.element.stategraph;

import java.util.List;

import com.tibco.cep.runtime.model.element.Concept;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 1:40:54 PM
 * To change this template use File | Settings | File Templates.
 */

public interface StateMachineContext {

    public boolean forwardCorrelates();

    public void setCorrelationStatus(TransitionLink t, boolean correlated);

    public boolean isCorrelated(TransitionLink t);

    public void setTransitionActive (TransitionLink t);
    public void setTransitionInActive (TransitionLink t);
    public boolean isTransitionActive (TransitionLink t);


    public void closeMachine();
    public boolean isMachineClosed();

    public void watchState(StateVertex vertex, long period);

    public void setTransitionStatus(TransitionLink t, byte status);

    /**
     * Notify the context that the region within a concurrent state ended.
     * The context will return how many regions have ended including this one;
     * @param csv
     */
    public int regionEnded(CompositeStateVertex csv);

    /**
     * Notify the context that a concurrent state has been entered.
     * @param concurrent
     */

    public void concurrentEntered(CompositeStateVertex concurrent);

    /*
    State status API
    */
    public boolean isStateActive(StateVertex state);
    public boolean isStateComplete(StateVertex state);
    public boolean isStateTimedOut(StateVertex state);
    public boolean isStateWaiting(StateVertex state);
    public boolean isStateDeterministic(StateVertex state);
    public int getStateStatus(StateVertex state);

    Concept getSubject();

    void addCorrelationObjects(int index, Object[] objs);

    StartStateVertex getPathStart(int index);

    List getNextStates(int pathId, int stateIndex);

    List getTimeEvents(int stateId);

    long[] getEventIDs(int stateId);

    long[] getConceptIDs(int stateId);

    int[] calculatePathIDs();
}
