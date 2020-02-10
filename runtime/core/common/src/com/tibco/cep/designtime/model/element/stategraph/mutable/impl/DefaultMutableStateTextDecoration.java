/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 5:39:20 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateTextDecoration;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * If this class becomes concrete (no longer abstract) it must be added to
 * createEntityFromNode as a new object to be constructed from persistence.
 */
public abstract class DefaultMutableStateTextDecoration extends DefaultMutableStateDecoration implements MutableStateTextDecoration {


    public static final String PERSISTENCE_NAME = "StateTextDecoration";
    protected static final ExpandedName TEXT_TAG = ExpandedName.makeName("text");
    protected String m_text = "";


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology The ontology (BE model) to add this entity.
     * @param name     The name of this element.
     * @param bounds   The location of the entity on the main view (only top-left is used for iconic entities).
     */
    public DefaultMutableStateTextDecoration(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateTextDecoration


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology    The ontology (BE model) to add this entity.
     * @param name        The name of this element.
     * @param bounds      The location of the entity on the main view (only top-left is used for iconic entities).
     * @param fillPaint   The Paint to fill the decoration with (currently only Color objects can be persisted).
     * @param lineColor   The Color to draw lines in the decoration.
     * @param linePattern The pattern to draw lines in the decoration (currently actual dash pattern cannot be persisted).
     * @param text        The text to display in this decoration.
     */
    public DefaultMutableStateTextDecoration(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            Paint fillPaint,
            Color lineColor,
            BasicStroke linePattern,
            String text) {
        super(ontology, name, bounds, ownerStateMachine, fillPaint, lineColor, linePattern);
        m_text = text;
    }// end DefaultStateTextDecoration


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        m_text = root.getAttributeStringValue(TEXT_TAG);
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
     * Get the text to be displayed on this decoration.
     *
     * @return The text to display on this decoration.
     */
    public String getText() {
        return m_text;
    }// end getText


    /**
     * Set the text to be displayed on this decoration.
     *
     * @param text The new text to display on this decoration.
     */
    public void setText(
            String text) {
        if (text == null) {
            text = "";
        }//endif
        m_text = text;
    }// end setText


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        setAttributeStringValueSafe(root, TEXT_TAG, m_text.toString());
        return root;
    }// end toXiNode
}// end class DefaultStateTextDecoration
