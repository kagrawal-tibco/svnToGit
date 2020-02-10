package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;

/**
 * A utility class for different types of components; eliminate eventually, doesn't do much.
 */
public class XPathLineRenderer {

   private XPathTextRenderer mRenderer;

   public XPathLineRenderer(UIAgent uiAgent) {
      mRenderer = new XPathTextRenderer(uiAgent);
   }

   public void setErrorUnderlineColor(Color c) {
      if (c == null) {
         throw new NullPointerException("Null color");
      }
      mRenderer.setErrorUnderlineColor(c);
   }

   public void drawErrorLine(Graphics g, char[] lineTxt, int x, int y, int height, ErrorMessageList errorList) {
      Font oldFont = g.getFont();
      mRenderer.initialize(g);
      g.setFont(mRenderer.getFont());
      int yof = mRenderer.getFontBaselineYOffset(g);
      mRenderer.drawErrorsLine(g, lineTxt, errorList, x, y + yof);
      g.setFont(oldFont);
   }
}
