package com.tibco.rta.client.transport.impl.http;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.http.HTTPRtaConnection;
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
 * Date: 17/3/13
 * Time: 4:14 AM
 * HTTP protocol based implementation of transmission.
 */
public class DefaultMessageTransmissionStrategy implements MessageTransmissionStrategy {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private DefaultRtaSession session;

    /**
     * The base connection for transmission
     */
    private HTTPRtaConnection defaultConnection;

    /**
     * Facts work queue
     */
    private BatchingQueueManager batchingQueueManager;


    public DefaultMessageTransmissionStrategy(DefaultRtaSession session, HTTPRtaConnection defaultConnection) {
        this.session = session;
        this.defaultConnection = defaultConnection;
    }

    @Override
    public void init() throws Exception {

        Map<ConfigProperty, PropertyAtom<?>> configuration = session.getConfiguration();

        int factQueueDepth = (Integer) ConfigProperty.FACT_QUEUE_DEPTH.getValue(configuration);
        int factBatchSize = (Integer) ConfigProperty.FACT_BATCH_SIZE.getValue(configuration);
        int factBatchExpiry = (Integer) ConfigProperty.FACT_BATCH_EXPIRY.getValue(configuration);
        int threadPoolSize = (Integer) ConfigProperty.TASK_MANAGER_THREADPOOL_SIZE.getValue(configuration);

        batchingQueueManager = new BatchingQueueManager(threadPoolSize, factBatchSize, factBatchExpiry, factQueueDepth);
        //Register for batch criterion notifications on fact queue.
        batchingQueueManager.registerEavesDropper(new FactBufferCriterionEavesDropper(session));
        //TODO register mbean
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
    public HTTPRtaConnection getOwnerConnection() {
        return defaultConnection;
    }

    @Override
    public <R extends RtaConnectionEx> void setOwnerConnection(R connection) {
        this.defaultConnection = (HTTPRtaConnection) connection;
    }

    @Override
    public void shutdown() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Attempting to close session");
        }
    }

    @Override
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception {
        return defaultConnection.invokeService(endpoint, serviceOp, properties, payload);
    }

    @Override
    public ServiceResponse<?> transmit(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception {
        return defaultConnection.invokeService(endpoint, serviceOp, properties, payload);
    }
}
