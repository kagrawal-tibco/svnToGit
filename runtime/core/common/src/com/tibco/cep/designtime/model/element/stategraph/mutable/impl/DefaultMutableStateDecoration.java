/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 27, 2004
 * Time: 5:38:21 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateDecoration;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachine;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * If this class becomes concrete (no longer abstract) it must be added to
 * createEntityFromNode as a new object to be constructed from persistence.
 */
public abstract class DefaultMutableStateDecoration extends DefaultMutableStatePseudo implements MutableStateDecoration {


    public static final String PERSISTENCE_NAME = "StateDecoration";
    protected static final ExpandedName FILL_PAINT_TAG = ExpandedName.makeName("fillPaint");
    protected static final ExpandedName LINE_COLOR_TAG = ExpandedName.makeName("lineColor");
    protected static final ExpandedName LINE_PATTERN_WIDTH_TAG = ExpandedName.makeName("linePatternWidth");
    protected static final ExpandedName LINE_PATTERN_CAP_TAG = ExpandedName.makeName("linePatternCap");
    protected static final ExpandedName LINE_PATTERN_JOIN_TAG = ExpandedName.makeName("linePatternJoin");
    protected static final String DECORATIONS_MUST_HAVE_A_BOUNDING_RECTANGLE_SET = "DefaultStateComposite.getModelErrors.decorationsMustHaveABoundingRectangleSet";
    protected Rectangle m_bounds = null;
    protected Paint m_fillPaint = null;
    protected Color m_lineColor = null;
    protected BasicStroke m_linePattern = null;


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology The ontology (BE model) to add this entity.
     * @param name     The name of this element.
     * @param bounds   The location of the entity on the main view (only top-left is used for iconic entities).
     */
    public DefaultMutableStateDecoration(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine) {
        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateDecoration


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
    public DefaultMutableStateDecoration(
            DefaultMutableOntology ontology,
            String name,
            Rectangle bounds,
            MutableStateMachine ownerStateMachine,
            Paint fillPaint,
            Color lineColor,
            BasicStroke linePattern) {
        super(ontology, name, bounds, ownerStateMachine);
        m_fillPaint = fillPaint;
        m_lineColor = lineColor;
        m_linePattern = linePattern;
    }// end DefaultStateDecoration


    /**
     * Load this object from the XiMode passed.
     *
     * @param root The XiNode that holds the description of this model object.
     */
    public void fromXiNode(
            XiNode root) throws ModelException {
        super.fromXiNode(root);
        m_fillPaint = fromXiNodeOfColor(FILL_PAINT_TAG, root);
        m_lineColor = fromXiNodeOfColor(LINE_COLOR_TAG, root);
        double linePatternWidth = Double.valueOf(root.getAttributeStringValue(LINE_PATTERN_WIDTH_TAG)).doubleValue();
        int linePatternCap = getIntAttributeValue(root, LINE_PATTERN_CAP_TAG);
        int linePatternJoin = getIntAttributeValue(root, LINE_PATTERN_JOIN_TAG);
        m_linePattern = new BasicStroke((float) linePatternWidth, linePatternCap, linePatternJoin);
    }// end fromXiNode


    /**
     * Get the paint style of the fill in this decoration.  Can only be persisted if the Paint is a Color.
     *
     * @return The paint style of the fill in this decoration.
     */
    public Paint getFillPaint() {
        return m_fillPaint;
    }// end getFillPaint


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
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects (never returns null).
     */
    public List getModelErrors() {
        ArrayList modelErrors = new ArrayList();
        addErrorIfTrue(m_bounds == null, modelErrors, BEModelBundle.getBundle().getString(DECORATIONS_MUST_HAVE_A_BOUNDING_RECTANGLE_SET));
        return modelErrors;
    }// end getModelErrors


    /**
     * Return the name that identifies this class so it can be recognized when streaming in.
     *
     * @return The name that identifies this class so it can be recognized when streaming in.
     */
    public String getPersistenceName() {
        return PERSISTENCE_NAME;
    }// end getPersistenceName


    /**
     * Always throws an exception since decorations can't set a timeout.
     *
     * @return Nothing.
     */
    public int getTimeout() {
        throw new UnsupportedOperationException();
    }// end getTimeout


    /**
     * Set the paint style of the fill in this decoration.  Can only be persisted if the Paint is a Color.
     *
     * @param fillPaint The paint style of the fill in this decoration.
     */
    public void setFillPaint(
            Paint fillPaint) {
        m_fillPaint = fillPaint;
    }// end setFillPaint


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
     * Always throws an exception since decorations can't set a timeout.
     *
     * @param timeout Ignored.
     */
    public void setTimeout(
            int timeout) {
        throw new UnsupportedOperationException();
    }// end setTimeout


    /**
     * Convert the data in this model object into an XiNode so the data can be persisted to the repo.
     *
     * @param factory A factory that can be used to create XiNodes.
     */
    public XiNode toXiNode(
            XiFactory factory) {
        XiNode root = super.toXiNode(factory);
        if (m_fillPaint instanceof Color) {
            toXiNodeOfColor((Color) m_fillPaint, FILL_PAINT_TAG, root);
        } else {
            toXiNodeOfColor(new Color(0, 0, 0), FILL_PAINT_TAG, root);
        }//endif
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
        return root;
    }// end toXiNode
}// end class DefaultStateDecoration
