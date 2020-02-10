package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * A default implementation of {@link NameValueTreeCellRenderer} (handles renderering only).
 */
public class DefaultNameValueTreeCellPlugin implements NameValueTreeCellPlugin {
   private Font m_normalFont;
   private DefaultTextRenderer m_nameRenderer;

   public DefaultNameValueTreeCellPlugin(UIAgent uiAgent) {
      m_normalFont = uiAgent.getAppFont();
      m_nameRenderer = new DefaultTextRenderer(m_normalFont);
   }

   public Dimension getDataOvershowSize(Object node, Graphics g, int availableWidth, int rowHeight) {
      return new Dimension(0, 0);
   }

   public void paintOvershowData(Object node, Graphics g, Dimension size, int rowHeight) {
   }

   public boolean isMultiLineData(Object node) {
      return false;
   }

   public Color getBackgroundColor(Object node) {
      return null;
   }

   public String getNameToolTip(Object node) {
      return null;
   }

   public String getDataToolTip(Object node, Point dataRelativePoint) {
      return null;
   }

   public TextRenderer getDataRenderer(Object node) {
      return null; // n/a
   }

   public JExtendedEditTextArea getDataEditor(Object node) {
      return null;
   }

   public Color getDataBackgroundColor(Object node) {
      return null;
   }

   /**
    * Default implementation; returns 16.
    */
   public int getIconWidth() {
      return 16;
   }

   /**
    * Default implementation; returns a blank icon.
    */
   public Icon getIcon(Object node) {
      return DataIcons.getBlankIcon();
   }

   public TextRenderer getNameRenderer(Object node, boolean isSel, boolean isFocus) {
      m_nameRenderer.setText(getNodeNameValue(node));
      m_nameRenderer.setSelected(isSel);
      return m_nameRenderer;
   }

   /**
    * Subclasses should override for a name other than {@link #toString} on node.
    *
    * @param node
    * @return
    */
   public String getNodeNameValue(Object node) {
      return node.toString();
   }

   public boolean isDataEditable(Object node) {
      return false;
   }

   public JExtendedEditTextArea getNameEditor(Object node) {
      return null;
   }

   public boolean isNameEditable(Object node) {
      return false;
   }

   public void nameEditingFinished(Object node, JExtendedEditTextArea edit) {
   }

   public void dataEditingFinished(Object node, JExtendedEditTextArea edit) {
   }
}
