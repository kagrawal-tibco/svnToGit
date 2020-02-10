/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 5:31:34 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateRectangle;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableStateRectangle extends DefaultMutableStateDecoration implements MutableStateRectangle {


    public static final String PERSISTENCE_NAME = "StateRectangle";


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology The ontology (BE model) to add this entity.
     * @param name     The name of this element.
     * @param bounds   The location of the entity on the main view (only top-left is used for iconic entities).
     */
    public DefaultMutableStateRectangle(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateRectangle


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology    The ontology (BE model) to add this entity.
     * @param name        The name of this element.
     * @param bounds      The location of the entity on the main view (only top-left is used for iconic entities).
     * @param fillPaint   The Paint to fill the decoration with (currently only Color objects can be persisted).
     * @param lineColor   The Color to draw lines in the decoration.
     * @param linePattern The pattern to draw lines in the decoration (currently actual dash pattern cannot be persisted).
     */
    public DefaultMutableStateRectangle(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            Paint fillPaint,
            Color lineColor,
            BasicStroke linePattern) {
        super(ontology, name, bounds, ownerStateMachine, fillPaint, lineColor, linePattern);
    }// end DefaultStateRectangle


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
}// end class DefaultStateRectangle
