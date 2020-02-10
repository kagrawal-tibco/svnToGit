package com.tibco.cep.bpmn.runtime.activity;

/*
* Author: Suresh Subramani / Date: 2/12/12 / Time: 5:34 PM
*/
public enum TriggerType {
    NONE,
    MESSAGE,
    TIMER,
    CONDITIONAL,
    ESCALATION,
    ERROR,
    COMPENSATION,
    SIGNAL,
    MULTIPLE,
    PARALLEL_MULTIPLE
}
