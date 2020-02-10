package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

/**
 * Handles both function call and operators
 */
class FunctionCallGraphicalXPathItem extends GraphicalXPathItem {
   private int mWidth = 70;
   private String mDisplayText;

   public FunctionCallGraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      super(renderer, inRow, expr, parent);
      if (expr.getExprTypeCode() == ExprTypeCode.EXPR_FUNCTION_CALL) {
         mDisplayText = expr.getExprValue();
      }
      else {
         // for operators.
         mDisplayText = getTypeCodeDisplayName(expr.getExprTypeCode());
      }
   }

   public int getHeight() {
      return 24;
   }

   public int getWidth() {
      return mWidth;
   }

   public int getMinVerticalSpacing() {
      return 10;
   }

   public Point getEntryPointLocation(int childIndex) {
      return new Point(0, (1 + childIndex) * getHeight() / (getChildCount() + 1));
   }

   public void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel) {
      // Leftover, functions and operators:
      g.setColor(Color.white);
      g.fillRect(atX, atY, mWidth, getHeight());
      g.setColor(Color.black);
      //g.drawRect(atX,atY,mItemWidth,item.getHeight());
      g.drawString(mDisplayText, atX, atY + 20);
      drawFunction(g, atX, atY, mWidth, getHeight(), dragOver);
   }

   private final String getTypeCodeDisplayName(int tc) {
      switch (tc) {
         case ExprTypeCode.EXPR_ADD:
            return "+";
         case ExprTypeCode.EXPR_SUBTRACT:
            return "-";
         case ExprTypeCode.EXPR_DIVIDE:
            return "divide";
         case ExprTypeCode.EXPR_MULTIPLY:
            return "*";
         case ExprTypeCode.EXPR_MOD:
            return "mod";
      }
      return "?";
   }

   private void drawFunction(Graphics g, int x, int y, int width, int height, boolean dragOver) {
      if (dragOver) {
         g.setColor(Color.red);
      }
      else {
         g.setColor(Color.black);
      }
      int y2 = y + height;
      int x2 = x + width - height / 2;
      g.drawLine(x, y, x2, y);
      g.drawLine(x, y2, x2, y2);
      g.drawLine(x, y, x, y2);
      g.setColor(Color.white);
      g.fillArc(x2 - height / 2, y, height, height, -90, 180);
      if (dragOver) {
         g.setColor(Color.red);
      }
      else {
         g.setColor(Color.black);
      }
      g.drawArc(x2 - height / 2, y, height, height, -90, 180);
   }
}
