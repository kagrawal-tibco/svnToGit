package com.tibco.cep.studio.dashboard.core.listeners;

import java.util.EventListener;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

/**
 * @ *
 */
public interface ISynPropertyChangeListener extends EventListener {
	/**
	 * Called when the value of a property is changed
	 *
	 * @param property
	 *
	 * @param prop
	 *            the SynXAttribute that has changed
	 * @throws Exception
	 */
	public abstract void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue);

}
