package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * The default implemenation of a TreeCellStyle.
 */
public class DefaultTreeCellStyle implements TreeCellStyle {
   private Font normalFont;
   private Color textNonSelectionColor;

   public DefaultTreeCellStyle(Font normalFont) {
      this.normalFont = normalFont;
      this.textNonSelectionColor = UIManager.getColor("Tree.textForeground");
   }

   public void initCell(JTree tree, Object value, boolean sel, boolean expanded,
                        boolean leaf, int row, boolean hasFocus) {
   }

   public void setNormalFont(Font normalFont) {
      this.normalFont = normalFont;
   }

   public Font getNormalFont() {
      return normalFont;
   }

   public void setTextNonSelectionColor(Color textNonSelectionColor) {
      this.textNonSelectionColor = textNonSelectionColor;
   }

   public Color getTextNonSelectionColor() {
      return textNonSelectionColor;
   }
}
