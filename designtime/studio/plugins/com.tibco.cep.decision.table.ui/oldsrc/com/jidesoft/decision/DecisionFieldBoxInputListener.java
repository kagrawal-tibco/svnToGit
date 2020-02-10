/*
 * @(#)FieldMouseInputListener.java 5/11/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.jidesoft.swing.ShadowFactory;

/**
 * <code>FieldBoxInputListener</code> is a listener added to DecisionFieldBox to handle the drag-n-drop, keystrokes and
 * action.
 */
public class DecisionFieldBoxInputListener implements MouseListener, MouseMotionListener, KeyListener, ActionListener, PropertyChangeListener {
    private DecisionTablePane _pane;

    private static final int OFFSET = 2;

    // cached values for fast painting
    private int _distanceX = 0;
    private int _distanceY = 0;

    private int _positionX = 0;
    private int _positionY = 0;

    private boolean _isDragging = false;
    private boolean _shouldCancel = true;
    private boolean _dropAllowed = true;

    private int _deltaX;
    private int _deltaY;

    private BufferedImage _image;
    private BufferedImage _shadow;

    private boolean _isAboutToDrag = false;
    private Point _startLocation;

    private Rectangle _dropArea = null;
    private Container _dropContainer = null;
    private int _dropSide = -1;

    private static final float ANGLE = 90;
    private static final int DISTANCE = 5;

    public DecisionFieldBoxInputListener(DecisionTablePane pane) {
        _pane = pane;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            Point p = e.getPoint();
            p = SwingUtilities.convertPoint((Component) e.getSource(), p, _pane);
            _pane.showPopup(p);
            return;
        }

        if (!_pane.isRearrangable()) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e) && !_isAboutToDrag && !_isDragging) {
            _isAboutToDrag = true;
            _startLocation = e.getPoint();
            _startLocation = SwingUtilities.convertPoint((Component) e.getSource(), _startLocation, _pane);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            Point p = e.getPoint();
            p = SwingUtilities.convertPoint((Component) e.getSource(), p, _pane);
            _pane.showPopup(p);
            return;
        }

        if (!_pane.isRearrangable()) {
            return;
        }

        if (_isDragging) {
            endDragging((Component) e.getSource());
        }
        else {
            resetAll();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (!_pane.isRearrangable()) {
            return;
        }

        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        Point p = e.getPoint();
        p = SwingUtilities.convertPoint((Component) e.getSource(), p, _pane);
        if (_isAboutToDrag) {
            if (Math.abs(p.x - _startLocation.x) > OFFSET
                    || Math.abs(p.y - _startLocation.y) > OFFSET) {
                _isDragging = true;
                beginDragging((Component) e.getSource(), _startLocation);
                _isAboutToDrag = false;
            }
        }
        else if (_isDragging) {
            dragging((Component) e.getSource(), p);
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cancelDragging();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public boolean isDragging() {
        return _isDragging;
    }

    private void beginDragging(Component target, Point p) {
        Point start = target.getLocation();
        start = SwingUtilities.convertPoint(target.getParent(), start, _pane);
        _positionX = start.x;
        _positionY = start.y;
        _deltaX = p.x - _positionX;
        _deltaY = p.y - _positionY;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();

        // Create an image that does not support transparency
        _image = gc.createCompatibleImage(target.getWidth(), target.getHeight(), Transparency.TRANSLUCENT);
        boolean wasDoubleBuffered = false;
        if ((target instanceof JComponent) && target.isDoubleBuffered()) {
            wasDoubleBuffered = true;
            ((JComponent) target).setDoubleBuffered(false);
        }

        try {
            target.paint(_image.getGraphics());
        }
        finally {
            if (wasDoubleBuffered && (target instanceof JComponent)) {
                ((JComponent) target).setDoubleBuffered(true);
            }
        }

        ShadowFactory factory = new ShadowFactory(5, 0.5f, Color.BLACK);
        _shadow = factory.createShadow(_image);
        _isDragging = true;
    }

    /**
     * Calculate if you allow the customer to drop a DecisionFieldBox to the area in the container.
     * <p/>
     * You can use this method to control the behavior of the mouse dragging. For example, you can just return false if
     * you don't want the customer to move any fields.
     * <p/>
     * The default implementation of this method is just to return true. So it allows any dragging and drop behavior
     * provided by JIDE jars.
     *
     * @param draggingComponent the component being dragged
     * @param dropArea          the area to be drawn as dropped in indication
     * @param dropSide          the drop side
     * @param dropContainer     the container where the draggingComponent will be dropped into
     * @return true if you allow the drop action. Otherwise false.
     */
    protected boolean isDropAllowed(Component draggingComponent, Rectangle dropArea, int dropSide, Container dropContainer) {
        return true;
    }

    private void dragging(Component target, Point p) {
        int saveX = _positionX;
        int saveY = _positionY;
        Rectangle saveDropArea = _dropArea;

        _positionX = p.x - _deltaX;
        _positionY = p.y - _deltaY;

        Component component = _pane.findComponentAt(p);
        Container container = DecisionTablePane.getValidParentOf(target, component);
        _shouldCancel = target == container;
        if (container instanceof JViewport) {
            container = (Container) ((JViewport) container).getView();
        }
        if (target == container) {
            container = null;
        }

        if (container != null) {
//        	Seems to be redundant code, since ConditionTable and ActionTable are not really used
//            if (container instanceof ConditionTable) {
//                container = _pane.getConditionFieldsArea();
//            }
//            else if (container instanceof ActionTable) {
//                container = _pane.getActionFieldsArea();
//            }
            Rectangle rect = container.getBounds();
            if (container instanceof DecisionFieldArea) {
                Insets insets = container.getInsets();
                rect.x += insets.left;
                rect.y += insets.top;
                rect.width -= insets.left + insets.right;
                rect.height -= insets.top + insets.bottom;
            }
            rect = SwingUtilities.convertRectangle(container.getParent(), rect, _pane);
            if (container instanceof DecisionFieldBox) {
                if (target instanceof DecisionFieldBox && ((DecisionFieldBox) target).getField().isAllowedAsField(((DecisionFieldBox) container).getField().getAreaType())) {
                    int side = sideOf(p, rect);
                    switch (side) {
                        case SwingConstants.EAST:
                            _dropArea = new Rectangle(rect.x + rect.width / 2, rect.y, rect.width / 2, rect.height);
                            _dropSide = side;
                            _dropContainer = container;
                            break;
                        case SwingConstants.WEST:
                            _dropArea = new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);
                            _dropSide = side;
                            _dropContainer = container;
                            break;
                        default:
                            resetDropArea();
                            break;
                    }
                }
                else {
                    resetDropArea();
                }
            }
            else if (container instanceof DecisionFieldArea) {
                if (container.getComponentCount() > 0 && target instanceof DecisionFieldBox && ((DecisionFieldBox) target).getField().isAllowedAsField(((DecisionFieldArea) container).getAreaType())) {
                    Insets insets = container.getInsets();
                    Component lastComponent = container.getComponent(container.getComponentCount() - 1);
                    _dropArea = new Rectangle(rect.x + lastComponent.getX() + lastComponent.getWidth() + DecisionTablePane.FIELD_GAP - insets.left, rect.y,
                            target.getPreferredSize().width, lastComponent.getHeight());
                    _dropSide = SwingConstants.EAST;
                    _dropContainer = container;
                }
                else if (target instanceof DecisionFieldBox && ((DecisionFieldBox) target).getField().isAllowedAsField(((DecisionFieldArea) container).getAreaType())) {
                    _dropArea = new Rectangle(rect.x, rect.y/* + (rect.height - _image.getHeight()) / 2*/,
                            Math.min(rect.width, _image.getWidth()), rect.height);
                    _dropSide = -1;
                    _dropContainer = container;
                }
                else {
                    resetDropArea();
                }
            }
            else {
                resetDropArea();
            }
        }
        else {
            resetDropArea();
        }

        _dropAllowed = isDropAllowed(target, _dropArea, _dropSide, _dropContainer);

        Rectangle rect1 = new Rectangle(saveX, saveY, _shadow.getWidth() + _distanceX, _shadow.getHeight() + _distanceY);
        Rectangle rect2 = new Rectangle(_positionX, _positionY, _shadow.getWidth() + _distanceX, _shadow.getHeight() + _distanceY);
        _pane.repaint(rect2.union(rect1));
        if (saveDropArea != null) {
            _pane.repaint(saveDropArea);
        }
        if (_dropArea != null) {
            _pane.repaint(_dropArea);
        }
    }

    private void resetAll() {
        _pane.setCursor(null);
        resetDropArea();
        _positionX = 0;
        _positionY = 0;
        _deltaX = 0;
        _deltaY = 0;
        _startLocation = null;
        _isAboutToDrag = false;
        _isDragging = false;
    }

    private void resetDropArea() {
        _dropArea = null;
        _dropSide = -1;
        _dropContainer = null;
    }

    private void cancelDragging() {
        if (_shadow != null && _image != null) {
            Rectangle rect = new Rectangle(_positionX, _positionY, _shadow.getWidth() + _distanceX, _shadow.getHeight() + _distanceY);
            _pane.repaint(rect);
        }

        if (_dropArea != null) {
            _pane.repaint(_dropArea);
        }

        resetAll();
    }

    private void endDragging(Component draggingComponent) {
        if (_shadow != null && _image != null) {
            Rectangle rect = new Rectangle(_positionX, _positionY, _shadow.getWidth() + _distanceX, _shadow.getHeight() + _distanceY);
            _pane.repaint(rect);
        }

        if (_dropArea != null) {
            _pane.repaint(_dropArea);
        }

        if (!(draggingComponent instanceof DecisionFieldBox) || _shouldCancel || !_dropAllowed) {
            resetAll();
            return;
        }

        if (_dropContainer instanceof DecisionFieldBox) {
            DecisionFieldBox dropBox = (DecisionFieldBox) _dropContainer;
            DecisionFieldBox dragBox = (DecisionFieldBox) draggingComponent;
            boolean ltr = _pane.getComponentOrientation().isLeftToRight();
            int dropSide = _dropSide;
            if (!ltr) {
                dropSide = _dropSide == SwingConstants.EAST ? SwingConstants.WEST : SwingConstants.EAST;
            }
            switch (dropSide) {
                case SwingConstants.EAST:
                    _pane.moveFieldBox(dragBox, dropBox, false);
                    break;
                case SwingConstants.WEST:
                    _pane.moveFieldBox(dragBox, dropBox, true);
                    break;
            }
        }
        else if (_dropContainer instanceof DecisionFieldArea) {
            DecisionFieldBox dragBox = (DecisionFieldBox) draggingComponent;
            DecisionFieldArea dropArea = (DecisionFieldArea) _dropContainer;
            _pane.addFieldBox(dragBox, dropArea.getAreaType());
        }
        else if (_pane.isHideFieldOnDraggingOut()) {
        	/*
        	 * if the field box will be hidden when user drags it and drop outside the field areas. 
        	 * No action taken 
        	 */
//            DecisionFieldBox dragBox = (DecisionFieldBox) draggingComponent;
//            _pane.removeFieldBox(dragBox);
        }

        resetAll();
    }

    public BufferedImage getImage() {
        return _image;
    }

    public BufferedImage getShadow() {
        return _shadow;
    }

    private void computeShadowPosition(double distance) {
        double angleRadians = Math.toRadians(ANGLE);
        _distanceX = (int) (Math.cos(angleRadians) * distance);
        _distanceY = (int) (Math.sin(angleRadians) * distance);
    }

    public void drawItem(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (_dropAllowed) {
            if (_dropArea != null) {
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(_dropArea.x, _dropArea.y, _dropArea.width - 1, _dropArea.height - 1);
                g2.drawRect(_dropArea.x + 1, _dropArea.y + 1, _dropArea.width - 3, _dropArea.height - 3);
                _pane.setCursor(null);
            }
            else if (!_shouldCancel) {
                _pane.setCursor(_pane.getDragRemoveCursor());
            }
            else {
                _pane.setCursor(null);
            }
        }
        else {
            _pane.setCursor(_pane.getDragNoDropCursor());
        }

        int width = _shadow.getWidth();
        int height = _shadow.getHeight();

        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

        int x = _positionX;
        int y = _positionY;

        computeShadowPosition((DISTANCE) + 1.0);
        g.drawImage(_shadow,
                x + _distanceX, y + _distanceY,
                width, height, null);

        width = _image.getWidth();
        height = _image.getHeight();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

        g.drawImage(_image, x, y, width, height, null);

        g2.setComposite(composite);
    }

    private static int sideOf(Point p, Rectangle rect) {
        if (rect.contains(p)) {
            if (p.x <= rect.x + rect.width / 2) {
                return SwingConstants.WEST;
            }
            else if (p.x > rect.x + rect.width / 2) {
                return SwingConstants.EAST;
            }
        }
        return -1;
    }

    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        Object source = evt.getSource();
//        if (DecisionFieldBox.PROPERTY_ASCENDING.equals(evt.getPropertyName())) {
//
//        }
    }
}