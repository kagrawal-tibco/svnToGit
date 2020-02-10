package com.tibco.cep.driver.as3;

import com.tibco.cep.runtime.channel.ChannelProperties;

public class AS3Properties implements ChannelProperties {
	public static final String AS3_CHANNEL_PROPERTY_DESCRIPTION = "Description";
	public static final String AS3_CHANNEL_PROPERTY_REALMSERVER = "RealmServer";
	public static final String AS3_CHANNEL_PROPERTY_GRIDNAME = "GridName";
	public static final String AS3_CHANNEL_PROPERTY_USERNAME = "UserName";
	public static final String AS3_CHANNEL_PROPERTY_PASSWORD = "Password";
	public static final String AS3_CHANNEL_PROPERTY_SECONDARY_REALM = "Secondary";

	public static final String AS3_CHANNEL_PROPERTY_SSL_NODE = "ssl";
	public static final String AS3_CHANNEL_PROPERTY_USESSL = "useSsl";
	public static final String AS3_CHANNEL_PROPERTY_TRUST_TYPE_FILE = "TrustFile";
	public static final String AS3_CHANNEL_PROPERTY_TRUST_TYPE_STRING = "TrustString";
	public static final String AS3_CHANNEL_PROPERTY_TRUST_STRING_TEXT= "Trust_String";
	
	public static final String AS3_CHANNEL_PROPERTY_TRUST_FILE_LOCATION = "Trust_File";
	public static final String AS3_CHANNEL_PROPERTY_TRUST_TYPE = "Trust_Type";

	// the following two properties are values for Trust type;
	// either trust everyone has to be specified or if
	// CHANNEL_PROPERTY_HTTPS_USE_TRUST_FILE is used
	// Trust file has to be specified
	public static final String AS3_CHANNEL_PROPERTY_HTTPS_USE_TRUST_FILE = "TrustUseFile";
	public static final String AS3_CHANNEL_PROPERTY_HTTPS_TRUST_TYPE_EVERYONE = "TrustEveryone";

	public static final String AS3_CHANNEL_NAME = "AS 3.x";
}
