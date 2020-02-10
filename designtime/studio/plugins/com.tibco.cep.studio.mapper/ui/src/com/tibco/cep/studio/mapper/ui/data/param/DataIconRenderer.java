package com.tibco.cep.studio.mapper.ui.data.param;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;

/**
 * Knows how to draw the data icon, including overlays, etc.
 */
public class DataIconRenderer implements Icon {
   private Icon m_icon;
   private boolean m_isSubstituted;
   private boolean m_isDisabled;
   private boolean m_error;
   private int m_min;
   private int m_max;
   private boolean m_nilled;
   private Color m_disabledColor;
   private Font m_cardFont;

   private static final int CARDINALITY_OFFSET = 16;

   public DataIconRenderer(Color backgroundColor, UIAgent uiAgent) {
      m_disabledColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 128);
      m_cardFont = uiAgent.getAppFont();
   }

   public void setBaseIcon(Icon baseIcon) {
      m_icon = baseIcon;
   }

   public void setMin(int min) {
      m_min = min;
   }

   public void setMax(int max) {
      m_max = max;
   }

   public void setNilled(boolean nilled) {
      m_nilled = nilled;
   }

   public void setError(boolean error) {
      m_error = error;
   }

   public void setIsDisabled(boolean disabled) {
      m_isDisabled = disabled;
   }

   public void setSubstituted(boolean substituted) {
      m_isSubstituted = substituted;
   }

   public int getIconHeight() {
      return 16;
   }

   public int getIconWidth() {
      return CARDINALITY_OFFSET + 7;
   }

   public void paintIcon(Component c, Graphics g, int x, int y) {
      Icon icon = m_icon;
      if (icon == null) {
         icon = DataIcons.getErrorIcon();// don't crash it.
      }
      icon.paintIcon(c, g, 0, 0);

      if (m_isSubstituted) {
         // overlay substituted icon:
         DataIcons.getAnySubstitutedIcon().paintIcon(c, g, 0, 0);
      }
      if (m_isDisabled) {
         // overlay disabled icon:
         g.setColor(m_disabledColor);
         for (int i = 0; i < 16; i++) {
            for (int j = i % 2; j < 16; j += 2) {
               g.drawLine(i, j, i, j);
            }
         }
      }
      g.setColor(Color.blue);
      g.setFont(m_cardFont);
      int xat = CARDINALITY_OFFSET;
      if (m_max > 1) {
         if (m_min == 0) {
            g.drawString("*", xat, 10);
         }
         else {
            g.drawString("+", xat, 10);
         }
      }
      else {
         if (m_min == 0) {
            g.drawString("?", xat, 10);
         }
      }
      if (m_nilled) {
         DataIcons.getNilIcon().paintIcon(c, g, 6, 0);
      }
      if (m_error) {
         DataIcons.getErrorIcon().paintIcon(c, g, 6, 0);
      }
   }
}
