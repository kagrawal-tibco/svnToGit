package com.tibco.cep.runtime.model.pojo.exim;

/*
* Author: Ashwin Jayaprakash / Date: 5/16/12 / Time: 1:35 PM
*/

/**
 * Some known extensions to {@link PortablePojo}.
 */
public interface PortablePojoConstants {
	public static final String PROPERTY_NAME_KEY = "id";
	public static final String PROPERTY_NAME_ID = PROPERTY_NAME_KEY;

	public static final String PROPERTY_NAME_EXT_ID = "extId";
	public static final String PROPERTY_NAME_TYPE_ID = "typeId__";
	public static final String PROPERTY_NAME_VERSION = "version__";
	public static final String PROPERTY_CONCEPT_NAME_DELETED = "deleted__";
	public static final String PROPERTY_EVENT_NAME_RECOVERED = "recovered__";
	public static final String PROPERTY_EVENT_NAME_PAYLOAD = "payload__";
	public static final String PROPERTY_CONCEPT_NAME_PARENT = "parent__";
	public static final String PROPERTY_CONCEPT_NAME_REV_REFERENCES = "rrf__";

	// The TimeEvent constants
	public static final String PROPERTY_NAME_TIME_EVENT_CLOSURE = "closure";
	public static final String PROPERTY_NAME_TIME_EVENT_NEXT = "next";
	public static final String PROPERTY_NAME_TIME_EVENT_TTL = "ttl";
	public static final String PROPERTY_NAME_TIME_EVENT_FIRED = "fired";
}
