package com.tibco.cep.runtime.model.element.stategraph;



/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 12:43:26 PM
 * To change this template use File | Settings | File Templates.
 */

public interface StateVertex extends ModelElement{
    public static final byte STATUS_INACTIVE                =  '0'; // Not Waiting for any event
    public static final byte STATUS_READY                   =  '1'; // Waiting for an event
    public static final byte STATUS_ENTER                   =  '2'; // Event received, State Entered
    public static final byte STATUS_LEAVE_NEXT              =  '3'; // Leave State, Next Transition Fired
    public static final byte STATUS_LEAVE_PARENT            =  '4'; // Leave State, Group Transition Fired
    public static final byte STATUS_LEAVE_CHILD             =  '5'; // Leave State, SubState Transitions Out to higher level
    public static final byte STATUS_LEAVE_CHILD_FINAL       =  '6'; // Leave State, Final SubState Reached
    public static final byte STATUS_TIMEOUT                 =  '7'; // State timed out
    public static final byte STATUS_TIMEOUT_CHILD_FINAL     =  '8'; // Final Substate timed out

    public static final byte NO_ACTION_TIMEOUT_POLICY = 0x00;
    public static final byte NON_DETERMINISTIC_STATE_TIMEOUT_POLICY = 0x01;
    public static final byte DETERMINISTIC_STATE_POLICY = 0x02;

    public byte getTimeoutPolicy();
    public com.tibco.cep.runtime.model.element.stategraph.StateVertex getTimeoutState();
    public long getTimeout(java.lang.Object[] args);
    public long getTimeoutMultiplier();

    /* @return true if this state is contained in a composite state */
    public boolean hasSuperState ();

    /* @return parent state */
    public CompositeStateVertex getSuperState ();

    /* @return Internal Transition, null if none */
    public InternalTransitionLink getInternalTransition();
    
    public TransitionLink [] getToTransitions();
    public TransitionLink [] getFromTransitions();

    public void onEntry(java.lang.Object[] args);
    public void onExit(java.lang.Object[] args);
    public void onTimeout(java.lang.Object[] args);

    public java.lang.String getDesignTimeConceptPath();

}

