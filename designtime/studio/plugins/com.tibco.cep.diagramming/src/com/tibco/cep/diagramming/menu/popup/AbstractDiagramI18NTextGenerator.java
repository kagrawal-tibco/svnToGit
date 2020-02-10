package com.tibco.cep.diagramming.menu.popup;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractDiagramI18NTextGenerator {

	public final Pattern tokenPattern = Pattern.compile("\\$\\{([^}]*)\\}");
	public final String tokenStartString="${";
	public final String tokenEndString="}";
	protected ResourceBundle RESOURCE_BUNDLE;
	
	protected abstract ResourceBundle getResourceBundle();
	protected abstract String getResourceBundleId();

	public String getLocaleText(String text) {
		if (text != null) {
			if (RESOURCE_BUNDLE == null) {
				RESOURCE_BUNDLE = getResourceBundle();
			}
			Matcher matcher = tokenPattern.matcher(text); 
			while (matcher.find()) {
				text=text.replace(tokenStartString + matcher.group(1).trim() + tokenEndString, getString(matcher.group(1).trim()));
			}
		}
		return text;
	}

	private String getString(String key, Object... arguments) {
		try {
			String unformatted = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(unformatted, arguments);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}