package com.tibco.cep.studio.ui.xml.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * 
 * @author sasahoo
 *
 */
public class Messages{

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.ui.xml.utils.messages";

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
}
