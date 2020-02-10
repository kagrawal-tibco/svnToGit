package com.tibco.tea.model.listener;

import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;
import com.tibco.tea.agent.be.BEAgent;
import com.tibco.tea.agent.be.launcher.BETeaAgentLauncher;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;
import com.tibco.tea.agent.events.Event.EventType;
import com.tibco.tea.agent.events.NotificationService;

/**
 * This class is the listener for status change of agent
 * 
 * @author dijadhav
 *
 */
public class BEAgentStatusChangeListener implements StatusChangeListener {

	// Logger Variable
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEAgentStatusChangeListener.class);

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
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
					.getMessage(MessageKey.STATUS_CHANGED, oldStatus, newStatus));
			NotificationService notificationService = ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT)
					.getNotificationService();
			if (null != notificationService) {
				BEAgent agent = (BEAgent) ObjectCacheProvider.getInstance().getProvider(Constants.BE_AGENT)
						.getInstance(monitorableEntity.getKey());
				if (null != agent) {
					if (BETeaAgentStatus.RUNNING.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STOPPED.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STOPPING.getStatus().equals(newStatus)
							|| BETeaAgentStatus.STARTING.getStatus().equals(newStatus)) {
						notificationService.notify(agent, EventType.STATUS_CHANGE, null, agent.getAgent());
						LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService()
								.getMessage(MessageKey.NOTIFICATION_SENT_ON_STATUS_CHANGE));
					}
				}
			} else {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService()
						.getMessage(MessageKey.NOTIFICATION_SERVICE_NOT_INITIALIZED));
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}
}
