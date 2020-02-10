package com.tibco.cep.runtime.service.tester.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.tester.model.InvocationObject;
import com.tibco.cep.runtime.service.tester.model.LifecycleObject;
import com.tibco.cep.runtime.service.tester.model.ModifiedReteObject;
import com.tibco.cep.runtime.service.tester.model.ReteChangeType;
import com.tibco.cep.runtime.service.tester.model.ReteObject;
import com.tibco.cep.runtime.service.tester.model.TesterObject;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.xml.datamodel.XiFactory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 6:10:11 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class TesterRun {
    /**
     * A run name unique in a session.
     */
    protected String runName;

    /**
     * The owner session for this run.
     */
    protected TesterSession ownerSession;

    /**
     * List of input objects asserted/modified/retracted in this session.
     * Any object that can fire rules.
     */
    protected Set<ReteObject> inputObjects;

    /**
     * Hold the objects created from execution.
     */
    protected BlockingQueue<ReteObject> objectQueue = new LinkedBlockingQueue<ReteObject>();

    /**
     * Hold the lifecycle events created from execution (sent events, scheduled events, etc).
     */
    protected BlockingQueue<LifecycleObject> lifecycleQueue = new LinkedBlockingQueue<LifecycleObject>();
    
    /**
     * Newly asserted objects
     */
    private AtomicInteger newEntities = new AtomicInteger();

    /**
     * Modified objects
     */
    private AtomicInteger modifiedEntities = new AtomicInteger();

    /**
     * Sent Events
     */
    private AtomicInteger eventsSent = new AtomicInteger();
    
    /**
     * Work scheduled
     */
    private AtomicInteger workEntries = new AtomicInteger();
    
    /**
     * Rules fired
     */
    private AtomicInteger rulesFired = new AtomicInteger();
    
    /**
     * Retracted objects
     */
    private AtomicInteger retractedEntities = new AtomicInteger();

    protected final ReentrantLock lock = new ReentrantLock();

    private long startTime;

	private Map<Rule, ReteObject> ruleToObjectMap = new ConcurrentHashMap<Rule, ReteObject>();

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(TesterRun.class);


    public TesterRun(String runName, TesterSession testerSession) {
        this.runName = runName;
        this.ownerSession = testerSession;
        inputObjects = new LinkedHashSet<ReteObject>();
        ReteChangeWatchdog reteChangeWatchdog = new ReteChangeWatchdog(this);
        testerSession.getRSP().registerWatchdog(reteChangeWatchdog);
    }

    /**
     *
     * @param reteObject
     */
    public void queue(ReteObject reteObject) {
        reteObject.setTesterRun(this);
        LOGGER.log(Level.DEBUG, "Queueing object >>> %s", reteObject);
        boolean inserted = true;
        snapshot(reteObject);
        if (reteObject.getReteChangeType() != ReteChangeType.RULEFIRED) {
        	// don't insert RULEFIRED objects, as they'll be combined with the ACTIONSTARTED Rete Object
        	inserted = objectQueue.offer(reteObject);
        	// this attempts to look up the initial state of the causal objects (Concepts/Events)
        	// that is, before any modifications
        	if (reteObject.getInvocationObject().getWrappedObject() instanceof Rule) {
        		Rule rule = (Rule) reteObject.getInvocationObject().getWrappedObject();
        		ReteObject currentRuleObj = this.ruleToObjectMap.get(rule);
        		if (currentRuleObj != null) {
        			//LOGGER.log(Level.DEBUG, "Setting causal object start/end state for change type >>> %s", reteObject.getReteChangeType());
        			reteObject.getInvocationObject().setInputEndState(reteObject.getInvocationObject().getCausalObjects());
        			InvocationObject invocationObject = currentRuleObj.getInvocationObject();
        			if (invocationObject.getStartStateSnapshotNode() != null) {
        				reteObject.getInvocationObject().setInputStartStateSnapshot(invocationObject.getStartStateSnapshotNode().copy());
        			}
        			if (reteObject instanceof ModifiedReteObject) {
        				// update the initial values of the modified properties in the serialized XiNode
        				((ModifiedReteObject) reteObject).updateInitialValues(currentRuleObj);
        			}
        		}
        	}
        }
        LOGGER.log(Level.TRACE, "Queueing object Is Inserted()>>> %s", inserted);
        
        if (inserted) {
            switch (reteObject.getReteChangeType()) {

            case ASSERT :
            	newEntities.getAndIncrement();
                //Also add this to asserted list in case of nested firing.
                /**
                 * Example :
                 * E1 asserts results in firing R1 which asserts Concept C1.
                 * Concept C1 fires Rule R2 creating C2. This can go upto infinity.
                 */
                ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
                inputObjects.add(reteObject);
                break;
            case MODIFY :
            	LOGGER.log(Level.DEBUG, "Modified Rete Object>>> %s", reteObject);
                modifiedEntities.getAndIncrement();
                ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
                inputObjects.add(reteObject);
                break;
            case RULEEXECUTION :
            	LOGGER.log(Level.DEBUG, "Action Started>>> %s", reteObject);
            	ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
            	ruleToObjectMap.put((Rule) reteObject.getWrappedObject(), reteObject);
            	inputObjects.add(reteObject);
            	break;
            case RULEFIRED :
            	LOGGER.log(Level.DEBUG, "Fired Rule>>> %s", reteObject);
            	rulesFired.getAndIncrement();
            	ReteObject currentRuleObj = this.ruleToObjectMap.get(reteObject.getInvocationObject().getWrappedObject());
            	if (currentRuleObj != null) {
            		LOGGER.log(Level.TRACE, "Setting End State for RULEEXECTION Object>>> %s", reteObject);
            		currentRuleObj.getInvocationObject().setInputEndState(reteObject.getInvocationObject().getCausalObjects());
            		currentRuleObj.setReteChangeType(ReteChangeType.RULEFIRED); // convert this to a RULEFIRED type
            	} else {
            		LOGGER.log(Level.TRACE, "Rule not found in map>>> %s", reteObject);
            	}
//            	ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
//            	inputObjects.add(reteObject);
            	break;
            case RETRACT :
                retractedEntities.getAndIncrement();
                ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
                inputObjects.add(reteObject);
                break;
            case SCHEDULEDTIMEEVENT: 
            	LOGGER.log(Level.DEBUG, "Scheduled Time Event>>> %s", reteObject);
            	retractedEntities.getAndIncrement();
            	ReteObjectInfoProvider.getInstance().getReteObjectList().add(reteObject);
            	inputObjects.add(reteObject);
            	break;
            }
        } else {
        	LOGGER.log(Level.ERROR, "ReteObject was not inserted!!! >>> %s", reteObject);
        }
    }

    /**
    *
    * @param reteObject
    */
    public void queue(LifecycleObject lifecycleObject) {
    	lifecycleObject.setTesterRun(this);
    	LOGGER.log(Level.DEBUG, "Queueing object >>> %s", lifecycleObject);
    	boolean inserted = true;
    	snapshot(lifecycleObject);
    	inserted = lifecycleQueue.offer(lifecycleObject);

    	LOGGER.log(Level.TRACE, "Queueing object Is Inserted()>>> %s", inserted);

    	if (inserted) {
    		switch (lifecycleObject.getLifecycleEventType()) {

    		case EVENT_SENT :
    			LOGGER.log(Level.DEBUG, "Sent Event >>> %s", lifecycleObject);
    			eventsSent.getAndIncrement();
    			break;
    			
    		case WORK_REMOVED:
    			LOGGER.log(Level.DEBUG, "Work Removed >>> %s", lifecycleObject);
    			workEntries.getAndDecrement();
    			break;
    			
    		case WORK_SCHEDULED:
    			LOGGER.log(Level.DEBUG, "Work Scheduled >>> %s", lifecycleObject);
    			workEntries.getAndIncrement();
    			break;
    		}
    	} else {
    		LOGGER.log(Level.ERROR, "LifecycleObject was not inserted!!! >>> %s", lifecycleObject);
    	}
    }

    private void snapshot(LifecycleObject lifecycleObject) {
        XiFactory factory = XiSupport.getXiFactory();

        try {
        	lifecycleObject.serialize(factory, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void snapshot(ReteObject reteObject) {
        XiFactory factory = XiSupport.getXiFactory();

        try {
			reteObject.serialize(factory, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     *
     * @param assertObject -> The object to assert
     * @throws com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException
     */
    public void assertObject(RuleSession ruleSession, Object assertObject) throws DuplicateExtIdException, Exception {
    	assertObject(ruleSession, assertObject, true);
    }

	/**
     *
     * @param assertObject -> The object to assert
     * @throws com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException
     */
    public void assertObject(RuleSession ruleSession, Object assertObject, boolean executeRules) throws DuplicateExtIdException, Exception {
    	assertObject(ruleSession, assertObject, executeRules, null);
//        if (startTime == 0) {
//            startTime = System.currentTimeMillis();
//        }
//        LOGGER.log(Level.DEBUG, "Asserting object >>> %s into TesterRun %s", assertObject, runName);
//        TesterRuleServiceProvider RSPManager = ownerSession.getRSP();
//        ReteObject reteObject = new ReteObject(assertObject, ReteChangeType.ASSERT);
//        reteObject.setTesterRun(this);
//        if (RSPManager != null) {
//            Object wrappedObject = reteObject.getWrappedObject();
//            inputObjects.add(reteObject);
//            ruleSession.assertObject(wrappedObject, executeRules);
//        }
    }
    
	/**
     *
     * @param assertObject -> The object to assert
     * @throws com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException
     */
    public void assertObject(RuleSession ruleSession, Object assertObject, boolean executeRules, String preprocessorUri) throws DuplicateExtIdException, Exception {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        LOGGER.log(Level.DEBUG, "Asserting object >>> %s into TesterRun %s", assertObject, runName);
        TesterRuleServiceProvider RSPManager = ownerSession.getRSP();
        ReteObject reteObject = new ReteObject(assertObject, ReteChangeType.ASSERT);
        reteObject.setTesterRun(this);
        if (RSPManager != null) {
            Object wrappedObject = reteObject.getWrappedObject();
            inputObjects.add(reteObject);
    		if (preprocessorUri != null && preprocessorUri.trim().length() > 0) {
    			invokePreprocessor(ruleSession, assertObject, preprocessorUri);
    		} else {
    			ruleSession.assertObject(wrappedObject, executeRules);
    		}
    	
        }
    }

	private void invokePreprocessor(RuleSession ruleSession, Object assertObject, String preprocessorUri) {
		RuleSession currentRuleSession = RuleSessionManager.getCurrentRuleSession();
		SimpleEvent event = (SimpleEvent) assertObject;
		try {
			LOGGER.log(Level.DEBUG, "Calling preprocessor '%s' for event %s", preprocessorUri, event.getExpandedName());
			String preprocessorClassName = ModelNameUtil.modelPathToGeneratedClassName(preprocessorUri);
			final Class rf = Class.forName(preprocessorClassName, true, ruleSession.getRuleServiceProvider().getClassLoader());
			RuleFunction preprocessor = (RuleFunction) rf.newInstance();
			((RuleSessionManagerImpl)ruleSession.getRuleRuntime()).setCurrentRuleSession(null);
			((RuleSessionImpl)ruleSession).preprocessPassthru(preprocessor, event);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Calling preprocessor '%s' failed for event %s", preprocessorUri, event.getExpandedName());
		} finally {
			((RuleSessionManagerImpl)ruleSession.getRuleRuntime()).setCurrentRuleSession(currentRuleSession);
		}
	}


    /**
     * @return the runName
     */
    public final String getRunName() {
        return runName;
    }



    /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TesterRun)) {
            return false;
        }
        TesterRun other = (TesterRun)obj;

        if (other.getRunName().equals(runName)) {
            return true;
        }
        return false;
    }


    /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
    @Override
    public String toString() {
        return runName;
    }

    /**
     *
     * @return
     */
    @Remote
    public List<ReteObject> getQueuedObjects() {
        List<ReteObject> queuedObjects = new ArrayList<ReteObject>();
        try {
            lock.lock();
            //Need to lock the collection while doing this
            //because we don't want it to be modified while
            //draining is in progress
            if (!objectQueue.isEmpty()) {
                objectQueue.drainTo(queuedObjects);
                LOGGER.log(Level.TRACE, "Result Object size >>> %s", queuedObjects.size());
            } 
        } finally {
            lock.unlock();
        }
        return queuedObjects;
    }

    /**
    *
    * @return
    */
   @Remote
   public List<LifecycleObject> getQueuedLifecycleObjects() {
       List<LifecycleObject> queuedObjects = new ArrayList<LifecycleObject>();
       try {
           lock.lock();
           //Need to lock the collection while doing this
           //because we don't want it to be modified while
           //draining is in progress
           if (!lifecycleQueue.isEmpty()) {
        	   lifecycleQueue.drainTo(queuedObjects);
               LOGGER.log(Level.TRACE, "Result Lifecycle Object size >>> %s", queuedObjects.size());
           } 
       } finally {
           lock.unlock();
       }
       return queuedObjects;
   }

    /**
     * Checks whether all the causal objects were
     * created in this TesterRun or not.
     * @param causalObjects
     * @return true if all causal objects belong to this run else false
     */
    public boolean hasCausalObjects(TesterObject[] causalObjects) {
        boolean flag = false;
        if (inputObjects.isEmpty()) {
            return flag = false;
        }
        for (TesterObject causalObject : causalObjects) {
            Object wrappedObject = causalObject.getWrappedObject();
            if (wrappedObject instanceof Entity) {
                Entity causalEntity = (Entity)wrappedObject;
                //Search the input collection
                long causalEntityId = causalEntity.getId();
                LOGGER.log(Level.TRACE, "Searching causal object with id >>> %s", causalEntityId);
                TesterObject foundObject = searchInputObjects(causalEntityId);
                if (foundObject != null) {
                    //At least one causal object found.
                    /**
                     * It is possible that this causal object was
                     * joined with a previously created object and
                     * not part of this run's input data, but since
                     * there is at least one causal factor we can consider this run.
                     */
                    return flag = true;
                }
            }
        }
        return flag;
    }



    /**
     * Search the input objects for this causal object id.
     * @param causalEntityId
     * @return
     */
    private TesterObject searchInputObjects(long causalEntityId) {
        for (ReteObject reteObject : inputObjects) {
            LOGGER.log(Level.TRACE, "Input Rete Object >>> %s", reteObject.getId());
            if (reteObject.getId() == causalEntityId) {
                return reteObject;
            }
        }
        return null;
    }



    /**
     * Return the owner session for this run
     * @return
     */
    public TesterSession getOwnerSession() {
        return ownerSession;
    }


    /**
     * Return start time for this run
     * @return
     */
    public Date getStartTime() {
        return new Date(startTime);
    }

    public class RunSummary {

    	public ReteObject[] getCurrentQueuedObjects() {
    		ReteObject[] queuedObjs = new ReteObject[objectQueue.size()];
    		try {
    			lock.lock();
    			//Need to lock the collection while doing this
    			//because we don't want it to be modified while
    			//draining is in progress
    			if (!objectQueue.isEmpty()) {
    				objectQueue.toArray(queuedObjs);
    			} 
    		} finally {
    			lock.unlock();
    		}
    		return queuedObjs;
    	}
    	
    	public LifecycleObject[] getCurrentQueuedLifecycleObjects() {
    		LifecycleObject[] queuedObjs = new LifecycleObject[lifecycleQueue.size()];
    		try {
    			lock.lock();
    			//Need to lock the collection while doing this
    			//because we don't want it to be modified while
    			//draining is in progress
    			if (!lifecycleQueue.isEmpty()) {
    				lifecycleQueue.toArray(queuedObjs);
    			} 
    		} finally {
    			lock.unlock();
    		}
    		return queuedObjs;
    	}
    	
        public Set<ReteObject> getInputObjects() {
    		return inputObjects;
    	}

        public int getNumCreatedObjects() {
            return newEntities.get();
        }

        public int getNumModifiedObjects() {
            return modifiedEntities.get();
        }

        public int getNumEventsSent() {
        	return eventsSent.get();
        }
        
        public int getNumRetractedObjects() {
            return retractedEntities.get();
        }
        
        public int getNumRulesFired() {
        	return rulesFired.get();
        }
    }

    /**
     * Return the current summary for this run.
     * <p>
     * Each invocation may return a different result
     * depending upon RTC completions.
     * </p>
     * @return
     */
    public RunSummary getCurrentRunSummary() {
        return new RunSummary();
    }

    /**
     * Reset all of the tracked entries for this tester run.  This will reset the following:<br>
     * - Created entities<br>
     * - Modified entities<br>
     * - Rules fired<br>
     * - Retracted entities<br>
     * - Events sent<br>
     */
	public void reset() {
		newEntities = new AtomicInteger();
		modifiedEntities = new AtomicInteger();
		rulesFired = new AtomicInteger();
		retractedEntities = new AtomicInteger();
		eventsSent = new AtomicInteger();
		ruleToObjectMap.clear();
		inputObjects.clear();
	}

}
