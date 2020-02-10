package com.tibco.cep.kernel.helper;



abstract public class TimerTaskOnce extends HiResTimerTask {

    public long scheduledExecutionTime() {
        return nextExecutionTime;
    }
}
