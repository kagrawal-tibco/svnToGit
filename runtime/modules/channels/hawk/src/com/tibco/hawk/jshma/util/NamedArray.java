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

import java.util.HashMap;

import COM.TIBCO.hawk.utilities.misc.ObjChecker;

/**
 * This class is used to implement a very useful data structure to handle an
 * array of data. Basicly, the data structure is composed of two parts. First,
 * the array, in the form of Object[]. Second, optionally a list of element
 * names corresponding to the elements in the array. This class is similar to
 * NamedTabularData except that it has only one row and has no auxiliary data.
 */
public class NamedArray {
	private static String sClassName = "com.tibco.hawk.jshma.util.NamedArray";
	/**
	 * The list of all element names.
	 */
	protected String[] mNameList = null;
	/**
	 * The list of all element display names.
	 */
	protected String[] mDisplayNameList = null;
	/**
	 * The actual array data.
	 */
	protected Object[] mData = null;
	private int mElems = 0;

	/**
	 * The auxiliary array attribute data associated with the whole array.
	 */
	protected HashMap mArrayAttrs = null;

	/**
	 * Constructor for null data.
	 */
	public NamedArray() {
	}

	/**
	 * Constructor with an array data.
	 * 
	 * @param data
	 *            the array data
	 */
	public NamedArray(Object[] data) {
		this(null, data);
	}

	/**
	 * Constructor with an array data and an element name list
	 * 
	 * @param nameList
	 *            the element name list
	 * @param data
	 *            the array data
	 */
	public NamedArray(String[] nameList, Object[] data) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null)
				mElems = data.length;
		} else
			mElems = mNameList.length;
		mData = data;
	}

	/**
	 * Constructor with an array data and an element name list
	 * 
	 * @param nameList
	 *            the element name list
	 * @param displayNameList
	 *            the element display name list The displayn name list must have
	 *            the same length as the nameList length.
	 * @param data
	 *            the array data
	 */
	public NamedArray(String[] nameList, String[] displayNameList, Object[] data) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null)
				mElems = data.length;
		} else
			mElems = mNameList.length;
		mDisplayNameList = displayNameList;
		mData = data;
	}

	private NamedArray(String[] nameList, String[] displayNameList, Object[] data, HashMap arrayAttrs) {
		mNameList = nameList;
		if (nameList == null) {
			if (data != null)
				mElems = data.length;
		} else
			mElems = mNameList.length;
		mDisplayNameList = displayNameList;
		mData = data;
		mArrayAttrs = arrayAttrs;
	}

	/**
	 * Get the array data.
	 */
	public Object[] getData() {
		return mData;
	}

	/**
	 * Get the array element count.
	 */
	public int getElementCount() {
		return mElems;
	}

	/**
	 * @deprecated Use getElementNames()
	 */
	public String[] getColumnNames() {
		return mNameList;
	}

	/**
	 * Get element names
	 */
	public String[] getElementNames() {
		return mNameList;
	}

	/**
	 * Get element display names
	 */
	public String[] getElementDisplayNames() {

		return mDisplayNameList == null ? mNameList : mDisplayNameList;
	}

	/**
	 * Set element display names
	 */
	public void setElementDisplayNames(String[] displayNameList) {
		if (displayNameList != null && mNameList != null && displayNameList.length != mNameList.length)
			return;
		mDisplayNameList = displayNameList;
	}

	/**
	 * Get the data with the specified element index
	 * 
	 * @param col
	 *            the element index
	 */
	public Object get(int col) {
		return mData[col];
	}

	/**
	 * Get the data with the specified element name
	 * 
	 * @param colName
	 *            the element name
	 */
	public Object get(String colName) {
		if (colName != null)
			for (int i = 0; i < mElems; i++)
				if (colName.equals(mNameList[i]))
					return mData[i];
		return null;
	}

	/**
	 * @deprecated use changeElementNames
	 */
	public void changeColumnNames(String[] newColumnNames) {
		LastError.clear();
		if (newColumnNames == null || newColumnNames.length != mElems) {
			LastError.set("Column name list size does not match the original number.", sClassName
					+ ".changeColumnNames()");
			return;
		}
		mNameList = newColumnNames;
	}

	/**
	 * Change the element name list to the newly specified one
	 * 
	 * @param newElementNames
	 *            the new element name list
	 */
	public void changeElementNames(String[] newElementNames) {
		LastError.clear();
		if (newElementNames == null || newElementNames.length != mElems) {
			LastError.set("Element name list size does not match the original number.", sClassName
					+ ".changeElementNames()");
			return;
		}
		mNameList = newElementNames;
	}

	/*
	 * public void addElementNames(Object inArray) { LastError.clear(); if
	 * (mNameList != null) {
	 * LastError.set("Element name list has already been set.", sClassName +
	 * ".addElementNames()"); return; }
	 * 
	 * 
	 * Object[] row = JSHandler.convertToJavaArray(inArray); if (row == null)
	 * return;
	 * 
	 * if (mElems == 0) { mElems = row.length; } else if (mElems != row.length)
	 * { LastError.set("Element name list has different size than data.",
	 * sClassName + ".addElementNames()"); return; }
	 * 
	 * mNameList = new String[mElems]; for (int i = 0; i < mElems; i++)
	 * mNameList[i] = row[i].toString(); }
	 */

	/**
	 * Select the specified elements to form an array according to the specified
	 * element name order.
	 * 
	 * @param selectedColNames
	 *            the selected element name list.
	 * @return the ordered array according to the specified element name order.
	 *         If the selectedColNames was null or the element name list was
	 *         null, the data array is returned.
	 */
	public Object[] extractOrderedArray(String[] selectedColNames) {
		if (selectedColNames == null || mNameList == null)
			return mData;
		Object[] result = new Object[selectedColNames.length];
		for (int i = 0; i < selectedColNames.length; i++)
			result[i] = get(selectedColNames[i]);
		return result;

	}

	/**
	 * @deprecated Use hasElement
	 */

	public boolean hasColumn(String colName) {
		if (colName == null)
			return false;
		for (int i = 0; i < mElems; i++)
			if (colName.equals(mNameList[i]))
				return true;
		return false;
	}

	/**
	 * Check whether the specified element is a valid name in the element name
	 * list.
	 * 
	 * @param elementName
	 *            the element name to be checked
	 * @return true if the specified element is a valid name
	 */

	public boolean hasElement(String elementName) {
		if (elementName == null)
			return false;
		for (int i = 0; i < mElems; i++)
			if (elementName.equals(mNameList[i]))
				return true;
		return false;
	}

	/**
	 * A convenient method to dump all the data in a NamedArray object to a
	 * string
	 */

	public String toString() {
		return "NameList = " + ObjChecker.toString(mNameList) + " ArrayAttrs = " + ObjChecker.toString(mArrayAttrs)
				+ " Data = " + ObjChecker.toString(mData);
	}

	/**
	 * Set array attribute
	 * 
	 * @param name
	 *            the array attribute name
	 * @param value
	 *            the array attribute value
	 */
	public void setArrayAttr(String name, Object value) {
		if (mArrayAttrs == null)
			mArrayAttrs = new HashMap();
		mArrayAttrs.put(name, value);
	}

	/**
	 * Get array attribute
	 * 
	 * @param name
	 *            the array attribute name
	 * @return the array attribute value
	 */
	public Object getArrayAttr(String name) {
		if (mArrayAttrs == null)
			return null;
		return mArrayAttrs.get(name);
	}

	/**
	 * Get all array attributes
	 * 
	 * @return the array attribute HashMap
	 */
	HashMap getAllArrayAttrs() {
		return mArrayAttrs;
	}

}
