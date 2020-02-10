package com.tibco.be.bw.plugin;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.channel.DeployedDestinationConfig;
import com.tibco.cep.runtime.channel.DestinationConfig;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Oct 10, 2006
 * Time: 3:23:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BWDeployedDestinationConfig implements DeployedDestinationConfig {

    DestinationConfig config;
    Event filter;

    public BWDeployedDestinationConfig(DestinationConfig config, Event event) {
        this.config = config;
        this.filter = event;
    }

    public DestinationConfig getDestinationConfig() {
        return config;
    }

    public Event getFilter() {
        return filter;
    }

    public RuleFunction getPreprocessor() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isEnabled() {
        return true;
    }
}
