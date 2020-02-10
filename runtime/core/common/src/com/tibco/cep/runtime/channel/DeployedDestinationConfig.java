package com.tibco.cep.runtime.channel;


import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;


/**
 * @version 2.0
 * @since 2.0
 */
public interface DeployedDestinationConfig {
    //TODO why is this used only in BWDeployedDestinationConfig, which is never used?


    /**
     * Gets a destination Config
     *
     * @return a DestinationConfig
     */
    DestinationConfig getDestinationConfig();


    /**
     * Gets the Event that is to be used as filter, or null if all event types are ok.
     *
     * @return an Event
     */
    Event getFilter();


    /**
     * Gets the preprocessor function used to assert events to the RuleSession, or null if event are directly asserted.
     *
     * @return a RuleFunction
     */
    RuleFunction getPreprocessor();


    /**
     * true iif the Destination is enabled.
     *
     * @return a boolean
     */
    boolean isEnabled();


}
