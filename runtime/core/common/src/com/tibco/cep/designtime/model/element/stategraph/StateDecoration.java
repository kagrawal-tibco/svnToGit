package com.tibco.cep.designtime.model.element.stategraph;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 2:26:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateDecoration extends StatePseudo {


    /**
     * Get the paint style of the fill in this decoration.
     *
     * @return The paint style of the fill in this decoration.
     */
    Paint getFillPaint();


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
}
