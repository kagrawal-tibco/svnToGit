/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.scheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.AbstractStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.service.cluster.agent.tasks.RemoveStateTimeoutAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.StateTimeoutAction;
import com.tibco.cep.runtime.service.cluster.om.CacheEventExHandle;
import com.tibco.cep.runtime.service.om.api.EntityDaoConfig;
import com.tibco.cep.runtime.service.time.BETimeManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.locks.LockManager.LockLevel;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 12, 2008
 * Time: 11:34:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgentTimeManager extends BETimeManager {
    ScheduledThreadPoolExecutor TIMER;
    Map<Runnable, Long> pendingTasks = new ConcurrentHashMap<Runnable, Long>();
    InferenceAgent cacheAgent;
    Cluster cluster;
    String schedulerKeyTimeEvents;
    Map<TimeEvent, Long> pendingSchedules = new ConcurrentHashMap<TimeEvent, Long>();
    
    private boolean LOCK_EXTID_WHILE_EVENT_EXP_ENABLED = false;
    private long LOCK_EXTID_WHILE_EVENT_EXP_TIMEOUT = -1L;

    public AgentTimeManager() {
        super(null, null);
    }

    public void init(InferenceAgent agent) {
        this.cacheAgent = agent;
        this.ruleSession = agent.getRuleSession();
        this.cluster = agent.getCluster();
        this.cluster.getRuleServiceProvider().getLogger(this.getClass()).log(Level.INFO,
                "Initialized AgentTimeManager for session: %s", this.ruleSession.getName());
        
        LOCK_EXTID_WHILE_EVENT_EXP_ENABLED = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getBoolean("be.engine.cluster.event.expiry.lock", false);
        LOCK_EXTID_WHILE_EVENT_EXP_TIMEOUT = ((BEProperties) cluster.getRuleServiceProvider().getProperties()).getLong("be.engine.cluster.event.expiry.lock.timeout", -1);
    }

    protected void init(WorkingMemoryImpl wm) {
        workingMemory = wm;
        logger = wm.getLogManager().getLogger(AgentTimeManager.class);
    }

    public BaseHandle loadScheduleOnceOnlyEvent(Event event, long delay) {
        AbstractEventHandle evtHandle = (AbstractEventHandle) createHandle(event);
        Runnable job = new Delegator(new AssertEventHandleTask(evtHandle));
        if (m_started) {
            TIMER.schedule(job, delay, TimeUnit.MILLISECONDS);
        } else {
            pendingTasks.put(job, delay);
        }
        return evtHandle;
    }

    public void loadScheduleSMTimeoutEvent(BaseHandle evtHandle, long delay) {
        Runnable job = new Delegator(new AssertSMTimeoutEventTask((AbstractEventHandle)evtHandle));
        if (m_started) {
            TIMER.schedule(job, delay, TimeUnit.MILLISECONDS);
        } else {
            pendingTasks.put(job, delay);
        }
    }

    public void reset() {
    	//AA this seems wrong but it preserves what was there before
        stop();
    }
    
    @Override
    public void resetWithContext() {
    	// TODO : Not sure if this is the correct way to reset within the tester, revisit after testing
        if (TIMER != null) {
            TIMER.shutdownNow();
            TIMER = new ScheduledThreadPoolExecutor(1);
            schedulePendingStaticTimeEvents();
            schedulePendingTasks();
        }
    }
    
    public void stop() {
        if (TIMER != null) {
            TIMER.shutdownNow();
            TIMER = null;
        }
        m_started = false;
    }

    public void shutdown() {
        if (TIMER != null) TIMER.shutdownNow();
    }

    public void start() {
        try {
            if (!m_started) {
                getRuleSession().getRuleServiceProvider().getLogger(this.getClass()).log(Level.INFO,
                        "Starting AgentTimeManager for session: %s", this.getRuleSession().getName());
                //startScheduler();
                TIMER = new ScheduledThreadPoolExecutor(1);
                schedulePendingStaticTimeEvents();
                schedulePendingTasks();
                //schedulePendingCacheSchedules();
                m_started = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void scheduleEventExpiry(Handle handle, long expireTTL) {
        AbstractEventHandle evtHandle = (AbstractEventHandle) handle;
        if (evtHandle.isTimerCancelled()) {
            return;
        }
        Runnable job = new Delegator(new ExpireEventTask(evtHandle));
        if (m_started) {
            TIMER.schedule(job, expireTTL < 10 ? 10 : expireTTL, TimeUnit.MILLISECONDS);
        } else {
            pendingTasks.put(job, expireTTL);
        }
    }

    public Event scheduleOnceOnlyEvent(Event event, long delay) {
        try {
//            EntityConfiguration entityConfig=cacheAgent.getAgentConfig().getEntityConfig(event.getClass());
//            if (entityConfig != null) {
//                if (entityConfig.isCacheEnabled()) {
//                    // schedule the event in the cache
//                    if (m_started) {
//                        scheduleTimeEventInCache((TimeEvent) event, delay);
//                    } else {
//                        pendingSchedules.put((TimeEvent)event, delay);
//                    }
//                }
//            } else {
            scheduleOnceOnlyEvent2(event, delay);
//            }
            return event;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public BaseHandle scheduleOnceOnlyEvent2(Event event, long delay) {
        AbstractEventHandle evtHandle = (AbstractEventHandle) loadScheduleOnceOnlyEvent(event, delay);
        doScheduleOnceOnlyEvent(evtHandle);
        return evtHandle;
    }

    private void doScheduleOnceOnlyEvent(BaseHandle evtHandle) {
    	if (!WorkingMemoryImpl.executingInside(workingMemory)) {
            workingMemory.getGuard().lock();
            try {
                workingMemory.recordScheduled(evtHandle);
            } finally {
                workingMemory.getGuard().unlock();
            }
        } else {
            workingMemory.recordScheduled(evtHandle);
        }
    }

    
    protected void schedulePendingTasks() {
        Iterator allPendingTasks = pendingTasks.entrySet().iterator();
        while (allPendingTasks.hasNext()) {
            Map.Entry<Runnable, Long> entry = (Map.Entry) allPendingTasks.next();
            Runnable job = entry.getKey();
            long delay = entry.getValue();
            TIMER.schedule(job, delay < 10 ? 10 : delay, TimeUnit.MILLISECONDS);
        }
        pendingTasks.clear();
    }



    public void scheduleRepeating(TimerTaskRepeat timerTask, long delay, long period) {
        super.scheduleRepeating(timerTask, delay, period);
    }

    protected void schedulePendingStaticTimeEvents() {
        Iterator ite = m_eventClasses.iterator();
        while (ite.hasNext()) {
            Class eventClass = (Class) ite.next();
            scheduleStaticTimeEvent(eventClass);
        }
    }

    public void unregisterEvent(Class eventClass) {
        Runnable task = (Runnable) m_eventClassToTimeTask.remove(eventClass);
        if (TIMER != null) { // fix for CR:1-A34PER
            TIMER.remove(task);
        }
        m_eventClasses.remove(eventClass);
    }



    protected void scheduleStaticTimeEvent(Class eventClass) {
        try {
            long id = getRuleSession().getRuleServiceProvider().getIdGenerator().nextEntityId(eventClass);
            Constructor cons = eventClass.getConstructor(new Class[]{long.class});
            TimeEvent timeEvent = (TimeEvent) cons.newInstance(new Object[]{new Long(id)});
            if (timeEvent.isRepeating()) {
                int defcount = (Integer) eventClass.getField("TIME_EVENT_COUNT").get(null); //TODO: - remove reflection call
                int tecount = getOverridingCount(eventClass, defcount);
                Runnable task = new Delegator(new RepeatAssertEventTask(timeEvent, tecount));
                m_eventClassToTimeTask.put(eventClass, task);
                long interval = timeEvent.getInterval();
                if (interval <= 0) {
                    logger.log(Level.WARN, "Schedule interval for %s can't be %s. Defaulting interval to 60 seconds.", 
                            eventClass.getName(), interval);
                    interval = 60000;
                }
                TIMER.scheduleAtFixedRate(task, 0L, interval, TimeUnit.MILLISECONDS);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void scheduleStateExpiry(PropertyStateMachineState state, long timeout) {
        RuleSession rs = getRuleSession();
        Concept machine = state.getParent();
        if (isSMLocalMode(machine)) {
            scheduleStateExpiryLocal(state, timeout);
        } else {
        	if (AgentActionManager.hasActionManager(rs)) {
        		AgentActionManager.addAction(rs, new StateTimeoutAction(machine.getId(), state.getName(), state.getCount(), System.currentTimeMillis()+timeout));
	        } else {
	            try {
	                StateTimeoutAction.actionImpl(cacheAgent, machine.getId(), state.getName(), state.getCount(), System.currentTimeMillis()+timeout);
	            } catch (RuntimeException re) {
	                throw re;
	            } catch (Exception ex) {
	                throw new RuntimeException(ex);
	            }
	        }
        }
    }
    
    protected void scheduleSMTimeoutEvent(BaseHandle eventHandle, long delay) {
        loadScheduleSMTimeoutEvent(eventHandle, delay);
        doScheduleOnceOnlyEvent(eventHandle);
    }

    //don't need to remove from the scheduler because the scheduler already removes expired schedules automatically
    //however local map needs to be kept in sync by call to stateTimeoutEventExpiredLocal
    public void cleanupExpiredStateTimeoutEvent(AbstractStateTimeoutEvent timeoutEvent) {
        if (isSTELocalMode(timeoutEvent)) {
            cleanupExpiredStateTimeoutEventLocal(timeoutEvent);
        }        
    }

    //don't think this will ever get called here
    public void recordRecoveredStateTimeoutEvent(Handle steHandle) {
        StateMachineConceptImpl.StateTimeoutEvent ste = (StateMachineConceptImpl.StateTimeoutEvent)steHandle.getObject();
        if (ste != null) {
            if (isSTELocalMode(ste)) {
                recordRecoveredStateTimeoutEventLocal(steHandle);
            }
        }
    }
    
    private boolean isSTELocalMode(AbstractStateTimeoutEvent ste) {
    	return ste != null && cluster.getMetadataCache().getEntityDaoConfig(ste.getClass())
                .getCacheMode() == EntityDaoConfig.CacheMode.Memory;
    }
    
    private boolean isSMLocalMode(Concept machine) {
    	return machine != null && cluster.getMetadataCache().getEntityDaoConfig(machine.getClass())
                .getCacheMode() == EntityDaoConfig.CacheMode.Memory;
    }
    
    public void cancelStateExpiry(PropertyStateMachineState state) {
        RuleSession rs = getRuleSession();
        Concept machine = state.getParent();
        if (isSMLocalMode(machine)) {
            cancelStateExpiryLocal(state);
        } else {
            AgentActionManager.addAction(rs, new RemoveStateTimeoutAction(machine.getId(), state.getName(), state.getCount()));
        }
    }
    
    protected boolean checkSteRecordRetractConfig() {
		return true;
	}

    class ExpireEventTask implements Runnable {
        AbstractEventHandle handle;

        ExpireEventTask(AbstractEventHandle handle) {
            this.handle = handle;
        }

        public void run() {
            boolean extIdLocked = false;
            String extId = null;
            try {
            	if (LOCK_EXTID_WHILE_EVENT_EXP_ENABLED && handle instanceof CacheEventExHandle) {
    				extId = ((CacheEventExHandle) handle).getExtId();
            		if (extId != null) {
            			try {
            				logger.log(Level.TRACE, "Acquiring lock for event expiry '" + extId + "'");
            				extIdLocked = cacheAgent.getCluster().getLockManager().lock(extId, LOCK_EXTID_WHILE_EVENT_EXP_TIMEOUT, LockLevel.LEVEL2);
            			} catch(Exception lock_exp) {
            				logger.log(Level.WARN, "Error while acquiring lock for event expiry '" + extId + "' - " + lock_exp.getMessage());
            			}
            		}
            	}
            	
            	((RuleSessionImpl) getRuleSession()).expireEvent(handle);
            	
            } catch(Exception e) {
            	if (extIdLocked && extId != null) {
            		try {
    					cacheAgent.getCluster().getLockManager().unlock(extId);//Release back the lock only in case of exception otherwise postrtc would unlock. 
    				} catch(Exception unlock_exp) {
    					logger.log(Level.WARN, "Error while releasing lock of event expiry '" + extId + "' - " + unlock_exp.getMessage());
    				}
            	}
            }
        }
    }
    
    class AssertEventHandleTask implements Runnable {
        AbstractEventHandle handle;

        AssertEventHandleTask(AbstractEventHandle handle) {
            this.handle = handle;
        }

        public void run() {
            try {
                if (m_timerContext != null) {
                    m_timerContext.execute(null);
                }

                if (!handle.isTimerCancelled() && !handle.isRetracted()) {
                    //System.err.println("Asserting " + handle);
                    final TimeEvent te = (TimeEvent) handle.getObject();
                    if (te != null) {
                        final RuleSessionImpl sess = ((RuleSessionImpl) getRuleSession());
                        if (te instanceof VariableTTLTimeEventImpl) {
                            sess.invokeFunction(new RuleFunction() {

								@Override
								public Object invoke(Object[] objects) {
									//create a new time event with isFired()==true
		                            VariableTTLTimeEventImpl newTe = ((VariableTTLTimeEventImpl) te).cloneTimeEvent(sess.getRuleServiceProvider().getIdGenerator().nextEntityId(te.getClass()));
		                            newTe.fire();
		                            //delete the old time event with isFired()==false
									sess.retractObject(te, false);
									try {
										sess.assertObject(newTe, true);
									} catch (DuplicateExtIdException dupex) {
										throw new RuntimeException(dupex);
									}
									return null;
								}

								@Override
								public Object invoke(Map maps) {
									return invoke((Object[])null);
								}

								@Override
								public String getSignature() {
									return "AgentTimeManager: Internal Assert Time Event Function";
								}

								@Override
								public ParameterDescriptor[] getParameterDescriptors() {
									return new ParameterDescriptor[]{};
								}
                            	
                            }, null, false);
                        } else {
                            te.fire();
                            sess.assertObject(handle, true);
                        }
                    }
                } else {
                    //System.err.println("Not Asserting " + handle);
                }
            } catch (RuntimeException re) {
            	throw re;
            } catch (Exception ex) {
            	throw new RuntimeException(ex);
            }
        }
    }
    
    class RepeatAssertEventTask implements Runnable {
        TimeEvent timeEvent;
        int numPerInterval;


        RepeatAssertEventTask(TimeEvent timeEvent, int numPerInterval) {
            this.timeEvent = timeEvent;
            this.numPerInterval = numPerInterval;
        }

        public void run() {
            timeEvent.setScheduledTime(System.currentTimeMillis());
            ((RuleSessionImpl) getRuleSession()).fireRepeatEvent(timeEvent, numPerInterval);
        }
    }
    
    class AssertSMTimeoutEventTask extends AssertEventHandleTask {
    	
		AssertSMTimeoutEventTask(AbstractEventHandle handle) {
			super(handle);
		}
    	
		protected void assertEvent(RuleSessionImpl sess) throws Exception {
			AbstractStateTimeoutEvent ste = (AbstractStateTimeoutEvent) handle.getObject();
			if(ste != null) {
	    		//cleanup immediately before checking for errors since memory must be released for local time events
	    		//cache events are still null here but don't need to be cleaned up
	    		cleanupExpiredStateTimeoutEvent(ste);
				com.tibco.cep.runtime.util.AssertSMTimeoutEventTask.processTask(ruleSession, ste);
			}
		}
    }

    class Delegator implements Runnable {
        Runnable realJob;

        Delegator(Runnable realJob) {
            this.realJob = realJob;
        }

        public void run() {
            try {
                getRuleSession().getTaskController().processTask(TaskController.SYSTEM_DEFAULT_DISPATCHER_NAME, realJob);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
