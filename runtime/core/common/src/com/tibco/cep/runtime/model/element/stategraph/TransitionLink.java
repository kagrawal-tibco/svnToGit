package com.tibco.cep.runtime.model.element.stategraph;




/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 12:50:51 PM
 * To change this template use File | Settings | File Templates.
 */
/*
  * A transition is a directed relationship between a source state and a target state. It may be part of a compound
  * transition, which takes the state machine from one state configuration to another, representing the complete response
  * of the state machine to a particular event instance.
  */

 /*
  * Transition execution sequence
  * Every transition, except for internal transitions, causes exiting of a source state, and entering of the target state
  * These two states, which may be composite, are designated as the main source and main target of a transition.
  * The least common ancestor (LCA) state of transition is the lowest composite state that contains all the explicit
  * source states and explicit target states of the compound transition.
  * If the LCA is not a concurrent state, the main source is a direct substate of the LCA that contains the explicit
  * source states, and the main target is a substate of the LCA that contains the explicit target states.
  * In case where the LCA is a concurrent state, the main source and the main target are the concurrent state itself.
  * The reason is that if a concurrent region is exited, it forces exit of the entire concurrent state.
  */
  /*
   *
   *
   */

public interface TransitionLink extends ModelElement{
    public static final byte STATUS_WAITING         = 0; // Transition idle,
    public static final byte STATUS_READY           = 1; // Transition ready, and Waiting for an event
    public static final byte STATUS_TIMEOUT         = 2; // Transition timedout
    public static final byte STATUS_COMPLETE        = 3; // Transition completed,
    public static final byte STATUS_LAMBDA          = 4; // Transition had not rule, just went thru
    public static final byte STATUS_SKIPPED         = 5; // Transition had skipped
    public static final byte STATUS_AMBIGUOUS       = 6; // Could not say the status of transition.

    //added by ISS
    public static final byte STATUS_WILL_BE_READY   = 7; // About to become enabled (executing onEntry)
    public static final byte STATUS_CORRELATED      = 8; // Used for Correlation Graphs when a transition's non guard conditions have been satisfied

    public static final String[] STATUSES = new String[] {"WAITING", "READY", "TIMED-OUT", "COMPLETED", "LAMBDA", "SKIPPED", "INDETERMINATE", "WILL-ENABLE", "CORRELATED" };
    public void     enable(StateMachineContext ctx);
    public void     disable(StateMachineContext ctx);
    public boolean  isEnabled(StateMachineContext ctx);
//    public void trigger (StateMachineContext ctx, Event x);
    public void trigger(TransitionLinkAction action, StateMachineContext ctx, Object[] closure);



    //TODO isLambda == emptyCondition && emptyAction 
    /**
     * @return true if the transition has no condition (but may have an action)
     */
    public boolean emptyCondition();

    /*
     * @return true if the transition is not dependent on an external rule
     */
    public boolean  isLambda();
    public StateVertex      source();
    public StateVertex      target();
    public int getTimeout();
}
