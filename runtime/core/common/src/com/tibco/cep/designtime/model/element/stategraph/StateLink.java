package com.tibco.cep.designtime.model.element.stategraph;


import java.awt.BasicStroke;
import java.awt.Color;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 2:21:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateLink extends StateEntity {


    /**
     * Get the label (what's displayed in the main view) for this link.
     *
     * @return The label (what's displayed in the main view) for this link.
     */
    String getLabel();


    /**
     * Get the color of the line around this decoration.
     *
     * @return The color of the line around this decoration.
     */
    Color getLineColor();


    /**
     * Set the pattern of the line around this decoration.
     *
     * @return linePattern The pattern of the line around this decoration.
     */
    BasicStroke getLinePattern();


    /**
     * Used during cut/paste.
     * Gets the GUID of the "from" state at time of cut.
     */
    String getPrepasteFromGuid();


    /**
     * Used during cut/paste.
     * During cutting and pasting: getToGuid () != getToState ().getGuid ()
     */
    String getPrepasteToGuid();
}
