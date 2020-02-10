package com.tibco.be.ws.notification.impl;

import java.util.Date;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.tibco.be.ws.notification.INotification;
import com.tibco.be.ws.notification.NotificationStatus;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailNotification implements INotification<EmailNotificationContext> {

	private Transport transport = null;
	private String messageTemplateContents = null;
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(EmailNotification.class);
	
	public void openConnection(EmailNotificationContext emailNotificationContext) throws Exception {
		
    	String mailProtocol = (emailNotificationContext.getMailProtocol() == null || emailNotificationContext.getMailProtocol().isEmpty()) ? 
				EmailNotificationContext.DEFAULT_MAIL_PROTOCOL : emailNotificationContext.getMailProtocol();

		int mailServerPort = (emailNotificationContext.getMailServerPort() <= 0) ? EmailNotificationContext.DEFAULT_PORT
				: emailNotificationContext.getMailServerPort();

		StringBuffer errorMessage = new StringBuffer();
		if (!validateConnectionParams(emailNotificationContext, errorMessage)) {
			throw new Exception(errorMessage.toString());
		}

		String senderPassword = emailNotificationContext.getSenderPassword();		
        try {
            boolean isObfuscated = ObfuscationEngine.hasEncryptionPrefix(senderPassword);
            if (isObfuscated) {
            	senderPassword = new String(ObfuscationEngine.decrypt(senderPassword));
            } else {
                LOGGER.log(Level.WARN, "Email Sender password is not obfuscated");
            }
        } catch (AXSecurityException axe) {
            throw new SecurityException("Error decrypting Email Sender password");
        }

    	//Get the default Mail Session
        Session session = Session.getDefaultInstance(emailNotificationContext.getMailSessionProperties(), null);
        session.setDebug(true);
        transport = session.getTransport(mailProtocol);
        transport.connect(emailNotificationContext.getMailServerHost(), mailServerPort, emailNotificationContext.getSenderUserName(), senderPassword);
	}
	
    /**
     * @param emailNotificationContext
     * @param errorMessage
     * @return
     */
    private boolean validateConnectionParams(EmailNotificationContext emailNotificationContext, StringBuffer errorMessage) {
    	
    	boolean isValid = true;
    	if (emailNotificationContext.getMailServerHost() == null || emailNotificationContext.getMailServerHost().isEmpty()) {
    		errorMessage.append("\n Mail Server Host name not defined.");
    		isValid = false;
    	}
    		
    	if (emailNotificationContext.getSenderUserName() == null || emailNotificationContext.getSenderUserName().isEmpty()) {
    		errorMessage.append("\n Sender Username not defined.");
    		isValid = false;
    	}
    	
    	if (emailNotificationContext.getSenderPassword() == null || emailNotificationContext.getSenderPassword().isEmpty()) {
    		errorMessage.append("\n Sender password not defined.");
    		isValid = false;
    	}
    	
    	return isValid;
    }
    
    /**
     * @param messageTemplateContents
     * @throws Exception
     */
    @Override
    public void loadMessageTemplates(String messageTemplateContents) throws Exception {
    	this.messageTemplateContents = new String(messageTemplateContents);
    }

	/**
	 * @param notificationContext
	 * @param notificationType
	 * @param messageTemplateContents
	 * @param messageDataMapObj
	 * @throws Exception
	 */
    @Override
	public void prepareMessage(EmailNotificationContext notificationContext, String notificationType, String messageTemplateContents, Map<String, String> messageDataMap) throws Exception {
		if (messageTemplateContents != null) {
			notificationContext.prepareMessage(notificationType, messageTemplateContents, messageDataMap);
		} else {
			notificationContext.prepareMessage(notificationType, this.messageTemplateContents, messageDataMap);
		}
	}
    
    /**
     *
     * @param emailNotificationContext
     * @return
     */
    @Override
    public NotificationStatus notify(EmailNotificationContext emailNotificationContext) {
    	
    	NotificationStatus notificationStatus = new NotificationStatus();
    	StringBuffer errorMessage = new StringBuffer();
    	
        try {
	    	//Get the default Mail Session
	        Session session = Session.getDefaultInstance(emailNotificationContext.getMailSessionProperties(), null);

	        //Reconnect if connection lost
	        if (transport == null || !transport.isConnected()) {
	        	openConnection(emailNotificationContext);           	
            }
	        
	        //Validate Context
	        if (!validateContext(emailNotificationContext, errorMessage)) {
                notificationStatus.setSuccess(false);
                notificationStatus.setErrorMessage("Error sending Email Notification - " + errorMessage.toString());
                return notificationStatus;
	        }
	        
        	//Prepare MimeMessage
	        MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailNotificationContext.getSenderEmail()));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailNotificationContext.getReceiverEmail(), false));
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailNotificationContext.getReceiverCCEmails(), false));
            mimeMessage.setSubject(emailNotificationContext.getEmailSubject(), "UTF-8");
            mimeMessage.setSentDate(new Date());

            //Prepare MimeMultipart Body
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailNotificationContext.getEmailBody(), "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);
            
            //Send Message
            if (transport != null && transport.isConnected()) {
            	transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                notificationStatus.setSuccess(true);
                notificationStatus.setErrorMessage(null);
            } else {
                notificationStatus.setSuccess(false);
                notificationStatus.setErrorMessage("Error sending Email Notification, SMTP Transport is NULL or not connected.");            	
            }
        } catch (Exception e) {
            notificationStatus.setSuccess(false);
            notificationStatus.setErrorMessage(String.format("Error sending Email Notification [%s] ", e.getMessage()));
        }
        return notificationStatus;    	
    }
    
    /**
     * @param emailNotificationContext
     * @param errorMessage
     * @return
     */
    private boolean validateContext(EmailNotificationContext emailNotificationContext, StringBuffer errorMessage) {
    	
    	boolean isValid = true;
    	if (emailNotificationContext.getMailServerHost() == null || emailNotificationContext.getMailServerHost().isEmpty()) {
    		errorMessage.append("\n Mail Server Host name not defined.");
    		isValid = false;
    	}
    		
    	if (emailNotificationContext.getSenderEmail() == null || emailNotificationContext.getSenderEmail().isEmpty()) {
    		errorMessage.append("\n Sender EMail not defined.");
    		isValid = false;
    	}
    	
    	if (emailNotificationContext.getSenderUserName() == null || emailNotificationContext.getSenderUserName().isEmpty()) {
    		errorMessage.append("\n Sender Username not defined.");
    		isValid = false;
    	}
    	
    	if (emailNotificationContext.getSenderPassword() == null || emailNotificationContext.getSenderPassword().isEmpty()) {
    		errorMessage.append("\n Sender password not defined.");
    		isValid = false;
    	}
    	
    	if (emailNotificationContext.getReceiverEmail() == null || emailNotificationContext.getReceiverEmail().isEmpty()) {
    		errorMessage.append("\n Receiver Email not defined.");
    		isValid = false;
    	}
    	return isValid;
    }

    /**
    * Disconnects a SMTP Transport connection
    */
   @Override
    public void closeConnection() throws Exception {
    	if (transport != null && transport.isConnected()) {
    		transport.close();
    	}
    }
    
}
