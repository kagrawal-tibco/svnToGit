package com.tibco.cep.runtime.model.element.stategraph;

import com.tibco.cep.runtime.model.element.Concept;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 19, 2004
 * Time: 4:21:51 PM
 * To change this template use File | Settings | File Templates.
 */

 /*
  * This graph represents the semantics of a state model resource in the repository.
  * This class provides the runtime semantics of a state machine
  */

public interface StateMachine {

    public Concept getAssociatedInstance();

//    public boolean isInState(StateVertex state);

//    public int countInState(StateVertex state);

    public boolean isComplete();

    public Concept getHistory();

    public StateGraphDefinition getGraphDefinition();

//    public void fire (TransitionLink t, Event e);

    public boolean isActivated(TransitionLink t);

    public void fire(TransitionLinkAction linkAction, int transitionIdx, Object[] closure);

    public void start();
}
