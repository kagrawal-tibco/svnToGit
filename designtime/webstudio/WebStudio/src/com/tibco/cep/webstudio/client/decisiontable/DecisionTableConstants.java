package com.tibco.cep.webstudio.client.decisiontable;

import com.google.gwt.i18n.shared.DateTimeFormat;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableConstants {

	//Date Formatter for BE Date Format
	public static DateTimeFormat BE_DATE_TIME_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static DateTimeFormat TIME_FORMAT = DateTimeFormat.getFormat("HH:mm:ss");

	public static final String CONDITION_COLUMN_DELIMITER = "$";
	public static final String ACTION_COLUMN_DELIMITER = "$$";
	
}
