package com.tibco.cep.studio.wizard.as.commons.beans;

import java.beans.PropertyChangeListener;
import java.util.Map;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.Constants;
import com.tibco.cep.studio.wizard.as.commons.utils.StringUtils;

public abstract class AObservableWithServiceInjectionSupport extends AObservable
{

	/**
	 *
	 * @param l
	 * @param properties
	 */
	public void bindPCL(PropertyChangeListener l, Map<?, ?> properties)
	{
		String propName = (String) properties.get(Constants._PROP_KEY_EVENT_NAME);
		if (StringUtils.isNotEmpty(propName))
		{
			addPropertyChangeListener(propName, l);
		}
		else
		{
			addPropertyChangeListener(l);
		}
	}

	/**
	 *
	 * @param l
	 * @param properties
	 */
	public void unbindPCL(PropertyChangeListener l, Map<?, ?> properties)
	{
		if (null != properties)
		{
			String propName = (String) properties.get(Constants._PROP_KEY_EVENT_NAME);
			if (StringUtils.isNotEmpty(propName))
			{
				removePropertyChangeListener(propName, l);
			}
			else
			{
				removePropertyChangeListener(l);
			}
		}
	}

}
