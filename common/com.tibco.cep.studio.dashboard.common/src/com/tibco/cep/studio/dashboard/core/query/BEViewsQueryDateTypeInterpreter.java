package com.tibco.cep.studio.dashboard.core.query;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BEViewsQueryDateTypeInterpreter {

	public static enum PRE_DEFINED_BINDS {
		YESTERDAY, STARTOFYESTERDAY, ENDOFYESTERDAY, TODAY, STARTOFDAY, ENDOFDAY, NOW
	};

	public static final SimpleDateFormat DATETIME_CONVERTOR = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static final SimpleDateFormat DATE_CONVERTOR = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat TIME_CONVERTOR = new SimpleDateFormat("HH:mm:ss.SSS");

	public static final SimpleDateFormat[] SUPPORTED_PATTERNS = new SimpleDateFormat[] { DATETIME_CONVERTOR, DATE_CONVERTOR, TIME_CONVERTOR };

	// ntamhank Fix for BE-11883 - possible to save a date in wrong format using Quick Edit in Browse
	// make strict parsing and compare parse position after parsing is over
	public static boolean isValidDate(String value) {
		for (SimpleDateFormat pattern : SUPPORTED_PATTERNS) {
			pattern.setLenient(false);
			ParsePosition position = new ParsePosition(0);
			Date date = pattern.parse(value, position);
			if (date != null && position.getErrorIndex() == -1 && position.getIndex() == value.length()) {
				return true;
			}
		}
		return false;
	}
	
	public static Date convert(String value) {
		for (SimpleDateFormat pattern : SUPPORTED_PATTERNS) {
			try {
				return pattern.parse(value);
			} catch (ParseException e1) {
			}
		}
		return null;
	}
	
	public static boolean isValidPredefinedBind(String value){
		return getPredefinedBind(value) != null;
	}
	
	public static PRE_DEFINED_BINDS getPredefinedBind(String value){
		if (value != null && value.trim().length() != 0 && value.charAt(0) == '#') {
			try {
				return PRE_DEFINED_BINDS.valueOf(value.substring(1));
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public static boolean isValidBindValue(String value) {
		return isValidPredefinedBind(value) || isValidDate(value);
	}

	public static Date convertBindValue(String value) {
		PRE_DEFINED_BINDS predefinedBind = getPredefinedBind(value);
		if (predefinedBind != null){
			return getDate(predefinedBind);
		}
		// we are not working with a predefined bind
		return convert(value);		
	}
	
	public static String convertBindValueToString(String value){
		if (isValidDate(value) == true){
			return value;
		}
		PRE_DEFINED_BINDS predefinedBind = getPredefinedBind(value);
		if (predefinedBind != null){
			Date date = getDate(predefinedBind);
			switch (predefinedBind) {
				case YESTERDAY:
				case TODAY:
					return DATE_CONVERTOR.format(date);
				case STARTOFDAY:
				case ENDOFDAY:
				case STARTOFYESTERDAY:
				case ENDOFYESTERDAY:
				case NOW:	
					return DATETIME_CONVERTOR.format(date);
				default:
					throw new IllegalArgumentException("Unknown predefined bind pattern ["+value+"]");
			}
		}		
		throw new IllegalArgumentException(value);
	}

	private static Date getDate(PRE_DEFINED_BINDS bind) {
		Calendar instance = Calendar.getInstance();
		switch (bind) {
			case YESTERDAY:
			case STARTOFYESTERDAY:
				instance.roll(Calendar.DAY_OF_YEAR, false);
				instance.set(Calendar.HOUR_OF_DAY, 0);
				instance.set(Calendar.MINUTE, 0);
				instance.set(Calendar.SECOND, 0);
				instance.set(Calendar.MILLISECOND, 0);				
				break;
			case TODAY:
			case STARTOFDAY:
				instance.set(Calendar.HOUR_OF_DAY, 0);
				instance.set(Calendar.MINUTE, 0);
				instance.set(Calendar.SECOND, 0);
				instance.set(Calendar.MILLISECOND, 0);
				break;
			case ENDOFDAY:
				instance.set(Calendar.HOUR_OF_DAY, 23);
				instance.set(Calendar.MINUTE, 59);
				instance.set(Calendar.SECOND, 59);
				instance.set(Calendar.MILLISECOND, 999);
				break;
			case ENDOFYESTERDAY:
				instance.roll(Calendar.DAY_OF_YEAR, false);
				instance.set(Calendar.HOUR_OF_DAY, 23);
				instance.set(Calendar.MINUTE, 59);
				instance.set(Calendar.SECOND, 59);
				instance.set(Calendar.MILLISECOND, 999);	
				break;
			case NOW:
				break;
			default:
				throw new IllegalArgumentException("Unknown predefined bind pattern ["+bind+"]");
		}		
		return instance.getTime();
	}
}
