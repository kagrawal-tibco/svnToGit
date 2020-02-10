package com.tibco.cep.studio.mapper.ui.edittree;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.undo.UndoManager;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeModel;
import com.tibco.cep.studio.mapper.ui.data.basic.ExtendedTreeModelListener;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizeable;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.OvershowManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;
import com.tibco.cep.studio.mapper.ui.edittree.render.ExtendedTreeCellRenderer;

/**
 * A generic editable tree based on {@link JTree}.<br>
 * The model for this component is a {@link EditableTreeModel}.<br>
 * If the {@link TreeCellRenderer} also implements {@link HorzSizeable}, the size information will be passed along.<br>
 * Supports cut/copy/paste at the node level if {@link #setCopyPasteHandler} is set.<br>
 * Supports right-click menus if {@link #setContextMenuHandler} is set.<br>
 * Defaults the {@link #setInvokesStopCellEditing} to <code>true</code>.
 */
public class EditableTree extends JTree implements SoftDragNDropable, HorzSizeable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Rectangle mDragHintBounds;
   private int m_computedIndentWidth;
   private Vector<ChangeListener> mChangeListeners = new Vector<ChangeListener>();
   private TreePath mDraggingPath;
   private EditableTreeButtonManager mButtonManager;
   private boolean mClickOutsideClearsSelection = true;
   private boolean m_treeMode;
   protected UIAgent uiAgent;
   private ArrayList<ExtendedTreeModelListener> m_extendedTreeModelListeners = new ArrayList<ExtendedTreeModelListener>();
   private OvershowManager m_overshowManager;
   private EditableTreeDragNDropHandler m_dragNDropHandler;
   private EditableTreeCopyPasteHandler m_copyPasteHandler;
   private EditableTreeContextMenuHandler m_contextMenuHandler;
   private UndoManager m_undoManager;
   private EditableTreeReportRunnerController m_reportRunner = new EditableTreeReportRunnerController();
   private Color m_lineSeparatorColor;
   private EditableTreeEditHandler m_editHandler;
   private EditableTreeExpansionHandler m_expansionHandler;
   private boolean m_treeEditable = true;

   /**
    * If not set, won't allow the root element to be collapsed.
    */
   private boolean m_allowRootCollapse = false;
   private boolean m_allowsCrossBranchDrops = true;

   boolean mInlineEditable = true;

   protected Rectangle mDropHintBounds;

   /**
    * The property associated with {@link #setTreeMode} and {@link #getTreeMode}.
    */
   public static final String TREE_MODE_PROPERTY = "treeMode";

   public EditableTree(UIAgent uiAgent) {
      super(new BasicTreeModel(null));
      // This is a 1.4 method: super.setDragEnabled(false); // just in case, should be off by default.
      this.uiAgent = uiAgent;
      
      // Use a smaller border:
      setBorder(DisplayConstants.getCompressedTreeContentsBorder());
      setOpaque(false);
      setup();

      // Unix fix - getRowHeight() returns incorrect value here on Unix.
      // Use the cell renderer's font to calcuate the row height.
      FontMetrics fm = getFontMetrics(uiAgent.getAppFont());
      FontMetrics fm2 = getFontMetrics(uiAgent.getScriptFont());
      setRowHeight(Math.max(fm.getHeight(), fm2.getHeight()) + 2); // 2-> 2 pixels for border.

      mButtonManager = new EditableTreeButtonManager(this);
      addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent ce) {
            mButtonManager.reenableButtons();
            TreePath tp = ce.getNewLeadSelectionPath();
            if (tp != null) {
               EditableTree.this.scrollPathToVisible(tp);
            }
         }
      });
      addTreeExpansionListener(new TreeExpansionListener() {
         public void treeCollapsed(TreeExpansionEvent tel) {
            // who cares..
            if (!m_allowRootCollapse) {
               if (tel.getPath().getPathCount() == 1) {
                  // don't allow root to collapse.
                  expandPath(tel.getPath());
               }
            }
         }

         public void treeExpanded(TreeExpansionEvent tel) {
            Object n = tel.getPath().getLastPathComponent();
            // for unexpanded nodes, fill in child reports:
            m_reportRunner.fillInChildReports(n);
         }
      });
      addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();
            if (ke.isConsumed()) {
               return;
            }
            switch (keyCode) {
               case KeyEvent.VK_ENTER:
                  if (!ke.isControlDown()) {
                     ke.consume();
                     TreePath path = getSelectionPath();
                     if (path != null) {
                        startEditingAtPath(path);
                     }
                  }
            }
         }
      });

      ToolTipManager.sharedInstance().registerComponent(this);

      MouseAdapter ma = new MouseAdapter() {
         // 1-20KD29 -- Windows systems trigger popups on mouse release
         public void mouseReleased(MouseEvent me) {
            if (me.isPopupTrigger()) {
               showPopup(me);
            }
         }

         // 1-20KD29 -- Unix systems trigger popups on mouse press
         public void mousePressed(MouseEvent me) {
            if (me.isPopupTrigger()) {
               showPopup(me);
            }
         }

         private void showPopup(MouseEvent me) {
            Point at = SwingUtilities.convertPoint((Component) me.getSource(), me.getPoint(), EditableTree.this);
            mButtonManager.showPopup(at.x, at.y);
            me.consume();
         }
      };
      addMouseListener(ma);
   }

   public void setUndoManager(UndoManager undoManager) {
      if (undoManager == null) {
         throw new NullPointerException("Null handler");
      }
      m_undoManager = undoManager;
   }

   public UndoManager getUndoManager() {
      if (m_undoManager == null) {
         m_undoManager = new UndoManager();
      }
      return m_undoManager;
   }

   /**
    * Returns the tree model, equivalent to {@link #getModel} but typecast.
    *
    * @return The tree model
    */
   public EditableTreeModel getEditableModel() {
      return (EditableTreeModel) getModel();
   }

   /**
    * Sets the tree model, equivalent to {@link #setModel} but type-safe.
    *
    * @param model The model
    */
   public void setEditableModel(EditableTreeModel model) {
      setModel(model);
   }

   public void setModel(TreeModel model) {
      super.setModel(model);
      markReportDirty();
   }

   /**
    * Sets if the root node of the tree is allowed to collapse.<br>
    * Default value is <code>true</code>
    */
   public void setAllowsRootCollapse(boolean allowsRootCollapse) {
      m_allowRootCollapse = allowsRootCollapse;
   }

   /**
    * Gets if the root node of the tree is allowed to collapse.<br>
    * Default value is <code>true</code>
    */
   public boolean getAllowsRootCollapse() {
      return m_allowRootCollapse;
   }

   /**
    * Gets if the tree should allow cross-branch-drops in drag'n'drop editing.<br>
    * Default value is <code>true</code>
    */
   public void setAllowsCrossBranchDrops(boolean allowsCrossBranchDrops) {
      m_allowsCrossBranchDrops = allowsCrossBranchDrops;
   }

   /**
    * Gets if the tree should allow cross-branch-drops in editing.<br>
    * Default value is <code>true</code>
    */
   public boolean getAllowsCrossBranchDrops() {
      return m_allowsCrossBranchDrops;
   }

   public void setLineSeparatorColor(Color c) {
      m_lineSeparatorColor = c;
   }

   /**
    * If set, any user click outside of the tree will clear the selection.
    */
   public void setClickOutsideClearsSelection(boolean val) {
      mClickOutsideClearsSelection = val;
   }

   public boolean getClickOutsideClearsSelection() {
      return mClickOutsideClearsSelection;
   }

   /**
    * Manually reenables all the buttons.<br>
    * Normally should not be needed, but often useful for subclasses to call after init.
    */
   public void reenableButtons() {
      mButtonManager.reenableButtons();
   }

   /**
    * Implementation override; handles buttons.
    *
    * @param enabled
    */
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      reenableButtons();
   }

   /**
    * Sets if this tree is in editable or read-only mode in the sense of moving nodes, etc.<br>
    * This differs from {@link JTree#setEditable} which is for controlling if a node is editable.<br>
    * By default, this is <code>true</code>
    */
   public void setTreeEditable(boolean edit) {
      m_treeEditable = edit;
      reenableButtons();
   }

   public boolean isTreeEditable() {
      return m_treeEditable;
   }

   private void setup() {
      javax.swing.plaf.basic.BasicTreeUI ui = (javax.swing.plaf.basic.BasicTreeUI) getUI();

      // Shrink indent to give more horizontal space:
      int leftIndent = 3; // normal default is 7
      int rightIndent = 8; // normal default is 13
      ui.setLeftChildIndent(leftIndent);
      ui.setRightChildIndent(rightIndent);
      m_computedIndentWidth = leftIndent + rightIndent;
   }

   /**
    * Gets the total indent from one level of the tree.<br>
    * There is no accessor since this is computed by adding left & right indent.
    *
    * @return The x-indent in pixels for one level.
    */
   public int getIndent() {
      return m_computedIndentWidth;
   }

   /**
    * Sets a handler for drag'n'drop.<br>
    * Default is null for no handling.
    *
    * @param handler The handler.
    */
   public void setDragNDropHandler(EditableTreeDragNDropHandler handler) {
      m_dragNDropHandler = handler;
   }

   public EditableTreeDragNDropHandler getDragNDropHandler() {
      return m_dragNDropHandler;
   }

   /**
    * Sets a handler for allowing better/more types of expand actions on the node.
    *
    * @param handler The expansion handler or null if none (it is optional)
    */
   public void setExpansionHandler(EditableTreeExpansionHandler handler) {
      m_expansionHandler = handler;
   }

   public EditableTreeExpansionHandler getExpansionHandler() {
      return m_expansionHandler;
   }

   public void setCopyPasteHandler(EditableTreeCopyPasteHandler handler) {
      m_copyPasteHandler = handler;
   }

   public EditableTreeCopyPasteHandler getCopyPasteHandler() {
      return m_copyPasteHandler;
   }

   public void setContextMenuHandler(EditableTreeContextMenuHandler handler) {
      m_contextMenuHandler = handler;
   }

   public EditableTreeContextMenuHandler getContextMenuHandler() {
      return m_contextMenuHandler;
   }

   public void setEditHandler(EditableTreeEditHandler handler) {
      m_editHandler = handler;
   }

   public EditableTreeEditHandler getEditHandler() {
      return m_editHandler;
   }

   /**
    * Implementation override.
    *
    * @param manager
    * @param mouseAt
    * @param dragObject
    * @return
    */
   public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      // just return the bounds for the line between the source & where we are:
      Point dragLoc = PaintUtils.getClosestEdgePoint(mDragHintBounds, mouseAt, 3);
      int minx = Math.min(dragLoc.x, mouseAt.x);
      int miny = Math.min(dragLoc.y, mouseAt.y);
      int maxx = Math.max(dragLoc.x, mouseAt.x);
      int maxy = Math.max(dragLoc.y, mouseAt.y);
      int slop = 10;
      return new Rectangle(minx - slop, miny - slop, 2 * slop + 1 + maxx - minx, 2 * slop + 1 + maxy - miny);
   }

   public void drawDraggingIndicator(Graphics g, SoftDragNDropManager manager, Point mouseAt, Object obj) {
      Point dragLoc = PaintUtils.getClosestEdgePoint(mDragHintBounds, mouseAt, 3);
      g.setColor(Color.green);
      if (!mDragHintBounds.contains(mouseAt)) {
         boolean isHorz = PaintUtils.isOnHorizontalEdge(mDragHintBounds, dragLoc);
         PaintUtils.drawAngledArrow(g, dragLoc.x, dragLoc.y, mouseAt.x, mouseAt.y, Color.green, Color.black, 3, 8, isHorz);
      }
   }

   /**
    * Implementation override.
    */
   public void paint(Graphics g) {
      g.setColor(getBackground());
      int rc = getRowCount();
      int rh = getRowHeight();
      int w = getWidth();
      int h = getHeight();
      Rectangle clip = g.getClipBounds();

      int topVisibleRow = Math.max(0, getRowForYLocation(clip.y) - 1);
      int bottom = getRowForYLocation(clip.y + clip.height);
      int bottomVisibleRow;
      if (bottom == -1) {
         bottomVisibleRow = rc;
      }
      else {
         bottomVisibleRow = Math.max(rc, bottom + 1);
      }
      int base = getInsets().top;

      g.fillRect(0, 0, w, h);
      // paint any rows:
      TreeCellRenderer r = getCellRenderer();
      if (r instanceof ExtendedTreeCellRenderer) {
         ExtendedTreeCellRenderer nv = (ExtendedTreeCellRenderer) r;
         // paint background on any rows that have it:
         for (int i = topVisibleRow; i < bottomVisibleRow; i++) {
            TreePath p = getPathForRow(i);
            Object n = p.getLastPathComponent();

            Color c = nv.getBackgroundColor(n);
            if (c != null) {
               g.setColor(c);
               int y = i * rh + base;
               int bt = rh - 1;
               g.fillRect(0, y, w, bt);
            }
         }
      }
      if (m_lineSeparatorColor != null) {
         g.setColor(m_lineSeparatorColor);
         for (int i = topVisibleRow; i < bottomVisibleRow; i++) {
            int y = i * rh + base;
            int bt = y + rh - 1;
            g.drawLine(0, bt, w, bt);
         }
      }
      g.setColor(Color.white);
      if (!getEditableModel().isRootNull()) {
         super.paint(g);
      }

      /*if (mHasDivider)
      {
          paintErrors(g);
      }*/

      if (isEditable() && mDropHintBounds != null) {
         g.setColor(Color.red);
         g.drawRect(mDropHintBounds.x, mDropHintBounds.y, mDropHintBounds.width - 1, mDropHintBounds.height - 1);
      }
   }

   public boolean drop(SoftDragNDropManager m, Point p, Object obj) {
      if (!isEditable() || !isTreeEditable() || !isEnabled()) {
         return true;
      }
      computeDropHintBounds(p, true);
      return true;
   }

   public void dropStopped() {
      mDropHintBounds = null;
   }

   public Rectangle getDropHintBounds() {
      if (!isEditable() || !isTreeEditable() || !isEnabled()) {
         return null;
      }
      return mDropHintBounds;
   }

   protected final Rectangle computeDropHintBounds(Point point, Object obj) {
      return computeDropHintBounds(point, false);
   }

   private Rectangle computeDropHintBounds(Point point, boolean doDrop) {
      EditableTreeModel model = getEditableModel();
      int row = getRowForLocation(point.x, point.y);
      Rectangle bottomRow = super.getRowBounds(super.getRowCount() - 1);
      if (point.y >= (bottomRow.y + bottomRow.height)) {
         // be gentle with bottom row... give a few extra pixels in y past end:
         point.y = Math.max((bottomRow.y + bottomRow.height - 1), point.y - 25);
      }
      if (row < 0) {
         // be gentle with x location:
         row = getRowForLocation(Math.max(20, point.x - 40), point.y);
         if (row < 0) {
            return null;
         }
      }
      TreePath draggingtp = mDraggingPath;
      int draggingRow = getRowForPath(draggingtp);
      if (draggingRow == -1) {
         // some sort of bug...
         return null;
      }
      int sourceWidth = getRowBounds(draggingRow).width;

      Object draggingNode = draggingtp.getLastPathComponent();
      TreePath path = getPathForRow(row);
      Object node = path.getLastPathComponent();
      if (draggingNode == node) {
         // can't drag onto self
         return null;
      }
      if (row == 0) {
         // can't drag onto root.
         return null;
      }
      if (draggingRow == 0) {
         // can't drag the root, either.
         return null;
      }
      if (draggingNode == node) {
         // can't drag onto self
         return null;
      }
      Object at = node;
      while (at != null) {
         if (at == draggingNode) {
            // can't drag onto ancestor of self...
            return null;
         }
         at = model.getParent(at);
      }
      int draggingDepth = draggingtp.getPathCount();
      int toDepth = path.getPathCount();

      Object moveNode = draggingNode;
      Object moveToParent = model.getParent(node);

      boolean isSameDepth = draggingDepth == toDepth;
      boolean isSameParent = model.getParent(moveNode) == moveToParent;
      boolean isJustAbove = draggingRow - 1 == row && isSameDepth;
      boolean isJustBelow = draggingRow + 1 == row && isSameDepth;
      if (!isSameParent && !getAllowsCrossBranchDrops()) {
         return null;
      }
      if (!model.canHaveChildren(moveToParent)) {
         return null;
      }

      Rectangle rb = getRowBounds(row);
      int ymin = rb.y;
      int ymax = ymin + rb.height;

      int childIndex = model.getIndexOfChild(moveToParent, node);
      if (!model.canHaveChildren(node) || !getAllowsCrossBranchDrops()) {
         // Split it into half, top half means add above
         if (point.y < (ymax + ymin) / 2) {
            if (isJustBelow) {
               return null;
            }
            if (doDrop) {
               EditableTreeActions.move(this, moveNode, moveToParent, childIndex);
            }
            return new Rectangle(rb.x, ymin - 2, sourceWidth, 4);
         }
         else {
            if (isJustAbove) {
               return null;
            }
            childIndex++;
            if (doDrop) {
               EditableTreeActions.move(this, moveNode, moveToParent, childIndex);
            }
            // bottom half, add below:
            return new Rectangle(rb.x, ymax - 2, sourceWidth, 4);
         }
      }
      // Split it into quarters, top half means add above
      if (point.y <= (ymin * 3 + ymax) / 4) {
         if (isJustBelow) {
            return null;
         }
         if (doDrop) {
            EditableTreeActions.move(this, moveNode, moveToParent, childIndex);
         }
         return new Rectangle(rb.x, ymin - 2, sourceWidth, 4);
      }
      if (point.y >= (ymin + ymax * 3) / 4) {
         if (isJustAbove) {
            return null;
         }
         childIndex++;
         if (doDrop) {
            EditableTreeActions.move(this, moveNode, moveToParent, childIndex);
         }
         return new Rectangle(rb.x, ymax - 2, sourceWidth, 4);
      }
      if (doDrop) {
         EditableTreeActions.move(this, moveNode, node, 0);
      }
      // can drop on it, too:
      return new Rectangle(rb);
   }

   /**
    * If is set, allows complex (hierarchical) editing.<br>
    * Otherwise, only allows single level editing.<br>
    *
    * @return
    */
   public boolean getTreeMode() {
      return m_treeMode;
   }

   /**
    * Set to allow complex (hierarhical) editing.<br>
    * Default is false.
    */
   public void setTreeMode(boolean treeMode) {
      if (treeMode != m_treeMode) {
         m_treeMode = treeMode;
         super.firePropertyChange(TREE_MODE_PROPERTY, !treeMode, treeMode);
      }
   }

   public boolean dragOver(SoftDragNDropManager manager, Point p, Object obj) {
      if (!isEditable() || !isTreeEditable() || !isEnabled()) {
         return true;
      }
      if (mDragHintBounds == null) {
         // drop is only working for intra-tree drag'n'drops.
         return true;
      }
      mDropHintBounds = computeDropHintBounds(p, obj);
      return true;
   }

   public void dragStopped() {
      mDropHintBounds = null;
      mDraggingPath = null;
   }

   public Rectangle getDragHintBounds() {
      return mDragHintBounds;
   }

   public int getRowForYLocation(int y) {
      int rh = getRowHeight();
      if (rh == 0 || getRowCount() == 0) {
         return -1;
      }
      Rectangle topRowBounds = getRowBounds(0);
      if (topRowBounds == null) {
         return -1;
      }
      int baseY = y - topRowBounds.y;
      int rr = baseY / rh;
      if (rr >= getRowCount()) {
         return -1;
      }
      return rr;
   }

   public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
      if (m_dragNDropHandler == null) {
         return null;
      }
      if (isEditing()) {
         // If we allow this, then Autoscroller (in Swing) will sometimes give crashes.
         // It's probably nicer this way anyway, it makes it harder to accidentally cancel editing.
         return null;
      }
      TreeCellRenderer crr = getCellRenderer();
      if (crr instanceof ExtendedTreeCellRenderer) {
         ExtendedTreeCellRenderer er = (ExtendedTreeCellRenderer) crr;
         if (er.isCapturingMouse()) {
            return null;
         }
      }
      double distance = mouseAt.distance(pressedAt.getLocation());
      if (distance < 10) {
         // not far enough to trigger it.
         return null;
      }
      int row = getRowForYLocation(pressedAt.y);
      TreePath path = getPathForRow(row);
      if (path == null) {
         return null;
      }
      Rectangle r = getPathBounds(path);
      if (r == null) {
         // shouldn't happen
         return null;
      }
      if (!isRowSelected(row)) {
         setSelectionRow(row);
      }
      Object obj = getDragObjectFor(path);
      if (obj == null) {
         return null;
      }
      Graphics g = getGraphics();
      // r.width is maximum the width of <all> cells (because of how the renderer works), actual width is the
      // width it would be <excluding> truncation, so we want the min of these two number:
      int cellwidth = Math.min(r.width, r.width);
      int right = Math.min(cellwidth + r.x, getWidth()); // don't have right be beyond the window.
      int actualWidth = Math.max(right - r.x, 5); // 5 -> sanity lower limit.
      mDragHintBounds = new Rectangle(r.x, r.y, actualWidth, r.height);
      g.dispose();
      stopEditing();
      return obj;
   }

   public void addNotify() {
      super.addNotify();
      if (!(getParent() instanceof JViewport)) {
         throw new RuntimeException("Needs to go inside of a HorzSizedScrollPane");
      }
      JViewport jv = (JViewport) getParent();
      JScrollPane jsp = (JScrollPane) jv.getParent();
      if (!(jsp instanceof HorzSizedScrollPane)) {
         throw new RuntimeException("Needs to go inside of a HorzSizedScrollPane");
      }
   }

   /**
    * Expands to, selects, and makes visible the node.
    * Does nothing for null node.
    *
    * @param node The node, or null.
    */
   public void selectAndShowNode(Object node) {
      if (node == null) {
         return;
      }
      TreePath path = EditableTreePathUtils.getTreePath(getEditableModel(), node);
      expandPath(path.getParentPath());
      setSelectionPath(path);
      scrollPathToVisible(path);
   }

   /*
   public int getDividerLocation() {
       return mRenderer.mStartingDataOffset;
   }

   public void setDividerLocation(int width) {
       mRenderer.mStartingDataOffset = width;
   }*/

   /**
    * Does the 'copy' action.<br>
    * Equivalent to {@link EditableTreeActions#copy}.
    */
   public void copy() {
      // Delegate to another class (to keep file size down mostly)
      EditableTreeActions.copy(this);
   }

   /**
    * Does the 'cut' action.<br>
    * Equivalent to {@link EditableTreeActions#cut}.
    */
   public void cut() {
      // Delegate to another class (to keep file size down mostly)
      EditableTreeActions.cut(this);
   }

   /**
    * Does the 'delete' action.<br>
    * Equivalent to {@link EditableTreeActions#delete}.
    */
   public void delete() {
      // Delegate to another class (to keep file size down mostly)
      EditableTreeActions.delete(this);
   }

   public void deleteAll() {
      // Delegate to another class (to keep file size down mostly)
      EditableTreeActions.delete(this);
   }

   public void paste() {
      // Delegate to another class (to keep file size down mostly)
      EditableTreeActions.paste(this);
   }

   public void resized(int width) {
      int fixedOffset = getInsets().left + getInsets().right;
      if (getCellRenderer() instanceof HorzSizeable) {
         ((HorzSizeable) getCellRenderer()).resized(width - fixedOffset);
/*        mRenderer.mIndentWidth = mIndentWidth;

        int newOffset = Math.min(Math.max(50,mRenderer.mStartingDataOffset),width-50);
        mRenderer.mStartingDataOffset = newOffset;*/

         TreeCellRenderer r = getCellRenderer();
         setCellRenderer(null);
         setCellRenderer(r); // forces recomputation of widths.
      }
   }

   public TreeState getTreeState() {
      ArrayList<int[]> al = EditableTreePathUtils.getAllExpandedPosPaths(this);
      ArrayList<int[]> sal = EditableTreePathUtils.getAllSelectedPosPaths(this);
      return new TreeState((int[][]) al.toArray(new int[0][]),
                           (int[][]) sal.toArray(new int[0][]));
   }

   /**
    * For a given node, get the expansion state relative to that node (and in a rebuild independent way)
    *
    * @param node The tree node.
    * @return The state.
    */
   public TreeState getTreeStateForNode(Object node) {
      ArrayList<int[]> al = EditableTreePathUtils.getAllExpandedPosPaths(this, node);
      return new TreeState((int[][]) al.toArray(new int[0][]),
                           new int[0][]);
   }

   /**
    * For a given node, set an expansion 'state'.
    *
    * @param node
    * @param state
    */
   public void setTreeStateForNode(Object node, TreeState state) {
      ArrayList<int[]> al = new ArrayList<int[]>();
      for (int i = 0; i < state.mIntPaths.length; i++) {
         al.add(state.mIntPaths[i]);
      }
      EditableTreePathUtils.collapseAllPathsBelow(this, EditableTreePathUtils.getTreePath(getEditableModel(), node));
      EditableTreePathUtils.expandAllPosPaths(this, al, node);
      al.clear();
      for (int i = 0; i < state.mIntSelPaths.length; i++) {
         al.add(state.mIntSelPaths[i]);
      }
      EditableTreePathUtils.selectAllPosPaths(this, al);
   }

   public void setTreeState(TreeState state) {
      ArrayList<int[]> al = new ArrayList<int[]>();
      for (int i = 0; i < state.mIntPaths.length; i++) {
         al.add(state.mIntPaths[i]);
      }
      EditableTreePathUtils.expandAllPosPaths(this, al);
      al.clear();
      for (int i = 0; i < state.mIntSelPaths.length; i++) {
         al.add(state.mIntSelPaths[i]);
      }
      EditableTreePathUtils.selectAllPosPaths(this, al);
   }

   /**
    * Gets the currently selected node.<br>
    * Equivalent to getSelectionPath()
    *
    * @return The selected node, or null if none.
    */
   public Object getSelectionNode() {
      TreePath tp = getSelectionPath();
      if (tp == null) {
         return null;
      }
      return tp.getLastPathComponent();
   }

   /**
    * Gets the root node from the model.<br>
    * Equivalent to getMode().getRoot()
    *
    * @return The root node.
    */
   public Object getRootNode() {
      if (getModel() == null) {
         return null;
      }
      return getModel().getRoot();
   }

   /**
    * Gets the associated button manager; it allows access to standard buttons/menus for an editable tree.
    *
    * @return The button manager, never null.
    */
   public EditableTreeButtonManager getButtonManager() {
      return mButtonManager;
   }

   /**
    * Mostly for subclasses &c, to explicitly signal that the content changed.
    */
   public void contentChanged() {
      mButtonManager.reenableButtons();
      int ct = mChangeListeners.size();
      for (int i = 0; i < ct; i++) {
         ChangeListener cl = mChangeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }

   public void addContentChangeListener(ChangeListener cl) {
      mChangeListeners.add(cl);
   }

   public void removeContentChangeListener(ChangeListener cl) {
      mChangeListeners.remove(cl);
   }


   BasicTreeModel getBasicModel() {
      return (BasicTreeModel) super.getModel();
   }


   /*
    * Override to know when the root changes.
    *
   protected void rootChanged() {
   }

   protected boolean isRootNull() {
       return mRootIsNull;
   }

   protected void setRootNull(boolean rootNull) {
       mRootIsNull = rootNull;
       reenableButtons();
   }*/

   /**
    * Does not return until the report (which may have been cached already) is
    * done being generated.
    * Returns a sequence # for the how many times the report has been generated,
    * mainly for seeing if something changed.
    */
   public int waitForReport() {
      m_reportRunner.getReport(new DefaultCancelChecker()); // never cancel.
      return m_reportRunner.getReportSequenceNumber();
   }

   /**
    * Does not return until the report (which may have been cached already) is
    * done being generated.
    * Returns a sequence # for the how many times the report has been generated,
    * mainly for seeing if something changed.
    */
   public int waitForReport(CancelChecker cancelChecker) {
      m_reportRunner.getReport(cancelChecker);
      return m_reportRunner.getReportSequenceNumber();
   }

   public void markReportDirty() {
      if (m_reportRunner == null) {
         return; // happens at startup (with default JTree model)
      }
      m_reportRunner.setModel(getModel());
      m_reportRunner.markDirty();
      repaint();
   }

   public void setReportRunner(EditableTreeReportRunner runner) {
      m_reportRunner.setRunner(runner);
   }

   public String getToolTipText(MouseEvent me) {
      waitForReport();
      /*ErrorDesc ed = getErrorAt(me.getPoint());
      if (ed!=null) {
          return ed.mError.getMessage();
      }*/
      TreePath path;
      try {
         path = getPathForLocation(me.getX(), Math.max(0, me.getY() - 2)); // give 2 pixels slop because bottom part is more likely to be triggered for tool tips.
         if (path == null) {
            return null;
         }
      }
      catch (Exception e) {
         // In the unit tests, getPathForLocation can throw an exception, probably a timing thing in JTree.
         return null;
      }
      Object node = path.getLastPathComponent();

      TreeCellRenderer r = getCellRenderer();
      if (r instanceof ExtendedTreeCellRenderer) {
         Rectangle rr = getPathBounds(path);
         if (rr != null) {
            int x = me.getX() - rr.x;
            int y = me.getY() - rr.y;
            return ((ExtendedTreeCellRenderer) r).getToolTipText(node, new Point(x, y));
         }
      }
      return null;
   }

   /*
   private static class ErrorDesc {
       public TreePath mPath;
       public ErrorMessage mError;
   }

   private ErrorDesc getErrorAt(Point at) {
       ErrorDesc ed = getErrorAtExact(at);
       if (ed!=null) return ed;
       // Because errors are at the bottom of a line, be more forgiving & try
       // looking half a line up:
       return getErrorAtExact(new Point(at.x,at.y-getRowHeight()/2));
   }

   // Internal...
   private ErrorDesc getErrorAtExact(Point at) {
       TreePath path;
       try {
           path = getPathForLocation(at.x,at.y);
           if (path==null) {
               return null;
           }
       } catch (Exception e) {
           // Unit tests can cause getPathForLocation to fail, probably a timing thing in JTree.
           return null;
       }
       Rectangle b = getPathBounds(path);
       if (!b.contains(at))
       {
           return null;
       }
       BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
       ErrorMessageList lsel;
       if (getEditingPath()!=null && getEditingPath().equals(path)) {
           // special case because the values aren't up to date in the type node,
           // get directly from the editor:
           lsel = getEditorErrorMessageList();
       } else {
           lsel = node.getErrorMessages();
       }
       if (lsel==null) return null;
       ErrorMessage[] errs = lsel.getErrorAndWarningMessages();
       if (errs.length==0) return null;

       // Find closest error:
       int dof = mRenderer.getRelativeDataOffset(node);
       int xinset = (at.x - (b.x+dof+2));
       int columnsIn = 0 + getCharacterPosition(xinset);
       int smallestDistance = 100;
       for (int i=0;i<errs.length;i++) {
           ErrorMessage em = errs[i];
           int distance = computeColumnDistance(em.getTextRange(),columnsIn);
           smallestDistance = Math.min(smallestDistance,distance);
       }
       // If you weren't even close, forget it:
       if (smallestDistance>3) return null;

       // Go back & find the closest one:
       for (int i=0;i<errs.length;i++) {
           ErrorMessage em = errs[i];
           int distance = computeColumnDistance(em.getTextRange(),columnsIn);
           if (distance==smallestDistance) {
               ErrorDesc ed = new ErrorDesc();
               ed.mError = em;
               ed.mPath = path;
               return ed;
           }
       }
       // not reachable, actually.
       return null;
   }*/

   /*
    * Utility function that returns the number of columns away from the location
    *
   private static int computeColumnDistance(TextRange tl, int colNo) {
       if (tl.getStartPosition()<=colNo) {
           if (tl.getEndPosition()>=colNo) {
               return 0;
           }
           return colNo-tl.getEndPosition();
       }
//        tl.getDistance
       return tl.getStartPosition()-colNo;
   }*/

   /*
   protected int getCharacterPosition(int x)
   {
       if (getDataCharWidth()==0) { // can happen at startup.
           return 0;
       }
       return (x / this.getDataCharWidth());
   }*/

   /**
    * Adds an extended treemodel listener.
    *
    * @param listener
    */
   public void addExtendedTreeModelListener(ExtendedTreeModelListener listener) {
      m_extendedTreeModelListeners.add(listener);
   }

   void fireTreeNodesMovedInParent(TreePath path, int[] children, int offset) {
      for (int i = 0; i < m_extendedTreeModelListeners.size(); i++) {
         ExtendedTreeModelListener eml = m_extendedTreeModelListeners.get(i);
         eml.treeNodesMovedInParent(path, children, offset);
      }
   }

   public void setOvershowManager(OvershowManager overshowManager) {
      m_overshowManager = overshowManager;
   }

   public OvershowManager getOvershowManager() {
      return m_overshowManager;
   }

   private Object getDragObjectFor(TreePath path) {
      if (path == null || m_dragNDropHandler == null) {
         return null;
      }
      mDraggingPath = path;
      return m_dragNDropHandler.getDragObjectForNode(path.getLastPathComponent());
   }
}

