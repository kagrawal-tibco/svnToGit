package com.tibco.cep.driver.as.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

	protected static Pattern intRange = Pattern.compile("([\\d+]+)-([\\d+]+)"); //$NON-NLS-1$
	protected static Pattern endRange = Pattern.compile("([\\d+]+)-\\*");      //$NON-NLS-1$

	public static String unCapitalize(String str) {
		return toLowerCase(str, 0, 1);
	}

	public static String toLowerCase(String str, int beginOffset, int length) {
		char[] strChars = str.toCharArray();
		StringBuffer newStrBuffer = new StringBuffer();
		for (int i=0; i<str.length(); i++) {
			if (i>=beginOffset && i<(beginOffset+length)) {
				newStrBuffer.append(String.valueOf(strChars[i]).toLowerCase());
			} else {
				newStrBuffer.append(strChars[i]);
			}
		}
		return newStrBuffer.toString();
	}

	public static String capitalize(String str) {
		return toUpperCase(str, 0, 1);
	}

	public static String toUpperCase(String str, int beginOffset, int length) {
		char[] strChars = str.toCharArray();
		StringBuffer newStrBuffer = new StringBuffer();
		for (int i=0; i<str.length(); i++) {
			if (i>=beginOffset && i<(beginOffset+length)) {
				newStrBuffer.append(String.valueOf(strChars[i]).toUpperCase());
			} else {
				newStrBuffer.append(strChars[i]);
			}
		}
		return newStrBuffer.toString();
	}

	public static Properties toProperties(String props) {
		Properties p = new Properties();
		StringReader sbr = new StringReader(props);
		try {
			p.load(sbr);
		}
		catch (IOException e) {
			throw new RuntimeException("Unexpected Exception Converting String to Properties.", e); //$NON-NLS-1$
		}
		return p;
	}

	public static void replaceStringInFile(String fileName, String targetString, String replacementString) {
		FileReader fileReader = null;
		FileWriter fileWriter = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(fileName);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			StringBuilder buff = new StringBuilder((int) file.length());
			char[] charBuff = new char[8092];
			while (true) {
				int charsRead = bufferedReader.read(charBuff);
				if (charsRead == -1) break;
				buff.append(charBuff, 0, charsRead);
			}
			bufferedReader.close();
			String output = buff.toString().replace(targetString, replacementString);
			fileWriter = new FileWriter(file, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(output);
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to replace string in file for "+fileName+".", e); //$NON-NLS-1$
		}
		finally {
			try {
				bufferedWriter.flush();
			}
			catch (Exception e) {
			}
			try {
				bufferedWriter.close();
			}
			catch (Exception e) {
			}
		}
	}

	@SuppressWarnings({ "unchecked", "null" })
	public static <E> E[] append(E[] array, E... newValues) {
		if (newValues == null || newValues.length < 1) return array;
		int newSize = array == null ? newValues.length : array.length + newValues.length;
		// this line fails if newValues[0] is null.

		E[] newArray = (E[]) Array.newInstance(newValues[0].getClass(), newSize);

		int offset = 0;
		if (!(array == null && array.length < 1)) {
			System.arraycopy(array, 0, newArray, 0, array.length);
			offset = array.length;
		}
		System.arraycopy(newValues, 0, newArray, offset, newValues.length);
		return newArray;
	}

	public static boolean anyMatches(String target, Collection<Pattern> filters) {
		if (target == null || filters == null || filters.size() < 1) return false;
		for (Pattern p : filters) {
			Matcher m = p.matcher(target);
			if (m.matches()) return true;
		}
		return false;
	}

	public static String flattenArray(Object delimeter, Object... array) {
		StringBuilder buff = new StringBuilder();
		String delm = delimeter == null ? "" : delimeter.toString(); //$NON-NLS-1$
		if (array != null) {
			for (Object o : array) {
				buff.append(o.toString()).append(delm);
			}
			buff.delete(buff.length() - delm.length(), buff.length());
		}
		return buff.toString();
	}

	public static int[] compileRange(String valuesStr) {
		Set<Integer> values = new TreeSet<Integer>();
		String[] fragments = valuesStr.split(","); //$NON-NLS-1$
		for (String frag : fragments) {
			frag = frag.trim();
			if (frag.contains("-")) //$NON-NLS-1$
			{
				Matcher rangeMatcher = intRange.matcher(frag);
				if (rangeMatcher.matches() && rangeMatcher.groupCount() == 2) {
					rangeMatcher.group();
					int f1 = Integer.parseInt(rangeMatcher.group(1));
					int f2 = Integer.parseInt(rangeMatcher.group(2));
					if (f1 == f2) {
						values.add(f1);
					}
					else {
						int start = f1 > f2 ? f2 : f1;
						int end = f1 > f2 ? f1 : f2;
						while (start <= end) {
							values.add(start);
							start++;
						}
					}
				}

			}
			else {
				try {
					if (!frag.endsWith("-*")) //$NON-NLS-1$
					{
						values.add(Integer.parseInt(frag.trim()));
					}
				}
				catch (Exception e) {

				}
			}

		}
		int[] valuesArr = new int[values.size()];
		int index = 0;
		for (Integer i : values) {
			valuesArr[index] = i;
			index++;
		}
		return valuesArr;
	}

	public static String buildFromRange(String valuesStr, String delimeter, String... dataCells) {
		StringBuilder values = new StringBuilder();
		String[] fragments = valuesStr.split(","); //$NON-NLS-1$
		for (String frag : fragments) {
			frag = frag.trim();
			Matcher rangeMatcher = intRange.matcher(frag);
			Matcher endMatcher = endRange.matcher(frag);
			if (rangeMatcher.matches() && rangeMatcher.groupCount() == 2) {
				rangeMatcher.group();
				int f1 = Integer.parseInt(rangeMatcher.group(1));
				int f2 = Integer.parseInt(rangeMatcher.group(2));
				if (f1 == f2) {
					values.append(dataCells[f1]).append(delimeter);
				}
				else {
					int start = f1 > f2 ? f2 : f1;
					int end = f1 > f2 ? f1 : f2;
					while (start <= end) {
						values.append(dataCells[start]).append(delimeter);
						start++;
					}
				}
			}
			else if (endMatcher.matches() && endMatcher.groupCount() == 1) {
				endMatcher.group();
				int f1 = Integer.parseInt(endMatcher.group(1));
				for (; f1 < dataCells.length; f1++) {
					values.append(dataCells[f1]).append(delimeter);
				}
			}
			else {
				try {
					values.append(dataCells[Integer.parseInt(frag.trim())]).append(delimeter);
				}
				catch (Exception e) {
				}
			}
		}
		values.deleteCharAt(values.length() - 1);
		return values.toString();
	}

	@SuppressWarnings("unchecked")
	public static <E> E[] removeEntry(E[] values, E target) {
		List<E> cleanedValues = new ArrayList<E>(values.length);
		for (E e : values) {
			if (!e.equals(target)) {
				cleanedValues.add(e);
			}
		}
		E[] array = (E[]) Array.newInstance(target.getClass(), cleanedValues.size());
		return cleanedValues.toArray(array);
	}

	@SuppressWarnings("unchecked")
	public static <E> E[] removeEntry(E[] values, Collection<E> targets) {
		if (targets == null || targets.size() < 1) return values;
		List<E> cleanedValues = new ArrayList<E>(values.length);
		for (E e : values) {
			if (!targets.contains(e)) {
				cleanedValues.add(e);
			}
		}
		E[] array = (E[]) Array.newInstance(targets.iterator().next().getClass(), cleanedValues.size());
		return cleanedValues.toArray(array);
	}

	public static void log(Object message) {
		System.out.println(message);
	}

	public static String clean(String aRegexFragment) {
		final StringBuilder result = new StringBuilder();

		final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			/*
			 * All literals need to have backslashes doubled.
			 */
			if (character == '.') {
				result.append("\\."); //$NON-NLS-1$
			}
			else if (character == '\n') {
				result.append("\\n"); //$NON-NLS-1$
			}
			else if (character == '\\') {
				result.append("\\\\"); //$NON-NLS-1$
			}
			else if (character == '?') {
				result.append("\\?"); //$NON-NLS-1$
			}
			else if (character == '*') {
				result.append("\\*"); //$NON-NLS-1$
			}
			else if (character == '+') {
				result.append("\\+"); //$NON-NLS-1$
			}
			else if (character == '&') {
				result.append("\\&"); //$NON-NLS-1$
			}
			else if (character == ':') {
				result.append("\\:"); //$NON-NLS-1$
			}
			else if (character == '{') {
				result.append("\\{"); //$NON-NLS-1$
			}
			else if (character == '}') {
				result.append("\\}"); //$NON-NLS-1$
			}
			else if (character == '[') {
				result.append("\\["); //$NON-NLS-1$
			}
			else if (character == ']') {
				result.append("\\]"); //$NON-NLS-1$
			}
			else if (character == '(') {
				result.append("\\("); //$NON-NLS-1$
			}
			else if (character == ')') {
				result.append("\\)"); //$NON-NLS-1$
			}
			else if (character == '^') {
				result.append("\\^"); //$NON-NLS-1$
			}
			else if (character == '$') {
				result.append("\\$"); //$NON-NLS-1$
			}
			else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return null == str || 0 == str.length();
	}

	public static boolean isBlank(String str) {
		if (isEmpty(str)) return true;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

}
