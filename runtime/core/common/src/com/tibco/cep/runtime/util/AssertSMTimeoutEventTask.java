package com.tibco.cep.runtime.util;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.AbstractStateTimeoutEvent;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

public class AssertSMTimeoutEventTask implements Runnable {
	private final RuleSessionImpl session;
    private final AbstractStateTimeoutEvent smEvent;
    private final Concept owner;

    public AssertSMTimeoutEventTask(RuleSessionImpl session,
                             AbstractStateTimeoutEvent smEvent,
                             Concept owner) {
        this.session = session;
        this.smEvent = smEvent;
        this.owner = owner;
    }

    public void run() {
        try {
        	session.assertSMTimeout(smEvent, owner);
        } catch (RuntimeException re) {
        	throw re;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void processTask(RuleSession session, AbstractStateTimeoutEvent smEvent, Concept owner) throws Exception {
    	session.getTaskController().processTask(TaskController.SYSTEM_DEFAULT_DISPATCHER_NAME, 
    			new AssertSMTimeoutEventTask((RuleSessionImpl)session, smEvent, owner));
    }
    
    public static void processTask(RuleSession session, AbstractStateTimeoutEvent smEvent) throws Exception {
    	Concept owner = getParentConcept(session, smEvent.sm_id, smEvent.property_name, smEvent.getCount());
    	if(owner != null) processTask(session, smEvent, owner);
    }
    
    public static Concept getParentConcept(RuleSession session, long sm_id, String propertyName, int count) {
        //System.err.println("####### FIRE-NOTIFICATION State Timeout for " + sm_id + ", state= " + propertyName);
        StateMachineConceptImpl parent = (StateMachineConceptImpl) session.getObjectManager().getElement(sm_id);
        if (parent != null) {
            ConceptOrReference parentConceptRef = parent.getParentReference();
            if (parentConceptRef != null) {
                return (Concept) session.getObjectManager().getElement(parentConceptRef.getId());
            }
        }
        return null;
    }
}
