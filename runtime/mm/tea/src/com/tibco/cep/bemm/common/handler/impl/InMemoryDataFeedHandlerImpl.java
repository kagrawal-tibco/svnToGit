package com.tibco.cep.bemm.common.handler.impl;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.impl.cluster.InternalManagementTable;
import com.tibco.cep.runtime.management.impl.cluster.LocalManagementTable;
import com.tibco.cep.runtime.service.management.EntityMBeansManager;

/**
 * This feed handler is used to process the in memory application.
 * 
 * @author dijadhav
 *
 */
public class InMemoryDataFeedHandlerImpl implements ApplicationDataFeedHandler<Application> {

	private Logger logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
	private static final String OBJ_NAME_MNGMT_TABLE = "com.tibco.be:dir=Methods,Group=Management Table";
	private InternalManagementTable managementTable;

	private final class InMemoryDataCheckerTask extends TimerTask {
		private String applicationName;

		public InMemoryDataCheckerTask(String applicationName) {
			this.applicationName = applicationName;
		}

		@Override
		public void run() {
			BEApplicationsManagementService applicationsManagementService;
			try {
				applicationsManagementService = BEMMServiceProviderManager.getInstance()
						.getBEApplicationsManagementService();
				applicationsManagementService.updateInMemoryServiceInstance(applicationName);
			} catch (ObjectCreationException e) {
				logger.log(Level.ERROR, e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * @param application
	 */
	public InMemoryDataFeedHandlerImpl() {
		if (null == managementTable) {
			managementTable = new LocalManagementTable();
			EntityMBeansManager.registerStdMBean(OBJ_NAME_MNGMT_TABLE, managementTable, ManagementTable.class);
		}
	}

	@Override
	public void init(Properties properties) {

		TimerTask inMemoryDataTask = new InMemoryDataCheckerTask(properties.getProperty(Constants.APPLICATION_NAME));

		Timer timer = new Timer(true);
		timer.schedule(inMemoryDataTask, 0, 3000);

	}

	@Override
	public void connect(Application application) {
		//No-op
	}

	@Override
	public Application getTopologyData(Application application) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect(Application monitoredEntity) {
		// TODO Auto-generated method stub
		
	}


}
