package com.tibco.rta.client.taskdefs;

import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.util.ServiceConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractClientTask implements Task {

    protected MessageTransmissionStrategy messageTransmissionStrategy;

    protected Map<String, String> properties = new HashMap<String, String>();

    /**
     * Whether this task should be sync executed.
     */
    protected boolean sync = true;

    /**
     * Should this task be requeued by task manager.
     */
    protected boolean requeue = true;

    protected AbstractClientTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        this.messageTransmissionStrategy = messageTransmissionStrategy;
    }

    public RtaConnectionEx getConnnection() {
        return messageTransmissionStrategy.getOwnerConnection();
    }

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return sync;
    }


    protected void setBaseProps(String serviceUri) throws Exception {
        setBaseProps(serviceUri, 5);
    }


    protected void setBaseProps(String serviceUri, int priority) throws Exception {
        //Indicate url for non-http transports.
        properties.put(ServiceConstants.REQUEST_URI, serviceUri);
        properties.put(ServiceConstants.REQUEST_OP, getTaskName());
        properties.put(ServiceConstants.MESSAGE_PRIORITY, Integer.toString(priority));
    }

    /**
     * Perform any salvage if task execution throws exception.
     * For instance this could be reverting any local
     * state of this task to ensure idempotent behaviour on retry.
     * @throws Exception
     */
    public void salvage() throws Exception {

    }
}
