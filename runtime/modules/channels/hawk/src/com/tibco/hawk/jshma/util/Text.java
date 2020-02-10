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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Vector;

import COM.TIBCO.hawk.utilities.misc.StringTool;
import COM.TIBCO.hawk.utilities.misc.Strtok;

/**
 * This class provides many text manipulation utilities.
 */
public class Text {
	private static String sClassName = Text.class.getName();
	private static boolean showParseError = false;

	static {
		if (System.getProperty("hawk.showParseError") != null)
			showParseError = true;
	}

	/**
	 * Parse a string with a specified MessageFormat pattern, then return an
	 * array of elements that are selected among the parsed results.
	 * 
	 * @param src
	 *            the source string to be parsed
	 * @param selection
	 *            the selected element indexes It can be passed in through a
	 *            Java Number array (Number[]) or a JavaScript array, for
	 *            example, <code>new Array(2, 3, 5, 9)</code>.
	 * @param pattern
	 *            the java.text.MessageFormat pattern
	 * @return the result array
	 */
	public static Object[] parse(String src, Object selection, String pattern) {
		try {
			LastError.clear();

			MessageFormat mf = new MessageFormat((String) pattern);
			Object[] pResult = null;
			pResult = mf.parse((String) src);
			if (pResult != null) {
				// mf.parse() always return an array of size 10.
				int i = pResult.length - 1;
				for (; i >= 0; i--)
					if (pResult[i] != null)
						break;
				pResult = ArrayUtil.subarray(pResult, 0, i + 1);
				// System.out.println("pResult.length=" + pResult.length);
			}
			// System.out.println("src=" + src);
			return ArrayUtil.select(pResult, selection);
		} catch (Exception err) {
			String estr = "Failed to parse source:\n" + src + "\n" + "with pattern:\n" + pattern + "\n" + "because of "
					+ err;
			LastError.set(estr, sClassName + ".parse()");
			if (showParseError) {
				System.out.println(estr);
			}
			return null;
		}

	}

	/**
	 * Parse a string with a specified MessageFormat pattern, then return the
	 * first element in the parsed results.
	 * 
	 * @param src
	 *            the source string to be parsed
	 * @param pattern
	 *            the java.text.MessageFormat pattern
	 * @return the first element in the parsed results
	 */
	public static Object parse(String src, String pattern) {
		Integer[] selection = new Integer[] { new Integer(0) };
		Object[] r = parse(src, selection, pattern);
		return r == null ? null : r[0];
	}

	private static String[] cut(String src, int beginIndex, int endIndex, String delimiter, boolean includeRemaining) {

		if (src == null)
			return null;

		LastError.clear();
		if (beginIndex < 0) {
			LastError.set("The parameter 'beginIndex' can not be small than 0.", sClassName + ".cut()");
			return null;
		}
		if (beginIndex >= endIndex) {
			LastError.set("The parameter 'beginIndex' has to be smaller than 'endIndex'.", sClassName + ".cut()");
			return null;
		}

		int n = 0;
		Vector pResult = new Vector();
		String[] result = null;
		try {
			// System.out.println("src="+src+" delimiter="+delimiter);
			Strtok st = new Strtok(src);
			/*
            */
			String token = st.strtok(delimiter);
			int stopIndex = endIndex - 1;
			while (token != null) {
				// System.out.println("token="+token);
				if (n >= beginIndex && n < endIndex) {
					pResult.addElement(token);
					if (includeRemaining && n == stopIndex) {
						pResult.addElement(st.getRemaining());
						break;
					}
				}
				n++;
				token = st.strtok(delimiter);
			}
			result = new String[pResult.size()];
			pResult.copyInto(result);
		} catch (Throwable th) {
			LastError.set("Failed to parse :\n" + src + "\nbecause of " + th, sClassName + ".cut()", th);
			return null;
		}

		return result;
	}

	/**
	 * Cut a string based on the specified delimiter, then return a range of the
	 * elements in the result.
	 * 
	 * @param src
	 *            the source string to be cut
	 * @param delimiter
	 *            the delimiter. If it contains more than one character, all
	 *            characters are considered as delimiters.
	 * @param beginIndex
	 *            the begin index of the range selected
	 * @param endIndex
	 *            the end index of the range selected
	 * @param includeRemaining
	 *            it it is set to true, then all the remaining would be appended
	 *            as the last element in the result
	 * @return the result array
	 */
	public static String[] cut(String src, String delimiter, int beginIndex, int endIndex, boolean includeRemaining) {
		return cut(src, beginIndex, endIndex, delimiter, includeRemaining);
	}

	/*
	 * public static String[] cut(String src, String delimiter, int beginIndex,
	 * int lastIndex) { return cut(src, beginIndex, lastIndex, delimiter, true);
	 * }
	 * 
	 * public static String cut(String src, String delimiter, int index) {
	 * String[] pResult = cut(src, index, index+1, delimiter); if (pResult ==
	 * null || pResult.length != 1) return null; return pResult[0];
	 * 
	 * }
	 */

	/**
	 * Cut a string based on the specified delimiter, then return an array of
	 * elements that are selected among the cut results.
	 * 
	 * @param src
	 *            the source string to be cut
	 * @param delimiter
	 *            the delimiter. If it contains more than one character, all
	 *            characters are considered as delimiters.
	 * @param selection
	 *            the selected element indexes It can be passed in through a
	 *            Java Number array (Number[]) or a JavaScript array, for
	 *            example, <code>new Array(2, 3, 5, 9)</code>.
	 * @return the result array
	 */
	public static String[] cut(String src, String delimiter, Object selection) {
		String[] pResult = cut(src, delimiter, 0, 0x7fffffff, false);
		if (pResult == null || (!LastError.getWhy().equals("")))
			return null;

		if (selection == null)
			return pResult;
		else {
			Object[] r = ArrayUtil.select(pResult, selection);
			if (r == null)
				return null;
			String[] result = new String[r.length];
			for (int i = 0; i < r.length; i++)
				result[i] = (String) r[i];
			return result;
		}
	}

	/**
	 * Cut a string based on the specified delimiter, then return the result as
	 * an array.
	 * 
	 * @param src
	 *            the source string to be cut
	 * @param delimiter
	 *            the delimiter. If it contains more than one character, all
	 *            characters are considered as delimiters.
	 * @return the result array
	 */
	public static String[] cut(String src, String delimiter) {
		return cut(src, delimiter, null);
	}

	/**
	 * Replace all the "${<property name>}" in the source string with the
	 * property value associated with the property name.
	 * 
	 * @param s
	 *            the source string to be converted
	 * @param p
	 *            the Properties object contains all the Properties
	 * @return the converted result String
	 */
	public static String replaceVars(String s, Properties p) {

		if (s == null)
			return s;

		String result = "";
		String remaining = s;
		boolean inBracket = false;

		int pos;

		while (remaining.length() > 0) {
			if (!inBracket) {
				pos = remaining.indexOf("${");
				if (pos < 0) {
					result = result.concat(remaining);
					break;
				} else {
					inBracket = true;
					result = result.concat(remaining.substring(0, pos));
					remaining = remaining.substring(pos + 2);
				}
			} else {
				pos = remaining.indexOf("}");
				if (pos < 0) {
					result = result.concat("${" + remaining);
					break;
				} else {
					String var = remaining.substring(0, pos);
					String pVal = p.getProperty(var);
					if (pVal == null) {
						result = result.concat("${" + var + "}");
						// put it back
						// throw new Exception(
						// "Property '" + var + "' not defined.");
					} else
						result = result.concat(pVal);
					remaining = remaining.substring(pos + 1);
					inBracket = false;
				}
			}

		} // while

		return (result);

	}

	/**
	 * The content of the file (based on the specified directory name and file
	 * name) are read in as a String. Then, all the "${<property name>}" appear
	 * in the source string are replaced with the property value associated with
	 * the property name.
	 * 
	 * @param dirName
	 *            the directory name of the file
	 * @param fileName
	 *            the file name
	 * @param p
	 *            the Properties object contains all the Properties
	 * @param cacheIt
	 *            indicates whether the file content should be cached. The
	 *            default value is false.
	 * @return the converted result String
	 */
	public static String convert(String dirName, String fileName, Properties p, boolean cacheIt) {
		try {
			LastError.clear();
			String input = StringTool
					.file2str(dirName == null ? fileName : dirName + java.io.File.separator + fileName);
			return Text.replaceVars(input, p);
		} catch (Throwable e) {
			LastError.set("Failed to convert.", sClassName + ".convert()", e);
			return "";
		}

	}

	/**
	 * This method inserts a string into a specific location in the source
	 * string
	 * 
	 * @param index
	 *            the location index in the source string
	 * @param src
	 *            the source string
	 * @param tobeInserted
	 *            the string to be inserted
	 * @return the result string
	 */

	public static String insertAt(int index, String src, String tobeInserted) {
		if (src == null)
			return null;
		if (tobeInserted == null)
			return src;
		int len = src.length();
		if (len < index)
			return src;
		return src.substring(0, index) + tobeInserted + src.substring(index);

	}

	/*
	 * public static String substring(Object str, int beginIndex, int endIndex)
	 * { String s = str.toString(); if (endIndex <= 0) return
	 * s.substring(beginIndex); else return s.substring(beginIndex, endIndex);
	 * 
	 * }
	 */

	/**
	 * This method parses a string and substitute substring sub1 with substring
	 * sub2.
	 * 
	 * @param str
	 *            the string to be parsed
	 * @param sub1
	 *            the substring to be substitued
	 * @param sub2
	 *            the substring to substitue sub1
	 * @param firstOccurrenceOnly
	 *            if true, only the first occurrence of sub1 will be
	 *            substituted.
	 * @return the result string
	 */
	public static final String substitute(String str, String sub1, String sub2, boolean firstOccurrenceOnly) {

		if (str == null)
			return null;
		String s = str;
		int index;
		int l1 = sub1.length();
		StringBuffer result = new StringBuffer();
		if (l1 > 0)
			while ((index = s.indexOf(sub1)) >= 0) {
				result.append(s.substring(0, index));
				result.append(sub2);
				s = s.substring(index + l1);
				if (firstOccurrenceOnly)
					break;
			}
		result.append(s);

		return result.toString();
	}

	/**
	 * This method reads in a file and put the whole content into a String
	 * 
	 * @param filename
	 *            the name of the file to be read in
	 * @return a String of the file content
	 */

	public static final String file2str(String filename) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(filename);

		ByteArrayOutputStream outByteBuf = new ByteArrayOutputStream();
		byte[] buf = new byte[2048];
		int len;
		while ((len = in.read(buf)) >= 0)
			outByteBuf.write(buf, 0, len);
		in.close();
		return outByteBuf.toString();

	}

	/**
	 * This method writes a file based on the content of a String
	 * 
	 * @param filename
	 *            the name of the file to be written
	 * @param content
	 *            the file content
	 * @return void
	 */
	public static final void str2file(String filename, String content) throws IOException {
		FileOutputStream outStream = new FileOutputStream(filename);

		PrintWriter out = new PrintWriter(outStream);
		out.print(content);
		out.close();

	}

}
