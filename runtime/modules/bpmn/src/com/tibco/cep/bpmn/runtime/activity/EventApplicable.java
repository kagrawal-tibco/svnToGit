package com.tibco.cep.bpmn.runtime.activity;

import com.tibco.cep.designtime.model.event.Event;

/*
* Author: Suresh Subramani / Date: 2/13/12 / Time: 7:13 PM
*/
public interface EventApplicable {
    /**
     * Get the input event resource associated to this task.
     * @return
     */
    Event getInputEvent();

    /**
     * Get the output event resource associate to this task.
     * @return
     */
    Event getOutputEvent();

}
