package com.tibco.cep.kernel.helper;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 27, 2006
 * Time: 9:03:22 PM
 * To change this template use File | Settings | File Templates.
 */

abstract public class TimerTaskRepeat extends HiResTimerTask {

    protected long period = 0;
    
    public long scheduledExecutionTime() {
        return (period < 0 ? nextExecutionTime + period : nextExecutionTime - period);
    }
}
