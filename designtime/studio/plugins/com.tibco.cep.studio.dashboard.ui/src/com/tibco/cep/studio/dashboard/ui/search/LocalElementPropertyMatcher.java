package com.tibco.cep.studio.dashboard.ui.search;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalElementPropertyMatcher extends PropertyMatcher<LocalElement> {

	private String propertyName;

	public LocalElementPropertyMatcher(String propertyName, String pattern, boolean ignoreCase) {
		super(pattern, ignoreCase);
		this.propertyName = propertyName;
	}

	@Override
	protected String getPropertyValue(LocalElement element) {
		return element.getPropertyValue(propertyName);
	}

}
