package com.tibco.cep.bemm.model;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;

/**
 * Provides the method to get the different types of object.
 * 
 * @author dijadhav
 *
 */
public interface BEMMModelFactory {
	/**
	 * Get the Agent Object
	 * 
	 * @return Agent type object
	 * @throws ObjectCreationException
	 */
	Agent getAgent() throws ObjectCreationException;

	/**
	 * Get the Application Object
	 * 
	 * @return Application type object
	 * @throws ObjectCreationException
	 */
	Application getApplication() throws ObjectCreationException;

	/**
	 * Get the Host Object
	 * 
	 * @return Host type object
	 * @throws ObjectCreationException
	 */
	Host getHost() throws ObjectCreationException;

	/**
	 * Get the ServiceInstance Object
	 * 
	 * @return ServiceInstance type object
	 * @throws ObjectCreationException
	 */
	ServiceInstance getServiceInstance() throws ObjectCreationException;

	/**
	 * Get the BERuntimeVariable Object
	 * 
	 * @return BERuntimeVariable type object
	 * @throws ObjectCreationException
	 */
	BERuntimeVariable getBERuntimeVariable() throws ObjectCreationException;

	/**
	 * Get the ProcessingUnit Object
	 * 
	 * @return ProcessingUnit type object
	 * @throws ObjectCreationException
	 */
	ProcessingUnit getProcessingUnit() throws ObjectCreationException;

	/**
	 * @return
	 * @throws ObjectCreationException
	 */
	AgentConfig getAgentConfig() throws ObjectCreationException;

	/**
	 * Get the Summary Object
	 * 
	 * @return Summary type object
	 * @throws ObjectCreationException
	 */
	Summary getSummary() throws ObjectCreationException;

	/**
	 * Get the LocalCache Object
	 * 
	 * @return LocalCache type object
	 * @throws ObjectCreationException
	 */
	LocalCache getLocalCache() throws ObjectCreationException;

	/**
	 * Get the SharedQueue Object
	 * 
	 * @return SharedQueue type object
	 * @throws ObjectCreationException
	 */
	SharedQueue getSharedQueue() throws ObjectCreationException;

}
