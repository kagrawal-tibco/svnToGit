package com.tibco.cep.decision.table.ui.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * @author sasahoo
 * @author hitesh
 * 
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.tibco.cep.decision.table.ui.utils.messages"; //$NON-NLS-1$

	private Messages() {
	}
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	/**
	 * @param key
	 * @param arguments
	 * @return
	 */
	public static String getString(String key, Object... arguments) {
		try {
			String unformatted = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(unformatted, arguments);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
