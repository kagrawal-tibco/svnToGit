/**
 * This interface is used to get the topology data from defined data handlers.
 */
package com.tibco.cep.bemm.common.handler;

import java.util.Properties;

import com.tibco.cep.bemm.model.Monitorable;

/**
 * @author dijadhav
 *
 */
public interface ApplicationDataFeedHandler<T extends Monitorable> {

	/**
	 * @param properties
	 */
	void init(Properties properties);

	/**
	 * @param monitoredEntity
	 * Connect the Monitored entity to the Feed source (JMX/AS)
	 */
	void connect(T monitoredEntity);
	
	/**
	 * Get the topology data for passed application/service instance from defined data handlers.
	 * 
	 * @param application
	 *            -Application from site topology.
	 * @return -Application/Service Instance data filled with data received from data
	 *         feedHandlers
	 */
	T getTopologyData(T monitoredEntity) throws Exception;
	
	
	/**
	 * @param monitoredEntity
	 * Disconnect the monitored entity from the Feed source (JMX/AS)
	 */
	void disconnect(T monitoredEntity);
}
