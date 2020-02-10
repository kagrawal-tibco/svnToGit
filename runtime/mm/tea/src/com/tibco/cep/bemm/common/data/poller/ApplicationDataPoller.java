package com.tibco.cep.bemm.common.data.poller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.service.ApplicationDataProviderService;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationDiscoveryService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.model.Application;

/**
 * This is application data poller task which is used get poll data and match it
 * with data from site topology file
 * 
 * @author dijadhav
 *
 */
public class ApplicationDataPoller extends TimerTask {
	private static final long MAX_TARDINESS = 30 * 1000;
	private BEApplicationsManagementService beApplicationsManagementService;
	private BEApplicationDiscoveryService clusterBased;
	private BEApplicationDiscoveryService instanceBased;

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ApplicationDataPoller.class);

	/**
	 * Constructor to initialize the Services
	 * 
	 * @param dataProviderService
	 *            - Service instance data provider
	 * @param beApplicationsManagementService
	 *            - Application Management service
	 * @throws ObjectCreationException 
	 */
	public ApplicationDataPoller(ApplicationDataProviderService dataProviderService,
			BEApplicationsManagementService beApplicationsManagementService) throws ObjectCreationException {
		super();
		this.beApplicationsManagementService = beApplicationsManagementService;
		clusterBased = BEMMServiceProviderManager.getInstance().getClusterBasedDiscoveryService();
		instanceBased = BEMMServiceProviderManager.getInstance().getInstanceBasedDiscoveryService();
	}
	
	public void init(Properties properties) throws Exception {
		clusterBased.init(properties);
		instanceBased.init(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		if (System.currentTimeMillis() - scheduledExecutionTime() >= MAX_TARDINESS)
			return; // Too late; skip this execution.
		
		try {
			// For each application get the data running instances data match data
			// and update status
			Map<String, Application> applicationsMap = beApplicationsManagementService.getApplications();
	
			if (null != applicationsMap && applicationsMap.size() > 0) {
				Collection<Application> applications = new ArrayList<Application>(applicationsMap.values());
	
				// For each application - Poll the starting service instances first
				for (Application application : applications) {
					if (application.connectToCluster()) {
						BEMMServiceProviderManager.getInstance().getClusterBasedDiscoveryService().discover(application);
					} else {
						BEMMServiceProviderManager.getInstance().getInstanceBasedDiscoveryService().discover(application);
					}
				}		
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}
	
	@Override
	public boolean cancel() {
		try {
			if (clusterBased != null) {
				clusterBased.stop();
			}
		} catch (Exception e) {
		}
		try {
			if (instanceBased != null) {
				instanceBased.stop();
			}
		} catch (Exception e) {
		}
		return super.cancel();
	}

}
