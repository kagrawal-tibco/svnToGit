package com.tibco.cep.metric.evaluator;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ops {

	private static Map<String, Pattern> patternMap = new ConcurrentHashMap<String, Pattern>();

	public static boolean greaterThan(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) > getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) > getLong(rhs);
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return (((Calendar) lhs).compareTo((Calendar) rhs) > 0);
		} else {
			throw new Exception("greater than op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static boolean greaterThanEquals(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) >= getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) >= getLong(rhs);
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return (((Calendar) lhs).compareTo((Calendar) rhs) >= 0);
		} else if (lhs == null || rhs == null) {
			return lhs == rhs;
		} else {
			throw new Exception("greater than equals op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static boolean equals(final Object lhs, final Object rhs)
			throws Exception {
		if (checkBoolean(lhs) || checkBoolean(rhs)) {
			return ((Boolean) lhs).equals((Boolean) rhs);
		} else if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs).equals(getDouble(rhs));
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs).equals(getLong(rhs));
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return ((Calendar) lhs).compareTo((Calendar) rhs) == 0;
		} else if (lhs == null || rhs == null) {
			return lhs == rhs;
		} else if (checkChar(lhs) || checkChar(rhs)) {
			return (lhs.toString()).equals(rhs.toString());
		} else if (checkString(lhs) || checkString(rhs)) {
			return ((String) lhs).equals((String) rhs);
		} else {
			throw new Exception("equals op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static boolean lessThan(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) < getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) < getLong(rhs);
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return (((Calendar) lhs).compareTo((Calendar) rhs) < 0);
		} else {
			throw new Exception("less than op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static boolean lessThanEquals(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) <= getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) <= getLong(rhs);
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return (((Calendar) lhs).compareTo((Calendar) rhs) <= 0);
		} else if (lhs == null || rhs == null) {
			return lhs == rhs;
		} else {
			throw new Exception("less than equals op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static boolean notEquals(final Object lhs, final Object rhs)
			throws Exception {
		if (checkBoolean(lhs) || checkBoolean(rhs)) {
			return !((Boolean) lhs).equals((Boolean) rhs);
		} else if (checkDouble(lhs) || checkDouble(rhs)) {
			return !getDouble(lhs).equals(getDouble(rhs));
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return !getLong(lhs).equals(getLong(rhs));
		} else if (checkDate(lhs) || checkDate(rhs)) {
			return !(((Calendar) lhs).compareTo((Calendar) rhs) != 0);
		} else if (lhs == null || rhs == null) {
			return lhs != rhs;
		} else if (checkChar(lhs) || checkChar(rhs)) {
			return !(lhs.toString()).equals(rhs.toString());
		} else if (checkString(lhs) || checkString(rhs)) {
			return !((String) lhs).equals((String) rhs);
		} else {
			throw new Exception("not equals op not supported for ["
					+ lhs.getClass() + "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object minus(final Object obj) throws Exception {
		if (checkDouble(obj)) {
			return -1 * getDouble(obj);
		} else if (checkNumber(obj)) {
			return -1 * getLong(obj);
		} else {
			throw new Exception("negation op not supported for ["
					+ obj.getClass() + "]");
		}
	}

	public static Object abs(final Object obj) throws Exception {
		if (checkDouble(obj)) {
			return Math.abs(getDouble(obj));
		} else if (checkNumber(obj)) {
			return Math.abs(getLong(obj));
		} else {
			throw new Exception("abs op not supported for [" + obj.getClass()
					+ "]");
		}
	}

	public static Object concat(final Object lhs, final Object rhs)
			throws Exception {
		if (checkChar(lhs) || checkChar(rhs)) {
			return lhs.toString() + rhs.toString();
		} else if (checkString(lhs) || checkString(rhs)) {
			return lhs.toString() + rhs.toString();
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) + getLong(rhs);
		} else if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) + getDouble(rhs);
		} else {
			throw new Exception("concat op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object and(final Object lhs, final Object rhs)
			throws Exception {
		if (checkBoolean(lhs) || checkBoolean(rhs)) {
			return ((Boolean) lhs) && ((Boolean) rhs);
		} else {
			throw new Exception("and op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object minus(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) - getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) - getLong(rhs);
		} else {
			throw new Exception("minus op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object star(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) * getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) * getLong(rhs);
		} else {
			throw new Exception("star op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object slash(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) / getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) / getLong(rhs);
		} else {
			throw new Exception("slash op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object mod(final Object lhs, final Object rhs)
			throws Exception {
		if (checkDouble(lhs) || checkDouble(rhs)) {
			return getDouble(lhs) % getDouble(rhs);
		} else if (checkNumber(lhs) || checkNumber(rhs)) {
			return getLong(lhs) % getLong(rhs);
		} else {
			throw new Exception("mod op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	public static Object like(final Object lhs, final Object rhs)
			throws Exception {
		if (checkChar(lhs) || checkChar(rhs)) {
			if (lhs == null || rhs == null) {
				return lhs == rhs;
			}
			return likePatternMatch(lhs.toString(), rhs.toString());
		} else if (checkString(lhs) || checkString(rhs)) {
			return likePatternMatch((String) lhs, (String) rhs);
		} else {
			throw new Exception("like op not supported [" + lhs.getClass()
					+ "] and [" + rhs.getClass() + "]");
		}
	}

	private static Double getDouble(final Object obj) throws Exception {
		if (obj == null)
			throw new Exception("operand must be of type double");
		return Double.valueOf(obj.toString());
	}

	private static Long getLong(final Object obj) throws Exception {
		if (obj == null)
			throw new Exception("operand must be of type long");
		return Long.valueOf(obj.toString());
	}

	// TODO - Optimize this
	public static boolean checkDouble(final Object obj) {
		if (obj == null) {
			return false;
		} else {
			return ((Float.class.isAssignableFrom(obj.getClass()) || Double.class
					.isAssignableFrom(obj.getClass())));
		}
	}

	public static boolean checkNumber(final Object obj) {
		if (obj == null) {
			return false;
		} else {
			return (Number.class.isAssignableFrom(obj.getClass()));
		}
	}

	public static boolean checkString(final Object obj) {
		if (obj == null) {
			return true;
		} else {
			return (String.class.isAssignableFrom(obj.getClass()));
		}
	}

	public static boolean checkChar(final Object obj) {
		if (obj == null) {
			return true;
		} else {
			return (Character.class.isAssignableFrom(obj.getClass()));
		}
	}

	public static boolean checkBoolean(final Object obj) {
		if (obj == null) {
			return false;
		} else {
			return (Boolean.class.isAssignableFrom(obj.getClass()));
		}
	}

	public static boolean checkDate(final Object obj) {
		if (obj == null) {
			return false;
		} else {
			return (Calendar.class.isAssignableFrom(obj.getClass()));
		}
	}

	private static Object likePatternMatch(final String lhs, final String rhs) {
		Pattern pattern = patternMap.get(rhs);
		if (pattern == null) {
			pattern = Pattern.compile(rhs);
			patternMap.put(rhs, pattern);
		}
		Matcher matcher = pattern.matcher((String) lhs);
		return matcher.matches();
	}
}
