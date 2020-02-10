package com.tibco.cep.studio.mapper.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Misc. graphics painting related utility functions.
 */
public class PaintUtils {

    /**
     * Draws a horizontal wavy line in the current color.
     */
    public static void drawWavyLine(Graphics g, int waveWidth, int waveHeight, int xleft, int xright, int y) {
        int segs = (xright-xleft)/waveWidth;
        int halfWidth = waveWidth/2;
        int halfHeight = waveHeight/2;
        int top = y-halfHeight;
        int bottom = y+halfHeight;
        for (int i=0;i<segs;i++) {
            int at = xleft + i*waveWidth;
            int mid = at + halfWidth;
            g.drawLine(at,bottom,mid,top);
            g.drawLine(mid,top,at+waveWidth,bottom);
        }
    }

    /**
     * Draws a cool 3d outline effect.<BR>
     * Lifted from 'basic' plaf package... is that public?<BR>
     * The rect is 2 pixels thick on every side.
     */
    public static void drawEtchedRect(Graphics g, int x, int y, int w, int h,
                                      Color shadow, Color darkShadow,
                                      Color highlight, Color lightHighlight)
    {
        Color oldColor = g.getColor();  // Make no net change to g
        g.translate(x, y);

        g.setColor(shadow);
        g.drawLine(0, 0, w-1, 0);      // outer border, top
        g.drawLine(0, 1, 0, h-2);      // outer border, left

        g.setColor(darkShadow);
        g.drawLine(1, 1, w-3, 1);      // inner border, top
        g.drawLine(1, 2, 1, h-3);      // inner border, left

        g.setColor(lightHighlight);
        g.drawLine(w-1, 0, w-1, h-1);  // outer border, bottom
        g.drawLine(0, h-1, w-1, h-1);  // outer border, right

        g.setColor(highlight);
        g.drawLine(w-2, 1, w-2, h-3);  // inner border, right
        g.drawLine(1, h-2, w-2, h-2);  // inner border, bottom

        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    /**
     * Draws a cool 3d outline effect.<BR>
     * The rect is 1 pixels thick on every side.
     */
    public static void drawThinEtchedRect(Graphics g, int x, int y, int w, int h,
                                      Color shadow,
                                      Color highlight)
    {
        Color oldColor = g.getColor();  // Make no net change to g
        g.translate(x, y);

        g.setColor(shadow);
        g.drawLine(0, 0, w-1, 0);      // outer border, top
        g.drawLine(0, 1, 0, h-2);      // outer border, left

        g.setColor(highlight);
        g.drawLine(w-1, 0, w-1, h-1);  // outer border, bottom
        g.drawLine(0, h-1, w-1, h-1);  // outer border, right

        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    // Helper fn. for getClosestEdgePoint
    private static int getClosestPixel(int x1, int x2, int atPos, int exclude) {
        if (atPos>=x2-exclude) {
            return x2-exclude;
        }
        if (atPos<x1+exclude) {
            return x1+exclude;
        }
        return atPos;
    }

    /**
     * For the specified rectangle and point, finds the point on the rectangle that is
     * closest to the point on the edge of the rectangle.<BR>
     * cornerExclude must be >= 0.  It specifies the number of pixels to back off from
     * corners, so 5 would mean that this function never returns something within 5 pixels
     * (in x or y) of a corner.<BR>
     * If the point is inside the rectangle, it just returns the point.
     */
    public static Point getClosestEdgePoint(Rectangle onRectangle, Point toPoint, int cornerExclude) {
        if (onRectangle.contains(toPoint)) {
            // degenerate case:
            return toPoint;
        }
        int right = onRectangle.x + onRectangle.width;
        int bottom = onRectangle.y + onRectangle.height;
        int xdist = Math.abs(toPoint.x - getClosestPixel(onRectangle.x,right,toPoint.x,0));
        int ydist = Math.abs(toPoint.y - getClosestPixel(onRectangle.y,bottom,toPoint.y,0));
        boolean vert = ydist>xdist;
        int xexclude = vert ? cornerExclude : 0;
        int yexclude = vert ? 0 : cornerExclude;

        int xPos = getClosestPixel(onRectangle.x,right,toPoint.x,xexclude);
        int yPos = getClosestPixel(onRectangle.y,bottom,toPoint.y,yexclude);
        return new Point(xPos,yPos);
    }

    /**
     * Returns true if the specified point sits on either of the horizontal lines
     * in the rectangle.  Useful in conjunction with getClosestEdgePoint()
     */
    public static boolean isOnHorizontalEdge(Rectangle rect, Point pointOnRect) {
        return (pointOnRect.y==rect.y || pointOnRect.y==rect.y+rect.height);
    }

    /**
     * Draws an arrow that only draws itself in 1 or 2 segments (1 segment if
     * the line is vertical or horizontal).  The first segment will be at a
     * 45 degree angle from horizontal or vertical.
     * @param originHorizontal If set, it will draw the origin of the arrow with a horizontally flat base,
     * otherwise draws it with a vertically flat base.
     */
    public static void drawAngledArrow(Graphics g, int x, int y, int x2, int y2, Color mainColor, Color edgeColor, int arrowWidth, int headWidth, boolean originHorizontal) {
        Color prev = g.getColor();
        int wx = Math.abs(x2-x);
        int wy = Math.abs(y2-y);
        int cutoff = Math.min(wy,wx);
        int xdir = x > x2 ? -1 : 1;
        int ydir = y > y2 ? -1 : 1;

        // Draw 45 degree line from origin to cutoff:
        int cx = x + xdir*cutoff;
        int cy = y + ydir*cutoff;
        boolean isVert = wy>wx;

        int halfArrowWidth = arrowWidth/2;
        int halfHeadWidth = headWidth/2;
        if (isVert) {
            // Draw the 45 degree part:
            for (int i=-halfArrowWidth;i<=halfArrowWidth;i++) {
                if (i==-halfArrowWidth || i==halfArrowWidth) {
                    g.setColor(edgeColor);
                } else {
                    g.setColor(mainColor);
                }
                if (originHorizontal) {
                    g.drawLine(x+i,y,x2+i,cy);
                } else {
                    g.drawLine(x,y+i,x2,cy+i);
                }

                // Draw the vertical line:
                g.drawLine(x2+i,cy,x2+i,y2);
            }

            // Draw the head:
            for (int i=1;i<=halfHeadWidth;i++) {
                g.drawLine(x2+i*xdir,y2-i*ydir,x2-i*xdir,y2-i*ydir);
            }
            g.setColor(edgeColor);
            g.drawLine(x2,y2,x2-halfHeadWidth*xdir,y2-halfHeadWidth*ydir);
            g.drawLine(x2,y2,x2+halfHeadWidth*xdir,y2-halfHeadWidth*ydir);
            g.drawLine(x2-halfHeadWidth*xdir,y2-halfHeadWidth*ydir,x2+halfHeadWidth*xdir,y2-halfHeadWidth*ydir);
        } else {
            // more horizontal:
            for (int i=-halfArrowWidth;i<=halfArrowWidth;i++) {
                if (i==-halfArrowWidth || i==halfArrowWidth) {
                    g.setColor(edgeColor);
                } else {
                    g.setColor(mainColor);
                }
                // Draw the 45 degree part:
                if (originHorizontal) {
                    g.drawLine(x+i,y,cx+i,y2);
                } else {
                    g.drawLine(x,y+i,cx,y2+i);
                }

                // Draw the vertical line:
                g.drawLine(cx,y2+i,x2,y2+i);
            }
            for (int i=1;i<=halfHeadWidth;i++) {
                g.drawLine(x2-i*xdir,y2+i*ydir,x2-i*xdir,y2-i*ydir);
            }
            g.setColor(edgeColor);
            g.drawLine(x2,y2,x2-halfHeadWidth*xdir,y2-halfHeadWidth*ydir);
            g.drawLine(x2,y2,x2-halfHeadWidth*xdir,y2+halfHeadWidth*ydir);
            g.drawLine(x2-halfHeadWidth*xdir,y2-halfHeadWidth*ydir,x2-halfHeadWidth*xdir,y2+halfHeadWidth*ydir);
        }
        // restore color.
        g.setColor(prev);
    }
}


