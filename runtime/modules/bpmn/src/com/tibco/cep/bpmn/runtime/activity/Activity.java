package com.tibco.cep.bpmn.runtime.activity;

import com.tibco.cep.bpmn.runtime.model.JobContext;

/*
* Author: Suresh Subramani / Date: 11/20/11 / Time: 6:33 PM
*/
public interface Activity {

    void init(InitContext context, Object... args) throws Exception ;

    /**
     * Return the fully qualified name of the Task
     * @return
     */
    String getName();

    /**
     * Return a fully qualified type name of the Task. This is defined in the ecore of the BPMN Model
     * @return
     */
    String getType();
    
    int getUniqueId();

    InitContext getInitContext();

    /**
     * gets the threadlocal locking context
     * @param job
     * @return
     */
    Object getLockContext(JobContext job);
    /**
     * Sets the threadlocal locking context
     * @param value
     */
    void setLockContext(Object value);
}
