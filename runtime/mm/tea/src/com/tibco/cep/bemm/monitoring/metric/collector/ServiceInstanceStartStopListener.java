package com.tibco.cep.bemm.monitoring.metric.collector;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;

import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class ServiceInstanceStartStopListener implements StatusChangeListener {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ServiceInstanceStartStopListener.class);

	@Override
	public void onChange(Monitorable monitorableEntity, String oldStatus, String newStatus) {
		try {
			ServiceInstance serviceInstance = (ServiceInstance) monitorableEntity;
			if (BETeaAgentStatus.RUNNING.getStatus().equals(newStatus)) {
				BEMMServiceProviderManager.getInstance().getEntityMonitoringService()
						.startMetricsCollection(serviceInstance);
				;
			} else if (BETeaAgentStatus.STOPPED.getStatus().equals(newStatus)) {
				BEMMServiceProviderManager.getInstance().getEntityMonitoringService()
						.stopMetricsCollection(serviceInstance, oldStatus, newStatus);
			} else if (BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(newStatus)) {
				BEMMServiceProviderManager.getInstance().getEntityMonitoringService()
						.performUndeployOperation(serviceInstance, oldStatus, newStatus);
			}
		} catch (Exception ex) {
			try {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.STARTING_STOPPING_METRIC_COLLECTION_ERROR), ex);
			} catch (ObjectCreationException e) {
				e.printStackTrace();
			}
		}
	}

}
