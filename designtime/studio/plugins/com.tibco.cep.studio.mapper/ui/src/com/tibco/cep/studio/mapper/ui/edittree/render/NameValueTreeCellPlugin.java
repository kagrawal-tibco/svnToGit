package com.tibco.cep.studio.mapper.ui.edittree.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * Customizes the {@link NameValueTreeCellRenderer} and {@link NameValueTreeCellEditor}.<br>
 * TODO -- need to augment to fully cover data editing.
 */
public interface NameValueTreeCellPlugin {
   /**
    * Gets the background color for the entire tree row (not just the renderer plugin area).
    *
    * @param node The node.
    * @return Tbe background color, or null for a default.
    */
   Color getBackgroundColor(Object node);

   /**
    * Gets an icon for painting the name.
    *
    * @param node The node.
    * @return The icon, the width <b>must</b> indicate how much space it will use.  To measure width, a large number
    *         is passed into availableWidth, then width is called.
    */
   TextRenderer getNameRenderer(Object node, boolean selected, boolean focus);

   /**
    * Gets the tool-tip associated with the 'name' area of this node, or null if none.
    *
    * @param node The node
    * @return The tooltip, or null if none.
    */
   String getNameToolTip(Object node);

   /**
    * Gets a renderer to paint a node.
    *
    * @param node
    * @return
    */
   TextRenderer getDataRenderer(Object node);

   /**
    * Indicates if this data line requires multiple lines.<br>
    * Used, for example, to see if an 'overshow' popup is required.
    *
    * @param node
    * @return
    */
   boolean isMultiLineData(Object node);

   /**
    * Differs from {@link #getDataRenderer} in that the overshow data can span multiple lines; so painting is done
    * differently.<br>
    * The background color will have already been filled in, and the context set up to start painting at 0,0.
    *
    * @param node      The node
    * @param g
    * @param size      The size allotted.
    * @param rowHeight The height of a row in the tree.
    */
   void paintOvershowData(Object node, Graphics g, Dimension size, int rowHeight);

   Dimension getDataOvershowSize(Object node, Graphics g, int availableWidth, int rowHeight);

   /**
    * Gets the tool-tip associated with the 'data' area of this node, or null if none.
    *
    * @param node The node
    * @return The tooltip, or null if none.
    */
   String getDataToolTip(Object node, Point dataRelativePoint);

   /**
    * Gets the background color for the data area.
    *
    * @param node The node.
    * @return Tbe background color, or null for a default.
    */
   Color getDataBackgroundColor(Object node);

   /**
    * Gets the fixed width of the icon area in the tree.<br>
    * This width is applied to all rows regardless of the actual icon size in {@link #getIcon}.
    *
    * @return The width, or 0 for none.
    */
   int getIconWidth();

   /**
    * Gets the icon to render for this node.
    *
    * @param node The node from {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    * @return The icon, or null for none.
    */
   Icon getIcon(Object node);

   /**
    * Indicates if the data is
    *
    * @param node The node from the {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    */
   boolean isDataEditable(Object node);

   boolean isNameEditable(Object node);

   /**
    * Gets the text area for editing the name field.
    *
    * @param node The node from the {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    * @return The editor.
    */
   JExtendedEditTextArea getNameEditor(Object node);

   /**
    * Gets the text area for editing the data field.
    *
    * @param node The node from the {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    * @return The editor.
    */
   JExtendedEditTextArea getDataEditor(Object node);

   /**
    * Called after editing is complete for a node, the implementation should set the name in the node.
    *
    * @param node The node from the {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    * @param edit The editor earlier returned by {@link #getNameEditor}.
    */
   void nameEditingFinished(Object node, JExtendedEditTextArea edit);

   /**
    * Called after editing is complete for a node, the implementation should set the data in the node.
    *
    * @param node The node from the {@link com.tibco.cep.studio.mapper.ui.edittree.EditableTreeModel}.
    * @param edit The editor earlier returned by {@link #getDataEditor}.
    */
   void dataEditingFinished(Object node, JExtendedEditTextArea edit);
}
