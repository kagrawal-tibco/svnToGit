package com.tibco.cep.runtime.service.tester.core;

import java.util.List;

import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.tester.model.InvocationObject;
import com.tibco.cep.runtime.service.tester.model.LifecycleEventType;
import com.tibco.cep.runtime.service.tester.model.LifecycleObject;
import com.tibco.cep.runtime.service.tester.model.ModifiedReteObject;
import com.tibco.cep.runtime.service.tester.model.ReteChangeType;
import com.tibco.cep.runtime.service.tester.model.ReteObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 11:38:58 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ReteChangeWatchdog {

    /**
     * The {@link TesterRun}
     */
    private TesterRun testerRun;

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ReteChangeWatchdog.class);

    public ReteChangeWatchdog(TesterRun testerRun) {
        this.testerRun = testerRun;
    }

    public void onEvent(final InvocationObject invocationObject,
    		final Object executionObject,
    		final LifecycleEventType eventType) {
    	queue(executionObject, invocationObject, eventType);
    }

	public void onChange(final InvocationObject invocationObject,
                         final Object executionObject,
                         final ReteChangeType reteChangeType) {
        queue(executionObject, invocationObject, reteChangeType);
    }

    public void onChange(final InvocationObject invocationObject,
                         final Object executionObject,
                         final List<Property> modifiedProperties,
                         final ReteChangeType reteChangeType) {
        queue(executionObject, invocationObject, modifiedProperties, reteChangeType);
    }

    private void queue(Object executionObject, InvocationObject invocationObject, LifecycleEventType eventType) {
    	LOGGER.log(Level.INFO, "Calling queue object for >>> %s", executionObject);
    	testerRun.queue(new LifecycleObject(invocationObject, executionObject, eventType)); 
	}

    private void queue(final Object executionObject,
                       final InvocationObject invocationObject,
                       final ReteChangeType reteChangeType) {
        LOGGER.log(Level.INFO, "Calling queue object for >>> %s", executionObject);
        //For now only concepts/simple-events
        if (executionObject instanceof Concept || 
        		executionObject instanceof SimpleEvent || 
        		executionObject instanceof Rule || 
        		executionObject instanceof TimeEvent) {
            testerRun.queue(new ReteObject(invocationObject, executionObject, reteChangeType));
        }
    }

    private void queue(final Object executionObject,
                       final InvocationObject invocationObject,
                       final List<Property> modifiedProperties,
                       final ReteChangeType reteChangeType) {
        LOGGER.log(Level.INFO, "Calling queue object for >>> %s", executionObject);
        //For now only concepts/simple-events
        if (executionObject instanceof Concept || 
        		executionObject instanceof SimpleEvent  || 
        		executionObject instanceof TimeEvent) {
            ReteObject modifiedObject = new ReteObject(invocationObject, executionObject, reteChangeType);
            testerRun.queue(new ModifiedReteObject(modifiedObject, modifiedProperties));
        }
    }

    /**
     * @return the testerRun
     */
    public final TesterRun getTesterRun() {
        return testerRun;
    }
}
