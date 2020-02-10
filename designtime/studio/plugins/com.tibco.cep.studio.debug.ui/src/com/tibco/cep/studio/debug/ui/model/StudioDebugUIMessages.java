package com.tibco.cep.studio.debug.ui.model;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class StudioDebugUIMessages {
	private static final String BUNDLE_NAME = "com.tibco.cep.studio.debug.ui.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private StudioDebugUIMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
