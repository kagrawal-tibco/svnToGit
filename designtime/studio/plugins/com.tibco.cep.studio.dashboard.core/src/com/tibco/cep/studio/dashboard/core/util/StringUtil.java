package com.tibco.cep.studio.dashboard.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * various static useful methods (all are for String class)
 */
public class StringUtil {

	private static final String ELLIPSES = "...";

	/**
	 * return true if the string is either null or empty.
	 * <p>
	 *
	 * @param string
	 *            : The <code>String</code> to be tested
	 * @return true if <code>string</code> is either null or empty, otherwise
	 *         false.
	 */
	public static boolean isEmpty(String string) {
		return (string == null) || (string.trim().length() == 0);
	}

	/**
	 * return true if the string array is either null or empty.
	 * <p>
	 *
	 * @param string
	 *            : The <code>String array</code> to be tested
	 * @return true if <code>string array</code> is either null or empty,
	 *         otherwise false.
	 */
	public static boolean isEmpty(String[] stringArray) {
		return (stringArray == null) || (stringArray.length == 0);
	}

	// convenience method. takes the first character of delimiter as separator.
	public static String[] split(String str, String delimiter) {
		return split(str, delimiter.charAt(0));
	}

	/**
	 * Parse the given string into separate strings, using the given delimiter.
	 * <p>
	 *
	 * @param str
	 *            The string to split
	 * @param delimiter
	 *            The delimiter to use to split <code>str</code>
	 * @return An array of strings, each representing the separate pieces of
	 *         <code>str</code> that were separated by <code>delimiter</code>
	 */
	public static String[] split(String str, char delimiter) {
		// Numbers like 1,345.04 must be in dq's if delimiter is ','
		// Side effect: drops dq's from the string
		char dq = '"'; // this had better not be the delimiter!
		ArrayList<String> v = new ArrayList<String>();
		boolean insideDoubleQuote = false;
		StringBuffer token = new StringBuffer();
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == dq) {
				insideDoubleQuote = !insideDoubleQuote;
			}
			if (str.charAt(i) == delimiter) { // add the token
				if (!insideDoubleQuote) {
					// this is it - the delimiter appears outside dq's
					v.add(token.toString().trim());
					token = new StringBuffer();
					continue;
				}
			}
			if (str.charAt(i) != dq) { // strip off the dq's
				token.append(str.charAt(i));
			}
		}
		v.add(token.toString().trim()); // add the last token
		return (String[]) v.toArray((Object[]) new String[v.size()]);
	}

	/**
	 * Join a bunch of arguments with the specified separator to separate them.
	 *
	 * @param args
	 *            things to join
	 * @param sep
	 *            the separator string. Typically of length 1.
	 * @param quote
	 *            if non-null, quote each argument before joining with this
	 *            quote character.
	 */
	public static String join(Object[] args, String sep, char quote) {
		StringBuffer sb = new StringBuffer();
		String s = "";
		boolean doQuote = (quote != '\u0000');
		for (int i = 0; i < args.length; ++i) {
			String o = (args[i] == null) ? "" : String.valueOf(args[i]);
			sb.append(s);
			if (doQuote)
				sb.append(quote);
			sb.append(o);
			if (doQuote)
				sb.append(quote);
			s = sep;
		}
		return sb.toString();
	}

	public static String join(Object[] args, String sep) {
		return join(args, sep, '\u0000');
	}

	public static <T> String join(List<T> l, String sep) {
		return join(l.toArray(new Object[0]), sep, '\u0000');
	}

	/**
	 * Replace a fragment of the string with a new fragment. begin and end
	 * indicate the first and last characters of the fragment.
	 */
	public static String splice(String s, int begin, int end, String repl) {
		if (s == null || begin >= s.length() || end >= s.length() || begin > end) {
			return s;
		}
		return s.substring(0, begin) + repl + s.substring(end + 1);
	}

	/**
	 * Return the count of the charToBeCounted
	 *
	 * @param strData
	 *            String to investigate
	 * @param charToBeCounted
	 */
	public static int count(String strData, char charToCount) {
		int count = 0;
		for (int i = 0; i < strData.length(); i++) {
			if (strData.charAt(i) == charToCount) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Replace all occurences of <code>from</code> with <code>to</code> in
	 * <code>string</code>
	 * <p>
	 *
	 * @param string
	 *            The string to search and replace in
	 * @param from
	 *            The string to search for
	 * @param to
	 *            The string to replace <code>from</code> with
	 */
	public static String replaceAll(String string, String from, String to) {
		int i = string.indexOf(from);
		StringBuilder sb = new StringBuilder(string);
		while (i != -1){
			sb.replace(i,i+from.length(),to);
			string = sb.toString();
			i = string.indexOf(from, i+to.length());
		}
		return string;
//
//
//
//
//		int fromLength = from.length();
//		int foundIndex = string.indexOf(from);
//		while (foundIndex >= 0) {
//			int endIndex = foundIndex + fromLength;
//			StringBuffer oldsb = new StringBuffer(string);
//			StringBuffer newsb = oldsb.replace(foundIndex, endIndex, to);
//			string = newsb.toString();
//			foundIndex = string.indexOf(from);
//		}
//		return string;
	}

	/**
	 * Replace all occurences of <code>from</code> with <code>to</code> in
	 * <code>string</code>
	 * <p>
	 *
	 * @param string
	 *            The string to search and replace in
	 * @param replaceArray
	 *            An array of replacement values, each element in the array is a
	 *            pair of {from,to} values to replace
	 */
	public static String replaceAll(String string, String[][] replaceArray) {
		for (int x = 0; x < replaceArray.length; x++) {
			string = replaceAll(string, replaceArray[x][0], replaceArray[x][1]);
		}
		return string;
	}

	/**
	 * Removes all the space ' ' characters from a <code>string</code>
	 * <p>
	 *
	 * @param string
	 *            The string to search for spaces
	 * @return String New string that does not have any spaces in it
	 */
	public static String removeSpaces(String string) {
		int i;
		int j;
		char[] output = new char[string.length()];
		for (i = 0, j = 0; i < string.length();) {
			if (string.charAt(i) != ' ') {
				output[j++] = string.charAt(i++);
			} else {
				i++;
			}
		}
		while (j < i) {
			output[j++] = ' ';
		}
		return new String(output).trim();
	}

	// Capitalize name.
	public static String capitalizeName(String name) {
		if (name != null && name.length() > 0) {
			if (Character.isLowerCase(name.charAt(0))) {
				StringBuffer sb = new StringBuffer();
				sb.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1));
				return sb.toString();
			}
		}
		return name;
	}

	// Sentence capitalize and add spaces at word boundaries
	public static String makePrettyName(String name) {
		String tname = name.trim();
		if (tname.length() == 0) {
			return name;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toUpperCase(tname.charAt(0)));
		for (int i = 1; i < tname.length(); i++) {
			if (!Character.isLetterOrDigit(tname.charAt(i - 1))) {
				if (Character.isLetterOrDigit(tname.charAt(i))) {
					sb.append(Character.toUpperCase(tname.charAt(i)));
				}
				continue;
			}
			if (!Character.isLetterOrDigit(tname.charAt(i))) {
				sb.append(" ");
				continue;
			}
			if (Character.isUpperCase(tname.charAt(i))) {
				sb.append(" ");
			}
			sb.append(tname.charAt(i));
		}
		return sb.toString();
	}

	public static final String quote(String str) {
		return quote(str, '"');
	}

	public static final String quote(String str, char quoteChar) {
		// For now, assume that str doesn't contain quote char.
		return quoteChar + str + quoteChar;
	}

	public static String stripQuote(String str) {
		return stripQuote(str, '"');
	}

	public static String stripQuote(String str, char quote) {
		int len = str.length();

		if (len < 2)
			return str;

		if (str.charAt(0) == quote && str.charAt(len - 1) == quote)
			return str.substring(1, len - 1);
		if (str.charAt(0) == quote)
			return str.substring(1, len);
		if (str.charAt(len - 1) == quote)
			return str.substring(0, len - 1);
		return str;
	}

	public static String stripToLength(String str, int len) {
		int slen = str.length();

		if ((slen < 4) || (slen < len))
			return str;
		else
			return str.substring(0, len - 3) + StringUtil.ELLIPSES;
	}

	/**
	 * Given a string of the form
	 *
	 * <pre>
	 *  value,value,...
	 * </pre>
	 *
	 * return a List of these values.
	 */
	public static List<String> toList(String s) {
		return toList(s, ",");
	}

	public static List<String> toList(String s, String sep) {
		List<String> l = new ArrayList<String>();
		if (!isEmpty(s)) {
			// We can't use the split() method in this class
			// because it strips double quotes.
			StringTokenizer st = new StringTokenizer(s, sep);
			while (st.hasMoreTokens()) {
				l.add(st.nextToken().trim());
			}
		}
		return l;
	}

	/**
	 * Convert a List of string values (or String-able values) into a
	 * comma-separated string.
	 */
	public static String fromList(List<?> l) {
		return fromCollection(l.iterator(), "", ",", "");
	}

	public static String fromList(List<?> l, String prefix, String sep, String suffix) {
		return fromCollection(l.iterator(), prefix, sep, suffix);
	}

	public static String fromCollection(Iterator<?> iter, String prefix, String sep, String suffix) {
		StringBuffer sb = new StringBuffer();
		String mysep = "";
		if (iter != null) {
			while (iter.hasNext()) {
				sb.append(mysep).append(prefix).append(String.valueOf(iter.next())).append(suffix);
				mysep = sep;
			}
		}
		return sb.toString();
	}

	public static String fromSet(Set<Object> s) {
		return fromCollection(s.iterator(), "", ",", "");
	}

	public static String fromSet(Set<Object> s, String prefix, String sep, String suffix) {
		return fromCollection(s.iterator(), prefix, sep, suffix);
	}

	/**
	 * Given a string of the form
	 *
	 * <pre>
	 *  name=value,value;name=value,...
	 * </pre>
	 *
	 * return a Map of Lists, the names being the map keys, and each list
	 * containing the values.
	 */
	public static Map<String, List<String>> toMap(String s) {
		Map<String, List<String>> m = new LinkedHashMap<String, List<String>>();
		StringTokenizer st = new StringTokenizer(s, ";");
		while (st.hasMoreTokens()) {
			String ss = st.nextToken();
			int idx = ss.indexOf("=");
			if (idx >= 0) {
				m.put(ss.substring(0, idx), toList(ss.substring(idx + 1)));
			} else {
				throw new StringIndexOutOfBoundsException(idx);
			}
		}
		return m;
	}

	/**
	 * Convert a Map of List of string values (or String-able values) into a
	 * string of the form name=value,value;name=value,...
	 */
	public static String fromMap(Map<Object, Object> m) {
		return fromMap(m, "", ";", "");
	}

	/**
	 * Stringify a map. <prefix>name=value<sep>name=value<sep><suffix>
	 *
	 * @param m
	 * @param prefix
	 * @param sep
	 * @param suffix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String fromMap(Map<Object, Object> m, String prefix, String sep, String suffix) {
		StringBuffer sb = new StringBuffer(prefix);
		Iterator<Object> iter = m.keySet().iterator();
		String delim = "";
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object val = m.get(key);
			val = (val instanceof List) ? fromList((List<Object>) val) : val.toString();
			sb.append(delim).append(key).append("=").append(val);
			delim = sep;
		}
		sb.append(suffix);
		return sb.toString();
	}

	public static int indexOf(String v, String[] array, boolean ignoreCase) {
		if (array == null)
			return -1;
		for (int i = 0; i < array.length; ++i) {
			if (((ignoreCase && array[i].equalsIgnoreCase(v))) || ((!ignoreCase && array[i].equals(v))))
				return i;
		}
		return -1;
	}

	private static String sSpaces = "                                                                                ";

	public static String getSpaces(int count) {
		if (count < 0)
			throw new RuntimeException("Count is less than 0.");
		if (count < 80)
			return sSpaces.substring(0, count);

		StringBuffer sb = new StringBuffer(sSpaces.substring(0, 80));

		count -= 80;
		while (count-- > 0)
			sb.append(" ");

		return sb.toString();
	}

	private static String sEllipses = "................................................................................";

	public static String getEllipsis(int count) {
		if (count < 0)
			return "...";
		if (count < 80)
			return sEllipses.substring(0, count);

		StringBuffer sb = new StringBuffer(sEllipses.substring(0, 80));

		count -= 80;
		while (count-- > 0)
			sb.append(".");

		return sb.toString();
	}

	private static String sZeros = "00000000000000000000000000000000000000000000000000000000000000000000000000000000";

	public static String getZeros(int count) {
		if (count < 80)
			return sZeros.substring(0, count);

		StringBuffer sb = new StringBuffer(sZeros.substring(0, 80));

		count -= 80;
		while (count-- > 0)
			sb.append("0");

		return sb.toString();
	}

	/**
	 * Create a sub array of objects in the specified array, starting from start
	 * and containing length items.
	 *
	 * @param array
	 *            Original array. Undisturbed.
	 * @param start
	 *            Start index in original array. Will be 0 in the new array.
	 * @param length
	 *            How many to include. If -1, include all.
	 * @return
	 */
	public static String[] subArray(String[] array, int start, int length) {
		if (length == -1)
			length = array.length - start;
		if (length > (array.length - start))
			return new String[0];
		String[] newArray = new String[length];
		System.arraycopy(array, start, newArray, 0, length);
		return newArray;
	}

	public static String[] subArray(String[] array, int start) {
		return subArray(array, start, -1);
	}

}