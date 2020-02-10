package com.tibco.cep.studio.wizard.as.commons.beans;

import java.beans.PropertyChangeListener;

public interface IObservable
{

	/**
	 * Adds the given PropertyChangeListener to the listener list. The listener is registered for
	 * all bound properties of this class.
	 * 
	 * @param listener
	 *            the PropertyChangeListener to be added
	 * 
	 * @see #removePropertyChangeListener(PropertyChangeListener)
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Removes the given PropertyChangeListener from the listener list. This method should be used
	 * to remove PropertyChangeListeners that were registered for all bound properties of this
	 * class.
	 * 
	 * @param listener
	 *            the PropertyChangeListener to be removed
	 * 
	 * @see #addPropertyChangeListener(PropertyChangeListener)
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
