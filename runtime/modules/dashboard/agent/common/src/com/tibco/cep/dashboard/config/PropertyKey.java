package com.tibco.cep.dashboard.config;

import java.util.Date;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.utils.StringUtil;

public class PropertyKey {

	public static enum DATA_TYPE {
		String, Integer, Long, Float, Double, Date, Time, Boolean
	};

	private boolean hidden;

	private String name;

	private String description;

	private DATA_TYPE dataType;

	private Object defaultValue;

	private boolean isArray;

	private Object[] possibleValues;

	private String referencePropertyName;

	public PropertyKey(String name, String description, DATA_TYPE dataType, Object defaultValue) {
		this(name, description, dataType, defaultValue, null);
	}

	public PropertyKey(boolean hidden, String name, String description, DATA_TYPE dataType, Object defaultValue) {
		this(hidden, name, description, dataType, defaultValue, null);
	}

	public PropertyKey(String name, String description, DATA_TYPE dataType, Object defaultValue, String referencePropertyName) {
		this.name = name;
		this.description = description;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.referencePropertyName = referencePropertyName;
	}

	public PropertyKey(boolean hidden, String name, String description, DATA_TYPE dataType, Object defaultValue, String referencePropertyName) {
		this.hidden = hidden;
		this.name = name;
		this.description = description;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.referencePropertyName = referencePropertyName;
	}

	public PropertyKey(String name, String description, DATA_TYPE dataType, Object[] possibleValues, int defaultValueIdx) {
		this(name, description, dataType, possibleValues, defaultValueIdx, null);
	}

	public PropertyKey(boolean hidden, String name, String description, DATA_TYPE dataType, Object[] possibleValues, int defaultValueIdx) {
		this(hidden, name, description, dataType, possibleValues, defaultValueIdx, null);
	}

	public PropertyKey(String name, String description, DATA_TYPE dataType, Object[] possibleValues, int defaultValueIdx, String referencePropertyName) {
		this.name = name;
		this.description = description;
		this.dataType = dataType;
		this.isArray = true;
		this.possibleValues = possibleValues;
		if (defaultValue != null) {
			checkIfExists(defaultValue);
		}
		this.defaultValue = possibleValues[defaultValueIdx];
	}

	public PropertyKey(boolean hidden, String name, String description, DATA_TYPE dataType, Object[] possibleValues, int defaultValueIdx, String referencePropertyName) {
		this.hidden = hidden;
		this.name = name;
		this.description = description;
		this.dataType = dataType;
		this.isArray = true;
		this.possibleValues = possibleValues;
		if (defaultValue != null) {
			checkIfExists(defaultValue);
		}
		this.defaultValue = possibleValues[defaultValueIdx];
	}

	public boolean isHidden() {
		return hidden;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public DATA_TYPE getDataType() {
		return dataType;
	}

	public String getRawValue(Properties properties) {
		return properties.getProperty(name);
	}

	public Object getValue(Properties properties) {
		Object value = doGetValue(properties);
		if (isArray == true) {
			checkIfExists(value);
		}
		return value;
	}

	private Object doGetValue(Properties properties) {
		String value = properties.getProperty(name);
		//iff the value is null, we will check for reference property name
		if (value == null) {
			if (referencePropertyName != null) {
				value = properties.getProperty(referencePropertyName);
			}
		}
		//iff the value is null or empty , we return default value
		if (StringUtil.isEmptyOrBlank(value) == true){
			return defaultValue;
		}
		switch (dataType) {
			case String:
				return value;
			case Integer:
				try {
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			case Long:
				try {
					return Long.parseLong(value);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			case Float:
				try {
					return Float.parseFloat(value);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			case Double:
				try {
					return Double.parseDouble(value);
				} catch (NumberFormatException e) {
					return defaultValue;
				}
			case Boolean:
				return Boolean.parseBoolean(value);
			case Date:
				if (value == null) {
					return defaultValue;
				}
				if (value.equalsIgnoreCase("now") == true) {
					return new Date();
				}
				return BuiltInTypes.DATETIME.valueOf(value);
			case Time:
				if (value == null) {
					return defaultValue;
				}
				Long convertedValue = getTime(value);
				if (convertedValue == null) {
					return defaultValue;
				}
				return convertedValue;
			default:
				throw new UnsupportedOperationException(dataType + " not supported yet...");
		}
	}

	private boolean checkIfExists(Object value) {
		for (Object possibleValue : possibleValues) {
			if (possibleValue.equals(value) == true) {
				return true;
			}
		}
		throw new IllegalArgumentException(value + " is not part of enumeration for " + name);
	}

	private static Long getTime(String value) {
		long frequencyMultiplier;

		if (value.endsWith("s")) {
			frequencyMultiplier = 1000L;
		} else if (value.endsWith("m")) {
			frequencyMultiplier = 1000L * 60;
		} else if (value.endsWith("h")) {
			frequencyMultiplier = 1000L * 60 * 60;
		} else if (value.endsWith("d")) {
			frequencyMultiplier = 1000L * 60 * 60 * 24;
		} else if (value.endsWith("y")) {
			frequencyMultiplier = 1000L * 60 * 60 * 24 * 365;
		} else {
			return null;
		}
		try {
			return new Long(value.substring(0, value.length() - 1)) * frequencyMultiplier;
		} catch (Exception e) {
			return null;
		}
	}
}
