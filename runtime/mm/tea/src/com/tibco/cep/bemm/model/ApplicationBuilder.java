package com.tibco.cep.bemm.model;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;

public interface ApplicationBuilder<T> {

	/**
	 * @param dataStoreService
	 *            TODO
	 * @return
	 * @throws ObjectCreationException
	 */
	Application newApplication() throws ObjectCreationException;

	/**
	 * @param application
	 * @param applicationConfig
	 * @throws Exception
	 */
	void enrichApplicationConfig(Application application, DeploymentVariables applicationConfig) throws Exception;
	
	/**
	 * @param application
	 * @param clusterConfig
	 * @param isImportApplication TODO
	 * @param site
	 * @throws Exception
	 */
	void enrichTopologyAndClusterConfigData(Application application, T topologyObject, ClusterConfig clusterConfig,
			String loggedInUser, boolean isImportApplication) throws Exception;

	/**
	 * @param application
	 * @param clusterConfig
	 */
	void enrichClusterConfigData(Application application, ClusterConfig clusterConfig);

	/**
	 * @param application
	 * @param site
	 * @return
	 * @throws Exception
	 */
	Application enrichTopologyData(Application application, T topologyObject, String loggedInUser,boolean isImportApplication) throws Exception;
}
