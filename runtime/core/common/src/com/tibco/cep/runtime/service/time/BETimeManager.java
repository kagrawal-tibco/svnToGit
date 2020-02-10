package com.tibco.cep.runtime.service.time;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.helper.HiResTimerTask;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.service.impl.DefaultTimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.AbstractStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.LocalStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionConfigImpl;
import com.tibco.cep.runtime.util.AssertSMTimeoutEventTask;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 14, 2006
 * Time: 2:51:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class BETimeManager extends DefaultTimeManager {
    protected RuleSession ruleSession;

    protected HashSet m_eventClasses;
    protected HashMap m_eventClassToTimeTask;
    
    //this is only marginally safe
    //you could delete and then add, but the users locking is supposed to prevent that
    private ConcurrentHashMap<StateTimeoutKey, Handle> stateTimeoutMap;


    public BETimeManager(RuleSession session, Action timerContext) {
        super(timerContext);
        ruleSession = session;
        m_eventClassToTimeTask = new HashMap();
        m_eventClasses = new HashSet();
        if(checkSteRecordRetractConfig()) stateTimeoutMap = new ConcurrentHashMap();
    }

    public RuleSession getRuleSession() {
        return ruleSession;
    }

    public void registerEvent(Class eventClass) {
        //only care if it is repeating timeevent
        if(!TimeEvent.class.isAssignableFrom(eventClass)) return;
        //TimeEventImpl is abstract but can be in a rule scope
        //which would cause it to be passed into this method
        if((eventClass.getModifiers() & Modifier.ABSTRACT) != 0) return;

        if(!m_eventClasses.contains(eventClass)) {
            m_eventClasses.add(eventClass);
            if(m_started)
                scheduleStaticTimeEvent(eventClass);
        }
    }

    public void unregisterEvent(Class eventClass) {
        HiResTimerTask task = (HiResTimerTask) m_eventClassToTimeTask.remove(eventClass);
        if (task != null)
            task.cancel();
        m_eventClasses.remove(eventClass);
    }

    public void start() {
        if (!m_started) {
            schedulePendingTimeEventTask();
            schedulePendingHiResTimerTask();
            schedulePendingStaticTimeEvents();
            m_expiryTimer.start(m_timerContext);
            m_hiResTimer.start(m_timerContext);
            m_started = true;
        }
    }

    protected void schedulePendingStaticTimeEvents() {
        Iterator ite = m_eventClasses.iterator();
        while(ite.hasNext()) {
            Class eventClass = (Class) ite.next();
            scheduleStaticTimeEvent(eventClass);
        }
    }

    protected void scheduleStaticTimeEvent(Class eventClass) {
        try {
            long id =  ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(eventClass);
            Constructor cons = eventClass.getConstructor(new Class[]{long.class});
            TimeEvent timeEvent = (TimeEvent) cons.newInstance(new Object[]{new Long(id)});
            if (timeEvent.isRepeating()) {
                int defcount = (Integer) eventClass.getField("TIME_EVENT_COUNT").get(null);       //todo - remove reflection call
                int tecount = getOverridingCount(eventClass, defcount);
                RepeatTimeEventTask task = new RepeatTimeEventTask(timeEvent, tecount, timeEvent.getInterval());
                m_eventClassToTimeTask.put(eventClass, task);
                m_hiResTimer.scheduleAtFixedRate(task, 0L, timeEvent.getInterval());
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

    protected int getOverridingCount(Class timerClass, int defcount) {

        String propertyName = timerClass.getName().substring(7);
        String s = System.getProperty(propertyName + ".numeventpertimer");
        if (s != null) {
            //System.out.println("find property -" + propertyName + " with value : " + s);
            return Integer.parseInt(s);
        }
        //System.out.println("could not find property -" + s);

       return defcount;
    }
    
    public void scheduleStateExpiry(PropertyStateMachineState state, long timeout) {
        scheduleStateExpiryLocal(state, timeout);
    }
    
    protected void scheduleStateExpiryLocal(PropertyStateMachineState state, long timeout) {
        long id= ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(LocalStateTimeoutEvent.class);
        Concept machine = state.getParent();
        LocalStateTimeoutEvent timeEvent = new LocalStateTimeoutEvent(id, machine.getId(), state.getName(), 
        		System.currentTimeMillis() + timeout, state.getCount());
        
        BaseHandle eventHandle = createHandle(timeEvent);
        //add handle to map before scheduling in so that there is no chance the event will expire
        //before being added to the map and therefore linger forever because it was not removed by
        //expiry action
        if(checkSteRecordRetractConfig()) {
            stateTimeoutMap.put(new StateTimeoutKey(state), eventHandle);
            Logger logger = ruleSession.getRuleServiceProvider().getLogger(BETimeManager.class);
            logger.log(Level.DEBUG, "added state timeout to map with id %d with key %d,%d for %s:%s count %d", 
            		timeEvent.getId(), machine.getId(), state.getPropertyIndex(), state.getStateMachineName(), state.getName(), state.getCount());
        }
        
        scheduleSMTimeoutEvent(eventHandle, timeout);
    }
    
    protected void scheduleSMTimeoutEvent(BaseHandle handle, long delay) {
    	SMTimeoutEventTask task = new SMTimeoutEventTask(handle);
    	scheduleOnceOnly(task, delay);
    	workingMemory.recordScheduled(handle);
    }
    
    public void recordRecoveredStateTimeoutEvent(Handle steHandle) {
        recordRecoveredStateTimeoutEventLocal(steHandle);
    }

    protected void recordRecoveredStateTimeoutEventLocal(Handle steHandle) {
        if(checkSteRecordRetractConfig()) {
            AbstractStateTimeoutEvent ste = (AbstractStateTimeoutEvent)steHandle.getObject();
            if(ste != null) {
                StateMachineConceptImpl sm = (StateMachineConceptImpl)ruleSession.getObjectManager().getElement(ste.sm_id);
                if(sm != null) {
                    Property stateProp = sm.getProperty(ste.property_name);
                    if(stateProp != null && stateProp instanceof PropertyStateMachineState) {
                        stateTimeoutMap.put(new StateTimeoutKey(ste), steHandle);
                    } else {
                    	Logger logger = ruleSession.getRuleServiceProvider().getLogger(BETimeManager.class);
                    	logger.log(Level.DEBUG, "got null or invalid type for property %s in state machine with id %d for state timeout event with id %d"
                    			, ste.property_name, ste.sm_id, ste.getId());
                    }
                }
            }
        }
    }
    
    public void cleanupExpiredStateTimeoutEvent(AbstractStateTimeoutEvent timeoutEvent) {
        cleanupExpiredStateTimeoutEventLocal(timeoutEvent);
    }
    
    protected void cleanupExpiredStateTimeoutEventLocal(AbstractStateTimeoutEvent timeoutEvent) {
        if(checkSteRecordRetractConfig()) {
            stateTimeoutMap.remove(new StateTimeoutKey(timeoutEvent));
            Logger logger = ruleSession.getRuleServiceProvider().getLogger(BETimeManager.class);
            logger.log(Level.DEBUG, "expiry removed state timeout event for sm_id %d state property name %s event count %d", timeoutEvent.sm_id, timeoutEvent.property_name, timeoutEvent.getCount());
        }
    }

    public void cancelStateExpiry(PropertyStateMachineState state) {
        cancelStateExpiryLocal(state);
    }
    
    protected void cancelStateExpiryLocal(PropertyStateMachineState state) {
        if(checkSteRecordRetractConfig()) {
        	Logger logger = ruleSession.getRuleServiceProvider().getLogger(BETimeManager.class);
            Handle handle = stateTimeoutMap.remove(new StateTimeoutKey(state));
            if(handle != null) {
                ruleSession.retractObject(handle, true);

                if(logger.isEnabledFor(Level.DEBUG)) {
                	AbstractStateTimeoutEvent ste = (AbstractStateTimeoutEvent)handle.getObject();
                	logger.log(Level.DEBUG, "retracted state timeout event id %s for %s:%s count %d", ste != null ? ste.getId() : null, state.getStateMachineName(), state.getName(), state.getCount());
                }
            } else {
            	if(logger.isEnabledFor(Level.DEBUG)) {
            		logger.log(Level.DEBUG, "state timeout event with key %d,%d not found for %s:%s count %d", state.getParent().getId(), state.getPropertyIndex(), state.getStateMachineName(), state.getName(), state.getCount());
            	}
            }
        }
    }
    
    protected boolean checkSteRecordRetractConfig() {
        return ((RuleSessionConfigImpl)ruleSession.getConfig()).deleteStateTimeoutOnStateExit();
    }

    
    static private class StateTimeoutKey {
        private final long parent_id;
        private final String prop_name;
        private final int count;

        public StateTimeoutKey(AbstractStateTimeoutEvent ste) {
        	parent_id = ste.sm_id;
        	prop_name = ste.property_name;
        	count = ste.getCount();
        }
        
        public StateTimeoutKey(PropertyStateMachineState state) {
        	parent_id = state.getParent().getId();
        	prop_name = state.getName();
        	count = state.getCount();
        }
        
        public boolean equals(Object o) {
            StateTimeoutKey stk = (StateTimeoutKey) o;
            return stk != null && stk.parent_id == parent_id && stk.count == count && stk.prop_name != null && stk.prop_name.equals(prop_name) ;
        }

        public int hashCode() {
            return new HashCodeBuilder().append(parent_id).append(count).append(prop_name).toHashCode();
        }
    }
    
    static protected class RepeatTimeEventTask extends TimerTaskRepeat {
        TimeEvent timeEvent;
        int numPerInterval = 1;
        long period;

        public RepeatTimeEventTask(TimeEvent timeEvent, int numPerInterval, long period) {
            this.timeEvent = timeEvent;
            this.numPerInterval = numPerInterval;
            this.period = period;
        }

        public long getPeriod() {
            return period;
        }

        public void execute(WorkingMemory workingMemory) {
            try {
                timeEvent.setScheduledTime(this.scheduledExecutionTime());
                ((WorkingMemoryImpl) workingMemory).fireRepeatEvent(timeEvent, numPerInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    protected class SMTimeoutEventTask extends OnceOnlyEventTask {
		public SMTimeoutEventTask(BaseHandle handle) {
			super(handle);
		}

		protected void assertEvent(WorkingMemory wm) throws Exception {
			if(eventHandle != null) {
				AbstractStateTimeoutEvent ste = (AbstractStateTimeoutEvent) eventHandle.getObject();
				if(ste != null) {
					//cleanup immediately before checking for errors since memory must be released for local time events
					cleanupExpiredStateTimeoutEvent(ste);
					AssertSMTimeoutEventTask.processTask(ruleSession, ste);
				}
			}
		}
    }
}
