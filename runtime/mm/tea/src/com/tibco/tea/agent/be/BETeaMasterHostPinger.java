
package com.tibco.tea.agent.be;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.model.MasterHost;

/**
 * This task is used to ping the tea server and check whether tea server is
 * running or not
 * 
 * @author dijadhav
 *
 */
public class BETeaMasterHostPinger extends TimerTask {
	/**
	 * Logger object
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaMasterHostPinger.class);
	private BEMasterHostManagementService masterHostManagementService;

	/**
	 * Constructor to set the server url and server
	 * 
	 * @param serverURL
	 * @param server
	 */
	public BETeaMasterHostPinger(BEMasterHostManagementService masterHostManagementService ) {
		super();
		this.masterHostManagementService=masterHostManagementService;
	}

	@Override
	public void run() {
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PINGER_TASK_UPDATE_STATUS_HOST));
			
			Map<String,MasterHost> masterHostMap=masterHostManagementService.getAllMasterHost();
			if(null!=masterHostMap){
				for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
					if(null!=entry){
						MasterHost masterHost = entry.getValue();
						if(null!=masterHost){
							masterHostManagementService.updateMasterhostStatus(masterHost);
						}
					}
				}
			}
			
		} catch (Exception e) {
			try {
				LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.MASTER_HOST_STATUS_TASK_ERROR), e);
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
		}
	}

}
