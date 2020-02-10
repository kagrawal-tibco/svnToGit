package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
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
import javax.swing.tree.TreePath;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultCancelChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizeable;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.OvershowManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.tns.cache.TnsDocumentProvider;

/**
 * Implementation utility base class for several data related trees.
 */
public abstract class BasicTree extends JTree implements SoftDragNDropable, HorzSizeable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private NamespaceContextRegistry m_mNamespaceContextRegistry;
   private Rectangle mDragHintBounds;
   private int mIndentWidth;
   boolean mAllowDeleteRoot;
   BasicTreeCellEditor mEditor;
   private BasicTreeCellRenderer mRenderer;
   private Vector<ChangeListener> mChangeListeners = new Vector<ChangeListener>();
   private TreePath mDraggingPath;
   boolean mRootIsNull = false; // only if allowRoot.
   private boolean mIsDragSource = true;
   private BasicTreeButtonManager mButtonManager;
   private boolean mClickOutsideClearsSelection = true;
   private boolean mDragEnabled = true;
   private OvershowPopup mPopup;
   protected UIAgent uiAgent;
//   private TnsDocumentProvider m_documentProvider;
   private ArrayList<ExtendedTreeModelListener> m_extendedTreeModelListeners = new ArrayList<ExtendedTreeModelListener>();
   private boolean m_doubleClickEdits;

   private String m_pathSeparator = "/";

   /**
    * If not set, won't allow the root element to be collapsed.
    */
   private boolean mAllowRootCollapse = false;

   final DefaultTextRenderer mDefaultDataRenderer;

   private BasicReportRunner mDefaultReportRunner = new BasicReportRunner() {
      public Object buildReport(CancelChecker cancelChecker) {
         return "none"; // not a user-visible string; just placeholder.
      }

      public Object getReportChild(Object val, int index) {
         return val;
      }

      public boolean getReportHasChildContent(Object val) {
         return false;
      }

      protected void ensureReportExpanded(Object report) {
      }
   };

   private BasicReportRunner mReportRunner = mDefaultReportRunner;

   private boolean mMovingInvisibleDivider;
   private boolean mHasDivider = true;
   boolean mHasDeleteAll = false; // If true, the 'deleteAll' action makes sense (as opposed to just delete)
   boolean mInlineEditable = true;

   protected Rectangle mDropHintBounds;


   protected BasicTree(UIAgent uiAgent) {
      this(uiAgent, "BasicTree");
   }

   protected BasicTree(UIAgent uiAgent, String compName) {
      super(new BasicTreeModel(null));
      this.uiAgent = uiAgent;
      mDefaultDataRenderer = new DefaultTextRenderer(uiAgent.getScriptFont(), false);
      mDefaultDataRenderer.mTextMissingNonSelectionColor = new java.awt.Color(140, 140, 140);
      setBorder(DisplayConstants.getCompressedTreeContentsBorder());
      setOpaque(false);
      setup();
      //setRowHeight(getRowHeight()+1);  value (11) is too small on Solaris and HP-UX; results in rows with height of 1 pixel

      // Unix fix - getRowHeight() returns incorrect value here on Unix.
      // Use the cell renderer's font to calcuate the row height.
      FontMetrics fm = getFontMetrics(uiAgent.getAppFont());
      FontMetrics fm2 = getFontMetrics(uiAgent.getScriptFont());
      setRowHeight(Math.max(fm.getHeight(), fm2.getHeight()) + 2); // 2-> 2 pixels for border.

      mRenderer = new BasicTreeCellRenderer(this, uiAgent);
      mButtonManager = new BasicTreeButtonManager(this);
      setCellRenderer(mRenderer);

      MouseAdapter ma = new MouseAdapter() {
         public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
               if (!isInlineEditable() && getDoubleClickEdits()) {
                  int row = getRowForYLocation(me.getY());
                  BasicTreeNode node = (BasicTreeNode) getPathForRow(row).getLastPathComponent();
                  setSelectionRow(row);
                  edit(node);
               }
            }
         }


         public void mousePressed(MouseEvent me) {
            if (me.isPopupTrigger()) {
               // 1-20KD29 -- X-Windows systems trigger popups on mouse press
               showPopup(me);
            }
            else {
               TreePath path = getPathForLocation(me.getX(), me.getY());
               if (path == null) {
                  int row = getRowForYLocation(me.getY());
                  if (row < 0) {
                     // If the user clicks outside of the tree area, deselect:
                     if (mClickOutsideClearsSelection) {
                        clearSelection();
                     }
                     stopEditing();
                  }
                  Rectangle pb = getRowBounds(row);
                  if (pb != null) {
                     BasicTreeNode node = (BasicTreeNode) getPathForRow(row).getLastPathComponent();
                     int offset = 0;// always select...node.isLeaf() ? 0 : 11; // allow selection to left of expander
                     if (pb.x > me.getX() + offset) {
                        setSelectionRow(row);
                     }
                     // ctrl press = edit.
                     if (me.isControlDown()) {
                        if (isEditable()) {
                           edit(node);
                        }
                     }
                  }
               }
               else {
                  if (!isInlineEditable() && isEditable() && me.isControlDown()) {
                     BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
                     edit(node);
                  }
               }
            }
         }

         public void mouseReleased(MouseEvent me) {
            if (me.isPopupTrigger()) {
               // 1-20KD29 -- Windows systems trigger popups on mouse release
               showPopup(me);
            }
         }

         private void showPopup(MouseEvent me) {
            Point at = SwingUtilities.convertPoint((Component) me.getSource(), me.getPoint(), BasicTree.this);
            mButtonManager.showPopup(at.x, at.y);
            me.consume();
         }

         public void mouseExited(MouseEvent me) {
            mPopup.dispose();
         }
      };
      addMouseListener(ma);
      addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent ce) {
            mButtonManager.reenableButtons();
            TreePath tp = ce.getNewLeadSelectionPath();
            if (tp != null) {
               BasicTree.this.scrollPathToVisible(tp);
            }
         }
      });
      addMouseMotionListener(new MouseMotionListener() {
         //private boolean mMoving = false;
         private int mMovingOffset;

         private boolean inRange(MouseEvent me) {
            int x = me.getX();
            int row = getRowForYLocation(me.getY());
            if (row < 0) {
               return false;
            }
            TreePath n = getPathForRow(row);
            if (n == null) {
               return false;
            }
            int dataOffset = mRenderer.getDataOffset((BasicTreeNode) n.getLastPathComponent());
            return x > dataOffset - 15 && x < dataOffset + 2;
         }

         public void mouseMoved(MouseEvent me) {
            mPopup.checkOvershow(me.getPoint());
            if (!mHasDivider) {
               BasicTree.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               return;
            }
            if (inRange(me)) {
               BasicTree.this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
               mMovingInvisibleDivider = true;
               mMovingOffset = me.getX() - mRenderer.mStartingDataOffset;
            }
            else {
               BasicTree.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               mMovingInvisibleDivider = false;
            }
         }

         public void mouseDragged(MouseEvent me) {
            if (mMovingInvisibleDivider) {
               stopEditing();
               setDataOffset(Math.min(Math.max(50, me.getX() - mMovingOffset), getWidth() - 50));
               repaint();
            }
         }
      });
      addTreeExpansionListener(new TreeExpansionListener() {
         public void treeCollapsed(TreeExpansionEvent tel) {
            // who cares..
            if (!mAllowRootCollapse) {
               if (tel.getPath().getPathCount() == 1) {
                  // don't allow root to collapse.
                  expandPath(tel.getPath());
               }
            }
         }

         public void treeExpanded(TreeExpansionEvent tel) {
            BasicTreeNode n = (BasicTreeNode) tel.getPath().getLastPathComponent();
            // for unexpanded nodes, fill in child reports:
            mReportRunner.fillInChildReports(n);
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
                  if (ke.isControlDown()) {
                     ke.consume();
                     BasicTreeActions.showEditor(BasicTree.this);
                  }
                  else {
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
      mPopup = new OvershowPopup(this, mRenderer);
   }

   public void setTreeCellStyle(TreeCellStyle style) {
      mRenderer.setTreeCellStyle(style);
   }

   public boolean getDoubleClickEdits() {
      return m_doubleClickEdits;
   }

   /**
    * Indicates if double-click should cause an edit, default is <code>false</code>
    */
   public void setDoubleClickEdits(boolean edits) {
      m_doubleClickEdits = edits;
   }

   public void setEditorDocument(SyntaxDocument doc) {
      mEditor.setEditorDocument(doc);
   }

   public boolean isInlineEditable() {
      return mInlineEditable;
   }

   public void setInlineEditable(boolean val) {
      mInlineEditable = val;
   }

   public void setOvershowManager(OvershowManager overshowManager) {
      mPopup.setOvershowManager(overshowManager);
   }

   /**
    * Sets the namespace to prefix resolver to be used when forming xpaths.<br>
    * <b>NOTE</b> It's a good ideas to pass in an implementation that will auto-register, otherwise, the xpaths
    * returned will have error markers in them.
    *
    * @param nm
    */
   public void setNamespaceImporter(NamespaceContextRegistry nm) {
      m_mNamespaceContextRegistry = nm;
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_mNamespaceContextRegistry;
   }

   /**
    * Does expandContent make sense?
    */
   protected boolean hasContent() {
      return mHasDivider;
   }

   /**
    * Does expandErrors make sense?
    */
   protected boolean hasErrors() {
      return mHasDivider;
   }

   /**
    * This evil call is used by JTreeUI.... and it looks like hell.
    */
   public void paintImmediately(int x, int y, int w, int h) {
      if (mEditor != null && mEditor.mJustStarted) {
         mEditor.mJustStarted = false;
         repaint();
         return;
      }
      super.paintImmediately(x, y, w, h);
   }

   /**
    * Allows overriding of the default root icon:
    */
   public void setRootDisplayIcon(Icon icon) {
      mRenderer.mRootDisplayIcon = icon;
   }


   public Icon getRootDisplayIcon() {
      return mRenderer.mRootDisplayIcon;
   }

   /**
    * Allows overriding of the default root name:
    */
   public void setRootDisplayName(String name) {
      mRenderer.mRootDisplayName = name;
   }

   /**
    * Allows overriding of the default root name:
    */
   public String getRootDisplayName() {
      return mRenderer.mRootDisplayName;
   }

   public void setRootDisplayNameBold(boolean bold) {
      mRenderer.mRootDisplayNameBold = bold;
   }

   public boolean getRootDisplayNameBold() {
      return mRenderer.mRootDisplayNameBold;
   }

   public static class FlashData {
      public FlashData(TreePath path, TextRange range) {
         this.path = path;
         this.range = range;
      }

      final TreePath path;
      final TextRange range;
   }

   /**
    * Flashes the data area for a short period of time.
    */
   public void setFlashData(FlashData[] flashData) {
      if (flashData.length == 0) {
         return;
      }
      mRenderer.setFlashData(flashData);
   }

   protected void setClickOutsideClearsSelection(boolean val) {
      mClickOutsideClearsSelection = val;
   }

   /**
    * The position where text entry fields start within the window.
    */
   protected int getDataOffset(BasicTreeNode node) {
      return mRenderer.getDataOffset(node);
   }

   protected void setHasDivider(boolean val) {
      mHasDivider = val;
      mRenderer.mHasDivider = val;
   }

   protected void setHasDeleteAll(boolean val) {
      mHasDeleteAll = val;
   }

   protected void setAllowDeleteRoot(boolean val) {
      mAllowDeleteRoot = val;
   }

   protected boolean getAllowDeleteRoot() {
      return mAllowDeleteRoot;
   }

   protected void setPaintCardinality(boolean val) {
      mRenderer.mPaintCardinality = val;
   }

   protected void setIndentData(boolean val) {
      mRenderer.mIndentDataOffset = val;
   }

   protected void setLightenMissing(boolean val) {
      mRenderer.mLightenMissing = val;
   }

   protected void setIsDragSource(boolean val) {
      mIsDragSource = val;
   }


   /**
    * Subclasses should call when the data editor's error list changes (for repaint)
    */
   protected void editingErrorsChanged() {
      TreePath editingPath = getEditingPath();

      if (editingPath != null) {
         // Errors changed, repaint a region <larger> than the editing cell
         // in order to paint the underlining properly:
         Rectangle r = getPathBounds(editingPath);
         Rectangle nr =
                 new Rectangle(r.x, r.y, r.width,
                               r.height += 10);

         repaint(nr);
      }
   }

   /**
    * Reenables all the buttons. Often useful for subclasses to call after init.
    */
   public void reenableButtons() {
      mButtonManager.reenableButtons();
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      reenableButtons();
   }

   private void setDataOffset(int value) {
      mRenderer.mStartingDataOffset = value;
   }

   private void setup() {
      javax.swing.plaf.basic.BasicTreeUI ui = (javax.swing.plaf.basic.BasicTreeUI) getUI();

      // Shrink indent to give more horizontal space:
      int leftIndent = 3; // normal default is 7
      int rightIndent = 8; // normal default is 13
      ui.setLeftChildIndent(leftIndent);
      ui.setRightChildIndent(rightIndent);
      mIndentWidth = leftIndent + rightIndent;
   }

   protected void setRootNode(BasicTreeNode node) {
      BasicTreeModel m = new BasicTreeModel(this, node);
      m.copyModelTo(node);
      mReportRunner.mRoot = node;
      super.setModel(m);
      markReportDirty();
// WCE --- why was this needed?        waitForReport();
      mButtonManager.reenableButtons();
      contentChanged();
   }

   protected void setReportRunner(BasicReportRunner runner) {
      if (runner == null) {
         runner = mDefaultReportRunner;
      }
      mReportRunner = runner;
      mReportRunner.mRoot = getRootNode();
   }

   /**
    * By default, drag is enabled (for applicable trees), this allows it to be turned off.
    */
   public void setDragEnabled(boolean enabled) {
      mDragEnabled = enabled;
   }

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
    * Returns the actual (display) width of a row.
    */
   public int getActualWidth(Graphics g, TreePath row) {
      if (mHasDivider) {
         return getWidth();
      }
      BasicTreeNode n = (BasicTreeNode) row.getLastPathComponent();
      return mRenderer.getActualRowWidth(g, n);
   }

   /**
    * Does not return until the report (which may have been cached already) is
    * done being generated.
    * Returns a sequence # for the how many times the report has been generated,
    * mainly for seeing if something changed.
    */
   public int waitForReport() {
      mReportRunner.getReport(new DefaultCancelChecker()); // never cancel.
      return mReportRunner.getReportSequenceNumber();
   }

   /**
    * Does not return until the report (which may have been cached already) is
    * done being generated.
    * Returns a sequence # for the how many times the report has been generated,
    * mainly for seeing if something changed.
    */
   public int waitForReport(CancelChecker cancelChecker) {
      mReportRunner.getReport(cancelChecker);
      return mReportRunner.getReportSequenceNumber();
   }


   public void paint(Graphics g) {

      g.setColor(Color.white);
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
      if (mHasDivider) {
         g.setColor(mRenderer.mRowSeparatorColor);
         for (int i = topVisibleRow; i < bottomVisibleRow; i++) {
            int y = i * rh + base;
            int bt = y + rh - 1;
            g.drawLine(0, bt, w, bt);
         }
      }
      // paint background on any rows that have it:
      for (int i = topVisibleRow; i < bottomVisibleRow; i++) {
         TreePath p = getPathForRow(i);
         //Fixing NPE for paint children
         if (p == null) {
        	 continue;
         }
         BasicTreeNode n = (BasicTreeNode) p.getLastPathComponent();
         Color c = n.getBackgroundColor();
         if (c != null) {
            g.setColor(c);
            int y = i * rh + base;
            int bt = rh - 1;
            g.fillRect(0, y, w, bt);
         }
      }
      g.setColor(Color.white);
      super.paint(g);

      if (mHasDivider) {
         paintErrors(g);
      }

      if (isInlineEditable() && isEditable() && mDropHintBounds != null) {
         g.setColor(Color.red);
         g.drawRect(mDropHintBounds.x, mDropHintBounds.y, mDropHintBounds.width - 1, mDropHintBounds.height - 1);
      }

   }

   public boolean drop(SoftDragNDropManager m, Point p, Object obj) {
      if (!isEditable()) {
         return true;
      }
      computeDropHintBounds(p, true);
      return true;
   }

   public void dropStopped() {
      mDropHintBounds = null;
   }

   public Rectangle getDropHintBounds() {
      if (!isEditable()) {
         return null;
      }
      return mDropHintBounds;
   }

   protected final Rectangle computeDropHintBounds(Point point, Object obj) {
      return computeDropHintBounds(point, false);
   }

   private Rectangle computeDropHintBounds(Point point, boolean doDrop) {
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

      BasicTreeNode draggingNode = (BasicTreeNode) draggingtp.getLastPathComponent();
      TreePath path = getPathForRow(row);
      BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
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
      int draggingDepth = draggingtp.getPathCount();
      int toDepth = path.getPathCount();

      BasicTreeNode moveNode = draggingNode;
      BasicTreeNode moveToParent = node.getParent();

      boolean isSameDepth = draggingDepth == toDepth;
      boolean isSameParent = moveNode.getParent() == moveToParent;
      boolean isJustAbove = draggingRow - 1 == row && isSameDepth;
      boolean isJustBelow = draggingRow + 1 == row && isSameDepth;
      if (!isSameParent && !allowsCrossBranchDrops()) {
         return null;
      }
      if (!moveToParent.isArray()) {
         return null;
      }

      Rectangle rb = getRowBounds(row);
      int ymin = rb.y;
      int ymax = ymin + rb.height;

      int childIndex = moveToParent.getIndexOfChild(node);
      boolean isOverParent = draggingNode.getParent() == node;
      if (!node.canHaveChildren() || !allowsCrossBranchDrops() || isOverParent) {
         // Split it into half, top half means add above
         if (point.y < (ymax + ymin) / 2) {
            if (isJustBelow) {
               return null;
            }
            if (doDrop) {
               BasicTreeActions.move(this, moveNode, moveToParent, childIndex);
            }
            return new Rectangle(rb.x, ymin - 2, sourceWidth, 4);
         }
         else {
            if (isJustAbove) {
               return null;
            }
            childIndex++;
            if (doDrop) {
               BasicTreeActions.move(this, moveNode, moveToParent, childIndex);
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
            BasicTreeActions.move(this, moveNode, moveToParent, childIndex);
         }
         return new Rectangle(rb.x, ymin - 2, sourceWidth, 4);
      }
      if (point.y >= (ymin + ymax * 3) / 4) {
         if (isJustAbove) {
            return null;
         }
         childIndex++;
         if (doDrop) {
            BasicTreeActions.move(this, moveNode, moveToParent, childIndex);
         }
         return new Rectangle(rb.x, ymax - 2, sourceWidth, 4);
      }
      if (doDrop) {
         BasicTreeActions.move(this, moveNode, node, 0);
      }
      // can drop on it, too:
      return new Rectangle(rb);
   }

   public boolean dragOver(SoftDragNDropManager manager, Point p, Object obj) {
      if (!isEditable()) {
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

   /**
    * If this tree is associated with a DesignerDocument, will use that document
    * to locate the relevant TnsDocumentProvider
    *
    * @return the TnsDocumentProvider for the DesignerDocument associated with
    *         this tree
    */
   public TnsDocumentProvider getTnsDocumentProvider() {
      return uiAgent.getTnsCache();
/*
      if (m_documentProvider == null && getDesignerDocument() != null) {
         SchemaTool schemaTool = (SchemaTool) getDesignerDocument().getTool(SchemaTool.NAME);
         m_documentProvider = schemaTool.getTnsDocumentProvider();
      }
      return m_documentProvider;
*/
   }


   public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
      if (!mIsDragSource) {
         return null;
      }
      if (!mDragEnabled) {
         return null;
      }
      if (mMovingInvisibleDivider) {
         return null;
      }
      if (mouseAt.distance(pressedAt.getLocation()) < 10) {
         return null;
      }
      int row = getRowForYLocation(pressedAt.y);
      TreePath path = getPathForRow(row);
      if (path == null) {
         return null;
      }
      Rectangle r = getPathBounds(path);
      if (r == null) { // shouldn't happen
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
      int cellwidth = Math.min(r.width, getActualWidth(g, path));
      int right = Math.min(cellwidth + r.x, getWidth()); // don't have right be beyond the window.
      int actualWidth = Math.max(right - r.x, 5); // 5 -> sanity lower limit.
      mDragHintBounds = new Rectangle(r.x, r.y, actualWidth, r.height);
      g.dispose();
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
   public void selectAndShowNode(BasicTreeNode node) {
      if (node == null) {
         return;
      }
      TreePath path = node.getTreePath();
      expandPath(path.getParentPath());
      setSelectionPath(path);
      scrollPathToVisible(path);
   }

   public int getDividerLocation() {
      return mRenderer.mStartingDataOffset;
   }

   public void setDividerLocation(int width) {
      mRenderer.mStartingDataOffset = width;
   }

   public void copy() {
      // Delegate to another class (to keep file size down mostly)
      BasicTreeActions.copy(this);
   }

   public void cut() {
      // Delegate to another class (to keep file size down mostly)
      BasicTreeActions.cut(this);
   }

   public void delete() {
      // Delegate to another class (to keep file size down mostly)
      BasicTreeActions.delete(this);
   }

   public void deleteAll() {
      // Delegate to another class (to keep file size down mostly)
      BasicTreeActions.delete(this);
   }

   public void paste() {
      // Delegate to another class (to keep file size down mostly)
      BasicTreeActions.paste(this);
   }

   public void resized(int width) {
      int fixedOffset = getInsets().left + getInsets().right;
      mRenderer.setAvailableWidth(width - fixedOffset);
      mRenderer.mIndentWidth = mIndentWidth;

      int newOffset = Math.min(Math.max(50, mRenderer.mStartingDataOffset), width - 50);
      mRenderer.mStartingDataOffset = newOffset;

      setCellRenderer(null);
      setCellRenderer(mRenderer); // forces recomputation of widths.
   }

   public TreeState getTreeState() {
      ArrayList<int[]> al = BasicTreeActions.getAllExpandedPosPaths(this);
      ArrayList<int[]> sal = BasicTreeActions.getAllSelectedPosPaths(this);
      return new TreeState(al.toArray(new int[0][]),
                           sal.toArray(new int[0][]));
   }

   /**
    * For a given node, get the expansion state relative to that node (and in a rebuild independent way)
    *
    * @param node The tree node.
    * @return The state.
    */
   public TreeState getTreeStateForNode(BasicTreeNode node) {
      ArrayList<int[]> al = BasicTreeActions.getAllExpandedPosPaths(this, node);
      return new TreeState((int[][]) al.toArray(new int[0][]),
                           new int[0][]);
   }

   /**
    * For a given node, set an expansion 'state'.
    *
    * @param node
    * @param state
    */
   public void setTreeStateForNode(BasicTreeNode node, TreeState state) {
      ArrayList<int[]> al = new ArrayList<int[]>();
      for (int i = 0; i < state.mIntPaths.length; i++) {
         al.add(state.mIntPaths[i]);
      }
      BasicTreeActions.collapseAllPathsBelow(this, node.getTreePath());
      BasicTreeActions.expandAllPosPaths(this, al, node);
      al.clear();
      for (int i = 0; i < state.mIntSelPaths.length; i++) {
         al.add(state.mIntSelPaths[i]);
      }
      BasicTreeActions.selectAllPosPaths(this, al);
      markReportDirty();
   }

   public void setTreeState(TreeState state) {
      ArrayList<int[]> al = new ArrayList<int[]>();
      for (int i = 0; i < state.mIntPaths.length; i++) {
         al.add(state.mIntPaths[i]);
      }
      BasicTreeActions.expandAllPosPaths(this, al);
      al.clear();
      for (int i = 0; i < state.mIntSelPaths.length; i++) {
         al.add(state.mIntSelPaths[i]);
      }
      BasicTreeActions.selectAllPosPaths(this, al);
      markReportDirty();
   }


   /**
    * Implementation override of basic JTree method.
    *
    * @param val
    */
   public void setEditable(boolean val) {
      if (val) {
         if (isInlineEditable()) {
            if (mEditor == null) {
               mEditor = new BasicTreeCellEditor(this);
               setCellEditor(mEditor);
               super.setInvokesStopCellEditing(true); // otherwise it is irritating.
            }
         }
      }
      super.setEditable(val);
   }

   public BasicTreeNode getSelectionNode() {
      TreePath tp = getSelectionPath();
      if (tp == null) {
         return null;
      }
      return (BasicTreeNode) tp.getLastPathComponent();
   }

   public BasicTreeNode getRootNode() {
      if (getModel() == null) {
         return null;
      }
      return (BasicTreeNode) getModel().getRoot();
   }

   public BasicTreeNode getSelectionNodeOrRoot() {
      if (getSelectionNode() == null) {
         return getRootNode();
      }
      else {
         return getSelectionNode();
      }
   }

   public BasicTreeButtonManager getButtonManager() {
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


   /**
    * Override to know when the root changes.
    */
   protected void rootChanged() {
   }

   protected boolean isRootNull() {
      return mRootIsNull;
   }

   protected void setRootNull(boolean rootNull) {
      mRootIsNull = rootNull;
      reenableButtons();
   }

   public void markReportDirty() {
      mReportRunner.markDirty();
      repaint();
   }

   private Object getDragObjectFor(TreePath path) {
      if (path == null) {
         return null;
      }
      mDraggingPath = path;
      return getDragObject(path);
   }

   protected Object getDragObject(TreePath path) {
      return new XPathSoftDragItem(getXPath(path), this);
   }

   public Object test_getDragObject(int row) {
      return getDragObjectFor(getPathForRow(row));
   }

   public void test_drop(Object object, int row) {
      TreePath path = getPathForRow(row);
      Rectangle b = getPathBounds(path);
      Point dropPoint = new Point(b.x + 2, b.y + 2);
      computeDropHintBounds(dropPoint, true);
   }

   public String test_getName(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.getDisplayName();
   }

   public int test_getMin(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.getMin();
   }

   public int test_getMax(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.getMax();
   }

   public boolean test_getIsSubstituted(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.isSubstituted();
   }

   public Icon test_getIcon(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.getIcon();
   }

   /**
    * Returns the blank text in the data display area:
    */
   public String test_getBlankText(int row) {
      TreePath path = getPathForRow(row);
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      return n.getBlankText();
   }

   public String getXPath(TreePath path) {
      if (path == null) {
         return ".";
      }
      StringBuffer sb = new StringBuffer();
      BasicTreeNode n = (BasicTreeNode) path.getLastPathComponent();
      getXPath(sb, n, true);
      String xp = sb.toString();
      return xp;
   }

   /**
    * For use in non-xpath contexts, sets the separator to something else.
    *
    * @param separator
    */
   public void setPathSeparator(String separator) {
      m_pathSeparator = separator;
   }

   public String getPathSeparator() {
      return m_pathSeparator;
   }

   private void getXPath(StringBuffer sb, BasicTreeNode node, boolean isLastStep) {
      if (node == null) {
         return;
      }
      String step = node.getXStepName(isLastStep, m_mNamespaceContextRegistry);
      getXPath(sb, node.getParent(), step == null && isLastStep);
      if (sb.length() > 0 && step != null) {
         sb.append(m_pathSeparator);
      }
      if (step != null) {
         sb.append(step);
      }
   }

   public String getToolTipText(MouseEvent me) {
      // First check for underlines in the data area:
      waitForReport();
      ErrorDesc ed = getErrorAt(me.getPoint());
      if (ed != null) {
         return ed.mError.getMessage();
      }
      TreePath path;
      try {
         path = getPathForLocation(me.getX(), me.getY());
         if (path == null) {
            return null;
         }
      }
      catch (Exception e) {
         // In the unit tests, getPathForLocation can throw an exception, probably a timing thing in JTree.
         return null;
      }
      BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
      // Next check for line errors:
      if (me.getX() > mRenderer.getDataOffset(node)) {
         // too far right.
         return node.getDataToolTip(me.getX() - mRenderer.getDataOffset(node));
      }
      return node.getLineError();
   }

   private static class ErrorDesc {
//      public TreePath mPath;
      public ErrorMessage mError;
   }

   private ErrorDesc getErrorAt(Point at) {
      ErrorDesc ed = getErrorAtExact(at);
      if (ed != null) {
         return ed;
      }
      // Because errors are at the bottom of a line, be more forgiving & try
      // looking half a line up:
      return getErrorAtExact(new Point(at.x, at.y - getRowHeight() / 2));
   }

   // Internal...
   private ErrorDesc getErrorAtExact(Point at) {
      TreePath path;
      try {
         path = getPathForLocation(at.x, at.y);
         if (path == null) {
            return null;
         }
      }
      catch (Exception e) {
         // Unit tests can cause getPathForLocation to fail, probably a timing thing in JTree.
         return null;
      }
      Rectangle b = getPathBounds(path);
      if (!b.contains(at)) {
         return null;
      }
      BasicTreeNode node = (BasicTreeNode) path.getLastPathComponent();
      ErrorMessageList lsel;
      if (getEditingPath() != null && getEditingPath().equals(path)) {
         // special case because the values aren't up to date in the type node,
         // get directly from the editor:
         lsel = getEditorErrorMessageList();
      }
      else {
         lsel = node.getErrorMessages();
      }
      if (lsel == null) {
         return null;
      }
      ErrorMessage[] errs = lsel.getErrorAndWarningMessages();
      if (errs.length == 0) {
         return null;
      }

      // Find closest error:
      int dof = mRenderer.getRelativeDataOffset(node);
      int xinset = (at.x - (b.x + dof + 2));
      int columnsIn = 0 + getCharacterPosition(xinset);
      int smallestDistance = 100;
      for (int i = 0; i < errs.length; i++) {
         ErrorMessage em = errs[i];
         int distance = computeColumnDistance(em.getTextRange(), columnsIn);
         smallestDistance = Math.min(smallestDistance, distance);
      }
      // If you weren't even close, forget it:
      if (smallestDistance > 3) {
         return null;
      }

      // Go back & find the closest one:
      for (int i = 0; i < errs.length; i++) {
         ErrorMessage em = errs[i];
         int distance = computeColumnDistance(em.getTextRange(), columnsIn);
         if (distance == smallestDistance) {
            ErrorDesc ed = new ErrorDesc();
            ed.mError = em;
//            ed.mPath = path;
            return ed;
         }
      }
      // not reachable, actually.
      return null;
   }

   /**
    * Utility function that returns the number of columns away from the location
    */
   private static int computeColumnDistance(TextRange tl, int colNo) {
      if (tl.getStartPosition() <= colNo) {
         if (tl.getEndPosition() >= colNo) {
            return 0;
         }
         return colNo - tl.getEndPosition();
      }
//        tl.getDistance
      return tl.getStartPosition() - colNo;
   }

   /**
    * Can I drag from anywhere to anywhere in the tree, or only with in one branch.
    */
   protected abstract boolean allowsCrossBranchDrops();

   protected int getCharacterPosition(int x) {
      if (getDataCharWidth() == 0) { // can happen at startup.
         return 0;
      }
      return (x / this.getDataCharWidth());
   }

   /**
    * Can I move things up & down & add.
    */
   protected boolean allowsMovement() {
      return true;
   }

   /**
    * For the editor, get the current error message list (now)
    */
   public abstract com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList getEditorErrorMessageList();

   /**
    * For the editor, set up for editing (don't need to set text, just any error context)
    */
   public abstract JExtendedEditTextArea initializeEditor(BasicTreeNode node);

   /**
    * For the data editor font, the char width (should be fixed width)
    */
   public abstract int getDataCharWidth();

   /**
    * Can mean, make editor visible.
    *
    * @param node
    */
   public void edit(BasicTreeNode node) {

   }

   public void hideEditor() {
   }

   /**
    * For the paste part of cut-n-paste:
    * The implementation does not need to add the node; pasteOn is provided
    * for context only.<br>
    *
    * @throws SAXException If the xml isn't valid in this context, it should throw this exception where the message
    *                      is appropriate for display (or empty string for generic).
    */
   public BasicTreeNode buildFromXML(BasicTreeNode pasteOn, String xml) throws SAXException {
      return null;
   }

   public interface BasicNodeBuilder {
      BasicTreeNode buildNode();
   }

   /**
    * For the move-in function.<br>
    * Returns a builder which can be called repeatedly to create new nodes...
    * an implementation of this method may need a modal dialog, etc., but for the redo part of undo/redo, we
    * may need to regenerate the node.
    *
    * @return The builder, or null for none, which will cancel.
    */
   public BasicNodeBuilder buildGroupNode(BasicTreeNode aroundThis) {
      return null;
   }


   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

   private void paintErrors(Graphics g) {
      Rectangle paintBounds = g.getClipBounds();
      Insets insets = getInsets();
      if (insets == null) {
         insets = EMPTY_INSETS;
      }

      TreePath editingPath = getEditingPath();
      int atRow = Math.max(0, getClosestRowForLocation(0, paintBounds.y) - 1);
      int maxRow = getRowCount();
      int endY = paintBounds.y + paintBounds.height;

      Rectangle r = g.getClipBounds();
      int linset = insets.left;
      while (atRow < maxRow) {
         TreePath tp = getPathForRow(atRow);
         if (tp == null) {
            break;// just in case...
         }
         Rectangle at = getPathBounds(tp);
         if (at == null) {
            break; // just in case.
         }
         // Information for the node being rendered.
         BasicTreeNode node = (BasicTreeNode) tp.getLastPathComponent();
         int dof = mRenderer.getDataOffset(node) + linset;

         // Setup the node available for painting errors.
         mRenderer.setTreeNode(node);

         g.setClip(dof, r.y, r.width + (r.x - dof), r.height);
         if (editingPath != null && editingPath.equals(tp)) {
            // Let the editor paint the errors...
         }
         else {
            mRenderer.paintErrors(g, node, dof, at.y);
         }
         if (at.y > endY + RendererConstants.ERROR_OVERLAP) {
            break;
         }
         atRow++;
      }
      g.setClip(r.x, r.y, r.width, r.height);
   }

   /**
    * Override to add more context menu items at the top:
    */
   public void augmentTopContextMenu(JPopupMenu menu) {
   }

   /**
    * Override to add more context menu items at the bottom:
    */
   public void augmentTopBottomContextMenu(JMenu menu) {
   }

   public void expandContent() {
      BasicTreeActions.expandAllContent(this);
   }

   public String convertValueToText(Object value, boolean selected,
                                    boolean expanded, boolean leaf, int row,
                                    boolean hasFocus) {
      BasicTreeNode node = (BasicTreeNode) value;
      return node.getDisplayName();
   }

   public void addExtendedTreeModelListener(ExtendedTreeModelListener listener) {
      m_extendedTreeModelListeners.add(listener);
   }

   void fireTreeNodesMovedInParent(TreePath path, int[] children, int offset) {
      for (int i = 0; i < m_extendedTreeModelListeners.size(); i++) {
         ExtendedTreeModelListener eml = m_extendedTreeModelListeners.get(i);
         eml.treeNodesMovedInParent(path, children, offset);
      }
   }
}

