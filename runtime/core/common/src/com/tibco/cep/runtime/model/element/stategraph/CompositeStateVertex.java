package com.tibco.cep.runtime.model.element.stategraph;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 12:47:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CompositeStateVertex extends StateVertex{

    final static public int ROOT_INDEX = -1;

    /* Returns if the composite state has a shallow history state */
    public boolean hasHistoryState();

    /* Return the child states */
    public StateVertex [] subStates();

    /* Return true if the composite state is a region in the parent superstate */
    public boolean isRegion ();

    /*
     * Return true if this composite state is the top of the state machine
     */

    public boolean isConcurrent();

    /*
     * @return true if this is the root
     */
    public boolean isTop ();

    /*
     * @return Returns the default start state
     */

    public StartTransitionLink getDefaultTransition ();

    /*
     * Gets the history state if enabled
     */
    public SimpleTransitionLink historyTransition (StateMachineContext ctx);

    /*
     * Gets all the Group Transitions that end in this composite state
     */

//    public TransitionLink [] getToTransitions();

    /*
     * Gets all the Group Transitions that originate from this composite state
     */

//    public TransitionLink [] getFromTransitions();

    public SimpleStateVertex getDefaultState();

    public boolean isSubMachine();

    

}
