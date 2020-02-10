package com.tibco.cep.sharedresource.ssl;

import com.tibco.cep.sharedresource.ssl.SslConfigModel;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_STRING;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_FILE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_EVERYONE;


public class SslConfigFtlModel extends SslConfigModel {
	public static final String ID_TRUST_TYPE = CHANNEL_PROPERTY_TRUST_TYPE;  
	public static final String ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE =  CHANNEL_PROPERTY_TRUST_TYPE_FILE;  
	public static final String ID_HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING = CHANNEL_PROPERTY_TRUST_TYPE_STRING;
	public static final String ID_HTTPS_CONNECTION_TRUST_EVERYONE =  CHANNEL_PROPERTY_TRUST_TYPE_EVERYONE;
	String trust_type;
	String trust_file;
	String trust_string;
	
	public SslConfigFtlModel() {
		trust_type = "";
		trust_file="";
		trust_string="";
	}

	public String getTrust_string() {
		return trust_string;
	}

	public void setTrust_string(String trust_string) {
		this.trust_string = trust_string;
	}

	public String getTrust_type() {
		return trust_type;
	}

	public void setTrust_type(String trust_type) {
		this.trust_type = trust_type;
	}

	public String getTrust_file() {
		return trust_file;
	}

	public void setTrust_file(String trust_file) {
		this.trust_file = trust_file;
	}
   
}
