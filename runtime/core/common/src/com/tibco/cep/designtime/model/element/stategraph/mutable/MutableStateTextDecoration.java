/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 8:01:39 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;

import com.tibco.cep.designtime.model.element.stategraph.StateTextDecoration;


public interface MutableStateTextDecoration extends StateTextDecoration, MutableStateDecoration {


    /**
     * Set the text to be displayed on this decoration.
     *
     * @param text The new text to display on this decoration.
     */
    public void setText(
            String text);
}// end interface StateTextDecoration
