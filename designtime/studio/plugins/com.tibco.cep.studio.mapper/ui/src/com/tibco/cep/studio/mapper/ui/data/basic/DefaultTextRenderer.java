package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.tree.DefaultTreeCellRenderer;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;

/**
 * Default implementation of text rendering for the basic tree.
 */
public class DefaultTextRenderer extends BasicTextRenderer {
   private Font mFont;
   private Font mBoldFont;
   public Color mTextSelectionColor;
   public Color mTextNonSelectionColor;
   public Color mTextMissingNonSelectionColor;
   private boolean m_showErrColor;

   public DefaultTextRenderer(Font font, boolean showErrColor) {
      mFont = font;
      m_showErrColor = showErrColor;
      mBoldFont = font.deriveFont(Font.BOLD);
      DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
      mTextSelectionColor = r.getTextSelectionColor();
      mTextNonSelectionColor = r.getTextNonSelectionColor();
      mTextMissingNonSelectionColor = Color.gray.darker();
   }

   public int getTextOffset(Graphics g, String text) {
      g.setFont(mFont);
      return g.getFontMetrics().charsWidth(text.toCharArray(), 0, text.length());
   }

   /**
    * @param y The top of the line to render.
    */
   public void render(Graphics g, int x, int y, int width, int height, String text, boolean sel, boolean blank, ErrorMessageList errors, BasicTreeNode node) {
      g.setFont(mFont);
      FontMetrics fm = g.getFontMetrics();
      y += computeInlineTextYBaseOffset(fm);
      if (sel) {
         if (node.getLineError() != null) {
            g.setColor(mTextSelectionColor);
            if (node.getParent() == null) {
               g.setFont(mBoldFont);
            }
         }
         else {
            g.setColor(mTextSelectionColor);
         }
      }
      else {
         if (!blank) {
            if (node.getLineError() != null && m_showErrColor) {
               g.setColor(Color.red);
               if (node.getParent() == null) {
                  g.setFont(mBoldFont);
               }
            }
            else {
               g.setColor(mTextNonSelectionColor);
            }
         }
         else {
            g.setColor(mTextMissingNonSelectionColor);
         }
      }
      com.tibco.cep.studio.mapper.ui.data.bind.StringPaintUtils.paintDisplayableString(g, text, width, x, y);
   }
}

