package com.tibco.cep.runtime.model.element.stategraph;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 19, 2004
 * Time: 3:43:18 PM
 * To change this template use File | Settings | File Templates.
 */

 /*
  * This graph represents the semantics of a state model resource in the repository.
  * This class holds all the designed constraints required to include this state model
  * as part of another state model or used as a stand-alone
  */

public interface StateGraphDefinition {
    /*
     * Gets the associated concept
     */
    public Class getAssociatedConcept();

    public String getName();

    /*
     *
     */
    public CompositeStateVertex getRoot();

    /*
     * @return Number of States, Deep Count
     */
    public int numberOfStates();

    /*
     * @return Number of Transitions, Deep Count
     */
    public int numberOfTransitions();


    /*
     * StartStub Required to create and associat this state machine with the parent
     */
    public StartTransitionLink startStub();

    public Class[] getRuleClasses();

    public TransitionLink getTransitionLink(int index);

    public int numberOfConcurrentStates();
//    public int getModelIdentifier();

    public FinalStateVertex[] getTopFinalStates();

    public StateVertex getStateVertex(int index);

    public List/*<Path>*/ getPaths();

    public boolean forwardCorrelates();
}

