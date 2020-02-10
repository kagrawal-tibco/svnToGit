package com.tibco.cep.runtime.service.om.api;

/*
* Author: Ashwin Jayaprakash / Date: Sep 8, 2010 / Time: 1:25:09 PM
*/

/**
 * This is the list of all the Control DAOs in use in BE.
 */
public enum ControlDaoType {
    /**
     * This is only a prefix
     */
    AgentTable,
    AgentTxn$AgentId("AgentTxn-"),
    BackingStoreTasks,
    CacheSequence,
    CacheSequenceManager,
    Classes,
    DeletedEntities,
    DeletedExternalEntities,
    /**
     * This is only a suffix
     */
    EventQueue$EventClass("EventQueue"),
    ExternalClasses,
    ExternalClassesLock,
    ExtIdTable,
    HotDeployer,
    /**
     * Should not have backing store.
     */
    LoadTable,
    TotalsTable,
    RecoveryTable,
    LoadBalancer,
    LockManager,
    Master,
    MasterId,
    ObjectTableExtIds,
    ObjectTableIds, /* TODO: Suresh: LIMITED_NOOBJECTTABLE = "dist-limited-no-ot" */
    MetadataRegistry,
    WorkList$SchedulerId("WorkList"),
    Topics,
    TypeIds,
    ClusterLocks("ClusterLocks"),
    WorkManager,
    InvocationService("InvocationService"),

    /**
     * Process Orchestration related control daos.
     */
    BPMN$MergeGatewayTable("MergeGatewayTable"),
    BPMN$ProcessTemplateTable("ProcessTemplates"),
    BPMN$LoopCounterTable("LoopCounter"),
    
    /**
     * LiveView publisher queue
     */
    PublisherQueue("PublisherQueue");


    protected String alias;

    ControlDaoType(String alias) {
        this.alias = alias;
    }

    /**
     * Defaults to {@link #name()}.
     */
    ControlDaoType() {
    }

    public String getAlias() {
        return alias == null ? name() : alias;
    }
}
