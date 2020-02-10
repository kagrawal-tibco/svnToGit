package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

class PredicateGraphicalXPathItem extends GraphicalXPathItem {
   private int mWidth = 50;
//   private int mArcWidth = 10;
//   private int mArcHeight = 10;

   public PredicateGraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      super(renderer, inRow, expr, parent);
   }

   public int getHeight() {
      return 40;
   }

   public int getWidth() {
      return mWidth;
   }

   public int getMinVerticalSpacing() {
      // have extra space because of line at bottom.
      return 10;
   }

   public Point getEntryPointLocation(int childIndex) {
      if (childIndex == 0) {
         return new Point(0, getHeight() / 2);
      }
      // Make #2 go inside the notch:
      return new Point(mWidth / 2, getHeight() + 4);
   }

   public void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel) {
      /*g.setColor(Color.green);
      g.fillRoundRect(atX,atY,mWidth,getHeight(),mArcWidth,mArcHeight);
      g.setColor(Color.black);
      g.drawRoundRect(atX,atY,mWidth,getHeight(),mArcWidth,mArcHeight);
      g.drawString(getExpr().getExprValue(),atX,atY+20);*/
      drawFilter(g, atX, atY, mWidth, getHeight());
   }

   private void drawFilter(Graphics g, int x, int y, int width, int height) {

      // First fill:
      g.setColor(Color.white);
      g.fillRect(x, y, width, height);
      g.setColor(Color.lightGray);

      int y2 = y + height;
      int x2 = x + width;
      int notchSize = 5;
      int xm = x + width / 2;

      g.fillRect(xm - notchSize, y2 - notchSize, notchSize, notchSize);

      // Then paint border:
      g.setColor(Color.black);
      g.drawLine(x, y, x2, y);
      g.drawLine(x, y, x, y2);
      g.drawLine(x2, y, x2, y2);

      // Then the notch:
      g.drawLine(x, y2, xm - notchSize, y2);
      g.drawLine(xm - notchSize, y2, xm, y2 - notchSize);
      g.drawLine(xm, y2 - notchSize, xm + notchSize, y2);
      g.drawLine(xm + notchSize, y2, x2, y2);
   }
}
