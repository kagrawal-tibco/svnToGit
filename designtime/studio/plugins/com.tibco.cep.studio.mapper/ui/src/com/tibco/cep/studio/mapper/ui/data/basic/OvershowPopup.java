package com.tibco.cep.studio.mapper.ui.data.basic;

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

/**
 * Handles popups 'overshow' popups for when text is truncated because of space.
 */
class OvershowPopup {
   private BasicTree mTree;
   private BasicTreeCellRenderer mRenderer;
   private BasicTreeNode mCurrentOvershow;
   private OvershowManager mOvershowManager;
   private boolean mCurrentIsData;

   public OvershowPopup(BasicTree tree, BasicTreeCellRenderer renderer) {
      mTree = tree;
      mRenderer = renderer;
   }

   public void setOvershowManager(OvershowManager overshowManager) {
      mOvershowManager = overshowManager;
   }

   void checkOvershow(Point at) {
      TreePath path = mTree.getPathForLocation(at.x, at.y);
      if (path == null) {
         updateOvershow(null, false);
         return;
      }
      BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
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
         int indent = node.getDepth() * mRenderer.mIndentWidth;
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
      int indent = node.getDepth() * mRenderer.mIndentWidth;
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
      OvershowManager dnd = mOvershowManager;//getDnD();
      if (dnd == null) {
         return;
      }
      dnd.setOvershowComponent(null);
      mCurrentOvershow = null;
   }

   private void updateOvershow(final BasicTreeNode node, boolean isData) {
      final OvershowManager w = mOvershowManager;
      if (w == null) {
         return;
      }
      if (mCurrentOvershow != node || mCurrentIsData != isData) {
         dispose();
         mCurrentOvershow = node;
         mCurrentIsData = isData;
         if (node != null) {
            final Point r = SwingUtilities.convertPoint(mTree, new Point(), w.getRoot());
            Rectangle tr = mTree.getPathBounds(node.getTreePath());
            final Insets i = mTree.getInsets();
            JPanel p = new JPanel() {
               /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
                  super.paint(g);
                  boolean isSel = mTree.getSelectionNode() == node;
                  boolean isFoc = mTree.hasFocus() && isSel;
                  boolean isExp = mTree.isExpanded(node.getTreePath());
                  Color backgroundColor = node.getBackgroundColor();
                  if (backgroundColor != null) {
                     g.setColor(backgroundColor);
                     g.fillRect(0, 0, getWidth(), getHeight());
                  }
                  mRenderer.getTreeCellRendererComponent(mTree, node, isSel, isExp, node.isLeaf(), 0, isFoc);
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
                     rr = mRenderer.getPreferredSize().width - mRenderer.getRelativeDataOffset(node);
                  }
                  else {
                     if (mRenderer.mHasDivider) {
                        rr = mRenderer.getRelativeDataOffset(node);
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
                  mRenderer.getTreeCellRendererComponent(mTree, node, false, false, node.isLeaf(), 0, false);
                  if (mCurrentIsData) {
                     int left = r.x + i.left + mRenderer.getRelativeDataOffset(node);
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
            };
            p.setOpaque(true);
            p.setBackground(mTree.getBackground());

            if (isData) {
               int indent = mRenderer.getDataOffset(node);
               //int height = tr.height;
               int yoff = tr.y;
               p.setLocation(r.x + indent + i.left, r.y + yoff);
            }
            else {
               int indent = node.getDepth() * mRenderer.mIndentWidth;
               //int height = tr.height;
               int yoff = tr.y;
               p.setLocation(r.x + indent + i.left, r.y + yoff);
            }
            p.setSize(p.getPreferredSize());
            w.setOvershowComponent(p);
         }
      }
   }
}
