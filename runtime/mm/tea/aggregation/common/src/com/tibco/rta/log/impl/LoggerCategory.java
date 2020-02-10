package com.tibco.rta.log.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/3/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LoggerCategory {

    RTA_SERVICES_METRIC("tea.agent.be.services.metric"),
    
    RTA_SERVICES_METRIC_DETAILS("tea.agent.be.services.metric.details"),

    RTA_SERVICES_PERSISTENCE("tea.agent.be.services.persistence"),
    
    RTA_SERVICES_PERSISTENCE_CONNECTION("tea.agent.be.services.persistence.connection"),
    
    RTA_SERVICES_PERSISTENCE_DETAILS("tea.agent.be.services.persistence.details"),
    
    RTA_SERVICES_RECOVERY("tea.agent.be.services.recovery"),

    RTA_SERVICES_TRANSPORT("tea.agent.be.services.transport"),

    RTA_SERVICES_TRANSPORT_REST("tea.agent.be.services.transport.rest"),
    
    RTA_SERVICES_TRANSPORT_DETAILS("tea.agent.be.services.transport.details"),

    RTA_SERVICES_ADMIN("tea.agent.be.services.admin"),

    RTA_SERVICES_RULES("tea.agent.be.services.rules"),

    RTA_SERVICES_QUERY("tea.agent.be.services.query"),
    
    RTA_SERVICES_QUERY_DETAILS("tea.agent.be.services.query.details"),

    RTA_SERVICES_CLUSTER("tea.agent.be.services.cluster"),

    RTA_SERVICES_OBJECT_MANAGER("tea.agent.be.services.store"),

    RTA_SERVICES_HAWK("tea.agent.be.services.hawk"),

    RTA_SERVICES_STICKY_THREADPOOL("tea.agent.be.services.threadpool.metrics"),

    RTA_SERVICES_GENERIC_THREADPOOL("tea.agent.be.services.threadpool.worker"),
    
    RTA_SERVICES_PURGE("tea.agent.be.services.purge"),

    RTA_RUNTIME_SESSION("tea.agent.be.runtime.session"),

    RTA_RUNTIME_CONTAINER("tea.agent.be.runtime.container"),

    RTA_COMMON("tea.agent.be.common"),

    RTA_FUNCTIONS("tea.agent.be.functions"),

    RTA_CLIENT("tea.agent.be.client"),

    RTA_CLIENT_PROBE("tea.agent.be.client.probe"),

    RTA_ACTIONS("tea.agent.be.actions"),
    
    RTA_SERVICES_PERSISTENCE_TOOLS("tea.agent.be.services.persistence.tools");

    private final String category;

    LoggerCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
