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

import java.lang.reflect.Array;

import COM.TIBCO.hawk.utilities.misc.ObjChecker;

/**
 * This class provides some misc array manipulation utilities.
 */
public class ArrayUtil {
	private static String sClassName = ArrayUtil.class.getName();

	/*
	 * public static Object get(Object[] a, int index) { LastError.clear(); if
	 * (a == null) { LastError.set("Unable to get element with index " + index +
	 * " from a null array.", sClassName + ".get()"); return null; } if (index <
	 * 0 || index >= a.length) {
	 * LastError.set("Failed to get element with index " + index +
	 * " from the input array " + "because of index out of bounds.", sClassName
	 * + ".get()"); return null; } return a[index]; }
	 * 
	 * public static Object get(Object[][] a, int row, int column) {
	 * LastError.clear(); if (a == null) {
	 * LastError.set("Unable to get element with row " + row + " column " +
	 * column + " from a null array.", sClassName + ".get()"); return null; }
	 * try { return a[row][column]; } catch (IndexOutOfBoundsException e) {
	 * LastError.set("Failed to get element with row " + row + " column " +
	 * column + " from the input array" + " because of index out of bounds.",
	 * sClassName + ".get()"); return null; } }
	 * 
	 * public static Object set(Object[] a, int index, Object element) {
	 * LastError.clear(); element = JSHandler.unwrap(element); if (a == null) {
	 * LastError.set("Unable to set element with index " + index +
	 * " in a null array.", sClassName + ".set()"); return null; } if (index < 0
	 * || index >= a.length) { LastError.set("Failed to set element with index "
	 * + index + " in the input array " + "because of index out of bounds.",
	 * sClassName + ".set()"); return null; } a[index] = element; return a; }
	 * 
	 * public static Object set(Object[][] a, int row, int column, Object
	 * element) { LastError.clear(); element = JSHandler.unwrap(element); if (a
	 * == null) { LastError.set("Unable to set element with row " + row +
	 * " column " + column + " in a null array.", sClassName + ".set()"); return
	 * null; } try { a[row][column] = element; } catch
	 * (IndexOutOfBoundsException e) {
	 * LastError.set("Failed to set element with row " + row + " column " +
	 * column + " in the input array" + " because of index out of bounds.",
	 * sClassName + ".get()"); return null; } return a; }
	 */

	/**
	 * This method extracts a range of elements from the original array to form
	 * a new array.
	 * 
	 * @param a
	 *            the original array
	 * @param beginIndex
	 *            the beginning element index (inclusive)
	 * @param endIndex
	 *            the end element index (exclusive)
	 */
	public static Object[] subarray(Object[] a, int beginIndex, int endIndex) {
		LastError.clear();
		if (a == null || beginIndex < 0 || beginIndex > a.length || endIndex > a.length || beginIndex > endIndex) {
			LastError.set("Getting elements from index " + beginIndex + " to " + endIndex
					+ " in the input array failed " + "because of index out of bounds.", sClassName + ".subarray()");
			return null;
		}
		int size = endIndex - beginIndex;
		Object[] r = new Object[size];
		int j = 0;
		for (int i = beginIndex; i < endIndex; i++)
			r[j++] = a[i];
		return r;

	}

	/**
	 * Select a set of elements from an array.
	 * 
	 * @param a
	 *            the original array
	 * @param selection
	 *            the selected element indexes. It can be passed in through a
	 *            Java Number array (Number[]) or a JavaScript array, for
	 *            example, <code>new Array(2, 3, 5, 9)</code>.
	 * @return the result array
	 */

	public static Object[] select(Object[] a, Object selection) {
		if (selection == null || a == null || a.length == 0)
			return a;

		Object[] ia = null;
		// System.out.println("selection=" + selection);
		ia = (Object[]) JSHandler.convertToJavaArray(selection);

		if (ia == null)
			if (!LastError.getWhy().equals(""))
				return null;
			else
				return a;

		LastError.clear();

		int size = ia.length;
		int[] picked = new int[size];
		for (int i = 0; i < size; i++) {
			if (!(ia[i] instanceof Number)) {
				LastError.set("The element at index " + i + " in the input selection is not a valid number.",
						sClassName + ".select()");
				return null;
			}
			int j = ((Number) ia[i]).intValue();
			if (j < 0 || j >= a.length) {
				LastError.set("Getting element at index " + j + " in the input array failed "
						+ "because of index out of bounds.", sClassName + ".select()");
				return null;
			}
			picked[i] = j;
		}

		Object[] r = new Object[size];
		for (int i = 0; i < size; i++)
			r[i] = a[picked[i]];
		return r;

	}

	/**
	 * Select a set of columns from a two dimensional array (Object[][]).
	 * 
	 * @param a
	 *            the original two dimensional array
	 * @param selection
	 *            the selected column indexes. It can be passed in through a
	 *            Java Number array (Number[]) or a JavaScript array, for
	 *            example, <code>new Array(2, 3, 5, 9)</code>.
	 * @return the result two dimensional array
	 */

	public static Object[][] selectColumns(Object[][] a, Object selection) {
		if (selection == null || a == null || a.length == 0)
			return a;

		Object[] ia = null;
		// System.out.println("selection=" + selection);
		ia = (Object[]) JSHandler.convertToJavaArray(selection);
		// System.out.println("ia=" + ia);

		if (ia == null)
			if (!LastError.getWhy().equals(""))
				return null;
			else
				return a;

		LastError.clear();

		int size = ia.length;
		int[] picked = new int[size];
		for (int i = 0; i < size; i++) {
			if (!(ia[i] instanceof Number)) {
				LastError.set("The element at index " + i + " in the input selection is not a valid number.",
						sClassName + ".select()");
				return null;
			}
			int j = ((Number) ia[i]).intValue();
			if (j < 0 || j >= a[0].length) {
				LastError.set("Getting element at index " + j + " in the input array failed "
						+ "because of index out of bounds.", sClassName + ".select()");
				return null;
			}
			picked[i] = j;
		}

		Object[][] r = new Object[a.length][size];
		for (int k = 0; k < a.length; k++)
			for (int i = 0; i < size; i++)
				r[k][i] = a[k][picked[i]];
		return r;

	}

	/**
	 * Insert an element at a specific location in an array.
	 * 
	 * @param a
	 *            the original array
	 * @param index
	 *            the position where the new element should be inserted
	 * @param element
	 *            the new element to be inserted
	 * @return the result array
	 */

	public static Object[] insert(Object[] a, int index, Object element) {
		return (Object[]) insert(a, index, element, null);
	}

	/**
	 * Insert an element at a specific location in a specified class array. The
	 * elements of the input array and the element to be inserted must belong to
	 * the specified class.
	 * 
	 * @param a
	 *            the original array
	 * @param index
	 *            the position where the new element should be inserted
	 * @param element
	 *            the new element to be inserted
	 * @param cls
	 *            the element class. If cls is null, <code>Object.class</code>
	 *            is used.
	 * @return the result array of class specified
	 */

	public static Object[] insert(Object[] a, int index, Object element, Class cls) {
		LastError.clear();
		if (a == null) {
			LastError.set("Unable to insert element at index " + index + " in a null array.", sClassName + ".insert()");
			return null;
		}
		if (index < 0 || index > a.length) {
			LastError.set("Failed to insert element at index " + index + " in the input array "
					+ "because of index out of bounds.", sClassName + ".insert()");
			return null;
		}
		int n = a.length;
		Object[] result = cls == null ? new Object[n + 1] : (Object[]) Array.newInstance(cls, n + 1);
		int j = 0;
		for (int i = 0; i <= n; i++) {
			if (i == index) {
				result[i] = element;
				continue;
			}
			result[i] = a[j++];
		}
		return result;
	}

	/**
	 * Insert a given array at a specific location in an array. The result array
	 * would have a length equal to the sum of the original two.
	 * 
	 * @param a
	 *            the original array
	 * @param index
	 *            the position where the new array should be inserted
	 * @param a2
	 *            the array to be inserted
	 * @return the result array
	 */

	public static Object[] insertArray(Object[] a, int index, Object[] a2) {
		LastError.clear();
		if (a == null) {
			LastError.set("Unable to insert element at index " + index + " in a null array.", sClassName
					+ ".insertArray()");
			return null;
		}
		if (index < 0 || index > a.length) {
			LastError.set("Failed to insert element at index " + index + " in the input array "
					+ "because of index out of bounds.", sClassName + ".insertArray()");
			return null;
		}
		if (a2 == null)
			return a;
		int n2 = a2.length;
		if (n2 == 0)
			return a;

		int n = a.length;
		Object[] result = new Object[n + n2];
		int j = 0;
		int i = 0;
		while (i < (n + n2)) {
			if (i == index) {
				for (int k = 0; k < n2; k++)
					result[i++] = a2[k];
				continue;
			}
			result[i++] = a[j++];
		}
		return result;
	}

	/**
	 * Insert a given two dimensional array into another two dimensional array
	 * at a specific location. The two 2-D arrays must have the same number of
	 * rows. The result two dimensional array would have the number of columns
	 * equal to the sum of the number of columns in the original two arrays .
	 * 
	 * @param a
	 *            the original two dimensional array
	 * @param index
	 *            the position where the new array should be inserted
	 * @param a2
	 *            the two dimensional array to be inserted
	 * @return the result two dimensional array
	 */

	public static Object[][] insertColumns(Object[][] a, int index, Object[][] a2) {
		LastError.clear();
		if (a == null) {
			LastError.set("Unable to insert columns at index " + index + " in a null array.", sClassName
					+ ".insertColumns()");
			return null;
		}
		int n = a[0].length;
		if (n == 0) {
			LastError.set("Unable to insert columns at index " + index + " in an empty array.", sClassName
					+ ".inserColumnst()");
			return null;
		}
		if (index < 0 || index > n) {
			LastError.set("Failed to insert columns at index " + index + " in the input array "
					+ "because of index out of bounds.", sClassName + ".insertColumns()");
			return null;
		}
		if (a2 == null || a2.length == 0)
			return a;

		if (a.length != a2.length) {
			LastError.set("Unable to insert columns because the row numbers " + "in the input arrays are different.",
					sClassName + ".insertColumns()");
			return null;
		}

		int n2 = a2[0].length;
		if (n2 == 0)
			return a;

		Object[][] result = new Object[a.length][n + n2];
		for (int l = 0; l < a.length; l++) {
			int j = 0;
			int i = 0;
			while (i < (n + n2)) {
				if (i == index) {
					for (int k = 0; k < n2; k++)
						result[l][i++] = a2[k];
					continue;
				}
				result[l][i++] = a[j++];
			}
		}
		return result;
	}

	/**
	 * Remove an element at a specific location from an array.
	 * 
	 * @param a
	 *            the original array
	 * @param index
	 *            the index of the element to be removed
	 * @return the result array
	 */
	public static Object[] remove(Object[] a, int index) {
		LastError.clear();
		if (a == null || a.length < 1) {
			LastError.set("Unable to remove element with index " + index + " from a null array.", sClassName
					+ ".remove()");
			return null;
		}
		if (index < 0 || index >= a.length) {
			LastError.set("Failed to remove element with index " + index + " from the input array "
					+ "because of index out of bounds.", sClassName + ".remove()");
			return null;
		}
		int n = a.length;
		Object[] result = new Object[n - 1];
		int j = 0;
		for (int i = 0; i < n; i++) {
			if (i == index)
				continue;
			result[j++] = a[i];
		}
		return result;
	}

	/**
	 * Remove a set of elements from an array.
	 * 
	 * @param a
	 *            the original array
	 * @param selection
	 *            the indexes of the elements to be removed It can be passed in
	 *            through a Java Number array (Number[]) or a JavaScript array,
	 *            for example, <code>new Array(2, 3, 5, 9)</code>.
	 * @return the result array
	 */

	public static Object[] remove(Object[] a, Object selection) {
		if (selection == null || a == null || a.length == 0)
			return a;

		Object[] ia = null;
		// System.out.println("selection=" + selection);
		ia = (Object[]) JSHandler.convertToJavaArray(selection);

		if (ia == null)
			if (!LastError.getWhy().equals(""))
				return null;
			else
				return a;

		LastError.clear();

		int size = ia.length;
		Object[] result = a;
		for (int i = 0; i < size; i++) {
			if (!(ia[i] instanceof Number)) {
				LastError.set("The element at index " + i + " in the input selection is not a valid number.",
						sClassName + ".remove()");
				return null;
			}
			int j = ((Number) ia[i]).intValue();
			result = remove(result, j);
			if (result == null)
				return null;
		}

		return result;

	}

	/**
	 * Remove a set of columns from a two dimensional array (Object[][]).
	 * 
	 * @param a
	 *            the original two dimensional array
	 * @param selection
	 *            the indexes of the columns to be removed. It can be passed in
	 *            through a Java Number array (Number[]) or a JavaScript array,
	 *            for example, <code>new Array(2, 3, 5, 9)</code>.
	 * @return the result two dimensional array
	 */

	public static Object[][] removeColumns(Object[][] a, Object selection) {
		if (selection == null || a == null || a.length == 0)
			return a;

		Object[] ia = null;
		// System.out.println("selection=" + selection);
		ia = (Object[]) JSHandler.convertToJavaArray(selection);

		if (ia == null)
			if (!LastError.getWhy().equals(""))
				return null;
			else
				return a;

		LastError.clear();

		Object[][] result = a;
		int size = ia.length;
		for (int k = 0; k < a.length; k++)
			for (int i = 0; i < size; i++) {
				if (!(ia[i] instanceof Number)) {
					LastError.set("The element at index " + i + " in the input selection is not a valid number.",
							sClassName + ".remove()");
					return null;
				}
				int j = ((Number) ia[i]).intValue();
				if (a[k] == null || j >= a[k].length) {
					LastError.set("Removing column at index " + j + " in element " + k + " of input array failed "
							+ "because of index out of bounds.", sClassName + ".remove()");
					return null;
				}
				a[k] = remove(a[k], j);
				if (result == null)
					return null;
			}

		return result;

	}

	/**
	 * Replace an element at a specific location in an array with another array.
	 * The length of the result array will be equal to the sum of the length of
	 * the two original arrays minus one.
	 * 
	 * @param a
	 *            the original array
	 * @param index
	 *            the index of the element to be replaced
	 * @param a2
	 *            the array to be inserted
	 * @return the result array
	 */

	public static Object[] replace(Object[] a, int index, Object[] a2) {
		Object[] result = remove(a, index);

		if (result == null)
			return result;

		return insert(a, index, a2);
	}

	/**
	 * Merge two arrays together. The length of the result array will be equal
	 * to the sum of the length of the two original arrays.
	 * 
	 * @param a1
	 *            the original array
	 * @param a2
	 *            the array to be appended
	 * @return the result array
	 */
	public static Object[] merge(Object[] a1, Object[] a2) {
		return merge(a1, a2, null);
	}

	/**
	 * Merge two uniform class arrays together into an array of the specified
	 * class. The elements of the two input arrays must belong to the specified
	 * class. The length of the result array will be equal to the sum of the
	 * length of the two original arrays.
	 * 
	 * @param a1
	 *            the original array
	 * @param a2
	 *            the array to be appended
	 * @param cls
	 *            the element class. If cls is null, <code>Object.class</code>
	 *            is used.
	 * @return the result array
	 */
	public static Object[] merge(Object[] a1, Object[] a2, Class cls) {
		// System.out.println("a1 class = " + a1.getClass().getName());
		int s1 = (a1 == null) ? 0 : a1.length;
		int s2 = (a2 == null) ? 0 : a2.length;

		if (s1 == 0)
			return a2;
		if (s2 == 0)
			return a1;

		int j = 0;
		Object[] result = cls == null ? new Object[s1 + s2] : (Object[]) Array.newInstance(cls, s1 + s2);

		for (int i = 0; i < s1; i++)
			result[j++] = a1[i];

		for (int i = 0; i < s2; i++)
			result[j++] = a2[i];

		// System.out.println("result = " + result);
		return result;
	}

	/**
	 * @deprecated Use merge(Object[] a1, Object[] a2, Class cls) instead.
	 */

	public static Object[] uniformMerge(Object[] a1, Object[] a2) {
		// System.out.println("a1 class = " + a1.getClass().getName());
		int s1 = (a1 == null) ? 0 : a1.length;
		int s2 = (a2 == null) ? 0 : a2.length;

		if (s1 == 0)
			return a2;
		if (s2 == 0)
			return a1;
		Object firstElem = a1[0];
		Class elemClass = firstElem.getClass();
		Object arr = Array.newInstance(elemClass, s1 + s2);
		Object[] result = (Object[]) arr;

		int j = 0;

		for (int i = 0; i < s1; i++)
			result[j++] = a1[i];

		for (int i = 0; i < s2; i++)
			result[j++] = a2[i];

		// System.out.println("result = " + result);
		return result;
	}

	/**
	 * Merge a two dimensional array into another two dimensional array The two
	 * 2-D arrays must have the same number of rows. The result two dimensional
	 * array would have the number of columns equal to the sum of the number of
	 * columns in the original two arrays .
	 * 
	 * @param a1
	 *            the original two dimensional array
	 * @param a2
	 *            the two dimensional array to merge
	 * @return the result two dimensional array
	 */

	public static Object[][] mergeColumns(Object[][] a1, Object[][] a2) {
		if (a1 == null)
			return a2;
		if (a2 == null)
			return a1;

		LastError.clear();

		if (a1.length != a2.length) {
			LastError.set("Unable to merge columns because the row numbers " + "in the input arrays are different.",
					sClassName + ".mergeColumns()");
			return null;
		}

		int nr = a1.length;
		if (nr == 0) // a2.length must be 0
			return a1;

		// System.out.println("a1 class = " + a1.getClass().getName());
		int s1 = a1[0].length;
		int s2 = a2[0].length;

		Object[][] result = new Object[nr][s1 + s2];

		for (int k = 0; k < nr; k++) {
			int j = 0;
			for (int i = 0; i < s1; i++)
				result[k][j++] = a1[k][i];

			for (int i = 0; i < s2; i++)
				result[k][j++] = a2[k][i];
		}
		// System.out.println("result = " + result);
		return result;
	}

	/**
	 * Append a two dimensional array into another two dimensional array The two
	 * 2-D arrays must have the same number of columns. The result two
	 * dimensional array would have the number of rows equal to the sum of the
	 * number of rows in the original two arrays .
	 * 
	 * @param a1
	 *            the original two dimensional array
	 * @param a2
	 *            the two dimensional array to append
	 * @return the result two dimensional array
	 */

	public static Object[][] appendRows(Object[][] a1, Object[][] a2) {
		if (a1 == null || a1.length == 0)
			return a2;
		if (a2 == null || a2.length == 0)
			return a1;

		LastError.clear();

		if (a1[0].length != a2[0].length) {
			LastError.set("Unable to append rows because the column numbers " + "in the input arrays are different.",
					sClassName + ".appendRows()");
			return null;
		}

		// System.out.println("a1 class = " + a1.getClass().getName());
		int s1 = a1.length;
		int s2 = a2.length;

		Object[][] result = new Object[s1 + s2][a1[0].length];

		int j = 0;
		for (int i = 0; i < s1; i++)
			result[j++] = a1[i];

		for (int i = 0; i < s2; i++)
			result[j++] = a2[i];

		// System.out.println("result = " + result);
		return result;
	}

	/*
	 * public static Object multiply(Object a, int index, Number n) { if (a ==
	 * null) { LastError.set("Can not perform multiplication on a null array.",
	 * sClassName + ".multiply()"); return null; } LastError.clear(); try {
	 * 
	 * if (a instanceof Object[][]) { Object[][] aa = (Object[][])a; int rn =
	 * aa.length; for (int i = 0; i < rn; i++) { aa[i] = multiply(aa[i], index,
	 * n); if (aa[i] == null) return null; } return aa; } else if (a instanceof
	 * Object[]) { return multiply((Object[])a, index, n); } else {
	 * LastError.set("Invalid input - not an array.", sClassName +
	 * ".multiply()"); return null; } } catch (IndexOutOfBoundsException e) {
	 * LastError.set("Failed to multiply " + " because of index out of bounds.",
	 * sClassName + ".multiply()"); return null; }
	 * 
	 * }
	 * 
	 * public static Object[] multiply(Object[] a, int index, Number n) {
	 * //LastError.clear(); if (a == null) {
	 * LastError.set("Can not perform multiplication on a null array.",
	 * sClassName + ".multiply()"); return null; } if (index >= a.length) {
	 * LastError.set("The element at index " + index + " is not a number.",
	 * sClassName + ".multiply()"); return null; }
	 * 
	 * try { Object o = a[index]; Number x = null; try { x = (Number)a[index]; }
	 * catch (Exception e) { LastError.set("Multiplication failed " +
	 * "because of " + e, sClassName + ".multiply()"); return null;
	 * 
	 * }
	 * 
	 * double d = x.doubleValue() * n.doubleValue();
	 * //System.out.println("index = " + index + " n = " + n + " d = " + d);
	 * 
	 * //System.out.println("o class = " + o.getClass().getName()); if (o
	 * instanceof Long) { long l = (long)d; a[index] = new Long(l); } else if (o
	 * instanceof Double) { a[index] = new Double(d); } else if (o instanceof
	 * Integer) { int i = (int)d; a[index] = new Integer(i); } else if (o
	 * instanceof Float) { float f = (float)d; a[index] = new Float(f); } else
	 * if (o instanceof Short) { short i = (short)d; a[index] = new Short(i); }
	 * else if (o instanceof Byte) { byte i = (byte)d; a[index] = new Byte(i); }
	 * //System.out.println("a[index] = " + a[index]); } catch (Exception e) {
	 * LastError.set("Multiplication failed " + "because of " + e, sClassName +
	 * ".multiply()"); return null;
	 * 
	 * } return a;
	 * 
	 * }
	 * 
	 * public static Object add(Object a, int index, Number n) { if (a == null)
	 * { LastError.set("Can not perform addition on a null array.", sClassName +
	 * ".add()"); return null; } LastError.clear(); try {
	 * 
	 * if (a instanceof Object[][]) { Object[][] aa = (Object[][])a; int rn =
	 * aa.length; for (int i = 0; i < rn; i++) { aa[i] = add(aa[i], index, n);
	 * if (aa[i] == null) return null; } return aa; } else if (a instanceof
	 * Object[]) { return add((Object[])a, index, n); } else {
	 * LastError.set("Invalid input - not an array.", sClassName + ".add()");
	 * return null; } } catch (IndexOutOfBoundsException e) {
	 * LastError.set("Failed to add " + " because of index out of bounds.",
	 * sClassName + ".add()"); return null; }
	 * 
	 * }
	 * 
	 * public static Object[] add(Object[] a, int index, Number n) {
	 * LastError.clear(); if (a == null) {
	 * LastError.set("Can not perform addition on a null array.", sClassName +
	 * ".add()"); return null; } if (index >= a.length) {
	 * LastError.set("The element at index " + index + " is not a number.",
	 * sClassName + ".add()"); return null; }
	 * 
	 * try { Object o = a[index]; Number x = null; try { x = (Number)a[index]; }
	 * catch (Exception e) { LastError.set("Addition failed " + "because of " +
	 * e, sClassName + ".add()"); return null;
	 * 
	 * }
	 * 
	 * double d = x.doubleValue() + n.doubleValue();
	 * //System.out.println("index = " + index + " n = " + n + " d = " + d);
	 * 
	 * //System.out.println("o class = " + o.getClass().getName()); if (o
	 * instanceof Long) { long l = (long)d; a[index] = new Long(l); } else if (o
	 * instanceof Double) { a[index] = new Double(d); } else if (o instanceof
	 * Integer) { int i = (int)d; a[index] = new Integer(i); } else if (o
	 * instanceof Float) { float f = (float)d; a[index] = new Float(f); } else
	 * if (o instanceof Short) { short i = (short)d; a[index] = new Short(i); }
	 * else if (o instanceof Byte) { byte i = (byte)d; a[index] = new Byte(i); }
	 * //System.out.println("a[index] = " + a[index]); } catch (Exception e) {
	 * LastError.set("Addition failed " + "because of " + e, sClassName +
	 * ".add()"); return null;
	 * 
	 * } return a;
	 * 
	 * }
	 */

	/**
	 * Convert an Object array to String array.
	 * 
	 * @param a
	 *            the Object array to be converted
	 * @return the result String array
	 */
	public static String[] toStringArray(Object[] a) {
		if (a == null)
			return null;
		int len = a.length;
		String[] result = new String[len];
		for (int i = 0; i < len; i++)
			result[i] = a[i].toString();
		return result;

	}

	/**
	 * Dump an array to String.
	 */
	public static String toString(Object obj) {
		return ObjChecker.toString(obj);
	}

}
