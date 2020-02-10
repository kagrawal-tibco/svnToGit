package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.FunctionCallExpr;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;

/**
 * Used inside XPathBuilder to handle a single category of functions.
 */
public class GraphicalXPath extends JPanel implements SoftDragNDropable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private ExprContext mExprContext;
   private Expr mCurrentExpr;
//    private int mItemWidth = 100;
   private int mRowHorizontalSpacing = 50;
   private String mCurrentXPath;
   private String mTargetFieldLabel;
//   private Binding mOptionalTargetBinding;

   private GraphicalXPathItem mDragOverItem; // The item that drag'n'drop is currently dragging over, null if none.
   private GraphicalXPathItem mDragBeforeItem; // The item that drag'n'drop is currently dragging for 'insert-before', null if none.
   private boolean mDragOverTarget; // The target item is being dragged over.
   private GraphicalXPathItem mSelectedItem; // The item that is selected.

   // Because we draw the lines to the data tree, we need to know about it & where it is:
   private DataTree mInputTree;
   private JComponent mPaintRoot; // The root component is required for getting component offsets involved in line-drawing.
//   private JComponent mLeftPaintTo; // For drawing lines, needs to know where the 'kink' goes.
   //private BasicTree mOutputSide;
   private BindingLineRenderer mBindingLineRenderer;
//   private JScrollPane mScroller; // contains me...

   /**
    * The rows (Row) computed from mCurrentExpr and the current tree expansion state.
    */
   private ArrayList<GraphicalXPathRow> mCurrentRows = new ArrayList<GraphicalXPathRow>();

   /**
    * The lines (Line) computed from mCurrentExpr
    */
   private ArrayList<Line> mCurrentLines = new ArrayList<Line>();

   /*static class PreviewTree extends BasicTree {
       public PreviewTree(Binding b) {
           super.setPaintCardinality(true);

           super.setShowsRootHandles(true);
           super.setRootVisible(false);
           super.setReportRunner(new BasicReportRunner() {
               protected Object buildReport() {
                   return null;
               }

               protected Object getReportChild(Object report, int index) {
                   return null;
               }

               protected boolean getReportHasChildContent(Object report) {
                   return false;
               }
           });
           Binding root = getRoot(b);
           BindingNode rn = new BindingNode(root);
           super.setRootNode(rn);


           super.setEditable(false);
           super.setHasDivider(false);

           //super.setEnabled(false);
           super.setDragEnabled(false);
           //super.set

           // select b now...
           BindingNode child = rn.findForBinding(b,true);
           if (child!=null) {
               final TreePath path = child.getTreePath();
               super.expandPath(path);
               super.scrollPathToVisible(path);
               super.setSelectionPath(path);
               super.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                   public void valueChanged(TreeSelectionEvent e) {
                       // change it back.
                       setSelectionPath(path);
                   }
               });
           }
       }

       private static Binding getRoot(Binding b) {
           if (b.getParent()==null) {
               return b;
           }
           return getRoot(b.getParent());
       }

       protected boolean allowsCrossBranchDrops() {
           return false;
       }

       public ErrorMessageList getEditorErrorMessageList() {
           return null;
       }

       public void initializeEditor(BasicTreeNode node) {
       }

       public int getDataCharWidth() {
           return 0;
       }

       public void edit(BasicTreeNode node) {
       }
   }*/

   public GraphicalXPath(JComponent paintRoot, JSplitPane leftPaintTo, DataTree inputTree, Binding optionalTargetBinding, String targetFieldName) {
      setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // giant border.
      setLayout(new BorderLayout());
      mInputTree = inputTree;
//      mLeftPaintTo = leftPaintTo;
      mPaintRoot = paintRoot;
//      mOptionalTargetBinding = optionalTargetBinding;
      /*if (mOptionalTargetBinding!=null) {
          mOutputSide = new PreviewTree(mOptionalTargetBinding);
      }*/
      //mTargetFieldLabel = targetFieldName==null ? XTypeSupport.getDisplayName(optionalTargetBinding.getOriginalXType()) : targetFieldName;
      if (inputTree == null) {
         throw new NullPointerException("Null tree");
      }
      mInputTree.addTreeExpansionListener(new TreeExpansionListener() {
         public void treeExpanded(TreeExpansionEvent event) {
            recomputeLines();
            mPaintRoot.repaint();
         }

         public void treeCollapsed(TreeExpansionEvent event) {
            recomputeLines();
            mPaintRoot.repaint();
         }
      });
      mBindingLineRenderer = new BindingLineRenderer();
      mBindingLineRenderer.setTrimTooSteep(false);
      /*
      if (mOutputSide!=null) {
          HorzSizedScrollPane hpp = new HorzSizedScrollPane(mOutputSide);
          hpp.setPreferredSize(new Dimension(200,100));
          add(hpp,BorderLayout.EAST);
      }*/
   }

   public void setExprContext(ExprContext exprContext) {
//      mExprContext = exprContext;
   }

   public void paint(Graphics g) {
      super.paint(g);
      /*if (mDragOverItem!=null && !mDragOverItem.isDataLine() ) {
          Rectangle r = getDropHintBounds();
          g.setColor(Color.red);
          //g.drawRect(r.x,r.y,r.width-1,r.height-1);
          g.fillRect(r.x,r.y,r.width-1,r.height-1);
      }*/
      if (mDragBeforeItem != null) {
         Rectangle r = getDropHintBounds();
         g.setColor(Color.blue);
         g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
      }
   }

   public boolean isOptimizedDrawingEnabled() {
      return false;
   }

   public void paintLines(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      //mBindingLineRenderer.computeOffsets();
      for (int i = 0; i < mCurrentLines.size(); i++) {
         Line l = mCurrentLines.get(i);
         GraphicalXPathItem item = l.mToItem;
         GraphicalXPathItem parent = item;
         boolean drawSel = false;
         if (item == mDragOverItem || item == mDragOverItem) {
            drawSel = true;
         }
         while (item.getParent() != null && item.getParent().isDataLine()) {
            parent = item;
            item = item.getParent();
            if (item == mDragOverItem || item == mDragOverItem) {
               drawSel = true;
            }
         }
         int x1Location;
         int x2Location;
         int y1Location;
         int y2Location;
         int endStyle;
         if (item.getParent() != null) {
            parent = item;
            item = item.getParent();
            Point offset = item.getEntryPointLocation(parent.getIndexOfChild());
            y1Location = parent.mYLocation + parent.getHeight() / 2;
            y2Location = item.mYLocation + offset.y;
            x1Location = parent.getCutoverXLocation();
            x2Location = item.getInRow().mXLocation + offset.x;
            endStyle = offset.x == 0 ? BindingLineRenderer.END_STYLE_ARROW : BindingLineRenderer.END_STYLE_UP_ARROW;
         }
         else {
            // This line goes straight to the target:
            Point p = getTargetLocation();
            x1Location = p.x;
            x2Location = p.x;
            y1Location = p.y;
            y2Location = p.y;
            endStyle = BindingLineRenderer.END_STYLE_DOT;
         }

         mBindingLineRenderer.drawLinesFor(l.mFromPath, g, x1Location, y1Location, x2Location, y2Location, endStyle, drawSel, false, false, true);
      }
   }

   protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//      int w = getWidth();
      for (int i = 0; i < mCurrentRows.size(); i++) {
         GraphicalXPathRow r = mCurrentRows.get(i);
         r.paint(g, mDragOverItem, mSelectedItem);
      }
      // Paint the line to the target piece:

      // Now paint the 'target' piece:
//      GraphicalXPathRow r0 = mCurrentRows.get(0);
      Point loc = getTargetLocation();
      Dimension size = getTargetDimension();
      g.setColor(Color.white);
      g.fillRect(loc.x, loc.y, size.width, size.height);
      if (mDragOverTarget) {
         g.setColor(Color.red);
      }
      else {
         g.setColor(Color.black);
      }
      g.drawRect(loc.x, loc.y, size.width - 1, size.height - 1);
      g.setColor(Color.black);
      g.drawString(mTargetFieldLabel, loc.x, loc.y + size.height);
   }

   private Point getTargetLocation() {
      GraphicalXPathRow r0 = mCurrentRows.get(0);
      int atX = r0.mXLocation + r0.getWidth() + mRowHorizontalSpacing;
      int atY = r0.get(0).mYLocation;
      return new Point(atX, atY);
   }

   private Dimension getTargetDimension() {
      return new Dimension(50, 20);
   }

   private Rectangle getTargetBounds() {
      return new Rectangle(getTargetLocation(), getTargetDimension());
   }

   private void makeRows() {
      mCurrentRows.clear();
      makeRows(null, mCurrentExpr, 0);
      computeLocations();
   }

   /**
    * Returns the totalHeight of the thing.
    */
   private void computeLocations() {
      GraphicalXPathItem rootItem = mCurrentRows.get(0).get(0);
      computeHeight(rootItem, getInsets().top + rootItem.getMinVerticalSpacing());
   }

   /**
    * Computes the y-locations of every item in every row.
    */
   private static int computeHeight(GraphicalXPathItem item, int atY) {
      GraphicalXPathItem[] children = item.getChildren();
      int nextOpenSpaceY = atY;
      item.mYLocation = atY + item.getMinVerticalSpacing();
      for (int c = 0; c < children.length; c++) {
         GraphicalXPathItem child = children[c];
         int yoffset = (item.getEntryPointLocation(c).y - child.getMinVerticalSpacing()) - child.getHeight() / 2; // this y location will draw a straight line.
         int startAt = Math.max(item.mYLocation + yoffset, nextOpenSpaceY);//+child.getMinVerticalSpacing());
         nextOpenSpaceY = computeHeight(child, startAt);
      }
      return Math.max(nextOpenSpaceY, item.mYLocation + item.getHeight() + item.getMinVerticalSpacing());
   }

   private void makeRows(GraphicalXPathItem parent, Expr expr, int depth) {

      if (mCurrentRows.size() <= depth) {
         mCurrentRows.add(new GraphicalXPathRow());
      }
      GraphicalXPathRow r = mCurrentRows.get(depth);
      GraphicalXPathItem item = GraphicalXPathItem.create(mBindingLineRenderer, r, expr, parent);
      r.add(item);

      int tc = expr.getExprTypeCode();
      if (tc == ExprTypeCode.EXPR_LOCATION_PATH || tc == ExprTypeCode.EXPR_STEP || tc == ExprTypeCode.EXPR_VARIABLE_REFERENCE) {
         // don't recurse, this is a leaf.
         return;
      }
      Expr[] children = expr.getChildren();
      for (int i = 0; i < children.length; i++) {
         Expr c = children[i];
         makeRows(item, c, depth + 1);
      }
   }

   private void recomputeLines() {
      mCurrentLines.clear();
      for (int i = 0; i < mCurrentRows.size(); i++) {
         GraphicalXPathRow r = mCurrentRows.get(i);
         for (int j = 0; j < r.getSize(); j++) {
            GraphicalXPathItem item = (GraphicalXPathItem) r.get(j);
            Line l = getLineForExpr(new String[0], item);
            if (l != null) {
               mCurrentLines.add(l);
            }
         }
      }
   }

   static class Line {
      public final int mFromRow;
      public final TreePath mFromPath;
      public final GraphicalXPathItem mToItem;

      public Line(int fromRow, TreePath fromPath, GraphicalXPathItem toItem) {
         mFromRow = fromRow;
         mFromPath = fromPath;
         mToItem = toItem;
      }
   }

   private Line getLineForExpr(String[] relPath, GraphicalXPathItem item) {
      Expr expr = item.getExpr();
      int tc = expr.getExprTypeCode();
      switch (tc) {
         case ExprTypeCode.EXPR_LOCATION_PATH:
         case ExprTypeCode.EXPR_STEP:
         case ExprTypeCode.EXPR_VARIABLE_REFERENCE:
            break;
         default: // Not an 'end' path (i.e. a formula), so don't draw any lines to it.
            return null;
      }
      String[][] paths = Utilities.getPaths(relPath, expr);
      if (paths.length == 0) {
         return null;
      }
      // can only be 1 path...
//      String[] path = paths[0];
      throw new RuntimeException("NYU");/*
       TreePath pathx = com.tibco.ui.data.basic.BasicTreeActions.getTreePathFromXPath(mInputTree,path);
       if (pathx!=null) {
           throw new RuntimeException("NYU");*
           TreePath deepestInput = null;//BindingLines.getDeepestExpandedPath(mInputTree,pathx);
           int fromRow = mInputTree.getRowForPath(deepestInput);
           if (fromRow>=0) {
               return new Line(fromRow,deepestInput,item);
           }
       }
       return null;*/
   }

//   private TreePath getDeepestExpandedInputPath(TreePath path) {
//      TreePath pp = path.getParentPath();
//      if (pp == null) {
//         return path;
//      }
//      TreePath dpe = getDeepestExpandedInputPath(pp);
//      if (dpe == pp) {
//         if (mInputTree.isExpanded(pp)) {
//            return path;
//         }
//         return pp;
//      }
//      else {
//         return dpe;
//      }
//   }

   /**
    * When a drag'n'drop, or other, operation causes the Expr to change inside the Row/Item objects, this rebuilds the
    * big expression.
    */
   private void recomputeExpr() {
      GraphicalXPathRow end = mCurrentRows.get(0);
      GraphicalXPathItem only = end.get(0);
      Expr e = recomputeExpr(only);
      String str = e.toExactString();
      setXPath(str);
   }

   private Expr recomputeExpr(GraphicalXPathItem item) {
      Expr e = item.getExpr();
      GraphicalXPathItem[] ichildren = item.getChildren();
      Expr ret;
      int tc = e.getExprTypeCode();
      int newChildrenLength = getMaxArgLength(e);
      String value = e.getExprValue();
      String whitespace = e.getWhitespace();
      String closure = e.getRepresentationClosure();
      Expr[] children = new Expr[newChildrenLength];
      for (int i = 0; i < newChildrenLength; i++) {
         if (i >= ichildren.length) {
            children[i] = e.getChildren()[i];
            if (children[i] == null) {
               children[i] = Parser.parse("<< argument >>");
            }
         }
         else {
            children[i] = recomputeExpr(ichildren[i]);
         }
      }
      ret = Expr.create(tc, children, value, whitespace, closure);
      if (item.getInsertExpr() != null) {
         return getInsertBefore(item.getInsertExpr(), ret);
      }
      return ret;
   }

   private int getMaxArgLength(Expr expr) {
      if (expr instanceof FunctionCallExpr) {
//         FunctionCallExpr fe = (FunctionCallExpr) expr;
//         String val = fe.getExprValue();
         throw new RuntimeException("NYI");/*
          XFunction xf = null;// thromExprContext.getFunctionResolver().getNamespace(null).get(val);
          if (xf!=null) {
              return xf.getNumArgs();
          }
          return 0;*/
      }
      return expr.getChildren().length;
   }

   private static Expr getInsertBefore(Expr insertBefore, Expr expr) {
      int ibtc = insertBefore.getExprTypeCode();
      Expr[] ibchildren = insertBefore.getChildren();
      String ibvalue = insertBefore.getExprValue();
      String ibwhitespace = insertBefore.getWhitespace();
      String ibclosure = insertBefore.getRepresentationClosure();
      Expr[] children = new Expr[ibchildren.length];
      for (int i = 0; i < children.length; i++) {
         if (i == 0) { // insert as first child:
            children[i] = expr;
         }
         else {
            children[i] = ibchildren[i];
         }
      }
      return Expr.create(ibtc, children, ibvalue, ibwhitespace, ibclosure);
   }

   public String getXPath() {
      return mCurrentXPath;
   }

   public void setXPath(String xpath) {
      if (xpath == null) {
         xpath = "";
      }
      if (xpath.equals(mCurrentXPath)) {
         return;
      }
      mCurrentXPath = xpath;
      Expr e = Parser.parse(xpath);
      mCurrentExpr = e;
      makeRows();
      computeDimensions();
      recomputeLines();
      repaint();
   }

   private void computeDimensions() {
      // Also lays out x locations:
      int il = getInsets().left;
//      int ir = getInsets().right;
      int w = il;
      for (int i = 0; i < mCurrentRows.size(); i++) {
         w += mCurrentRows.get(i).getWidth();
         w += mRowHorizontalSpacing;
      }
      int maxY = 0;
      int atX = w;
      for (int i = 0; i < mCurrentRows.size(); i++) {
         GraphicalXPathRow r = mCurrentRows.get(i);
         r.mXLocation = atX - (mRowHorizontalSpacing + r.getWidth());
         for (int j = 0; j < r.getSize(); j++) {
            int y = r.get(j).mYLocation + r.get(j).getHeight();
            maxY = Math.max(y, maxY);
         }
         atX = r.mXLocation;
      }
      maxY += getInsets().bottom;
      setPreferredSize(new Dimension(w, maxY));
      revalidate();
   }

   /**
    * Finds the Item that this point is in, null if it is in none.
    */
   private GraphicalXPathItem getItemAtPoint(Point at) {
      for (int i = 0; i < mCurrentRows.size(); i++) {
         GraphicalXPathRow r = mCurrentRows.get(i);
         GraphicalXPathItem item = r.getItemAtPoint(at);
         if (item != null) {
            return item;
         }
      }
      return null;
   }

   /**
    * Finds the Item that this point is in, null if it is in none.
    */
   private GraphicalXPathItem getItemInsertBeforePoint(Point at) {
      for (int i = 0; i < mCurrentRows.size(); i++) {
//         GraphicalXPathRow r = mCurrentRows.get(i);
         /*
         if (at.x >= r.mXLocation + mItemWidth && at.x <= r.mXLocation + mItemWidth + mRowHorizontalSpacing) {
             for (int j=0;j<r.getSize();j++) {
                 GraphicalXPathItem item = r.get(j);
                 if (at.y >= item.mYLocation && at.y<=item.mYLocation + item.getHeight()) {
                     return item;
                 }
             }
         }*/
      }
      return null;
   }

   public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
      return null;
   }

   public Rectangle getDragHintBounds() {
      return null;
   }

   public void dragStopped() {
   }

   /**
    * For the expression being dragged, does an 'insert before' even make sense? (Is it allowed?)
    */
   private boolean canInsertBefore(XPathSoftDragItem di) {
      String xp = di.path;
      Expr e = Parser.parse(xp);
      // easy enough --- if it's got children, we can insert using it:
      int tc = e.getExprTypeCode();
      switch (tc) {
         case ExprTypeCode.EXPR_VARIABLE_REFERENCE:
         case ExprTypeCode.EXPR_LOCATION_PATH:
         case ExprTypeCode.EXPR_STEP:
            // these are all terminals (from this graphical display's point of view)
            return false;
      }
      if (e.getChildren().length >= 1) {
         return true;
      }
      return false;
   }

   public boolean dragOver(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      if (!(dragObject instanceof XPathSoftDragItem)) {
         return false;
      }
      XPathSoftDragItem sp = (XPathSoftDragItem) dragObject;
      GraphicalXPathItem item = getItemAtPoint(mouseAt);
      mDragOverItem = item;
      if (mDragOverItem == null && canInsertBefore(sp)) {
         mDragBeforeItem = getItemInsertBeforePoint(mouseAt);
      }
      else {
         mDragBeforeItem = null;
      }
      mDragOverTarget = false;
      if (item == null && mDragBeforeItem == null) {
         if (getTargetBounds().contains(mouseAt)) {
            mDragOverTarget = true;
         }
      }

      return item != null || mDragBeforeItem != null || mDragOverTarget;
   }

   public Rectangle getDropHintBounds() {
      if (mDragOverItem != null) {
         if (mDragOverItem.isDataLine()) {
            return mPaintRoot.getBounds();
         }
         int y = mDragOverItem.mYLocation;
         int x = mDragOverItem.getInRow().mXLocation;
         return new Rectangle(x, y, mDragOverItem.getWidth() + 1, mDragOverItem.getHeight() + 10);
      }
      if (mDragBeforeItem != null) {
         if (mDragBeforeItem.isDataLine()) {
            return mPaintRoot.getBounds();
         }
//         int y = mDragBeforeItem.mYLocation;
//         int x = mDragBeforeItem.getInRow().mXLocation;
         return null;//new Rectangle(x+mItemWidth,y,x+mItemWidth+mRowHorizontalSpacing,mDragBeforeItem.getHeight());
      }
      if (mDragOverTarget) {
         return getTargetBounds();
      }
      return null;
   }

   public void dropStopped() {
      mDragOverItem = null;
      mDragBeforeItem = null;
   }

   public boolean drop(SoftDragNDropManager manager, Point mouseAt, Object dropObject) {
      if (!(dropObject instanceof XPathSoftDragItem)) {
         return false;
      }
      XPathSoftDragItem sp = (XPathSoftDragItem) dropObject;
      mDragBeforeItem = null;
      mDragOverItem = null;
      GraphicalXPathItem item = getItemAtPoint(mouseAt);
      if (item != null) {
         String path = sp.path;
         Expr e = Parser.parse(path);
         item.setExpr(e);
         recomputeExpr();
         return true;
      }
      GraphicalXPathItem insertItem = getItemInsertBeforePoint(mouseAt);
      if (insertItem != null && canInsertBefore(sp)) {
         String path = sp.path;
         Expr e = Parser.parse(path);
         insertItem.setInsertExpr(e);
         recomputeExpr();
         return true;
      }
      return false;
   }

   public void drawDraggingIndicator(Graphics g, SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
   }

   public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
      return null;
   }
}
