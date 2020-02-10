package com.tibco.cep.sharedresource.ui.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "com.tibco.cep.sharedresource.ui.util.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key, Object... arguments) {
		try {
			String unformatted = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(unformatted, arguments);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
