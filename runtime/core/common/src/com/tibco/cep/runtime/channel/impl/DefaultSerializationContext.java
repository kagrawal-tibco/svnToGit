package com.tibco.cep.runtime.channel.impl;


import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;


/**
 * Default implementation of SerializationContext.
 *
 * @version 2.0
 * @since 2.0
 */
public class DefaultSerializationContext implements SerializationContext {


    protected RuleSession session;
    protected Channel.Destination dest;
    protected RuleSessionConfig.InputDestinationConfig config;


    public DefaultSerializationContext(RuleSession session, Channel.Destination dest) {
        this.session = session;
        this.dest = dest;
        if (session != null) {
            final RuleSessionConfig.InputDestinationConfig cfgs[] = session.getConfig().getInputDestinations();
            for (int i = 0; i < cfgs.length; i++) {
                final RuleSessionConfig.InputDestinationConfig cfg = cfgs[i];
                if (dest.getURI().equals(cfg.getURI())) {
                    this.config = cfg;
                    break;
                }
            }
        }
    }


    public RuleSession getRuleSession() {
        return this.session;
    }


    public Channel.Destination getDestination() {
        return this.dest;
    }


    public RuleSessionConfig.InputDestinationConfig getDeployedDestinationConfig() {
        return this.config;
    }


    public void setDeployedDestinationConfig(
            RuleSessionConfig.InputDestinationConfig newConfig)
    {
        this.config = newConfig;
    }

}
