package com.tibco.rta.client.transport.impl.jms;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.jms.JMSRtaConnection;
import com.tibco.rta.client.tcp.TCPConnectionEvent;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.util.FactBufferCriterionEavesDropper;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.BatchingQueueManager;
import com.tibco.rta.queues.QueueException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSMessageTransmissionStrategy implements MessageTransmissionStrategy {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private DefaultRtaSession session;

    /**
     * The base connection for transmission
     */
    private JMSRtaConnection defaultConnection;

    /**
     * Facts work queue
     */
    private BatchingQueueManager batchingQueueManager;

    private boolean shouldPublishFact;

    public JMSMessageTransmissionStrategy(DefaultRtaSession session, JMSRtaConnection defaultConnection) {
        this.session = session;
        this.defaultConnection = defaultConnection;
    }

    @Override
    public void init() throws Exception {
        session.getTaskManager().setManagerStatus(TCPConnectionEvent.CONNECTION_TRY_EVENT);

        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();

        int factQueueDepth = (Integer) ConfigProperty.FACT_QUEUE_DEPTH.getValue(configuration);
        int factBatchSize = (Integer) ConfigProperty.FACT_BATCH_SIZE.getValue(configuration);
        int factBatchExpiry = (Integer) ConfigProperty.FACT_BATCH_EXPIRY.getValue(configuration);
        int threadPoolSize = (Integer) ConfigProperty.TASK_MANAGER_THREADPOOL_SIZE.getValue(configuration);
        shouldPublishFact = (Boolean) ConfigProperty.FACT_PUBLISH.getValue(session.getConfiguration());

        batchingQueueManager = new BatchingQueueManager(threadPoolSize, factBatchSize, factBatchExpiry, factQueueDepth);
        //Register for batch criterion notifications on fact queue.
        batchingQueueManager.registerEavesDropper(new FactBufferCriterionEavesDropper(session));
    }

    @Override
    public void shutdown() throws Exception {
        batchingQueueManager.close();
    }

    @Override
    public <T extends Fact> boolean enqueueForTransmission(T item) {
        BatchJob batchJob = new BatchJob(item);
        try {
            return batchingQueueManager.offer(batchJob);
        } catch (QueueException e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T takeQueued() {
        try {
            return (T) batchingQueueManager.take();
        } catch (QueueException e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        //Bad
        return null;
    }

    @Override
    public RtaConnectionEx getOwnerConnection() {
        return defaultConnection;
    }

    @Override
    public <R extends RtaConnectionEx> void setOwnerConnection(R connection) {
        this.defaultConnection = (JMSRtaConnection) connection;
    }

    @Override
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception {
        return defaultConnection.invokeService(endpoint, serviceOp, properties, payload);
    }

    @Override
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception {
        if (shouldPublishFact) {
            return defaultConnection.invokeService(endpoint, serviceOp, properties, payload);
        }
        return null;
    }
}
