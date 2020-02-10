package com.tibco.cep.kernel.helper;

import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 11, 2006
 * Time: 7:40:54 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class HiResTimerTask {
    static final byte VIRGIN      = 0;
    static final byte SCHEDULED   = 1;
    static final byte EXECUTED    = 2;
    static final byte CANCELLED   = 3;

    protected byte state = VIRGIN;

    protected long nextExecutionTime;

    public boolean isVirgin() {
        return state == VIRGIN;
    }

    public boolean cancel() {
        boolean result = (state == SCHEDULED);
        state = CANCELLED;
        return result;
    }

    abstract long scheduledExecutionTime();

    abstract public void execute(WorkingMemory workingMemory);
}
