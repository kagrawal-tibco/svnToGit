package com.tibco.cep.bpmn.runtime.activity;

/*
* Author: Suresh Subramani / Date: 2/12/12 / Time: 5:30 PM
*/

import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.model.entity.Event;

/**
 * Triggerable Interface are implemented by StartEvent, ReceiveTask,
 */
public interface Triggerable extends EventApplicable, Task {

    TriggerType getTriggerType();

    int getPriority();

    TriggerIdentifier[] getIdentifiers();

    boolean instantiatesProcess();

    boolean isIntermediate();

    boolean isInterrupting();

    String getJobKeyExpression();
    
    String getJobKeyFromEvent(Event event);

    boolean eval(Variables variables);




}
