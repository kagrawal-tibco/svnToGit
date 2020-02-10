package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Point;

import javax.swing.tree.TreeCellRenderer;

/**
 * A mixin to {@link TreeCellRenderer} which adds tooltips and background color.
 */
public interface ExtendedTreeCellRenderer {
   /**
    * Indicates if this renderer is somehow capturing mouse movement currently.
    */
   public boolean isCapturingMouse();

   public Color getBackgroundColor(Object node);

   /**
    * Gets the tool-tip for the node at the given position.
    *
    * @param node             The node
    * @param rendererRelative The renderer relative position.
    * @return The tip, or null for none.
    */
   public String getToolTipText(Object node, Point rendererRelative);
}
