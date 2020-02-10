package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

class LiteralStringGraphicalXPathItem extends GraphicalXPathItem {
   private int mWidth = 50;

   public LiteralStringGraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      super(renderer, inRow, expr, parent);
   }

   public int getHeight() {
      return 40;
   }

   public int getWidth() {
      return mWidth;
   }

   public int getMinVerticalSpacing() {
      return 10;
   }

   public Point getEntryPointLocation(int childIndex) {
      // n/a
      return new Point(0, 0);
   }

   public void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel) {
      // Leftover, functions and operators:
      g.setColor(Color.white);
      g.fillRect(atX, atY, mWidth, getHeight());
      g.setColor(Color.black);
      g.drawRect(atX, atY, mWidth, getHeight());
      g.drawString(getExpr().getExprValue(), atX, atY + 20);
      if (dragOver) {
         g.setColor(Color.red);
         g.drawRect(atX, atY, mWidth, getHeight());
      }
   }
}
