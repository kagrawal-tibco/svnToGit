package com.tibco.cep.studio.core.util;
/**
 * @author rmishra
 */
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.core.util.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key, Object...args) {
		try {
			String message = RESOURCE_BUNDLE.getString(key);
			String formattedMessage = 
				MessageFormat.format(message, args);
			return formattedMessage;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
