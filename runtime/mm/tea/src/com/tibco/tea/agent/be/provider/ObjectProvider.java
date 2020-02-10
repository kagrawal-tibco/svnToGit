package com.tibco.tea.agent.be.provider;

import java.util.HashMap;
import java.util.Map;

import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.api.TeaObjectProvider;
import com.tibco.tea.agent.api.WithNotifications;
import com.tibco.tea.agent.events.NotificationService;

/**
 * This is base class for TEA object provider.
 * 
 * @author dijadhav
 *
 */
public abstract class ObjectProvider<T extends TeaObject> implements TeaObjectProvider<T>, WithNotifications {
	protected NotificationService notificationService;
	Map<String, TeaObject> teaObjectMap;

	protected ObjectProvider() {
		teaObjectMap = new HashMap<>();
	}

	public boolean add(String key, TeaObject value) {
		return (teaObjectMap.put(key, value) != null) ? true : false;
	}

	public boolean remove(String key) {
		return (teaObjectMap.remove(key) != null ? true : false);
	}

	void clear() {
		teaObjectMap = new HashMap<>();
	}

	/**
	 * @return the notificationService
	 */
	public NotificationService getNotificationService() {
		return notificationService;
	}

	/**
	 * @param notificationService
	 *            the notificationService to set
	 */
	@Override
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

}
