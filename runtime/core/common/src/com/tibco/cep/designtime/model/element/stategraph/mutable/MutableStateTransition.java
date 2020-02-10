/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 5:22:43 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;

import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;


/**
 * A link between any two states on a state machine.  This gives a direction and a rule
 * that determines when the link can be traversed (and the rule can have an action).
 */
public interface MutableStateTransition extends StateTransition, MutableStateLink {


    /**
     * Set the new "from" State (where the link originates) for this link.
     *
     * @param fromState The new "from" State (where the link originates) for this link.
     */
    public void setFromState(
            State fromState);


    /**
     * Set whether this transition is a "lambda" transition (has no condition and is taken immediately).
     *
     * @param isLambda Is this transition a "lambda" transition.
     */
    public void setIsLambda(
            boolean isLambda);


    /**
     * Set the new "to" State (where the link terminates) for this link.
     *
     * @param toState The new "to" State (where the link terminates) for this link.
     */
    public void setToState(
            State toState);


    public void setPriority(int priority);


    public void setForwardCorrelate(boolean fwdCorrelate);
}// end interface StateTransition
