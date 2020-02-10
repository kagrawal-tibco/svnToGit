package com.tibco.cep.bpmn.runtime;

import com.tibco.cep.kernel.model.entity.Entity;

/*
* Author: Suresh Subramani / Date: 7/19/12 / Time: 10:35 AM
*/
public interface JobEvent {

    public enum EventType{
        NEW,
        MODIFY,
        RETRACT,
    }

    /**
     * Return the entity on which the event was generated. For Modify, it is the modified entity.
     * @return
     */
    Entity getEntity();

    /**
     * Return the type of event.
     * @return
     */
    EventType getEventType();

    /**
     * Typically will return the taskURI from which this event was generated
     * @return
     */
    String getSource();

    long getTimestamp();



}
