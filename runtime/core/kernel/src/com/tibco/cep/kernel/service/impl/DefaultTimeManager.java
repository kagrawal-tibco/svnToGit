package com.tibco.cep.kernel.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.helper.EventTimer;
import com.tibco.cep.kernel.helper.HiResTimer;
import com.tibco.cep.kernel.helper.HiResTimerTask;
import com.tibco.cep.kernel.helper.TimerTaskOnce;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 6, 2006
 * Time: 4:13:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTimeManager extends BaseTimeManager {
    final static private int MAX_LOCK_TIME = 150;

    protected EventTimer m_expiryTimer;
    protected HiResTimer m_hiResTimer;

    final protected Action m_timerContext;

    final protected LinkedList pendingExpiryScheduleList;
    final protected LinkedList hiResScheduleList;

    protected boolean m_started;

    public DefaultTimeManager(Action timerContext) {
        super();
        pendingExpiryScheduleList = new LinkedList();
        hiResScheduleList = new LinkedList();
        m_timerContext = timerContext;
        m_started = false;
    }

    protected void init(WorkingMemoryImpl wm) {
        super.init(wm);
        m_expiryTimer = new EventTimer(workingMemory.getName());
        m_hiResTimer = new HiResTimer(workingMemory, MAX_LOCK_TIME);
    }

    public void start() {
        if (!m_started) {
            schedulePendingTimeEventTask();
            schedulePendingHiResTimerTask();
            m_expiryTimer.start(m_timerContext);
            m_hiResTimer.start(m_timerContext);
            m_started = true;
        }
    }

    public void stop() {
    }

    public void shutdown() {
        m_expiryTimer.cancel();
        m_hiResTimer.cancel();
        m_expiryTimer.shutdown();
        m_hiResTimer.shutdown();
    }

    public void reset() {
        pendingExpiryScheduleList.clear();
        hiResScheduleList.clear();
        m_expiryTimer.cancel();
        m_hiResTimer.cancel();
        m_expiryTimer = new EventTimer(workingMemory.getName());
        m_hiResTimer = new HiResTimer(workingMemory, MAX_LOCK_TIME);
        m_expiryTimer.start(null);
        m_hiResTimer.start(null);
        m_started = true;
    }

    public void resetWithContext() {
        pendingExpiryScheduleList.clear();
        hiResScheduleList.clear();
        if (m_expiryTimer != null) {
        	m_expiryTimer.cancel();
        	m_expiryTimer = new EventTimer(workingMemory.getName());
        	m_expiryTimer.start(m_timerContext);
        }
        if (m_hiResTimer != null) {
        	m_hiResTimer.cancel();
        	m_hiResTimer = new HiResTimer(workingMemory, MAX_LOCK_TIME);
        	m_hiResTimer.start(m_timerContext);
        }
        m_started = true;
    }

    public Event scheduleOnceOnlyEvent(Event event, long delay) {
        scheduleOnceOnlyEvent2(event, delay);
        return event;
    }
    
    public BaseHandle scheduleOnceOnlyEvent2(Event event, long delay) {
        BaseHandle handle = loadScheduleOnceOnlyEvent(event, delay);
        workingMemory.recordScheduled(handle);
//        if(!WorkingMemoryImpl.executingInside(workingMemory)) {
//            synchronized(workingMemory) {
//                workingMemory.recordScheduled(handle);
//            }
//        } else {
//            workingMemory.recordScheduled(handle);
//        }

        return handle;
    }

    public BaseHandle loadScheduleOnceOnlyEvent(Event event, long delay) {
        BaseHandle handle = createHandle(event);
        OnceOnlyEventTask task = new OnceOnlyEventTask(handle);
        // TODO: nleong - if delay specified in designed ruletimeevent is < 10 then
        // TODO: nelong - there are chances that the delay in this function may be negative
        scheduleOnceOnly(task, delay);

        return handle;
    }

    public void scheduleOnceOnly(TimerTaskOnce timerTask, long delay) {
        if (m_started) {
            m_hiResTimer.scheduleOnceOnly(timerTask, delay);
        } else {
            hiResScheduleList.add(new PendingTimerTask(timerTask, 0, delay, 0));
        }
    }

    public void scheduleRepeating(TimerTaskRepeat timerTask, long delay, long period) {
        if (m_started) {
            m_hiResTimer.scheduleRepeat(timerTask, delay, period);
        } else {
            hiResScheduleList.add(new PendingTimerTask(timerTask, 0, delay, period));
        }
    }

    public void scheduleEventExpiry(Handle handle, long expireTTL) {
        AbstractEventHandle evtHandle = (AbstractEventHandle) handle;
        if (evtHandle.isTimerCancelled()) return;
        if (m_started) {
            m_expiryTimer.schedule(evtHandle, expireTTL);
        } else {
            pendingExpiryScheduleList.addLast(new PendingExpiryTask(evtHandle, expireTTL));
        }
    }

    protected void schedulePendingTimeEventTask() {
        for (Iterator it = pendingExpiryScheduleList.iterator(); it.hasNext();) {
            PendingExpiryTask task = (PendingExpiryTask) it.next();
            if (task.handle.isTimerCancelled()) return;
            m_expiryTimer.schedule(task.handle, task.ttl);
        }
        pendingExpiryScheduleList.clear();
    }

    protected void schedulePendingHiResTimerTask() {
        workingMemory.getGuard().lock();
        try {
            for (Iterator it = hiResScheduleList.iterator(); it.hasNext();) {
                PendingTimerTask pendingTask = (PendingTimerTask) it.next();
                if (!pendingTask.task.isVirgin()) {
                    continue;
                }
                try {
                    if (pendingTask.specifiedTime != 0L) {
                        if (pendingTask.period != 0L) {
                            m_hiResTimer.scheduleRepeat((TimerTaskRepeat) pendingTask.task, new Date(pendingTask.specifiedTime), pendingTask.period); // scheduleRepeating(task, time, period)
                        } else {
                            m_hiResTimer.scheduleOnceOnly((TimerTaskOnce) pendingTask.task, new Date(pendingTask.specifiedTime)); // scheduleOnceOnly(task, time)
                        }
                    } else if (pendingTask.delay != 0L) {
                        if (pendingTask.period != 0L) {
                            m_hiResTimer.scheduleRepeat((TimerTaskRepeat) pendingTask.task, pendingTask.delay, pendingTask.period); // never called in our case.
                        } else {
                            m_hiResTimer.scheduleOnceOnly((TimerTaskOnce) pendingTask.task, pendingTask.delay); // schedule(task, delay, extId, ttl) OR scheduleOnceOnly(task, delay)
                        }
                    } else {
                        //both specifiedTime == 0 and  pendingTask.delay == 0
                        if (pendingTask.period != 0L)
                            m_hiResTimer.scheduleAtFixedRate((TimerTaskRepeat) pendingTask.task, 0L, pendingTask.period); // scheduleRepeating(class, period, task, tecount)
                        else
                            m_hiResTimer.scheduleOnceOnly((TimerTaskOnce) pendingTask.task, pendingTask.delay); // schedule(task, delay, extId, ttl) OR scheduleOnceOnly(task, delay)
                    }
                }
                catch (IllegalStateException ex) {
                    logger.log(Level.ERROR,"Error in TimeManager.schedulePendingHiResTimerTask()", ex);
                }
            }
        } finally {
            workingMemory.getGuard().unlock();
        }
        hiResScheduleList.clear();
    }

    public void registerEvent(Class eventClass) {
    }

    public void unregisterEvent(Class eventClass) {
    }

    static protected class PendingTimerTask {
        public HiResTimerTask task;
        long delay;
        long specifiedTime;
        long period;

        public PendingTimerTask(HiResTimerTask _task, long _specifiedTime, long _delay, long _period) {
            task = _task;
            delay = _delay;
            specifiedTime = _specifiedTime;
            period = _period;
        }
    }

    static protected class OnceOnlyEventTask extends TimerTaskOnce {
        protected BaseHandle eventHandle;

        public OnceOnlyEventTask(BaseHandle handle) {
            this.eventHandle = handle;
        }

        public void execute(WorkingMemory workingMemory) {
            try {
                if(eventHandle != null && !eventHandle.isRetracted()) {
                    assertEvent(workingMemory);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public boolean cancel() {
            boolean result = super.cancel();
            eventHandle = null;
            return result;
        }
        
        protected void assertEvent(WorkingMemory workingMemory) throws Exception {
        	workingMemory.assertObject(eventHandle, true);
        }
    }

    static protected class PendingExpiryTask {
        AbstractEventHandle handle;
        long ttl;

        public PendingExpiryTask(AbstractEventHandle handle, long ttl) {
            this.handle = handle;
            this.ttl = ttl;
        }

    }
}
