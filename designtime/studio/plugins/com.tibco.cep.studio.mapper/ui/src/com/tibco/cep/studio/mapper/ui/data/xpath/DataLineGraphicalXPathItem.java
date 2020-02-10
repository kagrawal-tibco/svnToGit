package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Graphics;
import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.studio.mapper.ui.data.BindingLineRenderer;

class DataLineGraphicalXPathItem extends GraphicalXPathItem {
   public DataLineGraphicalXPathItem(BindingLineRenderer renderer, GraphicalXPathRow inRow, Expr expr, GraphicalXPathItem parent) {
      super(renderer, inRow, expr, parent);
   }

   public int getHeight() {
      return 5;
   }

   public int getWidth() {
      // really has no width...
      return 10;
   }

   public int getMinVerticalSpacing() {
      return 0;
   }

   public Point getEntryPointLocation(int childIndex) {
      return new Point(0, 0); // n/a
   }

   public void paint(Graphics g, int atX, int atY, boolean dragOver, boolean sel) {
      // no painting, lines are drawn elsewhere...
   }
}
