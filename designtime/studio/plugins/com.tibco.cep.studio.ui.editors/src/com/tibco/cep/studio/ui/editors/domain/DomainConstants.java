package com.tibco.cep.studio.ui.editors.domain;

import java.text.SimpleDateFormat;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainConstants {
	
	public static final String FALSE_VAL = "false";
	public static final String TRUE_VAL = "true";
	public static final String DETAILSINFO_PAGE = "detailsinfo";
	public static final String DETAILS_PAGE = "details";
	public static final String TYPE_STRING = "String";
	public static final String TYPE_INT = "int";
	public static final String TYPE_DATE = "DateTime";
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_REAL = "real";
	public static final String TYPE_LONG = "long";
	public static final String KIND_SIMPLE = "simple_";
	public static final String KIND_RANGE = "range_";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String RANGE_VALUE_SEPARATOR = ",";
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);
	public static final String DOMAIN_VALIDATION_ERROR = "INVALID_ENTRY";
	
}
