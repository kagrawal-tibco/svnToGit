package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.data.utils.OvershowManager;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreePathUtils;

/**
 * Handles popups 'overshow' popups for when text is truncated because of space for {@link NameValueTreeCellEditor}.
 */
class NameValueTreeCellOvershowPopup {
   private EditableTree m_tree;
   private NameValueTreeCellRenderer mRenderer;
   private Object m_currentOvershowNode;
   private boolean mCurrentIsData;

   public NameValueTreeCellOvershowPopup(NameValueTreeCellRenderer renderer, EditableTree tree) {
      m_tree = tree;
      mRenderer = renderer;
   }

   private OvershowManager getOvershowManager() {
      return m_tree.getOvershowManager();
   }

   void checkOvershow(Point at) {
      TreePath path = m_tree.getPathForLocation(at.x, at.y);
      if (path == null) {
         updateOvershow(null, false);
         return;
      }
      Object node = path.getLastPathComponent();
      int depth = EditableTreePathUtils.getDepth(m_tree.getEditableModel(), node);
      int indent = depth * mRenderer.mIndentWidth;
      if (mRenderer.mHasDivider) {
         // check both data & name overshow:
         int offset = mRenderer.getDataOffset(node);
         if (at.x >= offset) {
            // in the data area:
            if (!mRenderer.dataFits(node)) {
               updateOvershow(node, true);
               return;
            }
            updateOvershow(null, false);
            return;
         }
         if (at.x < indent + mRenderer.mNameOffset) {
            updateOvershow(null, false);
            return;
         }
         if (!mRenderer.nameFits(node)) {
            updateOvershow(node, false);
            return;
         }
         else {
            updateOvershow(null, false);
            return;
         }
      }
      if (at.x < indent + mRenderer.mNameOffset) {
         updateOvershow(null, false);
         return;
      }
      if (!mRenderer.nameFits(node)) {
         updateOvershow(node, false);
         return;
      }
      else {
         updateOvershow(null, false);
         return;
      }
   }

   public void dispose() {
      OvershowManager dnd = getOvershowManager();//getDnD();
      if (dnd == null) {
         return;
      }
      dnd.setOvershowComponent(null);
      m_currentOvershowNode = null;
   }

   private void updateOvershow(final Object node, boolean isData) {
      final OvershowManager w = getOvershowManager();
      if (w == null) {
         return;
      }
      if (m_currentOvershowNode != node || mCurrentIsData != isData) {
         dispose();
         m_currentOvershowNode = node;
         mCurrentIsData = isData;
         if (node != null) {
            final Point r = SwingUtilities.convertPoint(m_tree, new Point(), w.getRoot());
            final TreePath nodePath = EditableTreePathUtils.getTreePath(m_tree.getEditableModel(), node);
            Rectangle tr = m_tree.getPathBounds(nodePath);
            final Insets i = m_tree.getInsets();
            JPanel panel = new OvershowPanel(nodePath, r, i);
            panel.setOpaque(true);
            panel.setBackground(m_tree.getBackground());

            if (isData) {
               int indent = mRenderer.getDataOffset(node);
               //int height = tr.height;
               int yoff = tr.y;
               panel.setLocation(r.x + indent + i.left, r.y + yoff);
            }
            else {
               int depth = EditableTreePathUtils.getDepth(m_tree.getEditableModel(), node);
               int indent = depth * mRenderer.mIndentWidth;
               //int height = tr.height;
               int yoff = tr.y;
               panel.setLocation(r.x + indent + i.left, r.y + yoff);
            }
            Dimension sz = panel.getPreferredSize();
            if (sz == null) {
               System.err.println("Null size returned by:" + panel.getClass().getName());
               sz = new Dimension(20, 20);
            }
            panel.setSize(sz);
            w.setOvershowComponent(panel);
         }
      }
   }

   private class OvershowPanel extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreePath m_path;
      private Object m_node;
      private Point m_location;
      private Insets m_insets;

      public OvershowPanel(TreePath path, Point location, Insets i) {
         m_path = path;
         m_node = path.getLastPathComponent();
         m_location = location;
         m_insets = i;
      }

      public void paint(Graphics g) {
         super.paint(g);
         boolean isSel = m_tree.getSelectionNode() == m_node;
         boolean isFoc = m_tree.hasFocus() && isSel;
         boolean isExp = m_tree.isExpanded(m_path);
         boolean isLeaf = m_tree.getEditableModel().isLeaf(m_node);
         mRenderer.getTreeCellRendererComponent(m_tree, m_node, isSel, isExp, isLeaf, 0, isFoc);
         Color bc = mRenderer.getBackgroundColor(m_node);
         if (bc != null) {
            g.setColor(bc);
            g.fillRect(0, 0, getWidth(), getHeight());
         }
         if (mCurrentIsData) {
            mRenderer.paintOvershowData(g, getWidth(), getHeight());
         }
         else {
            mRenderer.paintLabel(g, getWidth(), getHeight(), getWidth());
         }
         int h = getHeight() - 1;
         if (!mCurrentIsData && mRenderer.mHasDivider) {
            g.setColor(mRenderer.mRowSeparatorColor);
            g.drawLine(0, h, getWidth(), h);
         }
         // draw the overlay border:
         int rr;
         if (mCurrentIsData) {
            rr = mRenderer.getPreferredSize().width - mRenderer.getRelativeDataOffset(m_node);
         }
         else {
            if (mRenderer.mHasDivider) {
               rr = mRenderer.getRelativeDataOffset(m_node);
            }
            else {
               rr = mRenderer.getPreferredSize().width;
            }
         }
         g.setColor(Color.lightGray);
         int ww = getWidth() - 1;
         g.drawLine(rr, 0, ww, 0);
         g.drawLine(ww, 0, ww, h);
         g.drawLine(rr, h, ww, h);
      }

      public Dimension getPreferredSize() {
         final OvershowManager w = getOvershowManager();
         boolean isLeaf = m_tree.getEditableModel().isLeaf(m_node);
         mRenderer.getTreeCellRendererComponent(m_tree, m_node, false, false, isLeaf, 0, false);
         if (mCurrentIsData) {
            int left = m_location.x + m_insets.left + mRenderer.getRelativeDataOffset(m_node);
            int availWidth = w.getRoot().getWidth() - left;
            return mRenderer.getDataOvershowSize(availWidth);
         }
         else {
            return mRenderer.getNameOvershowSize();
         }
      }

      public boolean isEnabled() {
         return false;//
      }
   }
}
