package com.tibco.cep.studio.ui.property;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.tibco.cep.studio.common.resources.ecore.ANTLRResourceFactory;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

public class StudioPropertyChangeListener implements IPropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (StudioUIPreferenceConstants.STUDIO_PERSIST_ENTITIES_AS_SOURCE.equals(event.getProperty())){
			ANTLRResourceFactory.SOURCE_BASED_PERSISTENCE = (boolean) event.getNewValue();
		}
	}

}
