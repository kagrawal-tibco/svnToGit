package com.tibco.rta.client.taskdefs.impl.jms;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.annotations.Idempotent;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.property.PropertyAtom;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/5/13
 * Time: 12:19 PM
 * Establish connection to JMS provider.
 */
@Idempotent
public class EstablishJMSConnectionTask extends AbstractClientTask {

    /**
     * Name of the JMS RTA connection class.
     * @see com.tibco.rta.client.jms.JMSRtaConnection
     */
    private String connectionClassName;

    /**
     * Configuration for creating connection.
     */
    private Map<ConfigProperty, PropertyAtom<?>> configuration;

    public EstablishJMSConnectionTask(String connectionClassName,
                                      MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);

        this.connectionClassName = connectionClassName;
    }

    public void setConfiguration(Map<ConfigProperty, PropertyAtom<?>> configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object perform() throws Throwable {
        Class<?> jmsConnClazz = this.getClass().getClassLoader().loadClass(connectionClassName);
        Constructor<?> constructor = jmsConnClazz.getConstructor(Map.class);
        return constructor.newInstance(configuration);
    }

    @Override
    public String getTaskName() {
        return "JMSConnection";
    }
}
