package com.tibco.cep.decision.table.model.controller;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.decision.table.model.notification.listener.IModelChangeListener;

public abstract class AbstractModelController {
	
	/**
	 * Which project we are working with
	 */
	protected String projectName;
	
	protected List<IModelChangeListener> modelNotificationsListeners = new ArrayList<IModelChangeListener>();
	
	public void registerModelChangeNotificationListener(IModelChangeListener modelChangeListener) {
		modelNotificationsListeners.add(modelChangeListener);
	}
	
	protected abstract boolean adaptsClass(Class<?> clazz);
	
	protected AbstractModelController(String projectName) {
		this.projectName = projectName;
	}
}
