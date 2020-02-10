/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 1, 2004
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateLink;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * A base class to handle common behavior for Transitions and Annotation Links.
 */
public class DefaultMutableStateLink extends DefaultMutableStateEntity implements MutableStateLink {


    public static final String PERSISTENCE_NAME = "StateLink";
    protected static final ExpandedName LINE_COLOR_TAG = ExpandedName.makeName("lineColor");
    protected static final ExpandedName LINE_PATTERN_WIDTH_TAG = ExpandedName.makeName("linePatternWidth");
    protected static final ExpandedName LINE_PATTERN_CAP_TAG = ExpandedName.makeName("linePatternCap");
    protected static final ExpandedName LINE_PATTERN_JOIN_TAG = ExpandedName.makeName("linePatternJoin");
    protected static final ExpandedName LABEL_TAG = ExpandedName.makeName("label");
    protected static final ExpandedName PREPASTE_TO_GUID_TAG = ExpandedName.makeName("prepasteFromGUID");
    protected static final ExpandedName PREPASTE_FROM_GUID_TAG = ExpandedName.makeName("prepasteToGUID");
    protected Color m_lineColor = null;
    protected BasicStroke m_linePattern = null;
    private String m_prepasteToGUID = null;
    private String m_prepasteFromGUID = null;
    private String m_label = null;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param ownerStateMachine The state machine that owns this state.
     */
    public DefaultMutableStateLink(
            DefaultMutableOntology ontology,
            String name,
            MutableStateMachine ownerStateMachine) {
        // Bounds are meaningless for transitions (they just follow the states) so just give a dummy bounds
        super(ontology, name, new Rectangle(0, 0, 0, 0), ownerStateMachine);
    }// end DefaultStateLink


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        m_lineColor = fromXiNodeOfColor(LINE_COLOR_TAG, root);
        double linePatternWidth = Double.valueOf(root.getAttributeStringValue(LINE_PATTERN_WIDTH_TAG)).doubleValue();
        int linePatternCap = getIntAttributeValue(root, LINE_PATTERN_CAP_TAG);
        int linePatternJoin = getIntAttributeValue(root, LINE_PATTERN_JOIN_TAG);
        m_linePattern = new BasicStroke((float) linePatternWidth, linePatternCap, linePatternJoin);
        m_label = root.getAttributeStringValue(LABEL_TAG);
        m_prepasteToGUID = root.getAttributeStringValue(PREPASTE_TO_GUID_TAG);
        m_prepasteFromGUID = root.getAttributeStringValue(PREPASTE_FROM_GUID_TAG);
    }// end fromXiNode


    /**
     * Used during cut/paste.
     *
     * @return return guid of this object at time of cut.
     */
    public String getPrepasteFromGuid() {
        return m_prepasteFromGUID;
    }// end getFromGuid


    /**
     * Get the label (what's displayed in the main view) for this link.
     *
     * @return The label (what's displayed in the main view) for this link.
     */
    public String getLabel() {
        return m_label;
    }// end getLabel


    /**
     * Get the color of the line around this decoration.
     *
     * @return The color of the line around this decoration.
     */
    public Color getLineColor() {
        return m_lineColor;
    }// end getLineColor


    /**
     * Set the pattern of the line around this decoration.
     * Only the width, end cap and join are persisted, not the pattern.
     *
     * @return linePattern The pattern of the line around this decoration.
     */
    public BasicStroke getLinePattern() {
        return m_linePattern;
    }// end getLinePattern


    /**
     * Get the state machine that this state belongs to.
     *
     * @return The state machine that this state belongs to.
     */
    public StateMachine getOwnerStateMachine() {
        return m_ownerStateMachine;
    }// end getOwnerStateMachine


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    /**
     * Get the parent of this state link.  Links are unlike other state entities in that
     * they are only children of the state machine, not some composite state.
     *
     * @return The parent of this state entity.
     */
    public StateEntity getParent() {
        return m_ownerStateMachine;
    }// end getParent


    /**
     * Used during cut/paste.
     *
     * @return return guid of this object at time of cut.
     */
    public String getPrepasteToGuid() {
        return m_prepasteToGUID;
    }// end getToGuid


    /**
     * Used during cut/paste.
     * Sets the GUID of the "from" state at time of cut.
     */
    public void setPrepasteFromGuid(
            String GUID) {
        m_prepasteFromGUID = GUID;
    }// end setFromGuid


    /**
     * Set the label (what's displayed in the main view) for this link.
     *
     * @param label The label (what's displayed in the main view) for this link.
     */
    public void setLabel(
            String label) {
        m_label = label;
    }// end setLabel


    /**
     * Set the color of the line around this decoration.
     *
     * @param lineColor The color of the line around this decoration.
     */
    public void setLineColor(
            Color lineColor) {
        m_lineColor = lineColor;
    }// end setLineColor


    /**
     * Set the pattern of the line around this decoration.
     * Only the width, end cap and join are persisted, not the pattern.
     *
     * @param linePattern The pattern of the line around this decoration.
     */
    public void setLinePattern(
            BasicStroke linePattern) {
        m_linePattern = linePattern;
    }// end setLinePattern


    /**
     * Set the state machine that this state belongs to.
     *
     * @param ownerStateMachine The state machine that this state belongs to.
     */
    public void setOwnerStateMachine(
            MutableStateMachine ownerStateMachine) {
        m_ownerStateMachine = ownerStateMachine;
    }// end setOwnerStateMachine


    /**
     * Used during cut/paste.
     * Set guid of this to object at time of cut.
     */
    public void setPrepasteToGuid(
            String GUID) {
        m_prepasteToGUID = GUID;
    }// end setToGuid


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        toXiNodeOfColor(m_lineColor, LINE_COLOR_TAG, root);
        BasicStroke linePattern;
        if (m_linePattern instanceof BasicStroke) {
            linePattern = (BasicStroke) m_linePattern;
        } else {
            linePattern = new BasicStroke();
        }//endif
        root.setAttributeStringValue(LINE_PATTERN_WIDTH_TAG, (Double.toString((double) linePattern.getLineWidth())));
        root.setAttributeStringValue(LINE_PATTERN_CAP_TAG, Integer.toString(linePattern.getEndCap()));
        root.setAttributeStringValue(LINE_PATTERN_JOIN_TAG, Integer.toString(linePattern.getLineJoin()));
        setAttributeStringValueSafe(root, LABEL_TAG, m_label);
        setAttributeStringValueSafe(root, PREPASTE_TO_GUID_TAG, m_prepasteToGUID);
        setAttributeStringValueSafe(root, PREPASTE_FROM_GUID_TAG, m_prepasteFromGUID);
        return root;
    }// end toXiNode
}// end class DefaultStateLink
