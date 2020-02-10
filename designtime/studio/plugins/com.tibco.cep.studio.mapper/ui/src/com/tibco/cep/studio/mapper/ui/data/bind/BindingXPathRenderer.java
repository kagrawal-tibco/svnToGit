package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.Graphics;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeCellRenderer;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathTextRenderer;
import com.tibco.cep.studio.mapper.ui.edittree.render.TextRenderer;

/**
 * A text renderer that draws XPaths/AVTS.
 */
final class BindingXPathRenderer implements TextRenderer {
   private XPathTextRenderer mRenderer;
   private ErrorMessageList m_errors;
   private String m_text = "";

   public BindingXPathRenderer(UIAgent uiAgent) {
      mRenderer = new XPathTextRenderer(uiAgent);
   }

   public XPathTextRenderer getRenderer() {
      return mRenderer;
   }

   public void setAVTMode(boolean avtMode) {
      mRenderer.setAVTMode(avtMode);
   }

   public boolean getAVTMode() {
      return mRenderer.getAVTMode();
   }

   public void setText(String text) {
      m_text = text;
   }

   public void setErrors(ErrorMessageList eml) {
      m_errors = eml;
   }

   public int getCharWidth() {
      return mRenderer.getCharWidth();
   }

   public int computeWidth(Graphics g) {
      mRenderer.initialize(g); // need to initialize so it can get fonts setup.
      String t = m_text;
      if (t == null) {
         return 0;
      }
      return mRenderer.calculateCharsPixelSize(g, m_text.toCharArray(), 0, m_text.length());
   }

   public void render(Graphics g, int x, int y, int width, int height) {
      //if (!blank) {
      int yo = mRenderer.getFontBaselineYOffset(g);
      String[] lines = BasicTreeCellRenderer.getAsLines(m_text);
      if (lines.length >= 1) {
         String line = lines[0];
         boolean endsWithNewLineMarker = lines.length > 1;
         char[] ch = line.toCharArray();
         mRenderer.drawSyntaxLine(g, ch, 0, ch.length, x, y + yo, width, -1, -1, endsWithNewLineMarker);
         if (m_errors != null) {
            mRenderer.drawErrorsLine(g, ch, m_errors, x, y + yo);
         }
      }
      //} else {
      //mBlank.render(g,x,y,width,height,text,sel,blank,errors,node);
      //}
   }
}

