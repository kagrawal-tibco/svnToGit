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

import java.lang.reflect.Constructor;

import COM.TIBCO.hawk.utilities.trace.Trace;

/**
 * This class is for internal use only.
 */
public class JSHandler {
	public static final int RHINO = JSAdapter.RHINO;
	public static final int FESI = JSAdapter.FESI;
	public static final int UNKNOWN = JSAdapter.UNKNOWN;

	static final String JS_PACKAGES = "Plugin = Packages.com.tibco.hawk.jshma.plugin;\n Util = Packages.com.tibco.hawk.jshma.util;\n Rv = Packages.com.tibco.tibrv";

	static final String RHINO_DRIVER = "Rhino";
	static final String FESI_DRIVER = "FESI";

	private static int UNSET = -2;
	private static int sType = UNSET;
	private static JSAdapter sJsHandle = null;
	private static String sJsPkgs = null;
	private static String[] sJsExts = null;

	/**
	 * Get JavaScript adapter type. The adapter type could be RHINO, FESI or
	 * UNKNOWN. RHINO is the default and is the only one currently fully
	 * supported. FESI is not supported although most of the functions do work.
	 * The Adapter would not work if the type is UNKNOWN.
	 */
	public static int getType() {
		if (sType != UNSET)
			return sType;

		sType = UNKNOWN;
		String driver = System.getProperty("js.JSDriver");
		// System.out.println("driver="+driver+" handle="+sJsHandle);
		if (driver != null && driver.length() != 0) {
			if (driver.equalsIgnoreCase(FESI_DRIVER)) {
				sType = FESI;
				try {
					// sJsHandle = new FESIAdapter();
					sJsHandle = (JSAdapter) Class.forName("com.tibco.hawk.jshma.util.FESIAdapter").newInstance();
					// System.out.println("driver="+driver+" handle="+sJsHandle);
				} catch (Exception e) {
				}
				;
			}
			if (driver.equalsIgnoreCase(RHINO_DRIVER)) {
				sType = RHINO;
				try {
					// sJsHandle = new RhinoAdapter();
					sJsHandle = (JSAdapter) Class.forName("com.tibco.hawk.jshma.util.RhinoAdapter").newInstance();
					// System.out.println("driver="+driver+" handle="+sJsHandle);
				} catch (Exception e) {
				}
				;
			}
		} else {
			sType = RHINO;
			try {
				// sJsHandle = new RhinoAdapter();
				sJsHandle = (JSAdapter) Class.forName("com.tibco.hawk.jshma.util.RhinoAdapter").newInstance();
				// System.out.println("driver="+driver+" handle="+sJsHandle);
			} catch (Exception e) {
			}
			;
		}
		return sType;
	}

	/**
	 * Get a new JavaScript handler. This method is for internal use only.
	 */
	public static JSAdapter getNewHandler(String[] extensions, String packages) {
		return getHandler(extensions, packages, true);
	}

	/**
	 * Get a JavaScript handler. This method is for internal use only.
	 */
	public static JSAdapter getHandler(String[] extensions, String packages) {
		return getHandler(extensions, packages, false);
	}

	private static JSAdapter getHandler(String[] extensions, String packages, boolean getNew) {
		String pkgs = packages;
		if (pkgs == null)
			pkgs = JS_PACKAGES;

		if ((!getNew) && (sJsHandle != null) && (sJsPkgs != null && sJsPkgs.equals(pkgs))
				&& ((sJsExts == null && extensions == null) || (sJsExts == extensions)))
			return sJsHandle;

		JSAdapter jsHandle = null;

		int type = getType();
		try {
			if (type == FESI) {
				Constructor cs = Class.forName("com.tibco.hawk.jshma.util.FESIAdapter").getConstructor(
						new Class[] { String[].class, String.class });
				jsHandle = (JSAdapter) cs.newInstance(new Object[] { extensions, pkgs });
			} else if (type == RHINO) {
				Constructor cs = Class.forName("com.tibco.hawk.jshma.util.RhinoAdapter").getConstructor(
						new Class[] { String.class });
				jsHandle = (JSAdapter) cs.newInstance(new Object[] { pkgs });
			}

		} catch (Exception e) {
		}
		;

		if (jsHandle != null) {
			sJsHandle = jsHandle;
			sJsPkgs = pkgs;
			sJsExts = extensions;
		}

		return jsHandle;
	}

	/**
	 * Convert a JavaScript array to Java array.
	 * 
	 * @param array
	 *            the JavaScript array to be converted
	 * @return the result Java array
	 */
	public static Object[] convertToJavaArray(Object array) {
		if (getType() == UNKNOWN)
			return null;
		else {
			if (sJsHandle == null) {
				if (array instanceof Object[])
					return (Object[]) array;
				// LastError.set("JavaScript handle is null.");
				// return null;
			}
			return sJsHandle.convertToJavaArray(array);
		}
	}

	/*
	 * public static String[] convertToJavaStringArray(Object inArray) { if
	 * (getType() == UNKNOWN) return null; else { if (sJsHandle == null) {
	 * LastError.set("JavaScript handle is null."); return null; } return
	 * sJsHandle.convertToJavaStringArray(inArray); } }
	 * 
	 * public static Object[] convertToJavaArray(Object inArray, String[]
	 * properties) { if (getType() == UNKNOWN) return null; else { if (sJsHandle
	 * == null) { LastError.set("JavaScript handle is null."); return null; }
	 * return sJsHandle.convertToJavaArray(inArray, properties); } }
	 */

	/**
	 * Convert a JavaScript array to Java NamedArray object.
	 * 
	 * @param array
	 *            the JavaScript array to be converted. The JavaScript array has
	 *            to be an array with properties, for example
	 *            <code>a["x"] = "abc"; a["y"] = "def];</code>
	 * @return the result Java NamedArray object
	 */
	public static NamedArray convertToNamedArray(Object array) {
		if (getType() == UNKNOWN)
			return null;
		else {
			if (sJsHandle == null) {
				LastError.set("JavaScript handle is null.");
				return null;
			}
			return sJsHandle.convertToNamedArray(array);
		}
	}

	/**
	 * Unwrap a JavScript object into Java Object
	 * 
	 * @param jsObject
	 *            the JavScript object to be unwrapped
	 * @return the result Java Object
	 */

	public static Object unwrap(Object jsObject) {
		if (getType() == UNKNOWN)
			return jsObject;
		else {
			if (sJsHandle == null) {
				LastError.set("JavaScript handle is null.");
				return null;
			}
			return sJsHandle.unwrap(jsObject);
		}
	}

	/*
	 * public static Object convertToJSArray(Object jsObject, Object[] inArray)
	 * { if (getType() == UNKNOWN) return null; else { if (sJsHandle == null) {
	 * LastError.set("JavaScript handle is null."); return null; } return
	 * sJsHandle.convertToJSArray(jsObject, inArray); } }
	 */

	/*
	 * public static void throwException (String s) throws JSException { throw
	 * new JSException(s); }
	 */

	static Object eval(String script) {
		LastError.clear();
		try {

			// String script = StringTool.file2str(scriptFn);
			return sJsHandle.eval(script);
		} catch (Throwable th) {
			Trace t = ContextControl.getTrace();
			t.log(Trace.ERROR, "Failed to eval '" + script + "' because of " + th);
			return null;
		}
	}

}
