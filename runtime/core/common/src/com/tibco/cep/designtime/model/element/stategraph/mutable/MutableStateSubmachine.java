/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 29, 2004
 * Time: 7:50:00 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;

import com.tibco.cep.designtime.model.element.stategraph.StateSubMachine;


/**
 * This interface is used to represent a "sub-machine" (a link to another state machine).
 */
public interface MutableStateSubmachine extends StateSubMachine,MutableStateComposite {





    /**
     * Set the URI of this submachine.
     *
     * @param URI The new URI of this submachine.
     */
    public void setSubmachineURI(
            String URI);


    


    public void setCallsExplicitly(boolean explicit);


    


    public void setPreserveForwardCorrelate(boolean preserve);

}// end interface StateSubmachine
