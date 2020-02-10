package com.tibco.tea.model.listener;

import java.util.Map;

import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.be.BEServiceInstance;
import com.tibco.tea.agent.be.launcher.BETeaAgentLauncher;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;

/**
 * This class is the listener for status change of instance
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstanceStatusChangeListener implements StatusChangeListener {

	// Logger Variable
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			BEServiceInstanceStatusChangeListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.listener.StatusChangeListener#onChange(com.tibco
	 * .cep.bemm.model.Monitorable, java.lang.String, java.lang.String)
	 */
	@Override
	public void onChange(Monitorable monitorableEntity, String oldStatus, String newStatus) {

		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INSTANCE_STATUS_CHANGED, oldStatus , newStatus));
			NotificationService notificationService = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_SERVICE_INSTANCE).getNotificationService();
			
			BEApplicationsManagementService applicationService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService();
        	Map<String, Application> application = applicationService.getApplications();
        	Application app = (Application) application.values().toArray()[0];        	
        	ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION);
        	NotificationService notificationServiceForApp = ObjectCacheProvider.getInstance()
					.getProvider(Constants.BE_APPLICATION).getNotificationService();  
        	
			if (null != notificationService) {
				BEServiceInstance serviceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(monitorableEntity.getKey());
				if (null != serviceInstance) {
					if (BETeaAgentStatus.RUNNING.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STOPPED.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STOPPING.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STARTING.getStatus().equals(newStatus)
							|| BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(newStatus)) {
						notificationService.notify(serviceInstance, EventType.STATUS_CHANGE, null,
								serviceInstance.getInstance());
						notificationServiceForApp.notify(provider.getInstance(app.getName()), EventType.STATUS_CHANGE, null, app);
						LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.NOTIFICATION_SENT_ON_STATUS_CHANGE));
					}
				}
			} else {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.NOTIFICATION_SERVICE_NOT_INITIALIZED));
			}

		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex.getLocalizedMessage(), ex);
		}
	}
}
