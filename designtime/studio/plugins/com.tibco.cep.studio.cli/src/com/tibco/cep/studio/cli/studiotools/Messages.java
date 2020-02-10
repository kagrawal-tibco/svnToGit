package com.tibco.cep.studio.cli.studiotools;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/*
@author ssailapp
@date Aug 25, 2009 2:52:39 PM
 */

public class Messages {

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.cli.studiotools.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String formatMessage(String key, Object ... args) {
		MessageFormat formatter = new MessageFormat(Messages.getString(key));
		return formatter.format(args);
	}
}
