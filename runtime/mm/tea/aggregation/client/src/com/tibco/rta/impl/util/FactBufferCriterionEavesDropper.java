package com.tibco.rta.impl.util;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.taskdefs.IdempotentRunnableRetryTask;
import com.tibco.rta.client.taskdefs.impl.FactPublisherTask;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.queues.event.QueueEavesDropEvent;
import com.tibco.rta.queues.event.QueueEavesDropper;
import com.tibco.rta.util.ServiceConstants;

import java.util.HashMap;
import java.util.Map;

import static com.tibco.rta.util.ServiceConstants.SESSION_ID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/4/13
 * Time: 11:31 AM
 * Execute using task manager a task for fact publish when batch criterion is met.
 * @see com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager
 * @see com.tibco.rta.queues.BufferCriterion
 */
public class FactBufferCriterionEavesDropper implements QueueEavesDropper {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Since session would be interested keep a reference.
     */
    private DefaultRtaSession session;

    /**
     * Set these static props upfront to avoid setting them each time.
     */
    private Map<String, String> factTransmissionProperties = new HashMap<String, String>();

    public FactBufferCriterionEavesDropper(DefaultRtaSession session) {
        this.session = session;
        factTransmissionProperties.put(ServiceConstants.REQUEST_URI, ServiceType.METRICS.getServiceURI());
        factTransmissionProperties.put(ServiceConstants.REQUEST_OP, FactPublisherTask.TASK_NAME);
        factTransmissionProperties.put(ServiceConstants.MESSAGE_PRIORITY, Integer.toString(2));
        factTransmissionProperties.put(SESSION_ID, session.getClientId());
    }

    @Override
    public void notifyEavesDropper(QueueEavesDropEvent eavesDropEvent) {
        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();

        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_FACT_OPS.getValue(configuration);
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(configuration);

        if (eavesDropEvent.getEventType() == QueueEavesDropEvent.BUFFER_CRITERION_MET) {
            //Publish fact
            try {
                @SuppressWarnings("unchecked")
                FactPublisherTask factPublisherTask = new FactPublisherTask(session, factTransmissionProperties);
                factPublisherTask.setSync(false);

                IdempotentRunnableRetryTask retryTask = new IdempotentRunnableRetryTask(session, retryCount, waitTime, factPublisherTask);
                session.getTaskManager().submitAsyncTask(retryTask);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Failed to publish fact", e);
            }
        }
    }
}
