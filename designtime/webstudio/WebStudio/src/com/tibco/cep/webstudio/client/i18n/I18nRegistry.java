/**
 * 
 */
package com.tibco.cep.webstudio.client.i18n;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection of methods to register and setup the application.
 * 
 * @author Vikram Patil
 */
@SuppressWarnings("serial")
public class I18nRegistry implements Serializable {
	// Message key constants
	public static final String RMS_MESSAGES = "RMS_MESSAGES";
	public static final String GLOBAL_MESSAGES = "GLOBAL_MESSAGES";
	public static final String DT_MESSAGES = "DT_MESSAGES";
	public static final String RTI_MESSAGES = "RTI_MESSAGES";
	public static final String PROCESS_MESSAGES = "PROCESS_MESSAGES";
	public static final String DOMAIN_MESSAGES = "DOMAIN_MESSAGES";
	public static final String CUSTOM_SGWT_MESSAGES = "CUSTOM_SGWT_MESSAGES";
	
	private static HashMap<String, Object> messageCollection = new HashMap<String, Object>();
	
	private Map<String, String> RMSMessages;
	private Map<String, String> globalMessages;
	private Map<String, String> DTMessages;
	private Map<String, String> RTIMessages;
	private Map<String, String> processMessages;
	private Map<String, String> domainMessages;
	private Map<String, String> customSgwtMessages; 

	public void initializeResourceBundle() {
		// All bundle creation and registration will go in here.
		
		messageCollection.put(RMS_MESSAGES, new RMSMessages(RMSMessages));
		messageCollection.put(GLOBAL_MESSAGES, new GlobalMessages(globalMessages));
		messageCollection.put(DT_MESSAGES, new DTMessages(DTMessages));
		messageCollection.put(RTI_MESSAGES, new RTIMessages(RTIMessages));
		messageCollection.put(PROCESS_MESSAGES, new ProcessMessages(processMessages));
		messageCollection.put(DOMAIN_MESSAGES, new DomainMessages(domainMessages));
		messageCollection.put(CUSTOM_SGWT_MESSAGES, new CustomizedSmartgwtMessages(customSgwtMessages));
	}
	
	public static Object getResourceBundle(String bundleName) {
		return messageCollection.get(bundleName);
	}

	public Map<String, String> getGlobalMessages() {
		return globalMessages;
	}

	public void setGlobalMessages(Map<String, String> globalMessages) {
		this.globalMessages = globalMessages;
	}

	public Map<String, String> getProcessMessages() {
		return processMessages;
	}

	public void setProcessMessages(Map<String, String> processMessages) {
		this.processMessages = processMessages;
	}

	public Map<String, String> getDomainMessages() {
		return domainMessages;
	}

	public void setDomainMessages(Map<String, String> domainMessages) {
		this.domainMessages = domainMessages;
	}

	public Map<String, String> getRMSMessages() {
		return RMSMessages;
	}

	public void setRMSMessages(Map<String, String> rMSMessages) {
		RMSMessages = rMSMessages;
	}

	public Map<String, String> getDTMessages() {
		return DTMessages;
	}

	public void setDTMessages(Map<String, String> dTMessages) {
		DTMessages = dTMessages;
	}

	public Map<String, String> getRTIMessages() {
		return RTIMessages;
	}

	public void setRTIMessages(Map<String, String> rTIMessages) {
		RTIMessages = rTIMessages;
	}

	public Map<String, String> getCustomSgwtMessages() {
		return customSgwtMessages;
	}

	public void setCustomSgwtMessages(Map<String, String> customSgwtMessages) {
		this.customSgwtMessages = customSgwtMessages;
	}
}
