package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

abstract class GraphicalXPathItem {
   private final BindingLineRenderer mBindingLineRenderer;
   private final GraphicalXPathItem mParent;
   private final int mIndexOfChild;
   private final GraphicalXPathRow mInRow;
   private Expr mExpr;
   private Expr mInsertExpr;
   private ArrayList<GraphicalXPathItem> mChildren = new ArrayList<GraphicalXPathItem>();
   private final boolean mIsDataLine;
   int mYLocation;

   public static GraphicalXPathItem create(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      switch (expr.getExprTypeCode()) {
         case ExprTypeCode.EXPR_MARKER:
            return new MarkerGraphicalXPathItem(renderer, inRow, expr, parent);
         case ExprTypeCode.EXPR_FUNCTION_CALL:
            return new FunctionCallGraphicalXPathItem(renderer, inRow, expr, parent);
         case ExprTypeCode.EXPR_LITERAL_STRING:
            return new LiteralStringGraphicalXPathItem(renderer, inRow, expr, parent);
         case ExprTypeCode.EXPR_NUMBER:
            return new LiteralStringGraphicalXPathItem(renderer, inRow, expr, parent);
         case ExprTypeCode.EXPR_STEP:
         case ExprTypeCode.EXPR_LOCATION_PATH:
         case ExprTypeCode.EXPR_VARIABLE_REFERENCE:
            return new DataLineGraphicalXPathItem(renderer, inRow, expr, parent);
         case ExprTypeCode.EXPR_PREDICATE:
            return new PredicateGraphicalXPathItem(renderer, inRow, expr, parent);
         default:
            // function calls & operators:
            return new FunctionCallGraphicalXPathItem(renderer, inRow, expr, parent);
      }
   }

   protected GraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      mBindingLineRenderer = renderer;
      mInRow = inRow;
      mExpr = expr;
      mParent = parent;
      if (parent != null) {
         mIndexOfChild = parent.mChildren.size();
         parent.mChildren.add(this);
      }
      else {
         mIndexOfChild = -1;
      }
      // These will be drawn with a dataline.
      switch (expr.getExprTypeCode()) {
         case ExprTypeCode.EXPR_VARIABLE_REFERENCE:
         case ExprTypeCode.EXPR_LOCATION_PATH:
         case ExprTypeCode.EXPR_STEP:
            mIsDataLine = true;
            break;
         default:
            mIsDataLine = false;
            break;
      }
   }

   public abstract int getHeight();

   public abstract int getWidth();

   /**
    * The required above & below spacing for this component.
    */
   public abstract int getMinVerticalSpacing();

   /**
    * Returns the relative Y location of the entry point (line) of the nth child.
    */
   public abstract Point getEntryPointLocation(int childIndex);

   /**
    * Returns the index inside the getParent() item that this child represents.
    */
   public final int getIndexOfChild() {
      return mIndexOfChild;
   }

   /**
    * Does this item represent a line going to the input data tree?
    */
   public final boolean isDataLine() {
      return mIsDataLine;
   }

   public final GraphicalXPathItem[] getChildren() {
      return mChildren.toArray(new GraphicalXPathItem[0]);
   }

   public final int getChildCount() {
      return mChildren.size();
   }

   /*
   public String getDisplayText() {
       return mDisplayText;
   }*/

   public final void setExpr(Expr expr) {
      mExpr = expr;
   }

   public final void setInsertExpr(Expr expr) {
      mInsertExpr = expr;
   }

   public final Expr getInsertExpr() {
      return mInsertExpr;
   }

   public final GraphicalXPathRow getInRow() {
      return mInRow;
   }

   public final GraphicalXPathItem getParent() {
      return mParent;
   }

   public final Expr getExpr() {
      return mExpr;
   }

   public abstract void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel);

   public final void paintConnection(Graphics g, int atX, int atY) {
      if (!isDataLine()) { // data lines (those going to the left side tree) are painted in a separate pass
         // this only paints the lines between functions.
         GraphicalXPathItem p = getParent();
         int indexOfChild = getIndexOfChild();
         if (p != null) {
            Point offset = p.getEntryPointLocation(indexOfChild);
            int yoffset = offset.y;
            int xoffset = offset.x;
            drawConnection(g, indexOfChild, atX + getWidth(), atY + getHeight() / 2, p.getInRow().mXLocation + xoffset, p.mYLocation + yoffset);
         }
      }
   }

   /**
    * Returns the xlocation where the connection line should go vertically upward from child to parent.
    */
   public final int getCutoverXLocation() {
//      GraphicalXPathItem p = getParent();
      int indexOfChild = getIndexOfChild();
      int x1 = getInRow().mXLocation + getInRow().getWidth();
      int x2 = x1 + 40;
      return x1 + (x2 - x1) / 2 + indexOfChild * 4;
   }

   private final void drawConnection(Graphics g, int indexOfChild, int x1, int y1, int x2, int y2) {
      int xm = getCutoverXLocation();
      mBindingLineRenderer.drawConnectyLine(g, x1, y1, xm, xm, x2, y2, x2, y2, BindingLineRenderer.END_STYLE_ARROW, false, false, false);
   }
}
