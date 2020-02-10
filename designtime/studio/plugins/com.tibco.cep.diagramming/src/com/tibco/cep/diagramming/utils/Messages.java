package com.tibco.cep.diagramming.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "com.tibco.cep.diagramming.utils.messages";
	
	private Messages() {
	}
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String format(String key, Object[] args) {
		return MessageFormat.format(getString(key), args);
	}
}

