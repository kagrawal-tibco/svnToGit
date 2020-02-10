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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import COM.TIBCO.hawk.utilities.misc.ObjChecker;

/**
 * This class is used to implement a very useful data structure to handle
 * tabular form of data. Basicly, the data structure is composed of three parts.
 * First, the tabular data, in the form of Object[][]. Second, optionally a list
 * of column names. And the third, some optional auxiliary table attribute data
 * associated with the whole table. The auxiliary table attribute data are
 * stored as a HashMap.
 */
public class NamedTabularData {
	public static final int ASCENDING = 1;
	public static final int DESCENDING = -1;

	private static String sClassName = NamedTabularData.class.getName();
	/**
	 * The list of all column names.
	 */
	protected String[] mNameList = null;
	/**
	 * The list of all column display names.
	 */
	protected String[] mDisplayNameList = null;
	/**
	 * The actual tabular data.
	 */
	protected Object[][] mData = null;
	/**
	 * The auxiliary table attribute data associated with the whole table.
	 */
	protected HashMap mTableAttrs = null;
	/**
	 * The number of columns.
	 */
	protected int mCols = 0;
	/**
	 * The total row count.
	 */
	public int rowCount = 0;

	/**
	 * Constructor for null data.
	 */
	public NamedTabularData() {
	}

	/**
	 * Constructor with a tabular data.
	 * 
	 * @param data
	 *            the tabular data
	 */
	public NamedTabularData(Object[][] data) {
		this(null, data);
	}

	/**
	 * Constructor with a tabular data and a column name list
	 * 
	 * @param nameList
	 *            the column name list
	 * @param data
	 *            the tabular data
	 */
	public NamedTabularData(String[] nameList, Object[][] data) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null && data.length > 0)
				mCols = data[0].length;
		} else
			mCols = mNameList.length;

		rowCount = data == null ? 0 : data.length;
		mData = data;
	}

	/**
	 * Constructor with a tabular data and a column name list and a column
	 * display name list
	 * 
	 * @param nameList
	 *            the column name list
	 * @param displayNameList
	 *            the column display name list The displayn name list must have
	 *            the same length as the nameList length.
	 * @param data
	 *            the tabular data
	 */
	public NamedTabularData(String[] nameList, String[] displayNameList, Object[][] data) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null && data.length > 0)
				mCols = data[0].length;
		} else
			mCols = mNameList.length;
		mDisplayNameList = displayNameList;

		rowCount = data == null ? 0 : data.length;
		mData = data;
	}

	private NamedTabularData(String[] nameList, String[] displayNameList, Object[][] data, HashMap tableAttrs) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null && data.length > 0)
				mCols = data[0].length;
		} else
			mCols = mNameList.length;
		mDisplayNameList = displayNameList;

		rowCount = data == null ? 0 : data.length;
		mData = data;
		mTableAttrs = tableAttrs;
	}

	/**
	 * Get the tabular data.
	 */
	public Object[][] getData() {
		return mData;
	}

	/**
	 * Get a row in the tabular data.
	 * 
	 * @param row
	 *            the row index
	 */
	public Object[] getRow(int row) {
		if (mData == null || row >= rowCount)
			return null;

		return mData[row];
	}

	/**
	 * Get column names
	 */
	public String[] getColumnNames() {

		return mNameList;
	}

	/**
	 * Get column display names
	 */
	public String[] getColumnDisplayNames() {

		return mDisplayNameList == null ? mNameList : mDisplayNameList;
	}

	/**
	 * Set column display names
	 */
	public void setColumnDisplayNames(String[] displayNameList) {
		if (displayNameList != null && mNameList != null && displayNameList.length != mNameList.length)
			return;
		mDisplayNameList = displayNameList;
	}

	/**
	 * Get the data in the first row with the specified column index
	 * 
	 * @param col
	 *            the column index
	 */
	public Object get(int col) {
		return get(0, col);
	}

	/**
	 * Get the data with the specified row and column index
	 * 
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 */
	public Object get(int row, int col) {
		return mData == null ? null : mData[row][col];
	}

	/**
	 * Get the data in the first row of the specified column
	 * 
	 * @param colName
	 *            the column name
	 */
	public Object get(String colName) {
		return get(0, colName);
	}

	/**
	 * Get the data with the specified row index in the specified column
	 * 
	 * @param row
	 *            the row index
	 * @param colName
	 *            the column name
	 */
	public Object get(int row, String colName) {
		if (colName != null && row < rowCount && mNameList != null)
			for (int i = 0; i < mCols; i++)
				if (colName.equals(mNameList[i]))
					return mData[row][i];
		return null;
	}

	/**
	 * Merge this NamedTabularData with another NamedTabularData. The column
	 * name list and the auxiliary table attribute data of this NamedTabularData
	 * are remained. The tabular data of the other NamedTabularData are appened
	 * to the end.
	 * 
	 * @param otherTabular
	 *            the other NamedTabularData to be merged.
	 */
	public NamedTabularData merge(NamedTabularData otherTabular) {
		LastError.clear();
		// Object[] t1 = (Object[])mData;
		// Object[] t2 = (Object[])inTabular.getData();
		try {
			return new NamedTabularData(mNameList, mDisplayNameList,
					ArrayUtil.appendRows(mData, otherTabular.getData()), mTableAttrs == null ? null
							: (HashMap) mTableAttrs.clone());
		} catch (Throwable th) {
			LastError.set("Failed to merge NamedTabularData because of " + th, sClassName + ".merge()", th);
			return null;
		}

	}

	/**
	 * Merge all NamedTabularData's into one. The columns in the new object are
	 * based on the specified selection which includes the table attribute names
	 * or column names. Within the selection, the table attribute names have to
	 * be at the front. Table attributes are turned into columns and are
	 * repeated for each row of an object. The result NamedTabularData object
	 * does not have any table attribute.
	 * 
	 * @param tabulars
	 *            the NamedTabularData objects to be merged.
	 * @param selection
	 *            the selected column names
	 * @param selectionDisplayNames
	 *            the selected column display names. It has to be either null or
	 *            one to one matching the column names specified in the
	 *            parameter <code>selection</code>. If the parameter
	 *            <code>selection</code> is null, it has to be either null or
	 *            one to one matching the column names in the
	 *            <code>tabulars</code> array.
	 * @param inReverseOrder
	 *            if true the order of the NamedTabularData objects is reversed
	 *            to compose the result object
	 */
	public static NamedTabularData merge(NamedTabularData[] tabulars, String[] selection,
			String[] selectionDisplayNames, boolean inReverseOrder) {
		LastError.clear();
		try {

			if (tabulars == null || tabulars.length == 0)
				return null;

			NamedTabularData data = tabulars[0];
			String[] colNames = data.getColumnNames();
			if (colNames == null)
				colNames = new String[0];
			String[] cols = null;
			String[] nonAuxCols = null;
			int[] index = null;
			int auxDataLen = 0; // number of aux at front
			if (selection == null) {
				cols = colNames;
				index = new int[cols.length];
				for (int i = 0; i < cols.length; i++)
					index[i] = i;
				nonAuxCols = colNames;
			} else {
				cols = selection;
				index = new int[cols.length];
				auxDataLen = -1;
				for (int i = 0; i < cols.length; i++) {
					int k = -1;
					for (int j = 0; j < colNames.length; j++)
						if (((String) cols[i]).equals(colNames[j])) {
							k = j;
							break;
						}
					index[i] = k;
					if (k >= 0 && auxDataLen < 0)
						auxDataLen = i;

				}
				if (auxDataLen < 0)
					auxDataLen = cols.length; // no non-aux data
				nonAuxCols = new String[cols.length - auxDataLen];
				for (int j = 0; j < nonAuxCols.length; j++)
					nonAuxCols[j] = (String) cols[j + auxDataLen];
			}
			// trace.log(Trace.DEBUG,
			// "nonAuxCols="+ObjChecker.toString(nonAuxCols));
			// trace.log(Trace.DEBUG, "index[]="+ObjChecker.toString(index));

			int arrayLength = tabulars.length;
			int cn = cols.length;
			int nrows = 0;
			for (int l = 0; l < arrayLength; l++)
				nrows += tabulars[l].rowCount;
			Object[][] o = new Object[nrows][cn];

			int rn = 0;
			for (int l = 0; l < arrayLength; l++) {
				int i = l;
				if (inReverseOrder) {
					i = arrayLength - l - 1;
				}
				Object[][] d = tabulars[i].getData();
				if (d == null)
					continue;
				for (int m = 0; m < d.length; m++) {
					for (int j = 0; j < cn; j++) {
						if (index[j] >= 0)
							o[rn][j] = d[m][index[j]];
						else
							o[rn][j] = tabulars[i].getTableAttr((String) cols[j]);
					}
					rn++;
				}
			}

			return new NamedTabularData(cols, o);

		} catch (Throwable th) {
			LastError.set("Failed to merge NamedTabularData arrays because of " + th, sClassName + ".merge()", th);
			return null;
		}

	}

	/**
	 * Similar to the method above except that the selection is String of comma
	 * separated selected column names public static NamedTabularData
	 * merge(NamedTabularData[] tabulars, String selection, boolean
	 * inReverseOrder) { if (selection == null) return null;
	 * 
	 * String x = Text.substitute(selection, "\\,", "_COMMA_", false); String[]
	 * s = Text.cut(selection, ","); if (s == null) return null; for (int i = 0;
	 * i < s.length; i++) { s[i] = s[i].trim(); s[i] = Text.substitute(s[i],
	 * "_COMMA_", ",", false); }
	 * 
	 * return merge(tabulars, s, inReverseOrder); }
	 */

	/**
	 * @deprecated Use setTableAttr
	 */
	public void setAuxData(String name, Object value) {
		if (mTableAttrs == null)
			mTableAttrs = new HashMap();
		mTableAttrs.put(name, value);
	}

	/**
	 * @deprecated Use getTableAttr
	 */
	public Object getAuxData(String name) {
		if (mTableAttrs == null)
			return null;
		return mTableAttrs.get(name);
	}

	/**
	 * Set table attribute
	 * 
	 * @param name
	 *            the table attribute name
	 * @param value
	 *            the table attribute value
	 */
	public void setTableAttr(String name, Object value) {
		if (mTableAttrs == null)
			mTableAttrs = new HashMap();
		mTableAttrs.put(name, value);
	}

	/**
	 * Get table attribute
	 * 
	 * @param name
	 *            the table attribute name
	 * @return the table attribute value
	 */
	public Object getTableAttr(String name) {
		if (mTableAttrs == null)
			return null;
		return mTableAttrs.get(name);
	}

	/**
	 * Get all table attributes
	 * 
	 * @return the table attribute HashMap
	 */
	HashMap getAllTableAttrs() {
		return mTableAttrs;
	}

	/**
	 * Change the column name list to the newly specified one
	 * 
	 * @param newColumnNames
	 *            the new column name list
	 */
	public void changeColumnNames(String[] newColumnNames) {
		LastError.clear();
		if (newColumnNames == null || newColumnNames.length != mCols) {
			LastError.set("Column name list size does not match the original number.", sClassName
					+ ".changeColumnNames()");
			return;
		}
		mNameList = newColumnNames;
	}

	/*
	 * public void addColumnNames(Object inArray) { LastError.clear(); if
	 * (mNameList != null) {
	 * LastError.set("Column name list has already been set.", sClassName +
	 * ".addColumnNames()"); return; }
	 * 
	 * 
	 * Object[] row = JSHandler.convertToJavaArray(inArray); if (row == null)
	 * return;
	 * 
	 * if (mCols == 0) { mCols = row.length; } else if (mCols != row.length) {
	 * LastError.set("Column name list has different size than data.",
	 * sClassName + ".addColumnNames()"); return; }
	 * 
	 * mNameList = new String[mCols]; for (int i = 0; i < mCols; i++)
	 * mNameList[i] = row[i].toString(); }
	 */

	/**
	 * Append one row of data
	 * 
	 * @param row
	 *            the row to be appnended
	 */
	public void appendOneRow(Object[] row) {
		if (row == null)
			return;

		LastError.clear();

		if (mCols != 0 && mCols != row.length) {
			LastError.set("Data has different size than column name list.", sClassName + ".addOneRow()");
			return;
		}

		if (mData == null) {
			mData = new Object[][] { row };
			mCols = row.length;
		} else {
			Object[] r = ArrayUtil.merge(mData, new Object[] { row });
			if (r == null)
				return;
			mData = (Object[][]) r;
		}
		rowCount++;
	}

	/**
	 * Select the specified columns to form another NamedTabularData. The
	 * auxiliary table attribute data are copied to the new NamedTabularData
	 * object.
	 * 
	 * @param selection
	 *            the coma separated selected column name list.
	 */
	public NamedTabularData selectColumns(String selection) {
		if (selection == null)
			return this;

		String x = Text.substitute(selection, "\\,", "_COMMA_", false);
		String[] s = Text.cut(selection, ",");
		if (s == null)
			return null;
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
			s[i] = Text.substitute(s[i], "_COMMA_", ",", false);
		}
		return selectColumns(s);
	}

	/**
	 * Select the specified columns to form another NamedTabularData. The
	 * auxiliary table attribute data are copied to the new NamedTabularData
	 * object.
	 * 
	 * @param selectedColNames
	 *            the selected column name list.
	 */
	public NamedTabularData selectColumns(String[] selectedColNames) {
		if (selectedColNames == null || mNameList == null)
			return null;

		int[] dl = new int[selectedColNames.length];
		String[] dName = mDisplayNameList == null ? null : new String[selectedColNames.length];
		for (int j = 0; j < selectedColNames.length; j++) {
			dl[j] = -1;
			for (int i = 0; i < mNameList.length; i++) {
				if (mNameList[i].equals(selectedColNames[j])) {
					dl[j] = i;
					if (dName != null)
						dName[j] = mDisplayNameList[i];
					break;
				}
			}
		}

		Object[][] newData = new Object[mData.length][selectedColNames.length];
		for (int i = 0; i < mData.length; i++)
			for (int j = 0; j < selectedColNames.length; j++)
				if (dl[j] < 0)
					newData[i][j] = null;
				else
					newData[i][j] = mData[i][dl[j]];

		return new NamedTabularData(selectedColNames, dName, newData, mTableAttrs == null ? null
				: (HashMap) mTableAttrs.clone());

	}

	/**
	 * Select the specified rows to form another NamedTabularData. The auxiliary
	 * table attribute data are copied to the new NamedTabularData object.
	 * 
	 * @param begin
	 *            the starting row.
	 * @param end
	 *            the end row
	 */
	public NamedTabularData selectRows(int begin, int end) {
		if (begin < 0 || end < 0 || begin >= end || rowCount <= begin)
			return null;

		int e = end > rowCount ? rowCount : end;
		int len = e - begin;

		Object[][] newData = new Object[len][];
		for (int i = 0; i < len; i++)
			newData[i] = mData[i + begin];

		return new NamedTabularData(mNameList, mDisplayNameList, newData, mTableAttrs == null ? null
				: (HashMap) mTableAttrs.clone());
	}

	/**
	 * Check whether the specified column is a valid name in the column name
	 * list.
	 * 
	 * @param colName
	 *            the column name to be checked
	 * @return true if the specified column is a valid name
	 */

	public boolean hasColumn(String colName) {
		if (colName == null)
			return false;
		for (int i = 0; i < mCols; i++)
			if (colName.equals(mNameList[i]))
				return true;
		return false;
	}

	/**
	 * Apply the "AND" Filters to all rows as well as the auxiliary table
	 * attribute data. If the Filter name is an auxiliary table attribute name,
	 * the auxiliary table attribute data value is checked. In this case, null
	 * is returned if the Filter validation failed. Then each row is checked
	 * against all Filters with a name corresponding to a column name. If any
	 * Filter validation failed, this row is removed.
	 * 
	 * @param andFilters
	 *            the "AND" Filters
	 * @return the result after filtering
	 */
	public NamedTabularData applyANDFilters(Filter[] andFilters) {

		if (andFilters == null)
			return this;
		// check aux data first
		for (int i = 0; i < andFilters.length; i++) {
			Filter f = andFilters[i];
			if (f == null)
				continue;
			String name = f.getName();
			Object auxValue = getTableAttr(name);
			if (auxValue != null)
				if (!f.validate(auxValue))
					return null;
		}

		if ((mData == null) || (mData.length == 0))
			return this;

		Vector rows = new Vector();
		// Trace t = ContextControl.getTrace();

		for (int j = 0; j < mData.length; j++) {
			boolean invalid = false;
			for (int i = 0; i < andFilters.length; i++) {
				Filter f = andFilters[i];
				if (f == null)
					continue;
				String name = f.getName();
				if (!hasColumn(name))
					continue;
				Object dataValue = get(j, name);
				if (dataValue != null) {
					// t.log(Trace.DEBUG, "validating:" + dataValue);
					if (!f.validate(dataValue)) {
						invalid = true;
						// t.log(Trace.DEBUG, "validation result = false");
						break;
					}
					// t.log(Trace.DEBUG, "validation result = true");
				}
			}
			if (!invalid)
				rows.addElement(mData[j]);
		}
		// t.log(Trace.DEBUG, "rows.size()" + rows.size());
		if (rows.size() == 0)
			return new NamedTabularData(mNameList, mDisplayNameList, new Object[0][], mTableAttrs == null ? null
					: (HashMap) mTableAttrs.clone());

		Object[][] newData = new Object[rows.size()][];
		for (int i = 0; i < rows.size(); i++)
			newData[i] = (Object[]) rows.elementAt(i);
		return new NamedTabularData(mNameList, mDisplayNameList, newData, mTableAttrs == null ? null
				: (HashMap) mTableAttrs.clone());
	}

	/**
	 * Apply the "OR" Filters to all rows as well as the auxiliary table
	 * attribute data. If the Filter name is an auxiliary table attribute name,
	 * the auxiliary table attribute data value is checked. In this case, null
	 * is returned if the Filter validation failed. Then each row is checked
	 * against all Filters with a name corresponding to a column name. If no any
	 * Filter validation succeded, this row is removed.
	 * 
	 * @param orFilters
	 *            the "OR" Filters
	 * @return the result after filtering
	 */
	public NamedTabularData applyORFilters(Filter[] orFilters) {

		if (orFilters == null)
			return this;
		// check aux data first
		for (int i = 0; i < orFilters.length; i++) {
			Filter f = orFilters[i];
			if (f == null)
				continue;
			String name = f.getName();
			Object auxValue = getTableAttr(name);
			if (auxValue != null) {
				if (f.validate(auxValue)) {
					return this;
				}
			}
		}

		if ((mData == null) || (mData.length == 0))
			return null;

		Vector rows = new Vector();

		for (int j = 0; j < mData.length; j++) {
			boolean valid = false;
			for (int i = 0; i < orFilters.length; i++) {
				Filter f = orFilters[i];
				if (f == null)
					continue;
				String name = f.getName();
				if (!hasColumn(name))
					continue;
				Object dataValue = get(j, name);
				if (dataValue != null)
					if (f.validate(dataValue)) {
						valid = true;
						break;
					}
			}
			if (valid)
				rows.addElement(mData[j]);
		}
		if (rows.size() == 0)
			return null;

		Object[][] newData = new Object[rows.size()][];
		for (int i = 0; i < rows.size(); i++)
			newData[i] = (Object[]) rows.elementAt(i);
		return new NamedTabularData(mNameList, mDisplayNameList, newData, mTableAttrs == null ? null
				: (HashMap) mTableAttrs.clone());
	}

	/**
	 * Apply the "AND" Filters to every element in the array.
	 * 
	 * @param array
	 *            the NamedTabularData array to be filtered
	 * @param andFilters
	 *            the "AND" Filters
	 * @return the result after filtering
	 */
	public static NamedTabularData[] applyANDFilters(NamedTabularData[] array, Filter[] andFilters) {
		if (array == null)
			return null;
		NamedTabularData[] result = new NamedTabularData[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i].applyANDFilters(andFilters);
		return result;
	}

	/**
	 * Apply the "OR" Filters to every element in the array.
	 * 
	 * @param array
	 *            the NamedTabularData array to be filtered
	 * @param orFilters
	 *            the "OR" Filters
	 * @return the result after filtering
	 */
	public static NamedTabularData[] applyORFilters(NamedTabularData[] array, Filter[] orFilters) {
		if (array == null)
			return null;
		NamedTabularData[] result = new NamedTabularData[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i].applyORFilters(orFilters);
		return result;
	}

	/**
	 * This method is used to convert an one row NamedTabularData object to
	 * NamedArray. Note: The "tableAttrs" in NamedTabularData are ignored. If
	 * the NamedTabularData has more than one row, only the first row is used.
	 * 
	 * @return the result NamedArray object
	 */
	public NamedArray toNamedArray() {
		if (mData == null || mData.length == 0)
			return new NamedArray(mNameList, mDisplayNameList, null);
		return new NamedArray(mNameList, mDisplayNameList, mData[0]);
	}

	/**
	 * A convenient method to dump all the data in a NamedTabularData object to
	 * a string
	 */

	public String toString() {
		return "NameList = " + ObjChecker.toString(mNameList) + " TabeAttrs = " + ObjChecker.toString(mTableAttrs)
				+ " Data = " + ObjChecker.toString(mData);
	}

	/**
	 * Sort the data with the specified column
	 * 
	 * @param colName
	 *            the column name
	 * @param direction
	 *            the sort direction
	 */
	public void sort(String colName, int direction) {
		if (colName != null && mNameList != null)
			for (int i = 0; i < mCols; i++)
				if (colName.equals(mNameList[i]))
					sort(i, direction);
	}

	/**
	 * Sort the data with the specified column index
	 * 
	 * @param col
	 *            the column index
	 * @param direction
	 *            the sort direction
	 */
	public void sort(int col, int direction) {
		if (col < 0 || col >= mCols)
			return;

		Arrays.sort(mData, new RowComparator(col, direction, null));
	}

	class RowComparator implements Comparator {

		private int m_sortCol;
		private int m_sortDir;

		private Comparator m_valueComparator;

		public RowComparator(int sortCol, int sortDir) {
			this(sortCol, sortDir, null);
		}

		public RowComparator(int sortCol, int sortDir, Comparator valueComparator) {
			m_sortCol = sortCol;
			m_sortDir = sortDir;
			m_valueComparator = valueComparator;
		}

		public int compare(Object o1, Object o2) {

			Object value1 = ((Object[]) o1)[m_sortCol];
			Object value2 = ((Object[]) o2)[m_sortCol];

			if (m_valueComparator != null) {
				return m_sortDir * m_valueComparator.compare(value1, value2);
			}
			if ((value1 == null) && (value2 == null)) {
				return 0;
			} else if (value1 == null) {
				return 1 * m_sortDir;
			} else if (value2 == null) {
				return -1 * m_sortDir;
			} else if ((value1 instanceof Boolean) && (value2 instanceof Boolean)) {
				Boolean b1 = (Boolean) value1;
				Boolean b2 = (Boolean) value2;
				if (b1.booleanValue() == true && b2.booleanValue() == false) {
					return 1;
				} else if (b1.booleanValue() == false && b2.booleanValue() == true) {
					return -1;
				} else {
					return 0;
				}

			} else if (value1 instanceof Comparable) {
				int v = ((Comparable) value1).compareTo(value2);

				if (m_sortDir == ASCENDING) {
					// return v;
				} else {
					v = v * -1;
					// return -1 * v;
				}
				return v;

			} else {
				return value1.toString().compareTo(value2.toString());
			}
		}

	}
}
