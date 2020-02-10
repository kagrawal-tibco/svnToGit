package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 6, 2006
 * Time: 6:21:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEventHandle extends BaseHandle {

    private static final byte TIMER_FIRED_            = 0x01;      //tell whether the timer is fired
    private static final byte TIMER_CANCELLED_        = 0x02;      //tell whether the expiry timer is canceled

    AbstractEventHandle nextHandle;
    private long eventExpiryTime;
    protected byte  timerStatus = 0;


    protected AbstractEventHandle(AbstractEventHandle _next, TypeInfo _typeInfo) {
        super(_typeInfo);
        nextHandle = _next;
    }

    public boolean isTimerCancelled() {
        return (timerStatus & TIMER_CANCELLED_) != 0;
    }

    public void cancelTimer() {
        timerStatus |= TIMER_CANCELLED_;
    }

    public void timerFired() {
        timerStatus |= TIMER_FIRED_;
    }

    public boolean isTimerFired() {
        return  (timerStatus & TIMER_FIRED_) != 0;
    }

//    public AbstractEventHandle nextHandle() {
//        return nextHandle;
//    }
//
//    public void setNextHandle(AbstractEventHandle _next) {
//        nextHandle = _next;
//    }

    public void setExpiryTime(long _expiryTime) {
        eventExpiryTime = _expiryTime;
    }

    public long getExpiryTime() {
        return eventExpiryTime;
    }

    abstract public long getEventId();

    abstract public void removeRef();
}
