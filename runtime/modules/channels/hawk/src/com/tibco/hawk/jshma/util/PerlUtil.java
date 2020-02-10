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

import java.util.Vector;

import com.oroinc.text.perl.Perl5Util;

/**
 * This class implements some frequently used Perl5 utilities
 */
public class PerlUtil {

	private static String sClassName = PerlUtil.class.getName();

	private static Perl5Util sPerl = null;
	private Perl5Util mPerl = null;

	public static final int SPLIT_ALL = Perl5Util.SPLIT_ALL;

	/**
	 * Constructor to instantiate a Perl5Util instance
	 */
	public PerlUtil() {
		mPerl = new Perl5Util();
	}

	/**
	 * Get the Perl5Util instance With a Perl5Util instance, you can invoke all
	 * the Perl5Util methods
	 * 
	 * @return Perl5Util instance
	 */
	public Perl5Util getPerl5Util() {
		return mPerl;
	}

	/**
	 * Check whether a specified pattern is valid Perl5 pattern
	 * 
	 * @param pattern
	 *            pattern in Perl5 native format: [m]/pattern/[i][m][s][x]. If
	 *            the pattern specified does not have any "/" chracter, then the
	 *            pattern is consider to be "/" + pattern + "/".
	 * @return true if the specified pattern is a valid Perl5 pattern
	 */
	public static boolean isValidPerlPattern(String pattern) {
		boolean result = false;
		if (sPerl == null)
			sPerl = new Perl5Util();
		try {
			if (pattern.indexOf('/') >= 0)
				sPerl.match(pattern, "foo");
			else
				sPerl.match("/" + pattern + "/", "foo");
			result = true;
		} catch (Exception ignore) {
			System.out.println(ignore);
		}
		return result;

	}

	/**
	 * Check whether a string matches the specified Perl5 pattern
	 * 
	 * @param pattern
	 *            pattern in Perl5 native format: [m]/pattern/[i][m][s][x]. If
	 *            the pattern specified does not have any "/" chracter, then the
	 *            pattern is consider to be "/" + pattern + "/".
	 * @param str
	 *            the String to be checked (against the pattern)
	 * @return true if the string matches the specified Perl5 pattern
	 */
	public static boolean match(String pattern, String str) {
		boolean result = false;
		if (sPerl == null)
			sPerl = new Perl5Util();
		try {
			if (pattern.indexOf('/') >= 0)
				result = sPerl.match(pattern, str);
			else
				result = sPerl.match("/" + pattern + "/", str);
		} catch (Exception ignore) {
			System.out.println(ignore);
		}
		return result;
	}

	/**
	 * Splits a String into a string array of length no greater than a specified
	 * limit. The String is split using a regular expression as the
	 * delimiteraking. The regular expressions is a pattern specified in Perl5
	 * native format: [m]/pattern/[i][m][s][x]
	 * 
	 * @param pattern
	 *            pattern in Perl5 native format: [m]/pattern/[i][m][s][x]. If
	 *            the pattern specified does not have any "/" chracter, then the
	 *            pattern is consider to be "/" + pattern + "/".
	 * @param input
	 *            the input String to be split
	 * @param limit
	 *            The limit on the length of the returned array. Values <= 0
	 *            produce the same behavior as the SPLIT_ALL constant which
	 *            causes the limit to be ignored and splits to be performed on
	 *            all occurrences of the pattern. You should use the SPLIT_ALL
	 *            constant to achieve this behavior instead of relying on the
	 *            default behavior associated with non-positive limit values
	 */
	public static String[] split(String pattern, String input, int limit) {
		LastError.clear();
		Vector v;
		String[] result = null;
		try {
			if (pattern.indexOf('/') < 0)
				v = sPerl.split("/" + pattern + "/", input, limit);
			else
				v = sPerl.split(pattern, input, limit);
			if (v != null)
				result = (String[]) VectorUtil.toUniformArray(v);
		} catch (Throwable th) {
			LastError.set("Failed to split '" + input + "' because of " + th, sClassName + ".split()", th);
		}
		return result;
	}

	/**
	 * This method is identical to calling:
	 * <code>  split(pattern, input, SPLIT_ALL);</code>
	 */
	public static String[] split(String pattern, String input) {
		return split(pattern, input, SPLIT_ALL);
	}

	/**
	 * This method is identical to calling:
	 * <code>  split("/\\s+/", input, SPLIT_ALL);</code>
	 */
	public static String[] split(String input) {
		return split("/\\s+/", input, SPLIT_ALL);
	}

	/**
	 * Substitutes a pattern in a given input with a replacement string. The
	 * substitution expression is specified in Perl5 native format: <br>
	 * <code> s/pattern/replacement/[g][i][m][o][s][x] </code>
	 * 
	 * @param expression
	 *            The substitution expression
	 * @param input
	 *            the input String to be substituted
	 */
	public static String substitute(String expression, String input) {
		LastError.clear();
		String result = null;
		try {
			result = sPerl.substitute(expression, input);
		} catch (Throwable th) {
			LastError.set("Failed to substitute on '" + input + "' because of " + th, sClassName + ".substitute()", th);
		}
		return result;
	}

}
