package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Point;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 * Has the soft drag'n'drop ability into text.
 */
public interface TextDragNDropHandler  {
    /**
     * Computes the string to be inserted for the drop.
     * @param dropRange The range over which this drop will take place.  Computed in {@link #computeDropTextRange}.
     * @param dropItem The drag-n-drop item dropped.
     * @return The string to be inserted, or null if the drop shouldn't take place.
     */
    String computeDropString(TextRange dropRange, Object dropItem);

    /**
     * Computes the string to be inserted for the surround drop.<br>
     * This will only be called for drops where
     * @param dropRange The range over which this drop will take place.  Computed in {@link #computeSurroundDropTextRange}.
     * @param dropItem The drag-n-drop item dropped.
     * @return An array of exactly 2 strings, the 'before' string and the 'after' string.<br>
     * For example, a function like <code>myFunction( annotation )</code> might split like:
     * '<code>myFunction(</code>' and '<code>)</code>'.
     */
    String[] computeSurroundDropStrings(TextRange dropRange, Object dropItem);

    /**
     * Gets the range that would be replaced in the event of a drop at this location.
     * @param dragItem The drag-n-drop being dragged.
     * @param mouseAt The position of the mouse where the drag is taking place.
     * @return The text range to insert at, or null if none.
     */
    TextRange computeDropTextRange(Object dragItem, Point mouseAt);

    /**
     * Gets the range that would be surrounded in the event of a drop at this location.<br>
     * This is called before {@link #computeDropTextRange} --- first it sees if the drop can be
     * a 'surround' drop, if not, then it checks for a normal drop.<br>
     * Note that some implementations may choose to always return null here (or always null for certain
     * dropped items).<br>
     * It <b>is</b> appropriate to return non-null when dropping, say, a function signature around some existing text.
     * @param dragItem The drag-n-drop being dragged.
     * @param mouseAt The position of the mouse where the drag is taking place.
     * @return The text range to surround with, or null if none.
     */
    TextRange computeSurroundDropTextRange(Object dragItem, Point mouseAt);
}
