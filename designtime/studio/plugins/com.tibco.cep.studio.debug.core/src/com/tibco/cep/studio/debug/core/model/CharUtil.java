package com.tibco.cep.studio.debug.core.model;

public class CharUtil {

	protected static int checkName(char[] name, char[] typeName, int pos,
			int length) {
			    if (fragmentEquals(name, typeName, pos, true)) {
			        pos += name.length;
			        if (pos == length) return pos;
			        char currentChar = typeName[pos];
			        switch (currentChar) {
			            case ' ' :
			            case '.' :
			            case '<' :
			            case '>' :
			            case '[' :
			            case ',' :
			                return pos;
						default:
						    if (isWhitespace(currentChar))
						    	return pos;
						    
			        }
			    }
			    return -1;
			}

	protected static boolean isWhitespace(char currentChar) {
		switch(currentChar) {
		case '\n':
		case '\r':
		case ' ':
		case '\t':
		case '\f':
			return true;
		default:
				return false;
		}
	}

	/**
	 * If isCaseSensite is true, the equality is case sensitive, otherwise it is case insensitive.
	 * 
	 * Answers true if the name contains the fragment at the starting index startIndex, otherwise false.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    fragment = { 'b', 'c' , 'd' }
	 *    name = { 'a', 'b', 'c' , 'd' }
	 *    startIndex = 1
	 *    isCaseSensitive = true
	 *    result => true
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    fragment = { 'b', 'c' , 'd' }
	 *    name = { 'a', 'b', 'C' , 'd' }
	 *    startIndex = 1
	 *    isCaseSensitive = true
	 *    result => false
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    fragment = { 'b', 'c' , 'd' }
	 *    name = { 'a', 'b', 'C' , 'd' }
	 *    startIndex = 0
	 *    isCaseSensitive = false
	 *    result => false
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    fragment = { 'b', 'c' , 'd' }
	 *    name = { 'a', 'b'}
	 *    startIndex = 0
	 *    isCaseSensitive = true
	 *    result => false
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param fragment the fragment to check
	 * @param name the array to check
	 * @param startIndex the starting index
	 * @param isCaseSensitive check whether or not the equality should be case sensitive
	 * @return true if the name contains the fragment at the starting index startIndex according to the 
	 * value of isCaseSensitive, otherwise false.
	 * @throws NullPointerException if fragment or name is null.
	 */
	public static final boolean fragmentEquals(char[] fragment, char[] name,
			int startIndex, boolean isCaseSensitive) {
			
				int max = fragment.length;
				if (name.length < max + startIndex)
					return false;
				if (isCaseSensitive) {
					for (int i = max;
						--i >= 0;
						) // assumes the prefix is not larger than the name
						if (fragment[i] != name[i + startIndex])
							return false;
					return true;
				}
				for (int i = max;
					--i >= 0;
					) // assumes the prefix is not larger than the name
					if (Character.toLowerCase(fragment[i])
						!= Character.toLowerCase(name[i + startIndex]))
						return false;
				return true;
			}

	/**
	 * Answers a new array with appending the suffix character at the end of the array.
	 * <br>
	 * <br>
	 * For example:<br>
	 * <ol>
	 * <li><pre>
	 *    array = { 'a', 'b' }
	 *    suffix = 'c'
	 *    => result = { 'a', 'b' , 'c' }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    array = null
	 *    suffix = 'c'
	 *    => result = { 'c' }
	 * </pre></li>
	 * </ol>
	 * 
	 * @param array the array that is concatenated with the suffix character
	 * @param suffix the suffix character
	 * @return the new array
	 */
	public static final char[] append(char[] array, char suffix) {
		if (array == null)
			return new char[] { suffix };
		int length = array.length;
		System.arraycopy(array, 0, array = new char[length + 1], 0, length);
		array[length] = suffix;
		return array;
	}

	/**
	 * Answers a new array which is a copy of the given array starting at the given start and 
	 * ending at the given end. The given start is inclusive and the given end is exclusive.
	 * Answers null if start is greater than end, if start is lower than 0 or if end is greater 
	 * than the length of the given array. If end  equals -1, it is converted to the array length.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    array = { { 'a' } , { 'b' } }
	 *    start = 0
	 *    end = 1
	 *    result => { { 'a' } }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    array = { { 'a' } , { 'b' } }
	 *    start = 0
	 *    end = -1
	 *    result => { { 'a' }, { 'b' } }
	 * </pre>
	 * </li>
	 * </ol>
	 *  
	 * @param array the given array
	 * @param start the given starting index
	 * @param end the given ending index
	 * @return a new array which is a copy of the given array starting at the given start and 
	 * ending at the given end
	 * @throws NullPointerException if the given array is null
	 */
	public static final char[][] subarray(char[][] array, int start, int end) {
		if (end == -1)
			end = array.length;
		if (start > end)
			return null;
		if (start < 0)
			return null;
		if (end > array.length)
			return null;
	
		char[][] result = new char[end - start][];
		System.arraycopy(array, start, result, 0, end - start);
		return result;
	}

	/**
	 * Answers a new array which is a copy of the given array starting at the given start and 
	 * ending at the given end. The given start is inclusive and the given end is exclusive.
	 * Answers null if start is greater than end, if start is lower than 0 or if end is greater 
	 * than the length of the given array. If end  equals -1, it is converted to the array length.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    array = { 'a' , 'b' }
	 *    start = 0
	 *    end = 1
	 *    result => { 'a' }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    array = { 'a', 'b' }
	 *    start = 0
	 *    end = -1
	 *    result => { 'a' , 'b' }
	 * </pre>
	 * </li>
	 * </ol>
	 *  
	 * @param array the given array
	 * @param start the given starting index
	 * @param end the given ending index
	 * @return a new array which is a copy of the given array starting at the given start and 
	 * ending at the given end
	 * @throws NullPointerException if the given array is null
	 */
	public static final char[] subarray(char[] array, int start, int end) {
		if (end == -1)
			end = array.length;
		if (start > end)
			return null;
		if (start < 0)
			return null;
		if (end > array.length)
			return null;
	
		char[] result = new char[end - start];
		System.arraycopy(array, start, result, 0, end - start);
		return result;
	}

	/**
	 * Return a new array which is the split of the given array using the given divider.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    divider = 'b'
	 *    array = { 'a' , 'b', 'b', 'a', 'b', 'a' }
	 *    result => { { 'a' }, {  }, { 'a' }, { 'a' } }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    divider = 'c'
	 *    array = { 'a' , 'b', 'b', 'a', 'b', 'a' }
	 *    result => { { 'a', 'b', 'b', 'a', 'b', 'a' } }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    divider = 'c'
	 *    array = { ' ', ' ', 'a' , 'b', 'b', 'a', 'b', 'a', ' ' }
	 *    result => { { ' ', 'a', 'b', 'b', 'a', 'b', 'a', ' ' } }
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param divider the given divider
	 * @param array the given array
	 * @return a new array which is the split of the given array using the given divider
	 */
	public static final char[][] splitOn(char divider, char[] array) {
		int length = array == null ? 0 : array.length;
		if (length == 0)
			return new char[0][];
	
		int wordCount = 1;
		for (int i = 0; i < length; i++)
			if (array[i] == divider)
				wordCount++;
		char[][] split = new char[wordCount][];
		int last = 0, currentWord = 0;
		for (int i = 0; i < length; i++) {
			if (array[i] == divider) {
				split[currentWord] = new char[i - last];
				System.arraycopy(
					array,
					last,
					split[currentWord++],
					0,
					i - last);
				last = i + 1;
			}
		}
		split[currentWord] = new char[length - last];
		System.arraycopy(array, last, split[currentWord], 0, length - last);
		return split;
	}

	/**
	 * Return a new array which is the split of the given array using the given divider. The given end 
	 * is exclusive and the given start is inclusive.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    divider = 'b'
	 *    array = { 'a' , 'b', 'b', 'a', 'b', 'a' }
	 *    start = 2
	 *    end = 5
	 *    result => { {  }, { 'a' }, {  } }
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param divider the given divider
	 * @param array the given array
	 * @param start the given starting index
	 * @param end the given ending index
	 * @return a new array which is the split of the given array using the given divider
	 * @throws ArrayIndexOutOfBoundsException if start is lower than 0 or end is greater than the array length
	 */
	public static final char[][] splitOn(char divider, char[] array, int start,
			int end) {
				int length = array == null ? 0 : array.length;
				if (length == 0 || start > end)
					return new char[0][];
			
				int wordCount = 1;
				for (int i = start; i < end; i++)
					if (array[i] == divider)
						wordCount++;
				char[][] split = new char[wordCount][];
				int last = start, currentWord = 0;
				for (int i = start; i < end; i++) {
					if (array[i] == divider) {
						split[currentWord] = new char[i - last];
						System.arraycopy(
							array,
							last,
							split[currentWord++],
							0,
							i - last);
						last = i + 1;
					}
				}
				split[currentWord] = new char[end - last];
				System.arraycopy(array, last, split[currentWord], 0, end - last);
				return split;
			}

	/**
	 * Answers the first index in the array for which the corresponding character is
	 * equal to toBeFound. Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @return the first index in the array for which the corresponding character is
	 * equal to toBeFound, -1 otherwise
	 * @throws NullPointerException if array is null
	 */
	public static final int indexOf(char toBeFound, char[] array) {
		return indexOf(toBeFound, array, 0);
	}

	/**
	 * Answers the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule. Answers -1 if no match is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = { 'c' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = { 'e' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the subarray to search
	 * @param array the array to be searched
	 * @param isCaseSensitive flag to know if the matching should be case sensitive
	 * @return the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule, -1 otherwise
	 * @throws NullPointerException if array is null or toBeFound is null
	 * @since 3.2
	 */
	public static final int indexOf(char[] toBeFound, char[] array, boolean isCaseSensitive) {
		return indexOf(toBeFound, array, isCaseSensitive, 0);
	}

	/**
	 * Answers the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule starting at the index start. Answers -1 if no match is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = { 'c' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = { 'e' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the subarray to search
	 * @param array the array to be searched
	 * @param isCaseSensitive flag to know if the matching should be case sensitive
	 * @param start the starting index 
	 * @return the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule starting at the index start, -1 otherwise
	 * @throws NullPointerException if array is null or toBeFound is null
	 * @since 3.2
	 */
	public static final int indexOf(final char[] toBeFound, final char[] array, final boolean isCaseSensitive,
			final int start) {
				return indexOf(toBeFound, array, isCaseSensitive, start, array.length);
			}

	/**
	 * Answers the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule starting at the index start. Answers -1 if no match is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = { 'c' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = { 'e' }
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the subarray to search
	 * @param array the array to be searched
	 * @param isCaseSensitive flag to know if the matching should be case sensitive
	 * @param start the starting index (inclusive)
	 * @param end the end index (exclusive)
	 * @return the first index in the array for which the toBeFound array is a matching
	 * subarray following the case rule starting at the index start, -1 otherwise
	 * @throws NullPointerException if array is null or toBeFound is null
	 * @since 3.2
	 */
	public static final int indexOf(final char[] toBeFound, final char[] array, final boolean isCaseSensitive,
			final int start, final int end) {
				final int arrayLength = end;
				final int toBeFoundLength = toBeFound.length;
				if (toBeFoundLength > arrayLength) return -1;
				if (toBeFoundLength == 0) return 0;
				if (toBeFoundLength == arrayLength) {
					if (isCaseSensitive) {
						for (int i = start; i < arrayLength; i++) {
							if (array[i] != toBeFound[i]) return -1;
						}
						return 0;
					} else {
						for (int i = start; i < arrayLength; i++) {
							if (Character.toLowerCase(array[i]) != Character.toLowerCase(toBeFound[i])) return -1;
						}
						return 0;
					}
				}
				if (isCaseSensitive) {
					arrayLoop: for (int i = start, max = arrayLength - toBeFoundLength + 1; i < max; i++) {
						if (array[i] == toBeFound[0]) {
							for (int j = 1; j < toBeFoundLength; j++) {
								if (array[i + j] != toBeFound[j]) continue arrayLoop;
							}
							return i;
						}
					}
				} else {
					arrayLoop: for (int i = start, max = arrayLength - toBeFoundLength + 1; i < max; i++) {
						if (Character.toLowerCase(array[i]) == Character.toLowerCase(toBeFound[0])) {
							for (int j = 1; j < toBeFoundLength; j++) {
								if (Character.toLowerCase(array[i + j]) != Character.toLowerCase(toBeFound[j])) continue arrayLoop;
							}
							return i;
						}
					}
				}
				return -1;
			}

	/**
	 * Answers the first index in the array for which the corresponding character is
	 * equal to toBeFound starting the search at index start.
	 * Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 2
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 3
	 *    result => -1
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 1
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @param start the starting index
	 * @return the first index in the array for which the corresponding character is
	 * equal to toBeFound, -1 otherwise
	 * @throws NullPointerException if array is null
	 * @throws ArrayIndexOutOfBoundsException if  start is lower than 0
	 */
	public static final int indexOf(char toBeFound, char[] array, int start) {
		for (int i = start; i < array.length; i++)
			if (toBeFound == array[i])
				return i;
		return -1;
	}

	/**
	 * Answers the first index in the array for which the corresponding character is
	 * equal to toBeFound starting the search at index start and before the ending index.
	 * Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 2
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 3
	 *    result => -1
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    start = 1
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @param start the starting index (inclusive)
	 * @param end the ending index (exclusive)
	 * @return the first index in the array for which the corresponding character is
	 * equal to toBeFound, -1 otherwise
	 * @throws NullPointerException if array is null
	 * @throws ArrayIndexOutOfBoundsException if  start is lower than 0 or ending greater than array length
	 * @since 3.2
	 */
	public static final int indexOf(char toBeFound, char[] array, int start,
			int end) {
				for (int i = start; i < end; i++)
					if (toBeFound == array[i])
						return i;
				return -1;
			}

	/**
	 * Answers the last index in the array for which the corresponding character is
	 * equal to toBeFound starting from the end of the array.
	 * Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' , 'c', 'e' }
	 *    result => 4
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 *
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @return the last index in the array for which the corresponding character is
	 * equal to toBeFound starting from the end of the array, -1 otherwise
	 * @throws NullPointerException if array is null
	 */
	public static final int lastIndexOf(char toBeFound, char[] array) {
		for (int i = array.length; --i >= 0;)
			if (toBeFound == array[i])
				return i;
		return -1;
	}

	/**
	 * Answers the last index in the array for which the corresponding character is
	 * equal to toBeFound stopping at the index startIndex.
	 * Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    startIndex = 2
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd', 'e' }
	 *    startIndex = 3
	 *    result => -1
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    startIndex = 0
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 *
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @param startIndex the stopping index
	 * @return the last index in the array for which the corresponding character is
	 * equal to toBeFound stopping at the index startIndex, -1 otherwise
	 * @throws NullPointerException if array is null
	 * @throws ArrayIndexOutOfBoundsException if startIndex is lower than 0
	 */
	public static final int lastIndexOf(char toBeFound, char[] array,
			int startIndex) {
				for (int i = array.length; --i >= startIndex;)
					if (toBeFound == array[i])
						return i;
				return -1;
			}

	/**
	 * Answers the last index in the array for which the corresponding character is
	 * equal to toBeFound starting from endIndex to startIndex.
	 * Answers -1 if no occurrence of this character is found.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    startIndex = 2
	 *    endIndex = 2
	 *    result => 2
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'c'
	 *    array = { ' a', 'b', 'c', 'd', 'e' }
	 *    startIndex = 3
	 *    endIndex = 4
	 *    result => -1
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    toBeFound = 'e'
	 *    array = { ' a', 'b', 'c', 'd' }
	 *    startIndex = 0
	 *    endIndex = 3
	 *    result => -1
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param toBeFound the character to search
	 * @param array the array to be searched
	 * @param startIndex the stopping index
	 * @param endIndex the starting index
	 * @return the last index in the array for which the corresponding character is
	 * equal to toBeFound starting from endIndex to startIndex, -1 otherwise
	 * @throws NullPointerException if array is null
	 * @throws ArrayIndexOutOfBoundsException if endIndex is greater or equals to array length or starting is lower than 0
	 */
	public static final int lastIndexOf(char toBeFound, char[] array,
			int startIndex, int endIndex) {
				for (int i = endIndex; --i >= startIndex;)
					if (toBeFound == array[i])
						return i;
				return -1;
			}

	/**
	 * Answers a new array removing a given character. Answers the given array if there is
	 * no occurrence of the character to remove.
	 * <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li><pre>
	 *    array = { 'a' , 'b', 'b', 'c', 'b', 'a' }
	 *    toBeRemoved = 'b'
	 *    return { 'a' , 'c', 'a' }
	 * </pre>
	 * </li>
	 * <li><pre>
	 *    array = { 'a' , 'b', 'b', 'a', 'b', 'a' }
	 *    toBeRemoved = 'c'
	 *    return array
	 * </pre>
	 * </li>
	 * </ol>
	 * 
	 * @param array the given array
	 * @param toBeRemoved the character to be removed
	 * @return a new array removing given character
	 * @since 3.2
	 */
	public static final char[] remove(char[] array, char toBeRemoved) {
	
		if (array == null) return null;
		int length = array.length;
		if (length == 0) return array;
		char[] result = null;
		int count = 0;
		for (int i = 0; i < length; i++) {
			char c = array[i];
			if (c == toBeRemoved) {
				if (result == null) {
					result = new char[length];
					System.arraycopy(array, 0, result, 0, i);
					count = i;
				}
			} else if (result != null) {
				result[count++] = c;
			}
		}
		if (result == null) return array;
		System.arraycopy(result, 0, result = new char[count], 0, count);
		return result;
	}

}
