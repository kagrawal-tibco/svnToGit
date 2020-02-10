/*
 * Copyright (c) 2000 TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * TIBCO Software Inc.
 *
 */

package com.tibco.hawk.jshma.util;

/**
 */
public class JSException extends Exception {

	public JSException(String s) {
		super("{{" + s + "}}");
	}

	public static void throwException(String s) throws JSException {
		throw new JSException(s);
	}

}
