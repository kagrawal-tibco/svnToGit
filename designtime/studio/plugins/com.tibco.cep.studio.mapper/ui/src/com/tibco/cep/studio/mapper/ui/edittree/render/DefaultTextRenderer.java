package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.tree.DefaultTreeCellRenderer;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTextRenderer;
import com.tibco.cep.studio.mapper.ui.data.bind.StringPaintUtils;

/**
 * Default implementation of text rendering for the basic tree.
 */
public class DefaultTextRenderer implements TextRenderer {
   private Font mFont;
   public Color mTextSelectionColor;
   public Color mTextNonSelectionColor;
   public Color mErrorColor = Color.red;
   private boolean m_selected;
   private boolean m_error;
   private String mText;

   public DefaultTextRenderer(Font font) {
      mFont = font;
      DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
      mTextSelectionColor = r.getTextSelectionColor();
      mTextNonSelectionColor = r.getTextNonSelectionColor();
   }

   public void setFont(Font font) {
      mFont = font;
   }

   public void setText(String text) {
      mText = text;
   }

   public void setSelected(boolean sel) {
      m_selected = sel;
   }

   public void setError(boolean error) {
      m_error = error;
   }

   public int computeWidth(Graphics g) {
      g.setFont(mFont);
      String txt = mText == null ? "" : mText;
      return g.getFontMetrics().charsWidth(txt.toCharArray(), 0, txt.length());
   }

   public void render(Graphics g, int x, int y, int width, int height) {
      g.setFont(mFont);
      FontMetrics fm = g.getFontMetrics();
      y += BasicTextRenderer.computeInlineTextYBaseOffset(fm);
      if (m_selected) {
         if (m_error) {
            g.setColor(mErrorColor);
         }
         else {
            g.setColor(mTextSelectionColor);
         }
      }
      else {
         if (m_error) {
            g.setColor(mErrorColor);
         }
         else {
            g.setColor(mTextNonSelectionColor);
         }
      }
      String txt = mText == null ? "<< null >>" : mText;
      StringPaintUtils.paintDisplayableString(g, txt, width, x, y);
   }
}

