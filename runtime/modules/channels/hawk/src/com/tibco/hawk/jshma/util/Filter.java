// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.util;

import java.text.DateFormat;
import java.util.Date;

import COM.TIBCO.hawk.utilities.trace.Trace;

/**
 * This class is used for filtering purpose. Typically, apllications would use
 * multiple of these to form an "AND" filtering or "OR" filtering. Each Filter
 * instance has three components. First, the Filter name (a String), which can
 * be used to match against application column names or any application data
 * element. The second and the third are operator and operand. They are used to
 * validate the Filter test condition. For example, with an operator ">" and an
 * operand 3 can used to validate whether the application data is > 3. There
 * four kinds of Filters - String, Number, Date, and Booolean Filters. Each of
 * them has a dedicate constructor and a separate set of operators.
 * 
 */
public class Filter {
	private static String sClassName = Filter.class.getName();
	private static final int UNKNOWN = -1;
	private static final int EQUAL = 0;
	private static final int NOT_EQUAL = 1;
	private static final int GREATER = 2;
	private static final int SMALLER = 3;
	private static final int GREATER_OR_EQUAL = 4;
	private static final int SMALLER_OR_EQUAL = 5;
	private static final int NUMBER_OPERATOR_UPPER_BOUND = 5;
	private static final int EQUALS = 6;
	private static final int NOT_EQUALS = 7;
	private static final int CONTAIN = 8;
	private static final int NOT_CONTAIN = 9;
	private static final int MATCH = 10;
	private static final int NOT_MATCH = 11;
	private static final int STRING_OPERATOR_UPPER_BOUND = 11;
	private static final int DATE_EQUAL = 12;
	private static final int DATE_NOT_EQUAL = 13;
	private static final int DATE_GREATER = 14;
	private static final int DATE_SMALLER = 15;
	private static final int DATE_GREATER_OR_EQUAL = 16;
	private static final int DATE_SMALLER_OR_EQUAL = 17;
	private static final int DATE_OPERATOR_UPPER_BOUND = 17;
	private static final int BOOLEAN_EQUAL = 18;

	private int mOperatorType = UNKNOWN;
	private String mName = null;
	private Number mNumberOperand = null;
	private String mStringOperand = null;
	private Date mDateOperand = null;
	private Boolean mBooleanOperand = null;

	/**
	 * Number Filter Constructor.
	 * 
	 * @param name
	 *            Filter name.
	 * @param operator
	 *            Filter operator. Valid operators include: "==" (or "is"), "!="
	 *            (or "is not"), ">", ">=", "<", "<="
	 * @param operand
	 *            Filter operand.
	 */
	public Filter(String name, String operator, Number operand) {
		LastError.clear();
		Trace t = ContextControl.getTrace();
		t.log(Trace.DEBUG, "Filter Name=" + name + " operator=" + operator + " operand=" + operand);
		if (name == null || name.equals("")) {
			LastError.set("Filter name can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operator == null) {
			LastError.set("Filter operator can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operand == null) {
			LastError.set("Filter operand can not be null.", sClassName + ".Filter()");
			return;
		}

		if (operator.equals("==") || operator.equalsIgnoreCase("is"))
			mOperatorType = EQUAL;
		else if (operator.equals(">"))
			mOperatorType = GREATER;
		else if (operator.equals("<"))
			mOperatorType = SMALLER;
		else if (operator.equals(">="))
			mOperatorType = GREATER_OR_EQUAL;
		else if (operator.equals("<="))
			mOperatorType = SMALLER_OR_EQUAL;
		else if (operator.equals("!=") || operator.equalsIgnoreCase("is not"))
			mOperatorType = NOT_EQUAL;
		else {
			LastError.set("Invalid operator '" + operator + "'", sClassName + ".Filter()");
			return;
		}

		mName = name;
		mNumberOperand = operand;
	}

	/**
	 * String Filter Constructor.
	 * 
	 * @param name
	 *            Filter name.
	 * @param operator
	 *            Filter operator. Valid operators include: "equals" (or "is"),
	 *            "!equals" (or "is not"), "contains", "!contains" (or
	 *            "not contains"), "matches", (or "not matches")
	 * @param operand
	 *            Filter operand.
	 */
	public Filter(String name, String operator, String operand) {
		LastError.clear();
		if (name == null || name.equals("")) {
			LastError.set("Filter name can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operator == null) {
			LastError.set("Filter operator can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operand == null) {
			LastError.set("Filter operand can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operator.equalsIgnoreCase("equals") || operator.equalsIgnoreCase("is"))
			mOperatorType = EQUALS;
		else if (operator.equalsIgnoreCase("!equals") || operator.equalsIgnoreCase("is not"))
			mOperatorType = NOT_EQUALS;
		else if (operator.equalsIgnoreCase("contain") || // for backward
															// compatability
				operator.equalsIgnoreCase("contains"))
			mOperatorType = CONTAIN;
		else if (operator.equalsIgnoreCase("!contains") || operator.equalsIgnoreCase("not contains"))
			mOperatorType = NOT_CONTAIN;
		else if (operator.equalsIgnoreCase("matches"))
			mOperatorType = MATCH;
		else if (operator.equalsIgnoreCase("!matches") || operator.equalsIgnoreCase("not matches"))
			mOperatorType = NOT_MATCH;
		else {
			LastError.set("Invalid operator '" + operator + "'", sClassName + ".Filter()");
			return;
		}

		mName = name;
		mStringOperand = operand;
	}

	/**
	 * Date Filter Constructor.
	 * 
	 * @param name
	 *            Filter name.
	 * @param operator
	 *            Filter operator. Valid operators include: "==" (or "is"), "!="
	 *            (or "is not"), ">" (or "after"), "<" (or "before"), "<="
	 * @param operand
	 *            Filter operand.
	 */
	public Filter(String name, String operator, Date operand) {
		LastError.clear();
		Trace t = ContextControl.getTrace();
		t.log(Trace.DEBUG, "Filter Name=" + name + " operator=" + operator + " operand=" + operand);
		if (name == null || name.equals("")) {
			LastError.set("Filter name can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operator == null) {
			LastError.set("Filter operator can not be null.", sClassName + ".Filter()");
			return;
		}
		if (operand == null) {
			LastError.set("Filter operand can not be null.", sClassName + ".Filter()");
			return;
		}

		if (operator.equals("==") || operator.equalsIgnoreCase("is"))
			mOperatorType = DATE_EQUAL;
		else if (operator.equals(">") || operator.equalsIgnoreCase("after"))
			mOperatorType = DATE_GREATER;
		else if (operator.equals("<") || operator.equalsIgnoreCase("before"))
			mOperatorType = DATE_SMALLER;
		else if (operator.equals(">="))
			mOperatorType = DATE_GREATER_OR_EQUAL;
		else if (operator.equals("<="))
			mOperatorType = DATE_SMALLER_OR_EQUAL;
		else if (operator.equals("!=") || operator.equalsIgnoreCase("is not"))
			mOperatorType = DATE_NOT_EQUAL;
		else {
			LastError.set("Invalid operator '" + operator + "'", sClassName + ".Filter()");
			return;
		}

		mName = name;
		mDateOperand = operand;
	}

	/**
	 * Boolean Filter Constructor. The operator is fixed to be "equals".
	 * 
	 * @param name
	 *            Filter name.
	 * @param booleanOperand
	 *            Filter operand.
	 */
	public Filter(String name, String booleanOperand) {
		LastError.clear();
		if (name == null || name.equals("")) {
			LastError.set("Filter name can not be null.", sClassName + ".Filter()");
			return;
		}
		if (booleanOperand == null) {
			LastError.set("Filter booleanOperand can not be null.", sClassName + ".Filter()");
			return;
		}

		try {
			mBooleanOperand = new Boolean(booleanOperand);
		} catch (Exception e) {
			LastError.set("Invalid boolean operand '" + booleanOperand + "'", sClassName + ".Filter()");
			return;
		}

		mName = name;
		mOperatorType = BOOLEAN_EQUAL;
	}

	/**
	 * Get the Filter name.
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Validate the given value according to the Filter test condition (the
	 * Filter operator and operand).
	 * 
	 * @param value
	 *            the value to be tested
	 */
	public boolean validate(Object value) {
		if (value == null || mOperatorType == UNKNOWN)
			return false;

		LastError.clear();
		Number nValue = null;
		String sValue = null;
		Boolean bValue = null;
		Date dValue = null;
		if (mOperatorType >= 0 && mOperatorType <= NUMBER_OPERATOR_UPPER_BOUND) {
			if (value instanceof Number)
				nValue = (Number) value;
			else if (value instanceof String) {
				try {
					// Best shot
					nValue = new Double((String) value);
				} catch (Exception e) {
				}
			}
			if (nValue == null) {
				LastError.set("Invalid value '" + value + "' to be filtered with a numeric operator", sClassName
						+ ".validate()");
				return false;
			}

			switch (mOperatorType) {
			case EQUAL:
				return nValue.doubleValue() == mNumberOperand.doubleValue();
			case NOT_EQUAL:
				return nValue.doubleValue() != mNumberOperand.doubleValue();
			case GREATER:
				return nValue.doubleValue() > mNumberOperand.doubleValue();
			case SMALLER:
				return nValue.doubleValue() < mNumberOperand.doubleValue();
			case GREATER_OR_EQUAL:
				return nValue.doubleValue() >= mNumberOperand.doubleValue();
			case SMALLER_OR_EQUAL:
				return nValue.doubleValue() <= mNumberOperand.doubleValue();
			}

		} else if (mOperatorType > NUMBER_OPERATOR_UPPER_BOUND && mOperatorType <= STRING_OPERATOR_UPPER_BOUND) {
			sValue = value.toString();
			switch (mOperatorType) {
			case EQUALS:
				return sValue.equals(mStringOperand);
			case NOT_EQUALS:
				return (!sValue.equals(mStringOperand));
			case CONTAIN:
				return sValue.indexOf(mStringOperand) >= 0;
			case NOT_CONTAIN:
				return sValue.indexOf(mStringOperand) < 0;
				// TODO
			case MATCH:
				return PerlUtil.match(mStringOperand, sValue);
			case NOT_MATCH:
				return (!PerlUtil.match(mStringOperand, sValue));
			}
		} else if (mOperatorType > STRING_OPERATOR_UPPER_BOUND && mOperatorType <= DATE_OPERATOR_UPPER_BOUND) {
			if (value instanceof Date)
				dValue = (Date) value;
			else if (value instanceof String) {
				try {
					// Best shot
					dValue = DateFormat.getDateTimeInstance().parse((String) value);
				} catch (Exception e) {
				}
			}
			if (dValue == null) {
				LastError.set("Invalid value '" + value + "' to be filtered with a Date operator", sClassName
						+ ".validate()");
				return false;
			}

			int n = dValue.compareTo(mDateOperand);
			switch (mOperatorType) {
			case DATE_EQUAL:
				return n == 0;
			case DATE_NOT_EQUAL:
				return n != 0;
			case DATE_GREATER:
				return n > 0;
			case DATE_SMALLER:
				return n < 0;
			case DATE_GREATER_OR_EQUAL:
				return n >= 0;
			case DATE_SMALLER_OR_EQUAL:
				return n <= 0;
			}

		} else {
			if (value instanceof Boolean)
				bValue = (Boolean) value;
			else if (value instanceof String) {
				try {
					// Best shot
					bValue = new Boolean((String) value);
				} catch (Exception e) {
				}
			}
			if (bValue == null) {
				LastError.set("Invalid value '" + value + "' to be filtered with a boolean operator", sClassName
						+ ".validate()");
				return false;
			}
			return bValue.equals(mBooleanOperand);
		}

		/*
		 * Trace t = ContextControl.getTrace(); t.log(Trace.DEBUG,
		 * "mOperatorType = " + mOperatorType + " nValue=" + nValue + " vs " +
		 * mNumberOperand + " sValue=" + sValue + " vs " + mStringOperand +
		 * " bValue=" + bValue + " vs " + mBooleanOperand);
		 */

		return false;
	}
}
