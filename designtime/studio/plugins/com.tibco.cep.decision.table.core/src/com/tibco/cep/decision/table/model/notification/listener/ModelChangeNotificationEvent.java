/**
 * 
 */
package com.tibco.cep.decision.table.model.notification.listener;

import java.util.EventObject;

/**
 * @author aathalye
 *
 */
public class ModelChangeNotificationEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7706132536454018333L;

	public ModelChangeNotificationEvent(Object source) {
		//The source object will be generally the EMF object
		super(source);
	}
	
	/**
	 * The feature id responsible for the notification
	 */
	private int featureId;
	
	/**
	 * The new value from the notifier.
	 */
	private Object newValue;

	/**
	 * @return the featureId
	 */
	public final int getFeatureId() {
		return featureId;
	}

	/**
	 * @param featureId the featureId to set
	 */
	public final void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	/**
	 * @return the newValue
	 */
	public final Object getNewValue() {
		return newValue;
	}

	/**
	 * @param newalue the newValue to set
	 */
	public final void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
}
