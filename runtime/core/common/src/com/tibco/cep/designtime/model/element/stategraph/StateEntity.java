package com.tibco.cep.designtime.model.element.stategraph;


import java.awt.Rectangle;

import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 10:38:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateEntity extends Entity {


    /**
     * Get the bounding rectangle for this decoration element.
     *
     * @return The bounding rectangle of this decoration element.
     */
    Rectangle getBounds();


    /**
     * Get the state machine that this state belongs to.
     *
     * @return The state machine that this state belongs to.
     */
    StateMachine getOwnerStateMachine();


    /**
     * Get the parent of this state entity.  Certain entities have no useful parent (e.g.
     * StateMachine has no parent).  For all states and decorations the return type will
     * be a StateComposite, for all links the return type will be the owner StateMachine.
     *
     * @return The parent of this state entity.
     */
    StateEntity getParent();

    /* * Get the timeout (time before this transition is traversed automatically) for this transition.
     * @return The timeout (time before this transition is traversed automatically) for this transition.
     * /
    public int getTimeout ();
    */


    /**
     * Set the timeout units (the time unit e.g. minute, for the timeout value).
     * The values are to be taken from Event.UNITS
     *
     * @return The timeout units (the time unit e.g. minute, for the timeout value).
     */
    int getTimeoutUnits();


    StateMachineRuleSet getRuleSet();

}
