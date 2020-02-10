package com.tibco.cep.studio.dashboard.ui.forms;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalElementDisplayNameLabelProvider extends LocalElementLabelProvider {

	public LocalElementDisplayNameLabelProvider() {
		super(false);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof LocalElement){
			LocalElement localElement = (LocalElement)element;
			try {
				if (localElement.getProperty(LocalConfig.PROP_KEY_DISPLAY_NAME) != null){
					String displayName = localElement.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME);
					if (displayName == null || displayName.trim().length() == 0){
						return super.getText(localElement);
					}
					return displayName;
				}
			} catch (Exception e) {
				//do nothing, let's use super's get text
			}
		}
		return super.getText(element);
	}

}
