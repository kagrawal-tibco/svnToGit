package com.tibco.cep.studio.mapper.ui.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.tree.TreePath;

/**
 * A small class for drawing the actual binding line.
 */
public class BindingLineRenderer {
   public static final int END_STYLE_NONE = 0;
   public static final int END_STYLE_ARROW = 1;
   public static final int END_STYLE_UP_ARROW = 2;
   public static final int END_STYLE_DOT = 3;


   private Color mInnerColor = new Color(128, 128, 255);
   private Color mOuterColor = new Color(200, 200, 255);
   private Color mSelInnerColor = mInnerColor.darker();
   private Color mSelOuterColor = mOuterColor.darker();

   private Color mSelForEachInnerColor = new Color(128, 255, 128);
   private Color mForEachInnerColor = new Color(200, 255, 200);
   private Color mSelForEachOuterColor = mSelForEachInnerColor.darker();
   private Color mForEachOuterColor = mForEachInnerColor.darker();

   private Stroke mDashedStroke;
   private Stroke mSelDashedStroke;
   private Stroke mNormalStroke;
   private Stroke mEraseStroke;
   private Stroke mEraseSelStroke;
//   private boolean mInDataMode;

   private boolean mTrimTooSteep = true;

   public BindingLineRenderer() {
      mNormalStroke = new BasicStroke(1);
      mEraseStroke = new BasicStroke(4);
      mEraseSelStroke = new BasicStroke(5); // bigger.
      final float dash1[] = {4.0f};
      mDashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f);
      mSelDashedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f);
   }

   /*
    * Sets up optimizing clipping range before painting any lines:
    *
   public void computeOffsets() {
       Point inputOffset = SwingUtilities.convertPoint(mInput,0,0,mRoot);
       mLeftXOffset = inputOffset.x;
       mLeftYOffset = inputOffset.y + mInput.getRowHeight()/2 + 2;

       Point outputOffset = SwingUtilities.convertPoint(mOutput,0,0,mRoot);
       mRightXOffset = outputOffset.x;
       mRightYOffset = outputOffset.y;

       mLeftSplitLocation = SwingUtilities.convertPoint(mSplitPane,0,0,mRoot).x + mSplitPane.getDividerLocation();
       mRightSplitLocation = SwingUtilities.convertPoint(mOutput,0,0,mRoot).x;
   }*/

   /**
    * If true, when drawing a connecty-line with a joining slope > 8-1, it will draw an abbreviated icon, otherwise, just draws it.
    */
   public void setTrimTooSteep(boolean val) {
      mTrimTooSteep = val;
   }

   public void drawLinesFor(TreePath path, Graphics g, int rightX, int rightY, int x3, int y3, int endStyle, boolean sel, boolean isDotted, boolean isForEach, boolean solid) {
      throw new RuntimeException();/*
       TreePath drawPath = BindingLines.getDeepestExpandedPath(mInput,path);
       isDotted = isDotted || path!=drawPath;
       if (isDotted == solid) {
           return;
       }
       Rectangle b = mInput.getPathBounds(drawPath);
       if (b==null) {
           // Once again, paranoia, shouldn't happen because we checked that it was expanded.
           return;
       }
       int actualWidth = mInput.getActualWidth(g,drawPath);
       int leftY = b.y+mLeftYOffset;

       int xil = mLeftSplitLocation;
       int xir = mRightSplitLocation;
       int left = mLeftXOffset + b.x+actualWidth+10;
       int x2 = rightX+mRightXOffset;
       int y2 = rightY+mRightYOffset;
       drawConnectyLine(g,left,leftY,xil,xir,x2,y2,x3+mRightXOffset,y3+mRightYOffset,endStyle,sel,isDotted,isForEach);*/
   }

   public void drawConnectyLine(Graphics g, int x1, int y1, int xil, int xir, int x2, int y2, int x3, int y3, int endStyle, boolean sel, boolean isDotted, boolean isForEach) {

      // Account for space required by end-of-arrow adornment, if any.
      int lastxoffset = 0;
      if (endStyle != END_STYLE_NONE && endStyle != END_STYLE_UP_ARROW) {
         lastxoffset = 4;
      }
      x3 -= lastxoffset;
      int lastyoffset = 0;
      boolean upArrow = false;
      if (endStyle == END_STYLE_UP_ARROW) {
         lastyoffset = 4;
         upArrow = true;
      }
      y3 += lastyoffset;
      y2 += lastyoffset;

      // These outer white lines give it a pseudo-bridge look:
      Graphics2D g2d = (Graphics2D) g;
      if (sel) {
         g2d.setStroke(mEraseSelStroke);
      }
      else {
         g2d.setStroke(mEraseStroke);
      }
      g.setColor(Color.white);
      drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, x3, y3, upArrow, sel);

      if (isForEach) {
         g.setColor(sel ? mSelForEachOuterColor : mForEachOuterColor);
      }
      else {
         g.setColor(sel ? mSelOuterColor : mOuterColor);
      }
      if (isDotted) {
         if (sel) {
            g2d.setStroke(mSelDashedStroke);
         }
         else {
            g2d.setStroke(mDashedStroke);
         }
         drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, x3, y3, upArrow, sel);
      }
      else {
         g2d.setStroke(mNormalStroke);

         drawConnectyLinePiece(g, x1, y1 - 1, xil, xir, x2, y2 - 1, x3, y3 - 1, upArrow, sel);
         if (sel) {
            drawConnectyLinePiece(g, x1, y1 - 2, xil, xir, x2, y2 - 2, x3, y3 - 2, upArrow, sel);
         }

         // looks WAY better if the inside is painted afterwards (antialiasing stuff I think)

         if (isForEach) {
            g.setColor(sel ? mSelForEachInnerColor : mForEachInnerColor);
         }
         else {
            g.setColor(sel ? mSelInnerColor : mInnerColor);
         }

         drawConnectyLinePiece(g, x1, y1, xil, xir, x2, y2, x3, y3, upArrow, sel);
      }
      switch (endStyle) {
         case END_STYLE_NONE:
            break;
         case END_STYLE_ARROW:
            drawArrowHead(g, x3 + lastxoffset, y3);
            break;
         case END_STYLE_UP_ARROW:
            drawUpArrowHead(g, x3, y3 - lastyoffset);
            break;
         case END_STYLE_DOT:
            drawCircleHead(g, x3 + lastxoffset, y3);
            break;
      }
   }

   public Color getInnerLineColor(boolean isForEach) {
      if (isForEach) {
         return mForEachInnerColor;
      }
      else {
         return mInnerColor;
      }
   }

   private void drawConnectyLinePiece(Graphics g, int x1, int y1, int xil, int xir, int x2, int y2, int x3, int y3, boolean upArrow, boolean sel) {
      int width = xir - xil;
      Graphics2D g2d = (Graphics2D) g;
      boolean y1inRange = inRange(y1);
      boolean y2inRange = inRange(y2);
      if (y2 == y3) { // it's a straight line, draw it in one swoop (because we're anti-aliasing, it will look different otherwise)
         x2 = x3;
      }
      boolean slopeTooGreat;
      if (mTrimTooSteep) {
         slopeTooGreat = width < 5 || ((!y1inRange || !y2inRange) && Math.abs(((y2 - y1) / width)) >= (sel ? 8 : 4));
      }
      else {
         slopeTooGreat = false;
      }

      if (!slopeTooGreat) {
         g.drawLine(xil, y1, xir, y2);
      }
      if (y1inRange) {
         int l = Math.min(xil - 4, x1);
         g.drawLine(l, y1, xil, y1);
         if (slopeTooGreat) {
            int dirOffset = (y1 < y2) ? 8 : -8;
            g2d.drawLine(xil, y1, xil + 4, y1 + dirOffset);
         }
      }
      if (y2inRange) {
         if (x2 > 0) {
            g.drawLine(xir, y2, x2, y2);
         }
         if (slopeTooGreat) {
            int dirOffset = (y1 > y2) ? 8 : -8;
            g2d.drawLine(xir, y2, xir - 4, y2 + dirOffset);
         }
      }
      if (y3 != y2) {
         g.drawLine(x2, y2, x2, y3);
         g.drawLine(x2, y3, x3, y3);
      }
      if (upArrow) {
         g.drawLine(x3, y3, x3, y3 - 3);
      }
   }

   private boolean inRange(int y) {
      /*
      if (y<mTopY+4) {
          return false;
      }
      if (y>mBottomY-4) {
          return false;
      }*/
      // FIXME
      return true;
   }

   public void drawArrowHead(Graphics g, int x2, int y2) {
      int arrowSize = 6;
      g.setColor(getInnerLineColor(false));
      for (int i = 0; i < arrowSize; i++) {
         g.drawLine(x2 - i, y2 - i, x2 - i, y2 + i);
      }
   }

   public void drawUpArrowHead(Graphics g, int x2, int y2) {
      int arrowSize = 6;
      g.setColor(getInnerLineColor(false));
      for (int i = 0; i < arrowSize; i++) {
         g.drawLine(x2 - i, y2 + i - arrowSize, x2 + i, y2 + i - arrowSize);
      }
   }

   public void drawCircleHead(Graphics g, int x2, int y2) {
      int circleSize = 8;
      g.setColor(Color.black);
      g.fillOval(x2 - circleSize / 2, y2 - circleSize / 2, circleSize, circleSize);
   }
}


