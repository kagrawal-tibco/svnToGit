/*
 * $RCSfile$ $Revision: 44827 $ $Date: 2005-08-25 12:04:24 -0500 (Thu, 25 Aug 2005) $
 *
 * Copyright(c) 2005-2012 TIBCO Software Inc. All rights reserved.
 *
 * mapper.jar Version Information
 *
 */

/*





	AUTOMATICALLY GENERATED AT BUILD TIME !!!!

	DO NOT EDIT !!!





 * "mapperVersion.java" is automatically generated at
 * build time from "mapperVersion.tag"
 *
 * Any maintenance changes MUST be applied to "mapperVersion.tag"
 *
 */
package com.tibco.ui.data.bind;

public final class mapperVersion {

	static final public String xmlui_component = "@XMLUI_COMPONENT@";
	static final public String mapper_version  = "@MAPPER_VERSION@";
	static final public String mapper_build    = "@MAPPER_BUILD@";
	static final public String mapper_date     = "@XMLUI_DATE@";

	static final public String xmlui_product   = "@XMLUI_PRODUCT@";
	static final public String xmlui_version   = "@XMLUI_VERSION@";
	static final public String xmlui_build     = "@XMLUI_BUILD@";
	static final public String xmlui_date      = "@XMLUI_DATE@";

	static final public String xmlui_company   = "@XMLUI_COMPANY@";
	static final public String xmlui_copyright = "@XMLUI_COPYRIGHT@";

	static public String mapper_getVersion() {
		return "Version " + mapper_version + "." + mapper_build + ", " + mapper_date;
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
		System.out.println(xmlui_getComponent() + " " + mapper_getVersion());
		System.out.println(xmlui_getProduct() + " " + xmlui_getVersion());
	}

}
