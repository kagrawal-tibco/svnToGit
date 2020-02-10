package com.tibco.cep.dashboard.management;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 * 
 */
public final class MessageGenerator {

	private static Enumeration<String> EMPTY_STRING_ENUMERATION = new Vector<String>().elements();

	private static Object[] EMPTY_ARGS = new Object[0];

	private static final String UNLOADABLE_RESOURCE = "could not load messages library for {0} under {1} locale";

	private static final String UNLOADABLE_RESOURCE_KEY = "could not find message for {2} under {0} and {1} locale";

	private static final ResourceBundle EMPTY_RESOURCE_BUNDLE = new ResourceBundle() {
		
		@Override
		public Enumeration<String> getKeys() {
			return EMPTY_STRING_ENUMERATION;
		}

		@Override
		protected Object handleGetObject(String key) {
			if (key == null) {
				throw new NullPointerException();
			}
			return null;
		}

	};

	private String serviceName;

	private Locale defaultLocale;

	private Map<String, Map<Locale, ResourceBundle>> resourceBundleCache;

	private List<String> attemptedKeys;

	private ExceptionHandler exceptionHandler;

	public MessageGenerator(String serviceName, ExceptionHandler exceptionHandler) {
		this.serviceName = serviceName;
		this.exceptionHandler = exceptionHandler;
		defaultLocale = Locale.getDefault();
		resourceBundleCache = new HashMap<String, Map<Locale, ResourceBundle>>();
		attemptedKeys = new LinkedList<String>();
	}

	public final void init(Properties properties) {
	}

	public final String getMessage(String key) {
		return getMessage(key, defaultLocale, null);
	}

	public final String getMessage(String key, MessageGeneratorArgs arguments) {
		return getMessage(key, defaultLocale, arguments);
	}

	public final String getMessage(String key, Locale locale) {
		return getMessage(key, locale, null);
	}

	public final String getMessage(String key, Locale locale, MessageGeneratorArgs arguments) {
		ResourceBundle resourceBundle = getResourceBundleFor(locale);
		try {
			String messageTemplate = resourceBundle.getString(key);
			if (arguments != null) {
				return MessageFormat.format(messageTemplate, arguments.getAsArray());
			}
			return MessageFormat.format(messageTemplate, EMPTY_ARGS);
		} catch (MissingResourceException e) {
			if (attemptedKeys.contains(key) == false) {
				String msg = MessageFormat.format(UNLOADABLE_RESOURCE_KEY, new Object[] { serviceName, locale.toString(), key });
				exceptionHandler.handleExceptionWithNoSysErrTrace(msg, e, Level.WARN, Level.DEBUG);
				attemptedKeys.add(key);
			}
		}
		if (arguments != null) {
			return key + " - " + arguments.toString();
		}
		return key;
	}

	/**
	 * @param serviceName
	 * @return
	 */
	private synchronized ResourceBundle getResourceBundleFor(Locale locale) {
		Map<Locale, ResourceBundle> localeSpecificResourceBundleCache = resourceBundleCache.get(serviceName);
		if (localeSpecificResourceBundleCache == null) {
			localeSpecificResourceBundleCache = new HashMap<Locale, ResourceBundle>();
			resourceBundleCache.put(serviceName, localeSpecificResourceBundleCache);
		}
		ResourceBundle bundle = localeSpecificResourceBundleCache.get(locale);
		if (bundle == null) {
			try {
				bundle = ResourceBundle.getBundle(serviceName, locale);
			} catch (MissingResourceException e) {
				String msg = MessageFormat.format(UNLOADABLE_RESOURCE, new Object[] { serviceName, locale.toString() });
				exceptionHandler.handleExceptionWithNoSysErrTrace(msg, e, Level.WARN, Level.DEBUG);
				bundle = EMPTY_RESOURCE_BUNDLE;
			}
			localeSpecificResourceBundleCache.put(locale, bundle);
		}
		return bundle;
	}
	
	public final String getServiceName(){
		return serviceName;
	}
	
}