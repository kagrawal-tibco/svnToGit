package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTree;

/**
 * Encapsulates style characteristics used by BasicTree
 * in rendering tree cells.
 */
public interface TreeCellStyle {
   /**
    * Called to set the tree cell that is about to be rendered.
    * Note that it is safe to assume this function will be called
    * for each cell before any of the "get" functions are called
    * e.g. getNormalFont
    *
    * @param tree     the JTree that is being rendered
    * @param value    the model object associated with this cell
    * @param sel      whether this cell is selected
    * @param expanded whether this cell is expanded
    * @param leaf     whether this cell is a leaf node
    * @param row      the row number for this cell
    * @param hasFocus whether this cell has focus
    */
   void initCell(JTree tree, Object value, boolean sel, boolean expanded,
                 boolean leaf, int row, boolean hasFocus);

   /**
    * Returns the font to be used for the cell label.
    *
    * @return the font to be used for the cell label.
    */
   Font getNormalFont();

   /**
    * Returns the color to be used for the cell label when it
    * is not the current selection
    *
    * @return the color to be used for the cell label when it
    *         is not the current selection
    */
   Color getTextNonSelectionColor();
}
