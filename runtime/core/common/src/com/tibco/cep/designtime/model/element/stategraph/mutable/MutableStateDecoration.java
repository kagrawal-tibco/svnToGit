/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 8:01:01 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;

import com.tibco.cep.designtime.model.element.stategraph.StateDecoration;


public interface MutableStateDecoration extends StateDecoration, MutableStatePseudo {


    /**
     * Set the paint style of the fill in this decoration.
     *
     * @param fillPaint The paint style of the fill in this decoration.
     */
    public void setFillPaint(
            Paint fillPaint);


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
}// end interface StateDecoration
