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

/**
 * This class is for internal use only.
 */
public interface JSAdapter {
	static final int RHINO = 0;
	static final int FESI = 1;
	static final int UNKNOWN = -1;

	/**
	 * Get JavaScript adapter type The adapter type could be RHINO, FESI or
	 * UNKNOWN. RHINO is the default and is the only one currently fully
	 * supported. FESI is not supported although most of the functions do work.
	 * The Adapter would not work if the type is UNKNOWN.
	 */
	abstract public int getType();

	// public Object getGlobalContext();
	// abstract public Object getScope() throws Throwable;

	/**
	 * Unwrap a JavScript object into Java Object
	 * 
	 * @param obj
	 *            the JavScript object to be unwrapped
	 * @return the result Java Object
	 */
	abstract public Object unwrap(Object obj);

	/**
	 * Converts a JavaScript array to a Java Object array This is used for
	 * converting JavaScript arrays without properties like
	 * <code>new Array(2, 3, 7, 8)</code> or
	 * <code>new Array(""x", "z", "abc")</code>.
	 * 
	 * @param inArray
	 *            the JavaScript array to be converted
	 * @return the result Java Object array
	 */
	abstract public Object[] convertToJavaArray(Object inArray);

	/*
	 * Converts a JavaScript array to a Java Object array based on a specified
	 * property list.
	 */
	// abstract public Object[] convertToJavaArray(Object inArray, String[]
	// properties);

	/**
	 * Converts a JavaScript array to a Java NamedArray object This is used for
	 * converting JavaScript arrays with properties like
	 * <code>a["x"] = "p"; a["y"] = "q";... </code>
	 * 
	 * @param inArray
	 *            the JavaScript array to be converted
	 * @return the result Java NamedArray object
	 */
	abstract public NamedArray convertToNamedArray(Object inArray);

	/**
	 * Make a JavaScript object with the specified name from a Java Object
	 * 
	 * @param jsObjName
	 *            the JavaScript object name
	 * @param obj
	 *            the Java Object
	 * @return a JavaScript object with the specified name
	 */
	abstract public Object makeJSObject(String jsObjName, Object obj);

	/**
	 * Make a cloned JavaScript scope. If in a mutithread environment, this
	 * cloned scope can be used to evaluate scripts without having to worry
	 * about blocking other thread's JavaScript execution. This cloned scope wil
	 * inherit the original "scope". But it can not add new objects to the
	 * original scope (to other threads).
	 */
	abstract public Object cloneScope();

	/**
	 * Make a JavaScript object with the specified name and scope from a Java
	 * Object
	 * 
	 * @param scope
	 *            the scope this JavaScript object to be created
	 * @param jsObjName
	 *            the JavaScript object name
	 * @param obj
	 *            the Java Object
	 * @return a JavaScript object with the specified name
	 */
	abstract public Object makeJSObject(Object scope, String jsObjName, Object obj);

	/**
	 * Make a JavaScript array name from a Java array
	 * 
	 * @param inArray
	 *            the Java array to be converted
	 * @return a JavaScript array
	 */
	abstract public Object convertToJSArray(Object[] inArray);

	// abstract public Object convertToJSArray(Object jsObject, Object[]
	// inArray)

	/**
	 * Evaluate a JavaScript without arguments.
	 * 
	 * @param script
	 *            the JavaScript string to be evaluated
	 */
	abstract public Object eval(String script) throws Throwable;

	/**
	 * Evaluate a JavaScript with input arguments.
	 * 
	 * @param script
	 *            the JavaScript string to be evaluated
	 * @param args
	 *            the input arguments
	 */
	abstract public Object eval(String script, Object[] args) throws Throwable;

	/**
	 * Evaluate a JavaScript as a function with input arguments.
	 * 
	 * @param script
	 *            the JavaScript string to be evaluated
	 * @param functionName
	 *            the JavaScript function name to be used
	 * @param args
	 *            the input arguments
	 */
	abstract public Object evalFunction(String script, String functionName, Object[] args) throws Throwable;

	/**
	 * Evaluate a JavaScript using a given scope.
	 * 
	 * @param scope
	 *            the scope to be used to evaluate the script
	 * @param script
	 *            the JavaScript string to be evaluated
	 */
	abstract public Object eval(Object scope, String script) throws Throwable;

	/**
	 * Evaluate a JavaScript with input arguments using a given scope.
	 * 
	 * @param scope
	 *            the scope to be used to evaluate the script
	 * @param script
	 *            the JavaScript string to be evaluated
	 * @param args
	 *            the input arguments
	 */
	abstract public Object eval(Object scope, String script, Object[] args) throws Throwable;

	/**
	 * Evaluate a JavaScript as a function with input arguments using a given
	 * scope.
	 * 
	 * @param scope
	 *            the scope to be used to evaluate the script
	 * @param script
	 *            the JavaScript string to be evaluated
	 * @param functionName
	 *            the JavaScript function name to be used
	 * @param args
	 *            the input arguments
	 */
	abstract public Object evalFunction(Object scope, String script, String functionName, Object[] args)
			throws Throwable;

}
