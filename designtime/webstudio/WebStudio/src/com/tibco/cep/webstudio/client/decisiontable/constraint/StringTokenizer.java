package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * StringTokenizer implementation mimicked since GWT does not support StringTokenizer
 */
public class StringTokenizer implements Enumeration<Object> {
	
	private int currentPosition;
	private int newPosition;
	private int maxPosition;
	private String str;
	private String delimiters;
	private boolean retDelims;
	private boolean delimsChanged;

	private int maxDelimCodePoint;

	private boolean hasSurrogates = false;

	private int[] delimiterCodePoints;
	
	private String string;
	private char delim;


	private void setMaxDelimCodePoint() {
		if (delimiters == null) {
			maxDelimCodePoint = 0;
			return;
		}

		int m = 0;
		int c;
		int count = 0;
		for (int i = 0; i < delimiters.length(); i += Character.charCount(c)) {
			c = delimiters.charAt(i);
			if (c >= Character.MIN_HIGH_SURROGATE && c <= Character.MAX_LOW_SURROGATE) {
				c = delimiters.codePointAt(i);
				hasSurrogates = true;
			}
			if (m < c)
				m = c;
			count++;
		}
		maxDelimCodePoint = m;

		if (hasSurrogates) {
			delimiterCodePoints = new int[count];
			for (int i = 0, j = 0; i < count; i++, j += Character.charCount(c)) {
				c = delimiters.codePointAt(j);
				delimiterCodePoints[i] = c;
			}
		}
	}

	public StringTokenizer(String string, char delim) {
		this.string = string;
		this.delim = delim;
	}

	public StringTokenizer(String str, String delim, boolean returnDelims) {
		currentPosition = 0;
		newPosition = -1;
		delimsChanged = false;
		this.str = str;
		maxPosition = str.length();
		delimiters = delim;
		retDelims = returnDelims;
		setMaxDelimCodePoint();
	}

	public StringTokenizer(String str, String delim) {
		this(str, delim, false);
	}

	public StringTokenizer(String str) {
		this(str, " \t\n\r\f", false);
	}

	private int skipDelimiters(int startPos) {
		if (delimiters == null)
			throw new NullPointerException();

		int position = startPos;
		while (!retDelims && position < maxPosition) {
			if (!hasSurrogates) {
				char c = str.charAt(position);
				if ((c > maxDelimCodePoint) || (delimiters.indexOf(c) < 0))
					break;
				position++;
			} else {
				int c = str.codePointAt(position);
				if ((c > maxDelimCodePoint) || !isDelimiter(c)) {
					break;
				}
				position += Character.charCount(c);
			}
		}
		return position;
	}

	private int scanToken(int startPos) {
		int position = startPos;
		while (position < maxPosition) {
			if (!hasSurrogates) {
				char c = str.charAt(position);
				if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
					break;
				position++;
			} else {
				int c = str.codePointAt(position);
				if ((c <= maxDelimCodePoint) && isDelimiter(c))
					break;
				position += Character.charCount(c);
			}
		}
		if (retDelims && (startPos == position)) {
			if (!hasSurrogates) {
				char c = str.charAt(position);
				if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
					position++;
			} else {
				int c = str.codePointAt(position);
				if ((c <= maxDelimCodePoint) && isDelimiter(c))
					position += Character.charCount(c);
			}
		}
		return position;
	}

	private boolean isDelimiter(int codePoint) {
		for (int i = 0; i < delimiterCodePoints.length; i++) {
			if (delimiterCodePoints[i] == codePoint) {
				return true;
			}
		}
		return false;
	}

	public boolean hasMoreTokens() {
		/*
		 * Temporarily store this position and use it in the following
		 * nextToken() method only if the delimiters haven't been changed in
		 * that nextToken() invocation.
		 */
		newPosition = skipDelimiters(currentPosition);
		return (newPosition < maxPosition);
	}

	public String nextToken() {
		/* 
		 * If next position already computed in hasMoreElements() and
		 * delimiters have changed between the computation and this invocation,
		 * then use the computed value.
		 */

		currentPosition = (newPosition >= 0 && !delimsChanged) ?  
				newPosition : skipDelimiters(currentPosition);

		/* Reset these anyway */
		delimsChanged = false;
		newPosition = -1;

		if (currentPosition >= maxPosition)
			throw new NoSuchElementException();
		int start = currentPosition;
		currentPosition = scanToken(currentPosition);
		return str.substring(start, currentPosition);
	}

	public String nextToken(String delim) {
		delimiters = delim;

		/* delimiter string specified, so set the appropriate flag. */
		delimsChanged = true;

		setMaxDelimCodePoint();
		return nextToken();
	}

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	public Object nextElement() {
		return nextToken();
	}

	public int countTokens() {
		int count = 0;
		int currpos = currentPosition;
		while (currpos < maxPosition) {
			currpos = skipDelimiters(currpos);
			if (currpos >= maxPosition)
				break;
			currpos = scanToken(currpos);
			count++;
		}
		return count;
	}

	public String nextToken(boolean isChar) {
		for (int i=0; i<string.length(); i++) {
			char c = string.charAt(i);
			if (c == delim) {
				String tkn = string.substring(0, i);
				string = string.substring(i+1);
				return tkn;
			}
		}
		// no delim was found, return the remaining string
		String lastToken = string;
		string = null;
		return lastToken;
	}

	public boolean hasMoreTokens(boolean isChar) {
		return string != null && string.length() > 0;
	}	
}
