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
import java.util.Enumeration;
import java.util.Vector;

/**
 */
public class VectorUtil {

	/**
	 * Convert a Vector to an array
	 * 
	 * @param v
	 *            the Vector to be converted
	 * @return the result array
	 */
	public static Object[] toArray(Vector v) {
		if (v == null)
			return null;
		int size = v.size();
		Object[] a = new Object[size];
		int i = 0;
		for (Enumeration e = v.elements(); e.hasMoreElements(); i++)
			a[i] = e.nextElement();
		return a;

	}

	/**
	 * Convert a Vector to an uniform class array The array elements would all
	 * have the same class as the first element in the Vector.
	 * 
	 * @param v
	 *            the Vector to be converted
	 * @return the result array
	 */
	public static Object[] toUniformArray(Vector v) {
		if (v == null)
			return null;
		int size = v.size();
		if (size == 0)
			return new Object[0];
		Object firstElem = v.firstElement();
		Class elemClass = firstElem.getClass();
		Object arr = Array.newInstance(elemClass, size);
		Object[] a = (Object[]) arr;
		int i = 0;
		for (Enumeration e = v.elements(); e.hasMoreElements(); i++)
			a[i] = e.nextElement();
		return a;
	}

	/**
	 * Merge two Vectors into one Vector.
	 * 
	 * @return If v1 is null, v2 is returned; otherwise v1 is returned with v2
	 *         elements merged into it
	 */
	public static Vector merge(Vector v1, Vector v2) throws Exception {
		if (v1 == null)
			return v2;

		if (v2 == null)
			return v1;

		for (Enumeration e = v1.elements(); e.hasMoreElements();)
			v1.addElement(e.nextElement());

		return v1;
	}

}
