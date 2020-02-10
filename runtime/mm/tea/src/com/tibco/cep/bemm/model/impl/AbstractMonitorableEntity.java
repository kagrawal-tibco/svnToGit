package com.tibco.cep.bemm.model.impl;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public abstract class AbstractMonitorableEntity implements Monitorable {

	/**
	 * Registered status change listeners
	 */
	protected static Map<ENTITY_TYPE, List<StatusChangeListener>> statusChangeListenerMap = new EnumMap<ENTITY_TYPE, List<StatusChangeListener>>(
			ENTITY_TYPE.class);

	/**
	 * Running status of the entity
	 */
	protected String status = BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus();

	protected AbstractMonitorableEntity() {
		status = BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus();
	}

	@Override
	public void setStatus(String status) {
		String oldStatus = this.status;
		this.status = status;
		onStatusChange(oldStatus, this.status);
	}

	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * @param entityType
	 * @param statusChangeListener
	 */
	public static void addStatusChangeListener(ENTITY_TYPE entityType, StatusChangeListener statusChangeListener) {
		List<StatusChangeListener> statusChangeListeners = statusChangeListenerMap.get(entityType);
		if (statusChangeListeners == null) {
			statusChangeListeners = new ArrayList<>();
			statusChangeListenerMap.put(entityType, statusChangeListeners);
		}
		if (!statusChangeListeners.contains(statusChangeListener)) {
			statusChangeListeners.add(statusChangeListener);
		}
	}

	private void onStatusChange(String oldStatus, String newStatus) {
		if (newStatus != null && !newStatus.equals(oldStatus)) {
			List<StatusChangeListener> statusChangeListeners = statusChangeListenerMap.get(this.getType());
			if (statusChangeListeners != null) {
				for (StatusChangeListener statusChangeListener : statusChangeListeners) {
					statusChangeListener.onChange((Monitorable) this, oldStatus, newStatus);
				}
			}
		}
	}
}
