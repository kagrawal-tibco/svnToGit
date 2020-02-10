package com.tibco.cep.dashboard.config;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class GlobalConfiguration {
	
	//Added to fix BE-9435 : anpatil
	private static final String[] POSSIBLE_INSTALLATION_HOME_KEYS = new String[]{
		"BE_HOME",
		"tibco.env.BE_HOME",
		"TIBCO_BE_HOME",
		"tibco.env.TIBCO_BE_HOME",
	};

	private static GlobalConfiguration instance;

	public static final synchronized GlobalConfiguration getInstance() {
		if (instance == null) {
			instance = new GlobalConfiguration();
		}
		return instance;
	}

	private Logger logger;
	@SuppressWarnings("unused")
	private ExceptionHandler exceptionHandler;
	private MessageGenerator messageGenerator;

	private Locale locale;
	private TimeZone timeZone;
	private SynchronizedSimpleDateFormat[] dateFormats;
	private SynchronizedSimpleDateFormat[] timeFormats;
	private SynchronizedSimpleDateFormat[] dateTimeFormats;
	
	//Added to fix BE-9435 : anpatil
	private String installationHome;

	private GlobalConfiguration() {

	}

	void init(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		//Added to fix BE-9435 - anpatil
		installationHome = findInstallationHome(properties);		
		locale = new LocaleFinder(logger, (String) ConfigurationProperties.LOCALE_ID.getValue(properties)).getLocale();
		timeZone = new TimeZoneFinder(logger, (String) ConfigurationProperties.LOCALE_ID.getValue(properties)).getTimeZone();
		String formatSeparator = (String) ConfigurationProperties.FORMAT_DELIMITER.getValue(properties);
		dateFormats = parseDateFormats(properties,ConfigurationProperties.DATE_FORMATS,"config.invalid.datepattern", formatSeparator);
		timeFormats = parseDateFormats(properties, ConfigurationProperties.TIME_FORMATS, "config.invalid.timepattern", formatSeparator);
		dateTimeFormats = parseDateFormats(properties, ConfigurationProperties.DATE_TIME_FORMATS, "config.invalid.datetimepattern", formatSeparator);
	}

	public Locale getLocale() {
		return locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	@SuppressWarnings("unused")
	private SynchronizedSimpleDateFormat[] parseDateFormats(String invalidpatternkey, String formatSeparator, String dateFormatPatterns) {
		if (StringUtil.isEmptyOrBlank(dateFormatPatterns) == true) {
			return new SynchronizedSimpleDateFormat[] { new SynchronizedSimpleDateFormat(DateFormat.getInstance()) };
		}
		int idx = dateFormatPatterns.indexOf(formatSeparator);
		List<DateFormat> formats = new ArrayList<DateFormat>();
		while (idx != -1) {
			String possibleDatePattern = dateFormatPatterns.substring(0, idx);
			try {
				formats.add(new SynchronizedSimpleDateFormat(possibleDatePattern));
			} catch (IllegalArgumentException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(null, possibleDatePattern);
				String message = messageGenerator.getMessage(invalidpatternkey, args);
				logger.log(Level.WARN, message);
			}
			dateFormatPatterns = dateFormatPatterns.substring(idx + 1);
			idx = dateFormatPatterns.indexOf(formatSeparator);
		}
		try {
			formats.add(new SynchronizedSimpleDateFormat(dateFormatPatterns));
		} catch (IllegalArgumentException e) {
			MessageGeneratorArgs args = new MessageGeneratorArgs(null, dateFormatPatterns);
			String message = messageGenerator.getMessage(invalidpatternkey, args);
			logger.log(Level.WARN, message);
		}
		if (formats.isEmpty() == true) {
			return new SynchronizedSimpleDateFormat[] { new SynchronizedSimpleDateFormat(DateFormat.getInstance()) };
		}
		return formats.toArray(new SynchronizedSimpleDateFormat[formats.size()]);
	}
	
	private SynchronizedSimpleDateFormat[] parseDateFormats(Properties properties, PropertyKey propertyKey, String invalidpatternkey, String formatSeparator) {
		String defaultValue = (String) propertyKey.getDefaultValue();
		boolean defaultValueIsValid = !StringUtil.isEmptyOrBlank(defaultValue);
		String propertyValue = (String) propertyKey.getValue(properties);
		ArrayList<String> patterns = new ArrayList<String>();
		if (StringUtil.isEmptyOrBlank(propertyValue) == false){
			int idx = propertyValue.indexOf(formatSeparator);
			do {
				if (idx == -1){
					patterns.add(propertyValue);
				}
				else {
					patterns.add(propertyValue.substring(0, idx));
					propertyValue = propertyValue.substring(idx + 1);
					idx = propertyValue.indexOf(formatSeparator);
				}
			} while (idx != -1);
		}
		else if (defaultValueIsValid == false){
			throw new IllegalArgumentException("No values specified for "+propertyKey.getName());
		}
		if (patterns.contains(defaultValue) == false){
			patterns.add(defaultValue);
		}
		ArrayList<SynchronizedSimpleDateFormat> formats = new ArrayList<SynchronizedSimpleDateFormat>();
		for (String pattern : patterns) {
			try {
				formats.add(new SynchronizedSimpleDateFormat(pattern));
			} catch (IllegalArgumentException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(null, pattern);
				String message = messageGenerator.getMessage(invalidpatternkey, args);
				logger.log(Level.WARN, message);
			}
		}
		return formats.toArray(new SynchronizedSimpleDateFormat[formats.size()]);
	}
	
	//Added to fix BE-9435 - anpatil
	private String findInstallationHome(Properties properties) {
		for (String key : POSSIBLE_INSTALLATION_HOME_KEYS) {
			String value = properties.getProperty(key);
			if (StringUtil.isEmptyOrBlank(value) == true){
				logger.log(Level.DEBUG, "could not find a value for "+key);
			}
			else {
				logger.log(Level.DEBUG, "Found installation home using "+key);
				logger.log(Level.INFO, "Found installation home as "+value);
				return value;
			}
		}
		throw new IllegalStateException("Could not find installation home");
	}


	public SynchronizedSimpleDateFormat getDateFormat() {
		return dateFormats[0];
	}

	public SynchronizedSimpleDateFormat[] getDateFormats() {
		return dateFormats;
	}

	public SynchronizedSimpleDateFormat getTimeFormat() {
		return timeFormats[0];
	}

	public SynchronizedSimpleDateFormat[] getTimeFormats() {
		return timeFormats;
	}

	public SynchronizedSimpleDateFormat getDateTimeFormat() {
		return dateTimeFormats[0];
	}

	public SynchronizedSimpleDateFormat[] getDateTimeFormats() {
		return dateTimeFormats;
	}

	public Calendar getCalendar() {
		return Calendar.getInstance(timeZone, locale);
	}

	//Added to fix BE-9435 : anpatil
	public String getInstallationHome(){
		return installationHome;
	}
}
