package com.tibco.rta.service.transport.jms;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.taskdefs.Task;
import com.tibco.rta.service.transport.TransportService;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/8/14
 * Time: 10:36 AM
 *
 * Reconnection task when JMS provider goes down.
 */
public class JMSReConnectionTask implements Task {

    private Properties configuration;

    public JMSReConnectionTask(Properties configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object perform() throws Throwable {
        TransportService transportService = ServiceProviderManager.getInstance().getTransportService();
        transportService.init(configuration);
        transportService.start();
        return null;
    }

    @Override
    public String getTaskName() {
        return "JMSReConnection";
    }
}
