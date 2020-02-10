package com.tibco.cep.bpmn.runtime.agent;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.kernel.model.entity.Event;


/*
* Author: Suresh Subramani / Date: 7/26/12 / Time: 10:23 AM
*/
public interface StarterJob {
    void assertEvent(Task task, Event event);
}
