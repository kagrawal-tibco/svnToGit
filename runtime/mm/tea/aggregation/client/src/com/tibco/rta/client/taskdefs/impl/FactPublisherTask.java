package com.tibco.rta.client.taskdefs.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.client.ConnectionException;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.serialize.impl.ModelJSONSerializer;
import com.tibco.rta.queues.BatchJob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class FactPublisherTask extends AbstractClientTask {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    public static final String TASK_NAME = "assertFact";

    private ModelJSONSerializer modelSerializer;

    private Map<String, String> transmissionProperties;

    private Collection<BatchJob> jobs = null;

    public FactPublisherTask(DefaultRtaSession session, Map<String, String> transmissionProperties) {
        super(session.getTransmissionStrategy());
        this.transmissionProperties = transmissionProperties;
        modelSerializer = ModelJSONSerializer.INSTANCE;
        requeue = false;
    }

    @Override
    public Object perform() throws Exception {
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.METRICS, messageTransmissionStrategy.getOwnerConnection());

        //hold on to jobs for retry task

        if (jobs == null) {
            jobs = messageTransmissionStrategy.takeQueued();
        }
        //It can still be null
        if (jobs != null) {
            List<Fact> facts = new ArrayList<Fact>(jobs.size());
            for (BatchJob job : jobs) {
                Fact fact = (Fact) job.getWrappedObject();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Json Fact [%s] in publisher [%s]", ((FactKeyImpl) fact.getKey()).getUid(), ((FactImpl) fact).getAttributes());
                }
                facts.add(fact);
            }
            //Clear batch
            jobs.clear();
            if (facts.size() > 0) {
                // TODO
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Publishing facts [%s]", facts);
                }
                publishFacts(endpoint, facts);
            }
        }
        return null;
    }


    @Override
    public String getTaskName() {
        return TASK_NAME;
    }


    private ServiceResponse publishFacts(String endpoint,
                                         List<Fact> facts) throws Exception {
        byte[] transformed = modelSerializer.serialize(facts);
        //Clear the fact batch.
        for (Fact fact : facts) {
            fact.clear();
        }

        try {
            return messageTransmissionStrategy.transmit(endpoint, getTaskName(), transmissionProperties, transformed);
        } catch (ConnectionException ce) {
            throw new Exception(ce);
        }
    }
}
