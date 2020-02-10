package com.tibco.cep.runtime.service.om.api;

/**
 * @author bala
 * 
 * This is the list of all the Control DAOs in use in BE.
 */
public enum ControlDaoType {

	//Related to Agent max active
	AgentTable ("AgentTable", true),

    //related to hot deployment
    HotDeployer ("HotDeployer", true),
    ExternalClasses ("ExternalClasses", true),
    ExternalClassesLock ("ExternalClassesLock", true),
    DeletedExternalEntities ("DeletedExternalEntities", true),
      
    //Load balancer
    LoadBalancer ("LoadBalancer", true),
    
    //Application cluster-wide locks
    ClusterLocks ("ClusterLocks", true),

    //Cluster Invocation service: Eg: SetWriteMode
    InvocationService ("InvocationService", true),
    
    //Recovery or populate cache from backing store related
    RecoveryCoordinator ("RecoveryCoordinator", true),
    TotalsTable ("TotalsTable", true),
    LoadTable ("LoadTable", true),

    //Cluster Sequence catalog functions related
    CacheSequenceManager ("CacheSequenceManager", true),
    CacheSequence ("CacheSequence", false), // from database sequences

    //General, used in GMP etc
    Master ("Master", true),

    //Cluster wide scheduler. Cluster scheduler catalog functions
    WorkManager ("WorkManager", true),
    WorkList$SchedulerId("WorkList", false), //workitems table

    //BE typeIds : if storage enabled, these are backed by a store if configured
    TypeIds ("TypeIds", false);
    
    
//TODO:6 not used!
//    MasterId, // min calculated on the fly from various entitites at recovery
    
//  AgentMaxId,
//    EventQueue$EventClass("EventQueue"),
//  AgentTxn$AgentId("AgentTxn-"),
//  BackingStoreTasks,
//    Classes,
//    DeletedEntities,
//    ExtIdTable,
//    RecoveryTable,
//    ObjectTableExtIds,
//    ObjectTableIds, /* TODO: Suresh: LIMITED_NOOBJECTTABLE = "dist-limited-no-ot" */
//    Topics,

//    /**
//     * Process Orchestration related control daos.
//     */
//    BPMN$MergeGatewayTable("MergeGatewayTable"),
//    BPMN$ProcessTemplateTable("ProcessTemplates"),
//    BPMN$LoopCounterTable("LoopCounter");


    protected String alias;
    
    protected boolean requiresCleanup;

    ControlDaoType(String alias, boolean requiresCleanup) {
        this.alias = alias;
        this.requiresCleanup = requiresCleanup;
    }

    /**
     * Defaults to {@link #name()}.
     */
    ControlDaoType() {
    }

    public String getAlias() {
        return alias == null ? name() : alias;
    }
    
    public boolean  requiresCleanupAtStartup() {
    	return requiresCleanup;
    }
    
    public static ControlDaoType getControlDaoType(String alias){
    	for(ControlDaoType daoT : ControlDaoType.values()){
    		if( daoT.getAlias().equals(alias) ){
    			return daoT;
    		}
    	}
    	return null;
    }
}
