package com.tibco.cep.studio.wizard.ftl.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "com.tibco.cep.studio.wizard.ftl.utils.messages"; //$NON-NLS-1$

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
