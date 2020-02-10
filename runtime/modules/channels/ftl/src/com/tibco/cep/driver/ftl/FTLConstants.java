package com.tibco.cep.driver.ftl;

import com.tibco.cep.runtime.channel.ChannelProperties;

public interface FTLConstants extends ChannelProperties {	
	static final String CHANNEL_PROPERTY_DESCRIPTION = "Description";
	static final String CHANNEL_PROPERTY_REALMSERVER = "RealmServer";
	static final String CHANNEL_PROPERTY_USERNAME = "UserName";
	static final String CHANNEL_PROPERTY_PASSWORD= "Password";
	static final String CHANNEL_PROPERTY_SECONDARY= "Secondary";
	static final String CHANNEL_PROPERTY_TRUST_TYPE= "Trust_Type";
	static final String CHANNEL_PROPERTY_TRUST_FILE_LOCATION= "Trust_File";
	static final String CHANNEL_PROPERTY_TRUST_STRING_TEXT= "Trust_String";
	static final String CHANNEL_PROPERTY_TRUST_STRING= "Trust_String";
	static final String CHANNEL_PROPERTY_USESSL = "useSsl";
	static final String CHANNEL_PROPERTY_SSL_NODE = "ssl";
	static final String CHANNEL_NAME = "FTL";
	
	static final String CHANNEL_PROPERTY_TRUST_TYPE_FILE = "TrustFile";
	static final String CHANNEL_PROPERTY_TRUST_TYPE_STRING = "TrustString";
	static final String CHANNEL_PROPERTY_TRUST_TYPE_EVERYONE = "TrustEveryone";

	static final String DESTINATION_PROPERTY_APPNAME= "AppName";
	static final String DESTINATION_PROPERTY_ENDPOINTNAME= "EndpointName";
	static final String DESTINATION_PROPERTY_INSTANCENAME= "InstName";
	static final String DESTINATION_PROPERTY_SECONDARY= "Secondary";
	static final String DESTINATION_PROPERTY_MATCH = "Match";
	static final String DESTINATION_PROPERTY_DURABLE_NAME = "DurableName";
	
	static final String DESTINATION_PROPERTY_MESSAGE_TYPE = "MessageType";
	static final String DESTINATION_PROPERTY_FORMATS = "Formats";
	
	static final String ESCAPE_HYPEN = "_HYPEN_";
	
	static final String FTL_INBOX_NAME = "My-Inbox";
	
	static final String PROPERTY_KEY_FTL_CLIENT_PROPERTY_PREFIX = "be.channel.ftl";
}
