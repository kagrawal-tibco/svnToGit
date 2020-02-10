package com.tibco.cep.designtime.model.element.stategraph;


import java.util.List;

import com.tibco.cep.designtime.model.element.Concept;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 4:14:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateMachine extends StateEntity {


    /**
     * Get the Concept that "owns" this StateMachine.
     *
     * @return The Concept that "owns" this StateMachine.
     */
    Concept getOwnerConcept();


    /**
     * Get the root state of this state machine.
     *
     * @return The root state of this state machine.
     */
    StateComposite getMachineRoot();


    /**
     * Get a List containing all the StateAnnotationLinks in this state machine.
     */
    List getAnnotationLinks();


    /**
     * Get a List containing all the StateTransitions in this state machine.
     */
    List getTransitions();


    /**
     * Get whether this state machine as the "main" state machine.  This means that this state machine
     * is the entry point and no other state machine can be.
     *
     * @return Is this StateMachine the main entry state machine?
     */
    boolean isMain();


    boolean forwardCorrelates();


    String getOwnerConceptPath();
}
