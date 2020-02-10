package com.tibco.cep.bpmn.ui.editor;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BpmnMessages {

	private BpmnMessages() {
	}

	private static final String         BUNDLE_NAME     = "com.tibco.cep.bpmn.ui.editor.BpmnMessages"; //$NON-NLS-1$
	
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,Locale.getDefault()/*Locale.FRANCE*/);
	
	public static String getString(String key, Object... arguments) {
		try {
			String unformatted = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(unformatted, arguments);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
