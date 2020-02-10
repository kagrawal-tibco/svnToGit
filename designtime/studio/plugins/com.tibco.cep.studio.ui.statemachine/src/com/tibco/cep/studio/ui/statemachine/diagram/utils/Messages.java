package com.tibco.cep.studio.ui.statemachine.diagram.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * @author ggrigore
 * 
 */
public class Messages /*extends NLS*/ {

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.ui.statemachine.diagram.utils.messages"; 

	private Messages() {
	}
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
