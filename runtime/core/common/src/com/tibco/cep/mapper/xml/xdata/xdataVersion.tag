/*
 * $RCSfile$ $Revision: 44827 $ $Date: 2005-08-25 12:04:24 -0500 (Thu, 25 Aug 2005) $
 *
 * Copyright(c) 2005-2012 TIBCO Software Inc. All rights reserved.
 *
 * xdata.jar Version Information
 *
 */

/*





	AUTOMATICALLY GENERATED AT BUILD TIME !!!!

	DO NOT EDIT !!!





 * "xdataVersion.java" is automatically generated at
 * build time from "xdataVersion.tag"
 *
 * Any maintenance changes MUST be applied to "xdataVersion.tag"
 *
 */
package com.tibco.xml.xdata;

public final class xdataVersion {

	static final public String xmlui_component = "@XMLUI_COMPONENT@";
	static final public String xdata_version   = "@XDATA_VERSION@";
	static final public String xdata_build     = "@XDATA_BUILD@";
	static final public String xdata_date      = "@XMLUI_DATE@";

	static final public String xmlui_product   = "@XMLUI_PRODUCT@";
	static final public String xmlui_version   = "@XMLUI_VERSION@";
	static final public String xmlui_build     = "@XMLUI_BUILD@";
	static final public String xmlui_date      = "@XMLUI_DATE@";

	static final public String xmlui_company   = "@XMLUI_COMPANY@";
	static final public String xmlui_copyright = "@XMLUI_COPYRIGHT@";

	static public String xdata_getVersion() {
		return "Version " + xdata_version + "." + xdata_build + ", " + xdata_date;
	}

	static public String xmlui_getVersion() {
		return "Version " + xmlui_version + "." + xmlui_build + ", " + xmlui_date;
	}

	static public String xmlui_getCopyright() {
		return xmlui_copyright;
	}

	static public String xmlui_getComponent() {
		return xmlui_component;
	}

	static public String xmlui_getProduct() {
		return xmlui_product;
	}

	static public void main(String[] args) {
		System.out.println(xmlui_getComponent() + " " + xdata_getVersion());
		System.out.println(xmlui_getProduct() + " " + xmlui_getVersion());
	}

}
