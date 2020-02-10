package com.tibco.cep.bpmn.ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * @author ggrigore
 * 
 */
public class Messages {

	private static final String BUNDLE_NAME = "com.tibco.cep.bpmn.ui.messages"; //$NON-NLS-1$

	private Messages() {
	}
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,Locale.getDefault());
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			BpmnUIPlugin.log(e);
			return '!' + key + '!';
		}
	}
	
	/**
	 * Returns the formatted message for the given key in JFace's resource
	 * bundle.
	 * 
	 * @param key
	 *            the resource name
	 * @param args
	 *            the message arguments
	 * @return the string
	 */
	public static String format(String key, Object ... args) {
		return MessageFormat.format(getString(key), args);
	}

	/**
	 * @param key
	 * @param arguments
	 * @return
	 */
	public static String getString(String key, Object... arguments) {
		try {
			String unformatted = getString(key);
			return MessageFormat.format(unformatted, arguments);
		} catch (MissingResourceException e) {
			BpmnUIPlugin.log(e);
			return '!' + key + '!';
		}
	}
}
