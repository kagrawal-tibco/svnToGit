/**
 * 
 */
package com.tibco.cep.designtime.core.model.domain.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;

/**
 * @author aathalye
 *
 */
public class DomainUtils {
	
	public static final String plus = "+";
	public static final String minus = "-";
	public static final String Digits     = "(\\p{Digit}+)";
	public static final String HexDigits  = "(\\p{XDigit}+)";
	// an exponent is 'e' or 'E' followed by an optionally 
	// signed decimal integer.
	public static final String Exp        = "[eE][+-]?"+Digits;
	public static final String fpRegex    =
			("[\\x00-\\x20]*"+  // Optional leading "whitespace"
					"[+-]?(" + // Optional sign character
					"NaN|" +           // "NaN" string
					"Infinity|" +      // "Infinity" string

					// A decimal floating-point string representing a finite positive
					// number without a leading sign has at most five basic pieces:
					// Digits . Digits ExponentPart FloatTypeSuffix
					// 
					// Since this method allows integer-only strings as input
					// in addition to strings of floating-point literals, the
					// two sub-patterns below are simplifications of the grammar
					// productions from the Java Language Specification, 2nd 
					// edition, section 3.10.2.

					// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
					"((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

					// . Digits ExponentPart_opt FloatTypeSuffix_opt
					"(\\.("+Digits+")("+Exp+")?)|"+

					// Hexadecimal strings
					"((" +
					// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
					"(0[xX]" + HexDigits + "(\\.)?)|" +

					// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
					"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

					")[pP][+-]?" + Digits + "))" +
					"[fFdD]?))" +
			"[\\x00-\\x20]*");// Optional trailing "whitespace"
	 /**
     * Binary search of a {@link DomainEntry} in a {@link EList}.
     * @param list
     * @param key
     * @return non-negative integer if found
     */
    public static int binarySearch(EList<DomainEntry> list, DomainEntry key) {
		int low = 0;
		
		int high = list.size() - 1;
	
		while (low <= high) {
		    int mid = (low + high) >> 1;
		
			DomainEntry midVal = list.get(mid);
		    int cmpResult = midVal.compareTo(key);
	
		    if (cmpResult < 0)
			low = mid + 1;
		    else if (cmpResult > 0)
			high = mid - 1;
		    else
			return mid; // key found
		}
		return -(low + 1);  // key not found
    }

	/**
	 * @param domain
	 * @param domainEntries
	 * @param isTestData
	 * @return
	 */
	public static List<String> getValArray(Domain domain, List<DomainEntry> domainEntries, boolean isTestData) {
		if (domain == null || domainEntries == null) {
			return new ArrayList<String>(0);
		}
		List<DomainEntry> entries = domainEntries;		
		if (entries.size() != 0) {
			List<String> values = new ArrayList<String>();
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);				
				if (entry instanceof Single) {
					Object value = ((Single)entry).getValue();
					String str = value.toString();
					values.add(str);
					if (domainEntries != null) {
						domainEntries.add(entry);
					}
				} else if (entry instanceof Range && !isTestData) {
					String str = getStringValueFromRangeInfo((Range)entry);
					values.add(str);
					if(domainEntries != null) domainEntries.add(entry);
				}
			}
			return values;
		}
		return new ArrayList<String>(0);
	}
	
	/**
	 * @param range
	 * @return
	 */
	public static String getStringValueFromRangeInfo(Range range){
		String lower = range.getLower();
		String upper = range.getUpper();
		boolean includeLower = range.isLowerInclusive();
		boolean includeUpper = range.isUpperInclusive();
		String lowerExp = "Undefined"
				.equalsIgnoreCase(lower) ? ""
						: (includeLower ? ">= " : "> ")
					+ lower;
		String upperExp = "Undefined"
				.equalsIgnoreCase(upper) ? ""
						: (includeUpper ? "<= " : "< ")
						+ upper;		
		String str = upperExp.length() != 0
		&& lowerExp.length() != 0 ? lowerExp
				+ " && " + upperExp
				: (lowerExp.length() != 0 ? lowerExp
						: (upperExp.length() != 0 ? upperExp
								: ""));
		return str;
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				Integer.parseInt(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isNumeric(String checkStr, boolean isSign) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				if(isSign && (checkStr.startsWith("+")|| checkStr.startsWith("-"))){
					checkStr  = checkStr.substring(1);
					isNumeric(checkStr, false);
				}
				Integer.parseInt(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isDouble(String checkStr) {
		if (!checkStr.trim().equalsIgnoreCase("")){
			if(Pattern.matches(fpRegex, checkStr)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}

	/**
	 * @param checkStr
	 * @return
	 */
	public static boolean isLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase("")){
				Long.parseLong(checkStr,10);
				return true;
			}
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

}
