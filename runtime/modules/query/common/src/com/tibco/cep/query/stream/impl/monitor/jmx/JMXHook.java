package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.util.Properties;

import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.monitor.ResourceId;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 5:45:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXHook implements Component {

    private ResourceId id;
    private QueryMonitor monitor;
    private ServiceInfoImpl serviceInfo;

    public JMXHook() {
        this.id = new ResourceId(getClass().getName());
    }

    public void init(Properties properties) throws Exception {
    }

    public void discard() throws Exception {
        id.discard();
        id = null;
    }

    public void start() throws Exception {
        monitor = Registry.getInstance().getComponent(QueryMonitor.class);
        serviceInfo = new ServiceInfoImpl( monitor );
        serviceInfo.register();
    }

    public void stop() throws Exception {
        monitor = null;
        serviceInfo.unregister();
    }

    public ResourceId getResourceId() {
        return id;
    }




}
