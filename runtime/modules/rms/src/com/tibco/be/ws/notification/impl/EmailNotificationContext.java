package com.tibco.be.ws.notification.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.lang3.StringUtils;

import com.tibco.be.ws.notification.INotificationContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailNotificationContext implements INotificationContext {

	private Properties mailSessionProperties = null;
	private String mailProtocol = null;
	private String mailServerHost = null; 
	private int mailServerPort;
	private String senderEmail = null;
	private String senderUserName = null;
	private String senderPassword = null;
	private String receiverEmail = null;
	private String receiverCCEmails = null;
	
	private String emailSubject = null;
	private String emailBody = null;

	public static final String DEFAULT_MAIL_PROTOCOL = "smtp";
	public static final int DEFAULT_PORT = 25;
	
	public Properties getMailSessionProperties() {
		if (mailSessionProperties != null) {
			return (Properties) mailSessionProperties.clone();
		}
		return null;
	}	
	public void addMailSessionProperty(String propName, String propValue) {
		if (mailSessionProperties == null) {
			mailSessionProperties = new Properties(); 
		}
		mailSessionProperties.put(propName, propValue);
	}
	public String getMailProtocol() {
		return mailProtocol;
	}
	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}
	public String getMailServerHost() {
		return mailServerHost;
	}
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}
	public int getMailServerPort() {
		return mailServerPort;
	}
	public void setMailServerPort(int mailServerPort) {
		this.mailServerPort = mailServerPort;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}	
	public String getSenderUserName() {
		return senderUserName;
	}
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	public String getSenderPassword() {
		return senderPassword;
	}
	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getReceiverCCEmails() {
		return receiverCCEmails;
	}
	public void setReceiverCCEmails(String receiverCCEmails) {
		this.receiverCCEmails = receiverCCEmails;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(Map<String, String> contextPropertiesMap) {

		Properties sysProps = System.getProperties();
		Set sysPropKeys = sysProps.keySet();
		for (Object key : sysPropKeys) {
			if (key.toString().startsWith("ws.notify.prop.")) {
				String propKey = StringUtils.substringAfter(key.toString(), "ws.notify.prop.");				
				contextPropertiesMap.put(propKey, sysProps.getProperty(key.toString()));;
			}	
		}
		mailProtocol = contextPropertiesMap.get("MAIL_PROTOCOL");
		mailServerHost = contextPropertiesMap.get("MAIL_SERVER_HOST");
		try {
			mailServerPort = Integer.parseInt((String)contextPropertiesMap.get("MAIL_SERVER_PORT"));
		} catch (NumberFormatException nfe) {
			mailServerPort = DEFAULT_PORT;
		}
		senderEmail = contextPropertiesMap.get("SENDER_EMAIL");
		senderUserName = contextPropertiesMap.get("SENDER_USERNAME");
		senderPassword = contextPropertiesMap.get("SENDER_PASSWORD");
		receiverEmail = contextPropertiesMap.get("RECEIVER_EMAIL");
		receiverCCEmails = contextPropertiesMap.get("RECEIVER_CC_EMAILS");
		
		mailSessionProperties = new Properties();
    	Set entries = contextPropertiesMap.entrySet();
    	Iterator itr = entries.iterator();
    	while (itr.hasNext()) {
    		Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
    		String key = entry.getKey();
    		if (key != null && key.startsWith("mail.smtp")) {
    			mailSessionProperties.put(entry.getKey(), entry.getValue());
    		}	
    	}			
	}

	@Override
	public void prepareMessage(String notificationType, String messageTemplateContents, Map<String, String> messageData) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(messageTemplateContents.getBytes("UTF-8"));
		StringTemplateGroup messageTemplates = new StringTemplateGroup(new InputStreamReader(bais,"UTF-8"), DefaultTemplateLexer.class);

		StringTemplate emailSubjectTemplate = messageTemplates.getInstanceOf(notificationType + "EmailSubject");
		emailSubjectTemplate.setAttributes(messageData);
		emailSubject = emailSubjectTemplate.toString();

		StringTemplate emailBodyTemplate = messageTemplates.getInstanceOf(notificationType + "EmailContent");
		emailBodyTemplate.setAttributes(messageData);
		emailBody = emailBodyTemplate.toString();
	}	
}
