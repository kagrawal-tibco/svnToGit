package com.tibco.cep.studio.dashboard.core.model.SYN.interfaces;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDPropertyProvider;

/**
 * @ *
 */
public interface ISynPropertyInstanceProvider extends ISynXSDPropertyProvider {

	public String getPropertyValue(String key);

	public void setPropertyValue(String key, String value);

	public boolean hasProperty(String key);
}
