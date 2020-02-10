package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Graphics;

/**
 * A simple interface for rendering text (or anything for that matter) where the size (width) is constrainted.
 */
public interface TextRenderer {
   /**
    * Compute the width of the string.
    *
    * @param g The graphics
    */
   int computeWidth(Graphics g);

   /**
    * Render the string.
    *
    * @param g      The graphics
    * @param x      The x location to paint at.
    * @param y      The top of the area to paint (so to paint a text string, the proper offset for the font height needs to be applied.
    * @param width  The available width.
    * @param height The available height.
    */
   void render(Graphics g, int x, int y, int width, int height);
}

