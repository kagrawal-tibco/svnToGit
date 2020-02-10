package com.tibco.cep.runtime.channel;


import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;


/**
 * Contains information about the context in which an event can be serialized or deserialized.
 *
 * @version 2.0
 * @since 2.0
 */
public interface SerializationContext {


    /**
     * Gets the RuleSession into which the Event is deserialized or from which the Event is serialized.
     *
     * @return a RuleSession
     * @since 2.0
     */
    RuleSession getRuleSession();


    /**
     * Gets the Destination into which the Event is sent ot from which the event originates.
     *
     * @return a Channel.Destination
     * @since 2.0
     */
    Channel.Destination getDestination();


    /**
     * Gets the configuration information for the context Destination in the context RuleSession.
     *
     * @return a RuleSessionConfig.InputDestinationConfig
     * @since 2.0
     */
    RuleSessionConfig.InputDestinationConfig getDeployedDestinationConfig();

}
