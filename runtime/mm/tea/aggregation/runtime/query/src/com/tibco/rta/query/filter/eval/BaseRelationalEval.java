package com.tibco.rta.query.filter.eval;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * 
 * Some utility methods used in relational evaluations.
 *
 */

abstract public class BaseRelationalEval {
	
	public enum RelationalOperator {
		EQUALS,
		NEQUALS,
		NOTEQUALS,
		LESSTHAN,
		LESSTHAN_OR_EQUAL,
		GREATERTHAN,
		GREATERTHAN_OR_EQUAL,
	}

	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY.getCategory());

	protected BigDecimal getFilterValue(Object filterValue) {
		BigDecimal val = null; 
		if (filterValue instanceof BigDecimal) {
			val = (BigDecimal) filterValue;
		} else if (filterValue instanceof Number) {
			Number number = (Number) filterValue;
			if (number instanceof Double) {
				val = new BigDecimal(number.doubleValue());
			} else if (number instanceof Float) {
				val = new BigDecimal(number.floatValue());
			} else if (number instanceof Long) {
				val = new BigDecimal(number.longValue());
			} else if (number instanceof Integer) {
				val = new BigDecimal(number.intValue());
			} else if (number instanceof Short) {
				val = new BigDecimal(number.shortValue());
			} else if (number instanceof Byte) {
				val = new BigDecimal(number.byteValue());
			} else {
				val = new BigDecimal(0D);
			}
		} else {
			val = new BigDecimal(0D);
		}
		return val;
	}

	protected boolean evalComparision(RelationalOperator operator, int comparision) {
		switch (operator) {
		case EQUALS:
			return comparision == 0;
		case NEQUALS:
			return comparision != 0;
		case LESSTHAN:
			return comparision < 0;
		case LESSTHAN_OR_EQUAL:
			return comparision <= 0;
		case GREATERTHAN:
			return comparision > 0;
		case GREATERTHAN_OR_EQUAL:
			return comparision >= 0;
		case NOTEQUALS:
			return comparision != 0;
		default:
			return false;
		}
	}
	
	protected boolean evalComparables(RelationalOperator operator,
			Object value, Object filterValue) {
		if (value == null && filterValue == null) {
			return true;
		} else if (value instanceof Comparable && filterValue != null) {
			Comparable comparable = (Comparable) value;
			int comparision = 0;
			if (comparable instanceof BigDecimal) {
				comparision = comparable.compareTo(getFilterValue(filterValue));
			} else {
				comparision = comparable.compareTo(filterValue);
			}
			return evalComparision(operator, comparision);
		} else {
			return false;
		}
	}
	
	protected boolean isInList(Object value, List<Object>filterValues) {
		for (Object filterValue : filterValues) {
			if (value.equals(filterValue)) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isPatternMatch(String regEx, String patternToMatch) {
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(patternToMatch);
		return matcher.find();
	}
}
