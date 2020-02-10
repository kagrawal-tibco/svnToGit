package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

class GraphicalXPathRow {
   private ArrayList<GraphicalXPathItem> mItems = new ArrayList<GraphicalXPathItem>();
   int mXLocation;

   public void add(GraphicalXPathItem item) {
      mItems.add(item);
   }

   public int getSize() {
      return mItems.size();
   }

   public GraphicalXPathItem get(int s) {
      return mItems.get(s);
   }

   public void paint(Graphics g, GraphicalXPathItem dragOver, GraphicalXPathItem selected) {
//      int rowHeight = 50;
      int atX = mXLocation;
      for (int i = 0; i < getSize(); i++) {
         GraphicalXPathItem item = get(i);
         int atY = item.mYLocation;
         item.paint(g, atX, atY, dragOver == item, selected == item);
         item.paintConnection(g, atX, atY);
      }
   }

   public GraphicalXPathItem getItemAtPoint(Point at) {
      int mItemWidth = 80;
      if (at.x >= mXLocation && at.x <= mXLocation + mItemWidth) {
         for (int j = 0; j < getSize(); j++) {
            GraphicalXPathItem item = get(j);
            if (at.y >= item.mYLocation && at.y <= item.mYLocation + item.getHeight()) {
               return item;
            }
         }
      }
      return null;
   }

   public int getWidth() {
      int mw = 10;
      for (int i = 0; i < getSize(); i++) {
         int w = get(i).getWidth();
         mw = Math.max(mw, w);
      }
      return mw;
   }
}
