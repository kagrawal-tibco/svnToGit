package com.tibco.cep.bemm.model;

import java.util.Map;

/**
 * The interface used by entities which we need to monitor
 * 
 * @author dijadhav
 *
 */
public interface Monitorable {
	
    public static enum ENTITY_TYPE {
        CLUSTER("cluster"), 
        HOST("host"), 
        PU_INSTANCE("pu_instance"), 
        INFERENCE_AGENT("inference"),         
        CACHE_AGENT("cache"), 
        QUERY_AGENT("query"),
        PROCESS_AGENT("process"), 
        DASHBOARD_AGENT("dashboard"),
        BE_TEA_AGENT("be_tea_agent"),;
    
        private final String entityType; 
        
        ENTITY_TYPE(String entityType) {
        	this.entityType = entityType;
        }
        
        public String value() {
            return entityType;
        }

        public static ENTITY_TYPE fromValue(String v) {
            return valueOf(v);
        }    
    }

	/**
	 * @return type of monitorable entity
	 */
    ENTITY_TYPE getType();

	/**
	 * @return key
	 */
	String getKey();
	
	/**
	 * Get Status
	 * 
	 * @return status
	 */
	String getStatus();

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(String status);

	/**
	 * @return
	 */
	Map<String, Object> getBasicAttributes();
	
	/**
	 * @param connectionType
	 * @return
	 */
	ConnectionInfo getConnectionInfo(String connectionType);
	
	/**
	 * @return the entity's name
	 */
	String getName();

}
