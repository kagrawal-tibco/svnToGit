/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 8:08:27 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.State;


public interface MutableState extends State, MutableStateVertex {


    public void setTimeoutPolicy(int flag);


    public void enableInternalTransition(boolean enable);


    /**
     * Delete the internal transition on this state (if any).
     */
    public void deleteInternalTransition() throws ModelException;


    public void setTimeoutState(State state);
}// end interface State
