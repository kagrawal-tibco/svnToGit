/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 1, 2004
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import java.awt.BasicStroke;
import java.awt.Color;

import com.tibco.cep.designtime.model.element.stategraph.StateLink;


/**
 * A base class to handle common behavior for Transitions and Annotation Links.
 */
public interface MutableStateLink extends StateLink, MutableStateEntity {


    /**
     * Set the label (what's displayed in the main view) for this link.
     *
     * @param label The label (what's displayed in the main view) for this link.
     */
    public void setLabel(
            String label);


    /**
     * Set the color of the line around this decoration.
     *
     * @param lineColor The color of the line around this decoration.
     */
    public void setLineColor(
            Color lineColor);


    /**
     * Set the pattern of the line around this decoration.
     *
     * @param linePattern The pattern of the line around this decoration.
     */
    public void setLinePattern(
            BasicStroke linePattern);


    /**
     * Used during cut/paste.
     * Sets the GUID of the "from" state at time of cut.
     */
    public void setPrepasteFromGuid(
            String GUID);


    public void setPrepasteToGuid(
            String GUID);
}// end interface StateLink
