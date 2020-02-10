/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 5:32:42 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.Rectangle;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateSimple;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableStateSimple extends DefaultMutableState implements MutableStateSimple {


    public static final String PERSISTENCE_NAME = "StateSimple";


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateSimple(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateSimple


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology               The ontology (BE model) to add this entity.
     * @param name                   The name of this element.
     * @param bounds                 The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine      The state machine that owns this state.
     * @param entryAction            The action to perform on entry to this state.
     * @param exitAction             The action to perform on exit from this state.
     * @param internalTransitionRule The internal transition (if any) for this state.
     */
    public DefaultMutableStateSimple(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine,
            Rectangle bounds,
            MutableRule entryAction,
            MutableRule exitAction,
            MutableRule internalTransitionRule) {
        super(ontology, name, bounds, ownerStateMachine, entryAction, exitAction, internalTransitionRule);
    }// end DefaultStateSimple


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
    }// end fromXiNode


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName

///** Always throws an exception since SimpleStates can't set a timeout (as of today...).
// * @return Nothing. */
//public int getTimeout () {
//  throw new UnsupportedOperationException ();
//}// end getTimeout

///** Always throws an exception since SimpleStates can't set a timeout (as of today...).
// * @param timeout Ignored.*/
//public void setTimeout (
//    int                     timeout) {
//  throw new UnsupportedOperationException ();
//}// end setTimeout


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        return root;
    }// end toXiNode

}// end class DefaultStateSimple
