package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizeable;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreePathUtils;

/**
 * A {@link TreeCellRenderer} which implements many features:<br>.
 * <ol>
 * <li>Implements {@link HorzSizeable} (which {@link EditableTree} takes advantage of) in order
 * to do a reasonable job with over-show painting (i.e. doing ...).
 * <li>Divides each tree node into 3 pieces; the icon, the name, and the data area.  Both the icon and data-area
 * are optional areas.
 * <li>Allows drag'n'drop resizing between name and data area (if data area available).
 * <li>Handles editing of name and/or data area (when there is a {@link NameValueTreeCellEditor} also set).
 * <li>Implements {@link ExtendedTreeCellRenderer} for background colors and tool tips.
 * </ol>
 */
public class NameValueTreeCellRenderer extends JPanel implements TreeCellRenderer, HorzSizeable, ExtendedTreeCellRenderer {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int DATA_TEXT_X_OFFSET = 2; // # of x pixels in to space from start of data area.
   public static final int FLASH_TIME = 500; // ms.  Time the area flashes when text is dropped on it.
   public static final Color FLASH_COLOR = new Color(235, 235, 255);

   private EditableTree m_tree;
   private int m_availableWidth;
   private int m_height = -1; // constant after first run.
   boolean mExpanded;
   boolean mSelected;
   boolean mFocus;
   Color mRowSeparatorColor = new Color(240, 240, 255);
   private boolean m_movingInvisibleDivider;
   private final NameValueTreeCellPlugin m_plugin;

//   private HashMap mFlashMap = new HashMap();

   private final Color mBackgroundSelectionColor;
   //private final Color mBackgroundNonSelectionColor;
   private final Color mBorderSelectionColor;
   private final Color mTextSelectionColor;
   private final Color mTextNonSelectionColor;
   private final Color mDataSolidBackgroundColor = new Color(255, 255, 240);
   private final Color mDataLeadingLineColor = new Color(220, 220, 220);
   private NameValueTreeCellOvershowPopup m_overshowPopup;

   boolean mHasDivider = false;
//    boolean mPaintCardinality = false;
   boolean mLightenMissing = false;

//    public int mMinimumNameWidth = 40;
//    public int mMinimumDataWidth = 40;
   boolean mDrawIcon = true;
   int mTreeExpansionIndent = 10; // # of pixels that 1 level of indent causes.

   /**
    * The indent width of a row.
    */
   int mIndentWidth;

   int mStartingDataOffset = 50;
   boolean mIndentDataOffset = true;
   int mNameOffset;

   Object m_node;

   // Unix fix - make the font available to BasicTree for
   // cell height calculation.

   public NameValueTreeCellRenderer(EditableTree tree, NameValueTreeCellPlugin plugin) {
      m_plugin = plugin;

      // steal initial colors from default:
      m_tree = tree;
      m_overshowPopup = new NameValueTreeCellOvershowPopup(this, tree);
      DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
      mBackgroundSelectionColor = r.getBackgroundSelectionColor();
      //mBackgroundNonSelectionColor = r.getBackgroundNonSelectionColor();
      mBorderSelectionColor = r.getBorderSelectionColor();
      mTextSelectionColor = r.getTextSelectionColor();
      mTextNonSelectionColor = r.getTextNonSelectionColor();

      setOpaque(false);

      /*
      MouseAdapter ma = new MouseAdapter() {
          public void mouseClicked(MouseEvent me) {
              if (me.getClickCount()==2)
              {
                  if (!isInlineEditable())
                  {
                      int row = m_tree.getRowForYLocation(me.getY());
                      Object node = m_tree.getPathForRow(row).getLastPathComponent();
                      m_tree.setSelectionRow(row);
                      //WCETODO REDO: edit(node);
                  }
              }
          }

          public void mousePressed(MouseEvent me) {
              TreePath path = getPathForLocation(me.getX(),me.getY());
              if (path==null) {
                  int row = getRowForYLocation(me.getY());
                  if (row<0)
                  {
                      // If the user clicks outside of the tree area, deselect:
                      if (mClickOutsideClearsSelection)
                      {
                          clearSelection();
                      }
                      stopEditing();
                  }
                  Rectangle pb = getRowBounds(row);
                  if (pb!=null) {
                      BasicTreeNode node = (BasicTreeNode) getPathForRow(row).getLastPathComponent();
                      int offset = 0;// always select...node.isLeaf() ? 0 : 11; // allow selection to left of expander
                      if (pb.x > me.getX()+offset) {
                          setSelectionRow(row);
                      }
                      // ctrl press = edit.
                      if (me.isControlDown()) {
                          if (isEditable()) {
                              edit(node);
                          }
                      }
                  }
              } else {
                  if (!isInlineEditable() && isEditable() && me.isControlDown()) {
                      BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
                      edit(node);
                  }
              }
          }

          public void mouseReleased(MouseEvent me) {
              if (me.isPopupTrigger()) {
                  Point at = SwingUtilities.convertPoint((Component)me.getSource(),me.getPoint(),BasicTree.this);
                  mButtonManager.showPopup(at.x,at.y);
                  me.consume();
              }
          }

          public void mouseExited(MouseEvent me) {
              mPopup.dispose();
          }
      };
      addMouseListener(ma);*/

      // Add listener for the draggable name/data splitter:
      tree.addMouseMotionListener(new MouseMotionListener() {
         //private boolean mMoving = false;
         private int mMovingOffset;

         private boolean inRange(MouseEvent me) {
            int x = me.getX();
            int row = m_tree.getRowForYLocation(me.getY());
            if (row < 0) {
               return false;
            }
            TreePath n = m_tree.getPathForRow(row);
            if (n == null) {
               return false;
            }
            int dataOffset = getDataOffset(n.getLastPathComponent());
            return x > dataOffset - 15 && x < dataOffset + 2;
         }

         public void mouseMoved(MouseEvent me) {
            m_overshowPopup.checkOvershow(me.getPoint());
            if (!mHasDivider) {
               m_tree.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               return;
            }
            if (inRange(me)) {
               m_tree.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
               m_movingInvisibleDivider = true;
               mMovingOffset = me.getX() - mStartingDataOffset;
            }
            else {
               m_tree.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               m_movingInvisibleDivider = false;
            }
         }

         public void mouseDragged(MouseEvent me) {
            if (m_movingInvisibleDivider) {
               m_tree.stopEditing();
               mStartingDataOffset = Math.max(50, Math.min(me.getX() - mMovingOffset, m_availableWidth - 50));
               m_tree.repaint();
            }
         }
      });
   }

   public Color getBackgroundColor(Object node) {
      return m_plugin.getBackgroundColor(node);
   }

   public boolean isCapturingMouse() {
      return m_movingInvisibleDivider;
   }

   public String getToolTipText(Object node, Point rendererRelative) {
      int dof = getRelativeDataOffset(node);
      if (rendererRelative.x >= dof) {
         if (m_plugin.isDataEditable(node)) {
            return m_plugin.getDataToolTip(node, new Point(rendererRelative.x - dof, rendererRelative.y));
         }
         return null; // no tooltip.
      }
      return m_plugin.getNameToolTip(node);
   }

   public int getDividerLocation() {
      return mStartingDataOffset;
   }

   public void setDividerLocation(int loc) {
      mStartingDataOffset = Math.max(loc, 50);
   }

   /**
    * Implementation override for {@link HorzSizeable}
    *
    * @param availableWidth
    */
   public void resized(int availableWidth) {
      if (availableWidth != m_availableWidth) {
         m_availableWidth = availableWidth;
         rescaled();
         mStartingDataOffset = Math.max(50, Math.min(mStartingDataOffset, m_availableWidth - 50));
      }
   }

   /*
   void setFlashData(BasicTree.FlashData[] data)
   {
       mFlashMap.clear();
       for (int i=0;i<data.length;i++) {
           BasicTree.FlashData fd = data[i];
           mFlashMap.put(fd.path,fd.range);
       }
       Timer deselectRange = new Timer(FLASH_TIME, new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent ae) {
               mFlashMap.clear();
               m_tree.repaint();
           }
       });
       deselectRange.setRepeats(false);
       deselectRange.start();
   }*/

   public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                 boolean sel,
                                                 boolean expanded,
                                                 boolean leaf, int row,
                                                 boolean hasFocus) {
      mSelected = sel;
      mExpanded = expanded;
      mFocus = hasFocus;
      m_node = value;
      if (value == null) {
         throw new NullPointerException();
      }
      return this;
   }

   public void setDrawIcon(boolean drawIcon) {
      mDrawIcon = drawIcon;
   }

   public boolean getDrawIcon() {
      return mDrawIcon;
   }

   boolean nameFits(Object node) {
      Graphics g = m_tree.getGraphics();
      TextRenderer tr = m_plugin.getNameRenderer(node, false, false);
      int textWidth = (tr == null ? 0 : tr.computeWidth(g)) + m_plugin.getIconWidth();
      g.dispose();
      if (mHasDivider) {
         return textWidth < getRelativeDataOffset(node);
      }
      else {
         return textWidth < getPreferredWidth(node);
      }
   }

   boolean dataFits(Object node) {
      if (!m_plugin.isDataEditable(node)) {
         return true;
      }
      Graphics g = m_tree.getGraphics();
      if (m_plugin.isMultiLineData(node)) {
         return false;
      }
      int textWidth = m_plugin.getDataRenderer(node).computeWidth(g);
      g.dispose();
      return textWidth < m_availableWidth - (getDataOffset(node));
   }

   private int computeNameWidth(Graphics g, Object node, int dataOffset) {
      int w = m_plugin.getNameRenderer(node, false, false).computeWidth(g);
      int t = w + mNameOffset + 4;
      return Math.min(t, dataOffset);
   }

   /**
    * Package private; used by {@link NameValueTreeCellEditor}, too.
    */
   void paintLabel(Graphics g, int width, int height, int dataOffset) {
      Object node = m_node;

      int awidth;
      awidth = dataOffset - mNameOffset;
      int selOffset = mNameOffset - 1;
      if (mSelected) {
         g.setColor(mBackgroundSelectionColor);
         int textWidth = computeNameWidth(g, node, dataOffset);
         g.fillRect(selOffset, 0, textWidth - selOffset, height);
      }
      if (mFocus) {
         int textWidth = computeNameWidth(g, node, dataOffset);
         g.setColor(mBorderSelectionColor);
         g.drawRect(selOffset, 0, textWidth - (selOffset + 1), height - 1);
      }
      Icon i = m_plugin.getIcon(m_node);
      if (i != null) {
         i.paintIcon(this, g, 0, 0);
      }
      if (mSelected) {
         g.setColor(mTextSelectionColor);
      }
      else {
         g.setColor(mTextNonSelectionColor);
      }
      TextRenderer r = m_plugin.getNameRenderer(node, mSelected, mFocus);
      if (r == null) {
         // Don't crash:
         g.setColor(Color.red);
         g.drawString("Null renderer", mNameOffset, 10);
      }
      else {
         r.render(g, mNameOffset, 0, awidth, height);
      }
      //StringPaintUtils.paintDisplayableString(g,dname,awidth,mNameOffset,aty);
   }

   /**
    * Allows setting if this has a data portion (and a draggable divider between name/data).<br>
    * Default is <code>false</code>.
    */
   public void setHasDivider(boolean hasDivider) {
      mHasDivider = hasDivider;
   }

   public void paint(Graphics g) {
      int width = getWidth();
      int height = getHeight();
      int dataAt = getRelativeDataOffset(m_node);

      if (mHasDivider) {
         paintLabel(g, width, height, dataAt);
      }
      else {
         paintLabel(g, width, height, width);
         return; // no more stuff.
      }
      int aty = 0;
      int dataWidth = width - dataAt;

      boolean editable = m_plugin.isDataEditable(m_node);
      int sheight = height - 1;
      int dataTextOffset = DATA_TEXT_X_OFFSET; // to line up with editor.
      int awidth = (width - dataAt) - dataTextOffset;
      if (editable) {
         TextRenderer renderer = m_plugin.getDataRenderer(m_node);
         // draw the value.
         if (renderer != null) {
            Color dataBackgroundColor = m_plugin.getDataBackgroundColor(m_node);
            if (dataBackgroundColor == null) {
               dataBackgroundColor = mDataSolidBackgroundColor;
            }
            g.setColor(dataBackgroundColor);
            g.fillRect(dataAt, 0, dataWidth - 1, sheight);
            g.draw3DRect(dataAt, 0, dataWidth - 1, sheight, true);

            /*if (mFlashMap.size()>0) {
                if (mFlashMap.containsKey(m_node.getTreePath())) {
                    String text = m_node.getDataValue();
                    if (text!=null) {
                        g.setColor(FLASH_COLOR);
                        TextRange r = (TextRange)mFlashMap.get(m_node.getTreePath());
                        int first = r.getStartPosition();
                        int o1 = m_node.getDataRenderer().getTextOffset(g,text.substring(0,first));
                        int end = r.getEndPosition();
                        int o2 = m_node.getDataRenderer().getTextOffset(g,text.substring(0,end));
                        g.fillRect(dataAt+dataTextOffset+o1,0,o2-o1,sheight);
                    }
                }
            }*/
            renderer.render(g, dataAt + dataTextOffset, aty, awidth, height);
         }
      }
      g.setColor(mDataLeadingLineColor);
      g.drawLine(dataAt - 1, 1, dataAt - 1, getHeight() - 3);
   }

   void paintOvershowData(Graphics g, int width, int height) {
      Color clr = m_plugin.getDataBackgroundColor(m_node);
      if (clr == null) {
         clr = mDataSolidBackgroundColor;
      }
      g.setColor(clr);
      g.fillRect(0, 0, width - 1, height - 1);
      g.draw3DRect(0, 0, width - 1, height - 1, true);
      g.translate(DATA_TEXT_X_OFFSET, 0);
      m_plugin.paintOvershowData(m_node, g, new Dimension(width, height), m_tree.getRowHeight());
      g.translate(-DATA_TEXT_X_OFFSET, 0);
   }

   private void rescaled() {
      int xat = m_plugin.getIconWidth();
      mNameOffset = xat;
      mIndentWidth = m_tree.getIndent();
   }

   /**
    * Gets the offset of the node's data display x location relative to the left-hand side of the tree.
    *
    * @param node The node.
    * @return The offset.
    */
   public int getDataOffset(Object node) {
      if (mIndentDataOffset) {
         int depth = EditableTreePathUtils.getDepth(m_tree.getEditableModel(), node);
         return mStartingDataOffset + depth * mIndentWidth;
      }
      else {
         return mStartingDataOffset;
      }
   }

   /**
    * Gets the offset of the node's data display x location relative to the already offset tree indentation.
    *
    * @param node The node
    * @return The offset
    */
   int getRelativeDataOffset(Object node) {
      if (mIndentDataOffset) {
         return mStartingDataOffset;
      }
      else {
         int depth = EditableTreePathUtils.getDepth(m_tree.getEditableModel(), node);
         return mStartingDataOffset - depth * mIndentWidth;
      }
   }

   private int getPreferredWidth(Object node) {
      int indent = EditableTreePathUtils.getDepth(m_tree.getEditableModel(), node);
      int avail = m_availableWidth - indent * mIndentWidth;

      return Math.max(100, avail);
   }

   public Dimension getPreferredSize() {
      if (m_height < 0) {
         m_height = super.getPreferredSize().height;
      }
      return new Dimension(getPreferredWidth(m_node), m_height);
   }

   public Dimension getNameOvershowSize() {
      Graphics g = m_tree.getGraphics();
      int nw = m_plugin.getNameRenderer(m_node, false, false).computeWidth(g);
      int totalWidth = nw + mNameOffset + 4; // 4 pixels for painting slop.
      g.dispose();
      return new Dimension(totalWidth, m_tree.getRowHeight());
   }

   NameValueTreeCellPlugin getPlugin() {
      return m_plugin;
   }

   Dimension getDataOvershowSize(int availableWidth) {
      Graphics g = m_tree.getGraphics();
      Dimension d = m_plugin.getDataOvershowSize(m_node, g, availableWidth, m_tree.getRowHeight());
      g.dispose();
      return d;
   }
}
