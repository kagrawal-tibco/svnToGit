package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

class MarkerGraphicalXPathItem extends GraphicalXPathItem {
   private int mWidth = 50;
   private int mArcWidth = 20;
   private int mArcHeight = 20;

   public MarkerGraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      super(renderer, inRow, expr, parent);
   }

   public int getHeight() {
      return 40;
   }

   public int getMinVerticalSpacing() {
      return 20;
   }

   public int getWidth() {
      return mWidth;
   }

   public Point getEntryPointLocation(int childIndex) {
      return new Point(0, 0);
   }

   public void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel) {
      g.setColor(Color.green);
      g.fillRoundRect(atX, atY, mWidth, getHeight(), mArcWidth, mArcHeight);
      g.setColor(Color.black);
      g.drawRoundRect(atX, atY, mWidth, getHeight(), mArcWidth, mArcHeight);
      g.drawString(getExpr().getExprValue(), atX, atY + 20);
      if (dragOver) {
         g.setColor(Color.red);
         g.drawRoundRect(atX, atY, mWidth, getHeight(), mArcWidth, mArcHeight);
      }
   }
}
