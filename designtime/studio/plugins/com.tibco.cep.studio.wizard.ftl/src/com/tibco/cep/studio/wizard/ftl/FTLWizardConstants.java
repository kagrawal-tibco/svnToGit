package com.tibco.cep.studio.wizard.ftl;

public interface FTLWizardConstants {
	final static  String CHANNEL = "FTL";
	
	final static String PAGE_NAME_ERROR= "Project Selection Error";
	final static String PAGE_NAME_CHANNEL = "Select Channel";
	final static String PAGE_NAME_FILE = "Select File";
	final static String PAGE_NAME_APP = "Select Application";
	final static String PAGE_NAME_APP_INFORS = "Select Application Informations";
	final static String PAGE_NAME_TRAN = "Select Transport";
	final static String PAGE_NAME_SETNAME = "Set name";
	final static String PAGE_NAME_CONFIRM_APPS = "Confirm selected app";
	
	final static String PAGE_NAME_CHANNEL_LABEL = "FTL Channel List:";
	final static String PAGE_NAME_SELECT_FILE_TEXT_LABEL = "ftlrealm File:";
	final static String PAGE_NAME_SELECT_APP_LABEL = "FTL Application List:";
	final static String PAGE_NAME_SELECT_SELECTED_APP_LABEL = "Selected configurations: ";
	final static String PAGE_NAME_APP_ENDPOINT_LABEL = "Endpoint List:";
	final static String PAGE_NAME_APP_INSTANCE_LABEL = "Instance List:";
	final static String PAGE_NAME_APP_MESSAGE_TYPE_LABEL = "Message Type:";
	final static String PAGE_NAME_APP_BUILD_IN_LABEL = "Built-in Formats Type:";
	final static String PAGE_NAME_APP_FORMAT_LABEL = "Format List:";
	final static String PAGE_NAME_APP_TRANSPORT_LABEL = "Transport List:";
	
	static final String ESCAPE_HYPEN = "_HYPEN_";
	static final String UNDERSCORE = "_";
	
	static final String MANAGED= "managedFormat";
	static final String BUILDIN = "built-in";
	static final String BUILDIN_OPAQUE = "built-in(opaque)";
	static final String BUILDIN_KEYED_OPAQUE = "built-in(keyedOpaque)";
	static final String DYNAMIC = "dynamic";
}
