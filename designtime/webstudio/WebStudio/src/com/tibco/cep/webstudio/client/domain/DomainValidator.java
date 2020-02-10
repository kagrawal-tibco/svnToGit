package com.tibco.cep.webstudio.client.domain;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableConstants.BE_DATE_TIME_FORMAT;

import java.util.Date;

import com.tibco.cep.webstudio.client.domain.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.webstudio.client.domain.model.Domain;

public class DomainValidator {
		
	public static boolean isValidSingleValue(Domain domain, String singleValue) {
		boolean isValid = false;
		DOMAIN_DATA_TYPES domainDataType = DOMAIN_DATA_TYPES.get(domain.getDataType());
		switch(domainDataType) {
		case STRING:
			isValid = (singleValue != null && !singleValue.isEmpty());
			break;
		case INTEGER:
			isValid = isNumeric(singleValue);
			break;
		case LONG:
			isValid = isLong(singleValue);
			break;
		case DOUBLE:
			isValid = isDouble(singleValue);
			break;				
		case DATE_TIME:
			isValid = isDate(singleValue);
			break;
		case BOOLEAN:
			isValid = true;
			break;
		}		
		return isValid;
	}

	public static boolean isValidRangeValue(Domain domain, String lowerValue, String upperValue) {
		boolean isValid = false;
		DOMAIN_DATA_TYPES domainDataType = DOMAIN_DATA_TYPES.get(domain.getDataType());
		switch(domainDataType) {
		case INTEGER:
			isValid = (isNumeric(lowerValue) && isNumeric(upperValue));
			if (isValid) {
				int lowerIntValue = Integer.parseInt(lowerValue, 10);
				int upperIntValue = Integer.parseInt(upperValue, 10);
				isValid = (lowerIntValue < upperIntValue);
			}
			break;
		case LONG:
			isValid = (isLong(lowerValue) && isLong(upperValue));
			if (isValid) {
				long lowerLongValue = Long.parseLong(lowerValue, 10);
				long upperLongValue = Long.parseLong(upperValue, 10);
				isValid = (lowerLongValue < upperLongValue);
			}
			break;
		case DOUBLE:
			isValid = (isDouble(lowerValue) && isDouble(upperValue));
			if (isValid) {
				double lowerDoubleValue = Double.parseDouble(lowerValue);
				double upperDoubleValue = Double.parseDouble(upperValue);
				isValid = (lowerDoubleValue < upperDoubleValue);
			}
			break;				
		case DATE_TIME:
			isValid = (isDate(lowerValue) && isDate(upperValue));
			if (isValid) {
				Date lowerDateValue = BE_DATE_TIME_FORMAT.parseStrict(lowerValue.trim());
				Date upperDateValue = BE_DATE_TIME_FORMAT.parseStrict(upperValue.trim());
				isValid = lowerDateValue.before(upperDateValue);
			}
			break;				
		}		
		return isValid;
	}

	public static boolean isNumeric(String checkStr) {
		try {
			if (checkStr != null && !checkStr.trim().isEmpty()){
				Integer.parseInt(checkStr, 10);
				return true;
			}
		} catch (NumberFormatException err) {
			//do nothing
		}
		return false;
	}
		
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isLong(String checkStr) {
		try {
			if (checkStr != null && !checkStr.trim().isEmpty()){
				Long.parseLong(checkStr, 10);
				return true;
			}
		} catch (NumberFormatException err) {
			//do nothing
		}
		return false;
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isDouble(String checkStr) {
		try {
			if (checkStr != null && !checkStr.trim().isEmpty()) {
				Double.parseDouble(checkStr);
				return true;
			}
		} catch (NumberFormatException err) {
			//do nothing
		}	
		return false;
	}
	
	public static boolean isDate(String checkStr) {
		try {
			if (!checkStr.trim().isEmpty()) {
				Date date = BE_DATE_TIME_FORMAT.parseStrict(checkStr.trim());
				if (date != null)
					return true;
			}
		} catch (Exception err) {
			//do nothing
		}
		return false;
	}
}
