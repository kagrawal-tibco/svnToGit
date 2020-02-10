package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.FontMetrics;
import java.awt.Graphics;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;

/**
 * Default implementation of text rendering for the basic tree.
 */
public abstract class BasicTextRenderer {
   public abstract int getTextOffset(Graphics g, String text);

   public abstract void render(Graphics g, int x, int y, int width, int height, String text, boolean sel, boolean blank, ErrorMessageList errors, BasicTreeNode node);

   /**
    * Gets the number of pixels from the top of a line to the base of text (for standard graphics drawString) calls.<br>
    * The formula used is that used by JTextArea, and it accounts for the 2 pixel border drawn when editing inline.
    *
    * @param fm The font.
    * @return
    */
   public static final int computeInlineTextYBaseOffset(FontMetrics fm) {
      return (fm.getHeight() - (fm.getLeading() + fm.getMaxDescent())) + 2; // 2 = # of pixels in editing border.
   }
}

