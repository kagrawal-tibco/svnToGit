package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface SoftDragNDropable {

    /**
     * Should return true if there's a reasonable thing to drag from here...
     */
    public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt);
    public Rectangle getDragHintBounds();
    public void dragStopped();

    public boolean dragOver(SoftDragNDropManager manager, Point mouseAt, Object dragObject);
    public Rectangle getDropHintBounds();
    public void dropStopped();
    public boolean drop(SoftDragNDropManager manager, Point mouseAt, Object dropObject);

    /**
     * Draw, in the coordinates currently set on the graphics, the drag'n'drop indicator.
     */
    public void drawDraggingIndicator(Graphics g, SoftDragNDropManager manager, Point mouseAt, Object dragObject);

    /**
     * Return null if there is no indicator.
     */
    public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject);
}
