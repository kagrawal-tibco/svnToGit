package com.tibco.cep.studio.dashboard.core.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat.FORMAT_STYLE;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat.FORMAT_TYPE;

public class DisplayValueFormatProvider {

	private static Map<String, List<DisplayValueFormat>> PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS;

	private static Map<String, DisplayValueFormat> PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS;

	private static Map<String, DisplayValueFormat> PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS;

	static {

		// numeric display value formats
		DisplayValueFormat[] numericformats = new DisplayValueFormat[] {
				// unformatted
				new UnformattedDisplayValueFormat(),
				// default
				new DisplayValueFormat("Default", null, null),
				// integer
				new DisplayValueFormat("Integer(e.g. 12)", FORMAT_TYPE.NUMBER, FORMAT_STYLE.INTEGER),
				// currency
				new DisplayValueFormat("Currency(e.g. $12.00)", FORMAT_TYPE.NUMBER, FORMAT_STYLE.CURRENCY),
				// percent
				new DisplayValueFormat("Percent(e.g. 12%)", FORMAT_TYPE.NUMBER, FORMAT_STYLE.PERCENT),
				// pattern
				new DisplayValueFormat("Pattern", FORMAT_TYPE.NUMBER, FORMAT_STYLE.PATTERN) };

		// date-time display value formats
		DisplayValueFormat[] datetimeformats = new DisplayValueFormat[] {
				// unformatted
				new UnformattedDisplayValueFormat(),
				// default
				new DisplayValueFormat("Default", null, null),
				// short date
				new DisplayValueFormat("Short Date(e.g. 7/27/07)", FORMAT_TYPE.DATE, FORMAT_STYLE.SHORT),
				// medium date
				new DisplayValueFormat("Long Date(e.g. Jul 27, 2007)", FORMAT_TYPE.DATE, FORMAT_STYLE.MEDIUM),
				// full date
				new DisplayValueFormat("Full Date(e.g. Friday, July 27, 2007)", FORMAT_TYPE.DATE, FORMAT_STYLE.FULL),
				// short time
				new DisplayValueFormat("Short Time(e.g. 2:08 PM)", FORMAT_TYPE.TIME, FORMAT_STYLE.SHORT),
				// medium time
				new DisplayValueFormat("Long Time(e.g. 2:08:18 PM)", FORMAT_TYPE.TIME, FORMAT_STYLE.MEDIUM),
				// full time
				new DisplayValueFormat("Full Time(e.g. 2:08:18 PM UTC)", FORMAT_TYPE.TIME, FORMAT_STYLE.FULL),
				// pattern
				new DisplayValueFormat("Pattern", FORMAT_TYPE.DATE, FORMAT_STYLE.PATTERN) };

		// String & Boolean display value format
		DisplayValueFormat[] stringOrBooleanFormats = new DisplayValueFormat[] {
				// default
				new DisplayValueFormat("Default", null, null),
				new DisplayValueFormat("Pattern", null, FORMAT_STYLE.PATTERN)
		};

		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS = new HashMap<String, List<DisplayValueFormat>>();
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.STRING.getLiteral(), Arrays.asList(stringOrBooleanFormats));
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.INTEGER.getLiteral(), Arrays.asList(numericformats));
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.LONG.getLiteral(), Arrays.asList(numericformats));
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DOUBLE.getLiteral(), Arrays.asList(numericformats));
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DATE_TIME.getLiteral(), Arrays.asList(datetimeformats));
		PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.BOOLEAN.getLiteral(), Arrays.asList(stringOrBooleanFormats));

		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS = new HashMap<String, DisplayValueFormat>();
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.STRING.getLiteral(), stringOrBooleanFormats[0]);
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.INTEGER.getLiteral(), numericformats[1]);
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.LONG.getLiteral(), numericformats[1]);
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DOUBLE.getLiteral(), numericformats[1]);
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DATE_TIME.getLiteral(), datetimeformats[1]);
		PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.BOOLEAN.getLiteral(), stringOrBooleanFormats[0]);

		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS = new HashMap<String, DisplayValueFormat>();
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.STRING.getLiteral(), stringOrBooleanFormats[0]);
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.INTEGER.getLiteral(), numericformats[0]);
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.LONG.getLiteral(), numericformats[0]);
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DOUBLE.getLiteral(), numericformats[0]);
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.DATE_TIME.getLiteral(), datetimeformats[0]);
		PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.put(PROPERTY_TYPES.BOOLEAN.getLiteral(), stringOrBooleanFormats[0]);

	}

	public static List<DisplayValueFormat> getDisplayValueFormats(String type) {
		return PROPERTY_TYPE_SPECIFIC_DISPLAY_VALUE_FORMATS.get(type);
	}

	public static DisplayValueFormat getDefaultDisplayValueFormat(String type) {
		return PROPERTY_TYPE_SPECIFIC_DEFAULT_DISPLAY_VALUE_FORMATS.get(type);
	}

	public static DisplayValueFormat parse(String fieldName, String dataType, String displayValue) {
		if (displayValue == null || displayValue.trim().length() == 0) {
			return PROPERTY_TYPE_SPECIFIC_UNFORMATTED_DISPLAY_VALUE_FORMATS.get(dataType);
		}
		DisplayFormatParser parser = new DisplayFormatParser(displayValue);
		String[] formatTokens = parser.getArgumentDetails();
		//Modified by Anand to fix BE-11806 & BE-11706
		if (parser.isSimple() == false || formatTokens == null || parser.getArguments().contains(fieldName) == false) {
			//we are dealing with a complex display format (foo{bar}/{foo}bar/{foo}{bar})
			return search(dataType, deduceFormatType(dataType), FORMAT_STYLE.PATTERN);
		}
		if (formatTokens.length == 3) {
			// first token is field
			// second token is format type
			// third token is format style
			// e.g. {foo,number,currency} or {foo,number,##.##}
			FORMAT_TYPE formatType = DisplayValueFormat.FORMAT_TYPE.valueOf(formatTokens[1].toUpperCase());
			FORMAT_STYLE formatStyle = FORMAT_STYLE.PATTERN;
			try {
				formatStyle = DisplayValueFormat.FORMAT_STYLE.valueOf(formatTokens[2].toUpperCase());
			} catch (IllegalArgumentException e) {
				// ignore the exception and assume that the format style is a pattern
			}
			return search(dataType, formatType, formatStyle);
		} else if (formatTokens.length == 2) {
			// first token is field
			// second token is the format type
			// e.g. {foo,number}
			FORMAT_TYPE formatType = DisplayValueFormat.FORMAT_TYPE.valueOf(formatTokens[1].toUpperCase());
			return search(dataType, formatType, null);
		} else {
			return search(dataType, null, null);
		}
	}

	private static DisplayValueFormat search(String dataType, FORMAT_TYPE formatType, FORMAT_STYLE formatStyle) {
		for (DisplayValueFormat displayValueFormat : getDisplayValueFormats(dataType)) {
			if (displayValueFormat instanceof UnformattedDisplayValueFormat) {
				continue;
			}
			boolean sameFormatType = false;
			if (displayValueFormat.getFormatType() == null && formatType == null) {
				sameFormatType = true;
			} else if (displayValueFormat.getFormatType() != null && formatType != null) {
				sameFormatType = formatType.compareTo(displayValueFormat.getFormatType()) == 0;
			}
			boolean sameFormatStyle = false;
			if (displayValueFormat.getFormatStyle() == null && formatStyle == null) {
				sameFormatStyle = true;
			} else if (displayValueFormat.getFormatStyle() != null && formatStyle != null) {
				sameFormatStyle = formatStyle.compareTo(displayValueFormat.getFormatStyle()) == 0;
			}
			if (sameFormatType && sameFormatStyle) {
				return displayValueFormat;
			}
		}
		return null;
	}

	private static FORMAT_TYPE deduceFormatType(String dataType) {
		PROPERTY_TYPES type = PROPERTY_TYPES.get(dataType);
		switch (type) {
			case BOOLEAN:
			case STRING:
				return null;
			case DATE_TIME:
				return FORMAT_TYPE.DATE;
			case INTEGER:
			case DOUBLE:
			case LONG:
				return FORMAT_TYPE.NUMBER;
			default:
				throw new IllegalArgumentException(dataType);
		}
	}
}
