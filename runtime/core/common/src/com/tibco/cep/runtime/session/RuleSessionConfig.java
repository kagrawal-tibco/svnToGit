package com.tibco.cep.runtime.session;


import java.util.Properties;
import java.util.Set;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;


/**
 * Describes the configuration of a <code>RuleSession</code> as specified in one BusinessEvents Archive resource.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface RuleSessionConfig {


    /**
     * Returns the URI of the BusinessEvents archive resource.
     *
     * @return a String
     * @.category public-api
     * @since 2.0.0
     */
    String getName();


    /**
     * Returns the URIs of the rules deployed in this BusinessEvents archive.
     *
     * @return a collection of <code>String</code>'s.
     * @.category public-api
     * @since 4.0.0
     */
    Set<String> getDeployedRuleUris();


//    /**
//     * Returns the URIs of the rulesets deployed in this BusinessEvents archive.
//     *
//     * @return a collection of <code>String</code>'s.
//     * @.category public-api
//     * @since 2.0.0
//     */
//    Collection getDeployedRulesets();


    /**
     * Gets the configurations of the destinations enabled for input in this archive.
     *
     * @return an array of <code>InputDestinationConfig</code>.
     * @.category public-api
     * @since 2.0.0
     */
    InputDestinationConfig[] getInputDestinations();


    /**
     * Returns the cache configuration specified for the <code>RuleSesion</code>.
     *
     * @return a <code>Properties</code>.
     */
    Properties getCacheConfig();


    /**
     * Gets the startup class for this session.
     * That class implements the startup functions associated to the BusinessEvents archive.
     *
     * @return the startup class for this session.
     * @.category not-public-api
     * @since 2.0.0
     */
    Class getStartupClass();


    /**
     * Gets the shutdown class for this session.
     * That class implements the shutdown functions associated to the BusinessEvents archive.
     *
     * @return the shutdown class for this session.
     * @.category not-public-api
     * @since 2.0.0
     */
    Class getShutdownClass();

    boolean deleteStateTimeoutOnStateExit();

    boolean removeRefOnDeleteNonRepeatingTimeEvent();
    
    boolean allowEventModificationInRTC();

//    String getTaskControllerName();

    /**
     * Describes a destination that is enabled for input.
     *
     * @version 2.0.0
     * @.category public-api
     * @since 2.0.0
     */
    public interface InputDestinationConfig {


        /**
         * Gets the URI of this destination.
         *
         * @return the URI of this destination.
         * @.category public-api
         * @since 2.0.0
         */
        String getURI();


        /**                                                                          
         * Gets the Filter for this destination.
         * Only events of this type are allowed, null means all event types are allowed.
         *
         * @return an Event
         */
        Event getFilter();


        /**
         * Gets the preprocessor <code>RuleFunction</code> associated to this destination.
         *
         * @return the preprocessor <code>RuleFunction</code> associated to this destination.
         * @.category public-api
         * @since 2.0.0
         */
        RuleFunction getPreprocessor();


        int getNumWorker();

        int getQueueSize();

        int getWeight();

        /**
         *
         * @return the {@link ThreadingModel} used by the endpoint.
         */
        ThreadingModel getThreadingModel();
        
        RuleFunction getThreadAffinityRuleFunction();

    }

    public enum ThreadingModel {
        CALLER("caller"),
        WORKERS("workers"),
        SHARED_QUEUE("shared-queue");

        private String modelType;

        private ThreadingModel(String modelType) {
            this.modelType = modelType;
        }

        public static ThreadingModel getByLiteral(String modelType) {
            if ("caller".equals(modelType)) {
                return CALLER;
            } else if ("workers".equals(modelType)) {
                return WORKERS;
            } else if ("shared-queue".equals(modelType)) {
                return SHARED_QUEUE;
            }
            return null;
        }

        public String toString() {
            return modelType;
        }
    }


}
