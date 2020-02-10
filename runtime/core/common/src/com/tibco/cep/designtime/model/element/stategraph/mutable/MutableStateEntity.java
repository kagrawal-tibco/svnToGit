/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 5:27:15 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import java.awt.Rectangle;

import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * This is the root interface that all elements of a State Machine must extend.
 */
public interface MutableStateEntity extends StateEntity, MutableEntity {


    /**
     * Set the new bounding rectangle for this decoration element.
     *
     * @param bounds The new bounding rectangle of this decoration element.
     */
    public void setBounds(
            Rectangle bounds);


    /**
     * Set the state machine that this state belongs to.
     *
     * @param ownerStateMachine The state machine that this state belongs to.
     */
    public void setOwnerStateMachine(
            MutableStateMachine ownerStateMachine);

///** Set the timeout (time before this transition is traversed automatically) for this transition.
// * @param timeout The timeout (time before this transition is traversed automatically) for this transition.*/
//public void setTimeout (
//    int                     timeout);


    /**
     * Set the timeout units (the time unit e.g. minute, for the timeout value).
     * The value is interpreted as from Event.UNITS
     *
     * @param timeoutUnits The timeout units (the time unit e.g. minute, for the timeout value).
     */
    public void setTimeoutUnits(
            int timeoutUnits);


    void setGUID(String guid);
}// end interface StateEntity
