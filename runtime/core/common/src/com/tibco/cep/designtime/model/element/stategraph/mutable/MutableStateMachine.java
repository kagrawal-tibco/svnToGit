/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 29, 2004
 * Time: 7:46:02 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import java.util.List;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;


/**
 * The complete state machine which contains all states, links and decorations.
 */
public interface MutableStateMachine extends StateMachine, MutableStateEntity {


    /**
     * Add the annotation link passed to this state machine at the index passed.  If the
     * index is greater that the number of annotation links in the state machine it is
     * added to the end.
     *
     * @param index             Where to add the new annotation link.
     * @param newAnnotationLink An annotation link to add to this state machine.
     */
    public void addAnnotationLink(
            int index,
            StateAnnotationLink newAnnotationLink);


    /**
     * Add the transition passed to this state machine at the index passed.  If the
     * index is greater that the number of transitions in the state machine it is
     * added to the end.
     *
     * @param index              Where to add the new transition.
     * @param newStateTransition A transition to add to this state machine.
     */
    public void addTransition(
            int index,
            StateTransition newStateTransition);


    /**
     * Delete the annotation link at the index passed from this state machine.  If the
     * index is greater that the number of annotation link in the state machine the
     * delete is ignored.
     *
     * @param index Which annotation link to delete from this state machine.
     */
    public void deleteAnnotationLink(
            int index);


    /**
     * Delete the annotation link passed from this state machine.
     *
     * @param annotationLinkToDelete Which annotation link to delete from this state machine.
     */
    public void deleteAnnotationLink(
            StateAnnotationLink annotationLinkToDelete);


    /**
     * Delete all transitions that the passed state is linked to.
     *
     * @param stateToUnlink The state to unlink.
     */
    public void deleteLinkedTransitions(
            State stateToUnlink);


    /**
     * Delete the transition at the index passed from this state machine.  If the
     * index is greater that the number of transitions in the state machine the
     * delete is ignored.
     *
     * @param index Which transition to delete from this state machine.
     */
    public void deleteTransition(
            int index);


    /**
     * Delete the transition passed from this state machine.
     *
     * @param stateTransitionToDelete Which transition to delete from this state machine.
     */
    public void deleteTransition(
            StateTransition stateTransitionToDelete);


    /**
     * Set this state machine as the "main" state machine.  This means that this state machine will
     * be the entry point and no other state machine can be (so all other state machines
     * will be marked as "non-main").
     *
     * @param main
     */
    public void setAsMain(boolean main);


    /**
     * Set the Concept that "owns" this StateModel.
     *
     * @param ownerConcept The Concept that "owns" this StateModel.
     */
    public void setOwnerConcept(
            Concept ownerConcept);


    /**
     * Set the root state of this state machine.
     */
    public void setMachineRoot(
            StateComposite rootState);


    /**
     * Set the collection containing all the StateAnnotationLinks in this state machine.
     */
    public void setAnnotationLinks(
            List annotationLinks);


    /**
     * Set the collection containing all the StateTransitions in this state machine.
     */
    public void setTransitions(
            List transitions);


    public void setForwardCorrelates(boolean fwdCorrelate);

// todo rkt These don't belong here, they need to be moved to Validator interface
// WELL FORMED-NESS RULES


    /**
     * Validates if a transition may be drawn between two states
     *
     * @param from state from which transition will be drawn
     * @param to   state to which transition will be drawn
     * @return null if transition may be drawn, otherwise error String
     */
    String validateTransitionConnection(
            State from,
            State to);


    /**
     * Validates if a transition may be drawn between two states
     *
     * @param from state from which transition will be drawn
     * @return null if transition may be drawn from this node, otherwise error String
     */
    String validateTransitionOrigin(
            State from);


    /**
     * Validates if a transition may be drawn between two states
     *
     * @param newFrom state from which transition will be drawn
     * @param newTo   state to which transition will be drawn
     * @return null if transition may be drawn, otherwise error String
     */
    String validateTransitionReconnection(
            StateTransition transition,
            State newFrom,
            State newTo,
            boolean isReconnectingOrigin);


    /**
     * Sets the Concept that "owns" this StateMachine.
     *
     * @param ownerConceptPath String path of the Concept that must "own" this StateMachine.
     */
    void setOwnerConceptPath(
            String ownerConceptPath);


}// end interface StateMachine
