package com.tibco.cep.bpmn.runtime.activity;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 4:56 PM
*/
public interface TaskResult {

    enum Status {
        NULL,
        OK,
        CANCELTASK,
        CANCELJOB,
        ERROREXCEPTION,
        ESCALTIONEXCEPTION,
        WAITFORJOB,  //Wait for the Job to Arrive to the task.
        WAITFOREVENT, //Wait for the Event to be receieved on the task,
        WAITFORJOIN, //Waiting to be joined with other process, typically due to FORK
        ASYNCEXEC,
        TIMEOUT,
        FORK,
        EXCLUSIVE,
        COMPLEX,
        SUBPROCESS_OR_CALLACTIVITY_COMPLETE,
        JOINWITHPARENT,
        COMPLETE,
        CALLACTIVITY,
        SUBPROCESS,
        MERGECOMPLETE,
        STARTEVENT,
        LOOP_WAIT,
        LOOP_CONTINUE;



    }

    TaskResult EMPTY = new TaskResult() {
        @Override
        public Status getStatus() {
            return Status.NULL;
        }

        @Override
        public Object getResult() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    Status getStatus();

    Object getResult();

}
