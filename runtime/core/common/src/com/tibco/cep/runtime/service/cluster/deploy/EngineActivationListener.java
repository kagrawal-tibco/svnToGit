package com.tibco.cep.runtime.service.cluster.deploy;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/3/11
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18 Nov, 2010
 * Time: 4:46:06 PM
 * Only care for action executed
 */
public class EngineActivationListener implements ChangeListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(EngineActivationListener.class);

    private List<AdvisoryEvent> advisoryEvents;

    public EngineActivationListener() {
        advisoryEvents = new ArrayList<AdvisoryEvent>();
    }

    public void asserted(Object obj, ExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void modified(Object obj, ExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void retracted(Object obj, ExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void eventExpired(Event evt) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void scheduledTimeEvent(Event evt, ExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ruleFired(AgendaItem context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void actionExecuted(ActionExecutionContext context) {
        //Get action
        Object cause = context.getCause();
        if (cause instanceof RuleSessionImpl.ActivateAction) {
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                for (AdvisoryEvent advisoryEvent : advisoryEvents) {
                    try {
                        ruleSession.assertObject(advisoryEvent, true);
                    } catch (DuplicateExtIdException e) {
                        LOGGER.log(Level.ERROR, e, "Failure in Asserting Advisory");
                    }
                }
                //Clean the list
                advisoryEvents.clear();
            }
        }
    }

    public void addEvent(AdvisoryEvent advisoryEvent) {
        if (advisoryEvent != null) {
            advisoryEvents.add(advisoryEvent);
        }
    }

    public void eventExpiryExecuted(EventExpiryExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void functionExecuted(FunctionExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void functionExecuted(FunctionMapArgsExecutionContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

