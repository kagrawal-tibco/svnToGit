package com.tibco.cep.bpmn.runtime.agent;

/*
* Author: Suresh Subramani / Date: 2/11/12 / Time: 8:54 PM
*/
public enum ProcessStatus {
    RUNNING(0),
    WAIT(10),
    WAITJOIN(11),
    WAITEVENT(12),
    WAITCALLACTIVITY(13),
    WAITSUBPROCESS(14),
    WAITCOMPLETE(15),
    COMPLETE(20),
    CANCEL(30),
    SUSPENDED(40),
    ERROR(50),
    TIMEOUT(60),
    COMPENSATED(70);

    private int statusId;
    ProcessStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public static ProcessStatus valueOf(int statusId) {
        for (ProcessStatus status : ProcessStatus.values()) {
            if (status.statusId == statusId) return status;
        }
        throw new RuntimeException(String.format("Invalid status :%d specified", statusId));
    }

}
