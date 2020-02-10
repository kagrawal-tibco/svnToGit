package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * A utility class to do drag'n'drop where the dragging is painted --- the
 * real swing drag'n'drop fell apart trying to do this stuff, lots of issues.
 * This must be the parent window, not for subclassing.
 */
public final class SoftDragNDropHandler implements SoftDragNDropManager, OvershowManager {
    private boolean mDragging;
    private boolean mDragIsCopy;
    private Object mDraggingObject;
    private Point mMousePressedAt; // in root
    private SoftDragNDropable mMousePressedIn; // w/ pressedAt, also the drag source
    private Point mDragCursorAt; // where the drag is.
    private SoftDragNDropable mDragOver; // if dragging & over something.
    private MyListener mListener;

    private Rectangle mDragHintBounds;
    private Rectangle mLastDropRect; // bounds of last repainted drop hint.
    private Rectangle mLastDraggingRect; // bounds of last dragging indicator

    private final JComponent mRoot;

    private JComponent mOvershow;
    private Rectangle mOvershowBounds;
    private CellRendererPane mRendererPane;

    class MyListener implements MouseMotionListener, MouseListener {
        public MyListener() {
        }

        public void mouseMoved(MouseEvent me) {
            preprocessEvent(me);
        }

        public void mouseDragged(MouseEvent me) {
            preprocessEvent(me);
        }

        public void mousePressed(MouseEvent me) {
            preprocessEvent(me);
        }

        public void mouseReleased(MouseEvent me) {
            preprocessEvent(me);
        }

        public void mouseClicked(MouseEvent e) {
            preprocessEvent(e);
        }

        public void mouseEntered(MouseEvent e) {
            preprocessEvent(e);
        }

        public void mouseExited(MouseEvent e) {
            preprocessEvent(e);
        }
    }

    public SoftDragNDropHandler(JComponent root, JComponent[] listenTo)
    {
        mRoot = root;
        mListener = new MyListener();
        for (int i=0;i<listenTo.length;i++)
        {
            addListenerTo(listenTo[i]);
        }
        mRendererPane = new CellRendererPane();
    }

    public void addListenerTo(JComponent c)
    {
        if (c!=null)
        {
            c.addMouseListener(mListener);
            c.addMouseMotionListener(mListener);
        }
    }

    public void removeListenerFrom(JComponent c)
    {
        if (c!=null)
        {
            c.removeMouseListener(mListener);
            c.removeMouseMotionListener(mListener);
        }
    }

    public JComponent getRoot() {
        return mRoot;
    }

    public void setOvershowComponent(JComponent component) {
        if (component==mOvershow) {
            return;
        }
        if (mOvershow!=null) {
            mRendererPane.removeAll();
            mRoot.repaint(mOvershowBounds);
        }
        mOvershow = component;
        if (component!=null) {
            //mRoot.add(component,JLayeredPane.POPUP_LAYER);
            //component.doLayout();
		    mRendererPane.add(component);
		    component.validate();
            mRoot.repaint(component.getBounds());
            mOvershowBounds = mOvershow.getBounds();
        }
    }

    public void paintDragging(Graphics g) {
        if (mDragging) {
            Point tsl = getInComponentCoords(mMousePressedIn,new Point(0,0));
            Point atInLocal = getInComponentCoords(mMousePressedIn,mDragCursorAt);
            g.translate(-tsl.x,-tsl.y);
            mMousePressedIn.drawDraggingIndicator(g,this, atInLocal, mDraggingObject);
            g.translate(tsl.x,tsl.y);
        }
        if (mOvershow!=null) {
            Rectangle bounds = mOvershow.getBounds();//?
            mRendererPane.paintComponent(g, mOvershow, mRoot, bounds.x, bounds.y,
                            bounds.width, bounds.height, true);
        }
    }

    public Point getDragOrigin() {
        return mMousePressedAt;
    }

    public Rectangle getDropHintBounds() {
        return mLastDropRect;
    }

    public Rectangle getDragHintBounds() {
        return mDragHintBounds;
    }

    private Component getInnermostComponent(Point atLoc) {
        Component at = mRoot;
        for (;;) {
            if (mRoot!=at) { // after first one, step in:
                Rectangle atb = at.getBounds();
                atLoc = new Point(atLoc.x-atb.x,atLoc.y-atb.y);
            }
            Component next;
            if (at instanceof JTabbedPane) {
                JTabbedPane jtp = (JTabbedPane) at;
                next = jtp.getSelectedComponent();
            } else {
                next = at.getComponentAt(atLoc);
            }
            if (next==null) {
                break;
            }
            if (next==at) {
                break;
            }
            at = next;
        }
        return at;
    }

    /**
     * Find uppermost SoftDragNDropable component
     */
    private SoftDragNDropable getDragNDropable(Point atLoc) {
        Component at = getInnermostComponent(atLoc);
        if (at==null) {
            return null;
        }
        SoftDragNDropable d = null;
        while (at!=null && at!=mRoot) {
            if (at instanceof SoftDragNDropable) {
                d = (SoftDragNDropable) at;
            }
            at = at.getParent();
        }
        return d;
    }

    private Point getInComponentCoords(SoftDragNDropable dnd, Point at) {
        Component c = (Component) dnd;
        Point ret = new Point(at);
        while (c!=null && c!=mRoot) {
            Rectangle r = c.getBounds();
            ret.x -= r.x;
            ret.y -= r.y;
            c = c.getParent();
        }
        return ret;
    }

    private Point getFromComponentCoords(SoftDragNDropable dnd, Point at) {
        Component c = (Component) dnd;
        Point ret = new Point(at);
        while (c!=null && c!=mRoot) {
            Rectangle r = c.getBounds();
            ret.x += r.x;
            ret.y += r.y;
            c = c.getParent();
        }
        return ret;
    }

    private Point getFromComponentCoords(MouseEvent me) {
        Object src = me.getSource();
        if (!(src instanceof Component)) return me.getPoint();
        Component c = (Component) src;
        Point ret = new Point(me.getPoint());
        while (c!=null && c!=mRoot) {
            Rectangle r = c.getBounds();
            ret.x += r.x;
            ret.y += r.y;
            c = c.getParent();
        }
        return ret;
    }

    private Rectangle getFromComponentCoords(SoftDragNDropable dnd, Rectangle r) {
        Point rr = getFromComponentCoords(dnd,new Point(r.x,r.y));
        return new Rectangle(rr.x,rr.y,r.width,r.height);
    }

    public void stopDrag() {
        if (!mDragging) return;
        repaintOldDraggingArea();
        repaintDragHint();
        repaintDropHint();
        mDragging = false;
        mDraggingObject = null;
        mMousePressedIn.dragStopped();
        mMousePressedIn = null;
        mMousePressedAt = null;
        if (mDragOver!=null) {
            mDragOver.dropStopped();
        }
        mDragOver = null;
        mDragCursorAt = null;
    }

    private void released(Point componentRelativeAt) {
        if (!mDragging) return;
        dropped(componentRelativeAt);
        mDragCursorAt = null;
        stopDrag();
    }

    private void dropped(Point at) {
        mDragOver = getDragNDropable(at);
        if (mDragOver==null) return;
        mDragOver.drop(this,getInComponentCoords(mDragOver,at),mDraggingObject);
    }

    private void repaintDragHint() {
        if (mMousePressedIn==null) return;
        Rectangle r = mMousePressedIn.getDragHintBounds();
        if (r!=null) {
            Rectangle r2 = getFromComponentCoords(mMousePressedIn,r);
            mRoot.repaint(r2);
        }
        mDragHintBounds = r;
    }

    private void repaintDropHint() {
        if (mLastDropRect!=null) {
            mRoot.repaint(mLastDropRect);
            mLastDropRect = null;
        }
        if (mDragOver==null) return;
        Rectangle r = mDragOver.getDropHintBounds();
        if (r!=null) {
            Rectangle r2 = getFromComponentCoords(mDragOver,r);
            mRoot.repaint(r2);
            mLastDropRect = r2;
        }
    }

    private void repaintOldDraggingArea() {
        if (mLastDraggingRect!=null) {
            mRoot.repaint(mLastDraggingRect);
        }
        mLastDraggingRect = null;
    }

    private void repaintDraggingArea(Point at) {
        Point atInLocal = getInComponentCoords(mMousePressedIn,at);
        Rectangle bounds = mMousePressedIn.getDraggingIndicatorBounds(this,atInLocal,mDraggingObject);
        if (bounds!=null) {
            bounds = getFromComponentCoords(mMousePressedIn,bounds);
            mRoot.repaint(bounds);
        }
        mLastDraggingRect = bounds;
    }

    private void drag(Point at) {
        if (!mDragging) return;
        mDragCursorAt = at;

        repaintOldDraggingArea();
        repaintDraggingArea(at);

        repaintDropHint(); // erase old drop hint area
        if (mDragOver!=null) {
            // Sort of a hack... gets it to clear out any memory of previous drop hints...
            // maybe add separate method.
            mDragOver.dropStopped();
        }
        mDragOver = getDragNDropable(at);
        if (mDragOver!=null) {

            boolean d = mDragOver.dragOver(this,getInComponentCoords(mDragOver,at),mDraggingObject);
            if (d) {
                repaintDropHint(); // repaint new area
            } else {
                // we're not dragging over.
                mDragOver = null;
            }
        }
    }

    private boolean checkDrag(Point componentRelativeAt, Point absoluteAt, boolean isCopy) {
        if (mMousePressedIn==null) { // If the click wasn't on something, we can't drag
            return false;
        }
        if (mDragging) {
            drag(absoluteAt);
            return true; //?
        }
        Point start = mMousePressedAt;
        Point rel = componentRelativeAt;
        Object drag = mMousePressedIn.startDrag(this,start,rel);
        if (drag==null) {
            return false;
        }
        mDragging = true;
        mDragIsCopy = isCopy;
        mDraggingObject = drag;
        repaintDragHint();
        drag(absoluteAt);
        return true;
    }

    public boolean isDragCopy() {
        return mDragIsCopy;
    }

    private SoftDragNDropable getSoftDrag(Component val) {
        if (val==null) {
            return null;
        }
        if (val instanceof SoftDragNDropable) {
            return (SoftDragNDropable) val;
        }
        return getSoftDrag(val.getParent());
    }

    private boolean preprocessEvent(AWTEvent event) {
        if (!(event instanceof MouseEvent)) { // shouldn't happen.
            return false;
        }
        MouseEvent me = (MouseEvent) event;
        Point at = me.getPoint();
        SoftDragNDropable sdnd = getSoftDrag((Component)me.getSource());
        at = SwingUtilities.convertPoint((Component)me.getSource(),at,(Component)sdnd);
        Point adjusted = getFromComponentCoords(me);
//        System.out.println("Process " + at + " and " + adjusted);
        switch (me.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                mMousePressedIn = sdnd;//getDragNDropable(adjusted);
                if (mMousePressedIn!=null) {
                    mMousePressedAt = at;//adjusted;
                } else {
                    mMousePressedAt = null;
                }
                break;
            case MouseEvent.MOUSE_DRAGGED:
                if (checkDrag(at,adjusted,(me.getModifiers()&MouseEvent.CTRL_MASK)>0)) {
                    return true;
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                released(adjusted);
                break;
            case MouseEvent.MOUSE_ENTERED:
                // keep it going... stopDrag();
                break;
            case MouseEvent.MOUSE_EXITED:
                // keep it going... stopDrag();
                break;
            case MouseEvent.MOUSE_MOVED:
                stopDrag(); // (Could happen if we left the area in a drag, released, then came back in)
                break;
        }
        return false;
    }
}
