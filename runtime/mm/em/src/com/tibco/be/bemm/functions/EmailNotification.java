/**
 * 
 */
package com.tibco.be.bemm.functions;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tibco.be.util.config.cdd.MmSendEmailConfig;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author Nick Xu
 *
 */
public class EmailNotification {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	private Logger logger;
	private String prot;
	private String auth;
	private String host;
	private String port;
	private String user;
	private String pwd;
	private String from;
	private static EmailNotification instance;
	
	public EmailNotification(){
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass());
        this.prot = currRuleServiceProvider.getProperties().getProperty("be.mm.email.protocol");
        this.auth = currRuleServiceProvider.getProperties().getProperty("be.mm.email.authentication");
        this.host = currRuleServiceProvider.getProperties().getProperty("be.mm.email.host");
        this.port = currRuleServiceProvider.getProperties().getProperty("be.mm.email.host.port");
        this.from = currRuleServiceProvider.getProperties().getProperty("be.mm.email.from");
        this.user = currRuleServiceProvider.getProperties().getProperty("be.mm.email.username");
        this.pwd = currRuleServiceProvider.getProperties().getProperty("be.mm.email.password");
	}
	
    public static synchronized EmailNotification getInstance(){
        if (instance == null){
            instance = new EmailNotification();
        }
        return instance;
    }	
    
	public void sendEmail(MmSendEmailConfig mmSendEmailConfig, String m) throws MessagingException{
		String subject = mmSendEmailConfig.getSubject();
		String to = mmSendEmailConfig.getTo();
		String cc = mmSendEmailConfig.getCc();
		String message = mmSendEmailConfig.getMsg() +"\n"+ m;
		boolean sessionDebug = true;
		Properties props = System.getProperties();
		props.put("mail."+prot+".host", host);
		if(port != null)
			props.put("mail.smtp.port", port);
		if(auth != null)
			props.put("mail." + prot + ".auth", auth);
		else
			props.put("mail." + prot + ".auth", "true");
		Session session = Session.getInstance(props, null);
	
		session.setDebug(sessionDebug);
		Transport t = null;
		try {
			// Instantiate a new MimeMessage and fill it with the
			// required information.
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
		    msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));
		    if (cc != null)
		    	msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(cc, false));
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(message);
			t = session.getTransport(prot);
	        t.connect(host, user, pwd);
	        t.sendMessage(msg, msg.getAllRecipients());
		}
		finally{
			if(t!=null) t.close();
		}		
	}

	public static void main(String[] args) throws MessagingException {
		String prot = "smtp";
		String host = "smtp.tibco.com";
		String port = "25";
		String to = "yaxu@tibco.com";
		String from = "yaxu@tibco.com";
		String subject = "My First Email";
		String messageText = "this mail is sent by java email";
		boolean sessionDebug = true;
		Properties props = System.getProperties();
		props.put("mail."+prot+".host", host);
		if(port != null) 
			props.put("mail.smtp.port", port);
		props.put("mail." + prot + ".auth", "false");
		Session session = Session.getInstance(props, null);
	
		session.setDebug(sessionDebug);
		Transport t = null;
		try {
			// Instantiate a new MimeMessage and fill it with the
			// required information.
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);
			t = session.getTransport(prot);
	        t.connect();
	        t.sendMessage(msg, msg.getAllRecipients());
		}
		catch (MessagingException mex) {
			mex.printStackTrace();
		}
		finally{
			if(t!=null) t.close();
		}
	}	
}
