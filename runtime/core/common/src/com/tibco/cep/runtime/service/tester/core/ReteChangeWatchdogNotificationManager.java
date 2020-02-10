package com.tibco.cep.runtime.service.tester.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.LifecycleListener;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.tester.model.InvocationObject;
import com.tibco.cep.runtime.service.tester.model.LifecycleEventType;
import com.tibco.cep.runtime.service.tester.model.ReteChangeType;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.AbstractReteListener;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 11:38:19 AM
 * <!--
 * Add Description of the class here
 * -->
 */
/**
 * Listen to Rete change notifications
 * @author aathalye
 *
 */
//TODO Handle functions
public class ReteChangeWatchdogNotificationManager extends AbstractReteListener implements ChangeListener, LifecycleListener {

	private List<ReteChangeWatchdog> registeredChangeWatchdogs;

	/**
	 * Keep a reference to the RSPManager also
	 */
	private TesterRuleServiceProvider RSPManager;

    /**
     * The rule session this listener is attached to
     */
    private RuleSession ruleSession; // the only need for this is to retrieve the current ExecutionContext (from WorkingMemory) during rule execution, which is different for each rule session

    public ReteChangeWatchdogNotificationManager(RuleServiceProvider RSPManager, RuleSession ruleSession) {
        //Bad but had to be done
        this.RSPManager = (TesterRuleServiceProvider)RSPManager;

		registeredChangeWatchdogs = new ArrayList<ReteChangeWatchdog>();

        this.RSPManager.setChangeListener(this);

        this.ruleSession = ruleSession;
    }

    @Override
    public void actionExecuted() {
//		Logger logger = RSPManager.getLogger(RSPManager.getName());
//		logger.log(Level.DEBUG, "Action executed");
    }
    
    @Override
    public void actionEnd(int agendaSize) {
//		Logger logger = RSPManager.getLogger(RSPManager.getName());
//		logger.log(Level.DEBUG, "Action end %d", agendaSize);
    }
    
	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#actionExecuted(com.tibco.cep.kernel.helper.ActionExecutionContext)
	 */
	public void actionExecuted(ActionExecutionContext arg0) {
//		Logger logger = RSPManager.getLogger(RSPManager.getName());
//		logger.log(Level.DEBUG, "Action executed %s", (Object[]) arg0.info());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#asserted(java.lang.Object, com.tibco.cep.kernel.model.knowledgebase.ExecutionContext)
	 */

	public void asserted(Object executionObject, ExecutionContext executionContext) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.DEBUG, "Asserted Object %s", executionObject);
		//Get params
		if (executionContext != null) {
			//Get invocation cause -> Generally Rule/RF etc.
			Object invocationObject = executionContext.getCause();
            logger.log(Level.DEBUG, "Invocation Object %s", invocationObject);
            Object causalObjects = executionContext.getParams();
			//This can be more than one
			if (causalObjects != null) {
				dispatchTo(invocationObject, causalObjects, executionObject, ReteChangeType.ASSERT);
			}
		} else {
			// context can be null when called from a preprocessor
			PreprocessContext context = PreprocessContext.getContext();
			if (context != null) {
				Object[] objs = new Object[] { context.getRtcTrigger() };
				dispatchTo(new PreprocessorCause(), objs, executionObject, ReteChangeType.ASSERT);
			} else {
				logger.log(Level.DEBUG, "Asserted Object with no execution context, object not tracked");
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#eventExpired(com.tibco.cep.kernel.model.entity.Event)
	 */

	public void eventExpired(Event arg0) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		if (arg0 instanceof TimeEvent) {
			logger.log(Level.INFO, "Event expired: "+arg0+" (fired: "+((TimeEvent)arg0).isFired()+")");
		}
		//Get params
//		if (executionContext != null) {
//			//Get invocation cause -> Generally Rule/RF etc.
//			Object invocationObject = executionContext.getCause();
//			logger.log(Level.DEBUG, "scheduledTimeEvent %s", event);
//            logger.log(Level.DEBUG, "Invocation Object %s", invocationObject);
//            Object causalObjects = executionContext.getParams();
//			//This can be more than one
//			if (causalObjects != null) {
//				dispatchTo(invocationObject, causalObjects, event, ReteChangeType.SCHEDULEDTIMEEVENT);
//			}
//		}

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#eventExpiryExecuted(com.tibco.cep.kernel.helper.EventExpiryExecutionContext)
	 */

	public void eventExpiryExecuted(EventExpiryExecutionContext arg0) {
		// TODO Auto-generated method stub
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.INFO, "Event expiry executed: "+arg0);

	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.session.impl.AbstractReteListener#rtcEnd()
	 */
	@Override
	public void rtcEnd() {
//		Logger logger = RSPManager.getLogger(RSPManager.getName());
//		logger.log(Level.INFO, "RTC ended");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#functionExecuted(com.tibco.cep.kernel.helper.FunctionExecutionContext)
	 */

	public void functionExecuted(FunctionExecutionContext arg0) {
		// TODO Auto-generated method stub
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.DEBUG, "Function executed %s", arg0.getCause());
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#functionExecuted(com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext)
	 */

	public void functionExecuted(FunctionMapArgsExecutionContext arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#modified(java.lang.Object, com.tibco.cep.kernel.model.knowledgebase.ExecutionContext)
	 */

	public void modified(Object executionObject, ExecutionContext executionContext) {
        Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.DEBUG, "Modified Object %s", executionObject);
		//Get params
		if (executionContext != null) {
			//Get invocation cause -> Generally Rule/RF etc.
			Object invocationObject = executionContext.getCause();
            logger.log(Level.DEBUG, "Invocation Object %s", invocationObject);
            Object causalObjects = executionContext.getParams();
			//This can be more than one
			if (causalObjects != null) {
				dispatchTo(invocationObject, causalObjects, executionObject, ReteChangeType.MODIFY);
			}
		} else {
			// context can be null when called from a preprocessor
			PreprocessContext context = PreprocessContext.getContext();
			if (context != null) {
				Object[] objs = new Object[] { context.getRtcTrigger() };
				dispatchTo(new PreprocessorCause(), objs, executionObject, ReteChangeType.MODIFY);
			} else {
				logger.log(Level.DEBUG, "Modified Object with no execution context, object not tracked");
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#retracted(java.lang.Object, com.tibco.cep.kernel.model.knowledgebase.ExecutionContext)
	 */

	public void retracted(Object executionObject, ExecutionContext executionContext) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.DEBUG, "Retracted Object %s", executionObject);

		//Get params
		if (executionContext != null) {
			//Get invocation cause -> Generally Rule/RF etc.
			Object invocationObject = executionContext.getCause();
            logger.log(Level.DEBUG, "Invocation Object %s", invocationObject);
            Object causalObjects = executionContext.getParams();
			//This can be more than one
			if (causalObjects != null) {
				dispatchTo(invocationObject, causalObjects, executionObject, ReteChangeType.RETRACT);
			}
		} else {
			// context can be null when called from a preprocessor
			PreprocessContext context = PreprocessContext.getContext();
			if (context != null) {
				Object[] objs = new Object[] { context.getRtcTrigger() };
				dispatchTo(new PreprocessorCause(), objs, executionObject, ReteChangeType.RETRACT);
			} else {
				logger.log(Level.DEBUG, "Retracted Object with no execution context, object not tracked");
			}
		}
	}

	@Override
	public void actionStart(int actionType, Object context) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		if (actionType == ReteListener.ACTION_RULE_ACTION) {
			Rule rule = (Rule) context;
			logger.log(Level.DEBUG, "Rule Action Start %s", rule.getName());
			//Get params
			if (rule != null) {
				ExecutionContext currentContext = ((ReteWM)((RuleSessionImpl)ruleSession).getWorkingMemory()).getCurrentContext();
				if (currentContext instanceof AgendaItem) {
					AgendaItem agendaItem = (AgendaItem) currentContext;
					Object invocationObject = agendaItem.getCause();
		            Object causalObjects = agendaItem.objects;
					//This can be more than one
					if (causalObjects != null) {
						dispatchTo(invocationObject, causalObjects, rule, ReteChangeType.RULEEXECUTION);
					}
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#ruleFired(com.tibco.cep.kernel.core.rete.conflict.AgendaItem)
	 */
	public void ruleFired(AgendaItem agendaItem) {
		Rule rule = agendaItem.rule;
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.DEBUG, "Fired Rule %s", rule.getName());
		//Get params
		if (rule != null) {
			//Get invocation cause -> Generally Rule/RF etc.
			Object invocationObject = agendaItem.getCause();
            Object causalObjects = agendaItem.objects;
			//This can be more than one
			if (causalObjects != null) {
				dispatchTo(invocationObject, causalObjects, rule, ReteChangeType.RULEFIRED);
			}
		}
	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.knowledgebase.ChangeListener#scheduledTimeEvent(com.tibco.cep.kernel.model.entity.Event, com.tibco.cep.kernel.model.knowledgebase.ExecutionContext)
	 */

	public void scheduledTimeEvent(Event event, ExecutionContext executionContext) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		//Get params
		if (executionContext != null) {
			//Get invocation cause -> Generally Rule/RF etc.
			Object invocationObject = executionContext.getCause();
			logger.log(Level.DEBUG, "scheduledTimeEvent %s", event);
            logger.log(Level.DEBUG, "Invocation Object %s", invocationObject);
            Object causalObjects = executionContext.getParams();
			//This can be more than one
			if (causalObjects != null) {
				dispatchTo(invocationObject, causalObjects, event, ReteChangeType.SCHEDULEDTIMEEVENT);
			}
		} else {
			// context can be null when called from a preprocessor
			PreprocessContext context = PreprocessContext.getContext();
			if (context != null) {
				Object[] rtcTrigger = new Object[] { context.getRtcTrigger() };
				dispatchTo(new PreprocessorCause(), rtcTrigger, event, ReteChangeType.SCHEDULEDTIMEEVENT);
			} else {
				logger.log(Level.INFO, "Scheduled Time Event with no execution context, object not tracked");
				logger.log(Level.INFO, "scheduledTimeEvent %s", event);
			}
		}

	}

	public void registerWatchdog(com.tibco.cep.runtime.service.tester.core.ReteChangeWatchdog reteChangeWatchdog) {
		registeredChangeWatchdogs.add(reteChangeWatchdog);
	}

	/**
	 * Dispatch to all running {@link TesterSession} for this RSP.
	 * @param cause -> The rule/RF which created this.
	 * @param causalObjects -> The causal objects resulting in the cause object
	 * @param executionObject -> The actual object created
	 * @param changeType
	 */
	private void dispatchTo(Object cause,
							Object causalObjects,
							Object executionObject,
			                ReteChangeType changeType) {
		//Get all registered sessions
		Collection<TesterSession> sessions = RSPManager.getAllConnectedSessions();
        Logger logger = RSPManager.getLogger(RSPManager.getName());

		if (causalObjects instanceof Object[]) {
			Object[] executionParams = (Object[])causalObjects;

            InvocationObject invocationObject = new InvocationObject(cause, executionParams);
			//The run which should contain all causal objects
			TesterRun testerRun = findTesterRun(invocationObject); 
//					null;
//			for (TesterSession session : sessions) {
//				TesterRun foundRun = session.dispatch(invocationObject);
//				if (foundRun != null) {
//					testerRun = foundRun;
//					break;
//				}
//			}

            if (changeType == ReteChangeType.MODIFY) {
                logger.log(Level.DEBUG, "Modification Object %s", executionObject);
                if (executionObject instanceof Concept) {
                    Concept modifiedConcept = (Concept)executionObject;
                    //Get all dirty properties
                    List<Property> dirtyProperties = getDirtyProps(modifiedConcept);
                    if (testerRun != null) {
                        notifyWatchdogForModification(testerRun, invocationObject, executionObject, dirtyProperties, changeType);
                    } else {
                        //Log it. Run could be null e.g: Running in debug mode.
                        logger.log(Level.DEBUG, "No appropriate tester run found");
                    }
                } else {
                    //TODO Could we modify events?

                }
            } else {
                //Dispatch
                notifyWatchdog(testerRun, invocationObject, executionObject, changeType);
            }
        }
	}

	/**
	 * Dispatch to all running {@link TesterSession} for this RSP.
	 * @param cause -> The task that caused this action
	 * @param causalObjects -> The causal objects resulting in the cause object
	 * @param eventType -> The type of lifecycle event
	 */
	private void dispatchTo(Object cause, Object causalObject,
			                LifecycleEventType eventType) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		
        if (cause instanceof SchedulerTask) {
			Object[] executionParams = new Object[] { causalObject };
            InvocationObject invocationObject = new InvocationObject(cause, executionParams);
            TesterRun testerRun = findTesterRun(invocationObject);
            if (testerRun == null) {
            	logger.log(Level.TRACE, "Could not find tester run for task, object not dispatched");
            	return;
            }
            notifyWatchdog(testerRun, invocationObject, cause, eventType);
        }
        
        if (cause instanceof ChannelTask) {
			Object[] executionParams = new Object[] { causalObject };
            InvocationObject invocationObject = new InvocationObject(cause, executionParams);
            TesterRun testerRun = findTesterRun(invocationObject);
            if (testerRun == null) {
            	logger.log(Level.TRACE, "Could not find tester run for task, object not dispatched");
            	return;
            }

            //Dispatch
            notifyWatchdog(testerRun, invocationObject, cause, eventType);
        }

//        if (cause instanceof SendEventTask) {
//			Object[] executionParams = new Object[] { ((SendEventTask) cause).getEvent() };
//
//            InvocationObject invocationObject = new InvocationObject(cause, executionParams);
//            TesterRun testerRun = findTesterRun(invocationObject);
//            if (testerRun == null) {
//            	logger.log(Level.ERROR, "Could not find tester run for task, object not dispatched");
//            }
//            
//            //Dispatch
//            notifyWatchdog(testerRun, invocationObject, cause, eventType);
//
//        }
	}

    private TesterRun findTesterRun(InvocationObject invocationObject) {
		//Get all registered sessions
		Collection<TesterSession> sessions = RSPManager.getAllConnectedSessions();

    	//The run which should contain all causal objects
		TesterRun testerRun = null;
		if (sessions.size() == 1) {
			testerRun = sessions.iterator().next().getCurrentRuns().next();
		} else {
			for (TesterSession session : sessions) {
				TesterRun foundRun = session.dispatch(invocationObject);
				if (foundRun != null) {
					testerRun = foundRun;
					break;
				}
			}
		}
		return testerRun;
    }

	/**
     *
     * @param modifiedConcept
     * @return
     */
    private List<Property> getDirtyProps(Concept modifiedConcept) {
        List<Property> dirtyProperties = new ArrayList<Property>();
        //This cast is not good
        if (modifiedConcept instanceof ConceptImpl) {
            ConceptImpl conceptImpl = (ConceptImpl)modifiedConcept;
            //Get dirty properties
            dirtyProperties.addAll(conceptImpl.getAllModifiedProperties(modifiedConcept.getDirtyBitArray(), true));
        }
        return dirtyProperties;
    }

    public String getRuleSessionName() {
        return ruleSession.getName();
    }

    /**
     *
     * @param testerRun -> Cannot be null
     * @param invocationObject
     * @param executionObject
     * @param modifiedProperties
     * @param changeType
     */
	private void notifyWatchdogForModification(TesterRun testerRun,
                                               InvocationObject invocationObject,
                                               Object executionObject,
                                               List<Property> modifiedProperties,
                                               ReteChangeType changeType) {
        Logger logger = RSPManager.getLogger(RSPManager.getName());
        logger.log(Level.DEBUG, "Tester Run expected %s", testerRun.getRunName());
		for (ReteChangeWatchdog reteChangeWatchdog : registeredChangeWatchdogs) {
            logger.log(Level.DEBUG, "Temp Test Run %s", reteChangeWatchdog.getTesterRun().getRunName());
			if (reteChangeWatchdog.getTesterRun().equals(testerRun)) {
				reteChangeWatchdog.onChange(invocationObject, executionObject, modifiedProperties, changeType);
				break;
			}
		}
	}

    /**
     *
     * @param testerRun
     * @param invocationObject
     * @param executionObject
     * @param changeType
     */
	private void notifyWatchdog(TesterRun testerRun,
								InvocationObject invocationObject,
			                    Object executionObject,
                                ReteChangeType changeType) {
		for (ReteChangeWatchdog reteChangeWatchdog : registeredChangeWatchdogs) {
			if (reteChangeWatchdog.getTesterRun().equals(testerRun)) {
				reteChangeWatchdog.onChange(invocationObject, executionObject, changeType);
				break;
			}
		}
	}


    /**
     *
     * @param testerRun
     * @param invocationObject
     * @param executionObject
     * @param changeType
     */
	private void notifyWatchdog(TesterRun testerRun,
								InvocationObject invocationObject,
			                    Object executionObject,
                                LifecycleEventType eventType) {
		for (ReteChangeWatchdog reteChangeWatchdog : registeredChangeWatchdogs) {
			if (reteChangeWatchdog.getTesterRun().equals(testerRun)) {
				reteChangeWatchdog.onEvent(invocationObject, executionObject, eventType);
				break;
			}
		}
	}

	@Override
	public void eventSent(Event event, Destination dest) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		if (dest != null) {
			logger.log(Level.INFO, "Sent Event %s to channel %s", event, dest.getChannel());
		} else {
			logger.log(Level.INFO, "Captured Send Event for %s.  In API mode, the actual Event was not sent to a destination", event);
		}
		dispatchTo(new ChannelTask(dest), event, LifecycleEventType.EVENT_SENT);
	}

	@Override
	public void workScheduled(WorkEntry entry, String schedulerId, String workKey) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.INFO, "Work scheduled: %s", entry);
		dispatchTo(new SchedulerTask(schedulerId, workKey, entry), entry, LifecycleEventType.WORK_SCHEDULED);
	}

	@Override
	public void workRemoved(WorkEntry entry, String schedulerId, String workKey) {
		Logger logger = RSPManager.getLogger(RSPManager.getName());
		logger.log(Level.INFO, "Work removed: %s", entry);
		dispatchTo(new SchedulerTask(schedulerId, workKey, entry), entry, LifecycleEventType.WORK_REMOVED);
	}
}

