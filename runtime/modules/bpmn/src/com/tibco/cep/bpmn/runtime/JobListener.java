package com.tibco.cep.bpmn.runtime;

/*
* Author: Suresh Subramani / Date: 7/19/12 / Time: 10:33 AM
*/
public interface JobListener
{
    void onAssertEntity(JobEvent event);
    void onModifyEntity(JobEvent event);
    void onRetractEntity(JobEvent event);

}
