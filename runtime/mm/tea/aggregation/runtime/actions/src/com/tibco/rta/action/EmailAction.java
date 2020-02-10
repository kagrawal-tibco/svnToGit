package com.tibco.rta.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportAdapter;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.taskdefs.IdempotentRetryTask;
import com.tibco.rta.common.taskdefs.Task;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.tea.agent.be.util.BEEntityDimensions;

public class EmailAction implements ActionHandlerContext {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_ACTIONS.getCategory());
	private Properties mailServerProps = new Properties();
	private String mailFrom;

	private Authenticator authenticator = null;	
	// Whether SMTP server is available or not.
	private static boolean isSmtpAvailable = false;
	
	private int emailRetryCount = 3;
	// retry interval in milliseconds.
	private long emailRetryInterval = 2000L;
	
	private String smtpHost;
	private String smtpPort;

	@Override
	public void init(Properties configuration) {
		smtpHost = (String) ConfigProperty.RTA_MAIL_SMTP_HOST.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Initializing EmailAction, configured SMTP host : [%s]", smtpHost);
		}
		mailServerProps.put("mail.host", smtpHost);
		mailServerProps.put("mail.smtp.host", smtpHost);
		smtpPort = (String) ConfigProperty.RTA_MAIL_SMTP_PORT.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "EmailAction configured SMTP port : [%s]", smtpPort);
		}
		mailServerProps.put("mail.smtp.port", smtpPort);
		String isAuth = (String) ConfigProperty.RTA_MAIL_SMTP_AUTHENTICATION.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "EmailAction SMTP Authentication is [%s]" , isAuth);
		}
		if ("true".equalsIgnoreCase(isAuth)) {			
			// Authentication is required.
			mailServerProps.put("mail.smtp.auth", "true");
			final String username = (String) ConfigProperty.RTA_MAIL_SMTP_USER.getValue(configuration);
			if (LOGGER.isEnabledFor(Level.INFO)) {
				LOGGER.log(Level.INFO, "EmailAction Configured SMTP user: [%s]" , username);
			}
			String password = (String) ConfigProperty.RTA_MAIL_SMTP_PASSWORD.getValue(configuration);
			
			try {
				boolean isObfuscated = com.tibco.security.ObfuscationEngine.hasEncryptionPrefix(password);
				if (isObfuscated) {
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "EmailAction SMTP password is obfuscated, decrypting it..");
					}
					password = new String(com.tibco.security.ObfuscationEngine.decrypt(password));
				} else {
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "EmailAction SMTP password is not obfuscated.");
					}
				}
			} catch (Throwable t) {
				LOGGER.log(Level.ERROR,"An error occurred, while decrypting SMTP password.", t);
			}
			final String fpassword = password;			
			authenticator = new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(username, fpassword);
			    }
			};
		}
		mailServerProps.put("mail.smtp.sendpartial", "true");
		mailFrom = (String) ConfigProperty.RTA_MAIL_FROM.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "EmailAction Configured FROM: [%s]" , mailFrom);
		}
		if (mailFrom != null && !mailFrom.isEmpty()) {
			mailFrom = mailFrom.trim();
			if (!EmailAddress.isValidEmailAddress(mailFrom)) {
				throw new RuntimeException("Not a valid FROM (Email sender) Email address: " + mailFrom);
			}
		}
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Email Server Props : %s, mailFrom: {%s}", mailServerProps, mailFrom);
		}
		
		String retryCountStr = (String) ConfigProperty.RTA_MAIL_RETRY_COUNT.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "EmailAction configured retry count: [%s]", retryCountStr);
		}
		if ("-1".equalsIgnoreCase(retryCountStr)) {
			// Special case when retry count is -1, go for infinite retry.
			emailRetryCount = Integer.MAX_VALUE;

		} else {
			try {
				emailRetryCount = Integer.parseInt(retryCountStr);
			} catch (NumberFormatException nfe) {

			}
		}
		String retryIntervalStr = (String) ConfigProperty.RTA_MAIL_RETRY_INTERVAL.getValue(configuration);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "EmailAction configured retry interval: [%s]", retryIntervalStr);
		}
		try {
			emailRetryInterval = Long.parseLong(retryIntervalStr);
		} catch (NumberFormatException nfe) {

		}
		
		
		if (smtpHost != null && !smtpHost.isEmpty()) {
//			String pingTimeStr = (String) ConfigProperty.RTA_MAIL_SMTP_PING_TIMEOUT.getValue(configuration);
//			if (LOGGER.isEnabledFor(Level.INFO)) {
//				LOGGER.log(Level.INFO, "Initializing EmailAction, configured SMTP server ping time : [%s] seconds", pingTimeStr);
//			}
//			String retryTimeStr = (String) ConfigProperty.RTA_MAIL_SMTP_RETRY_TIME.getValue(configuration);
//			if (LOGGER.isEnabledFor(Level.INFO)) {
//				LOGGER.log(Level.INFO, "Initializing EmailAction, configured SMTP server retry interval : [%s] seconds", retryTimeStr);
//			}
			// Start SMTP monitoring Thread.
//			SmtpMonitor monitor = new SmtpMonitor(Integer.parseInt(pingTimeStr), Integer.parseInt(retryTimeStr));
//			Thread monThread = new Thread(monitor, "SmtpMonitorThread");
//			if (LOGGER.isEnabledFor(Level.INFO)) {
//				LOGGER.log(Level.INFO, "Starting SMTP server monitoring...");
//			}
//			monThread.start();
			isSmtpAvailable = true;

		} else {
			LOGGER.log(Level.WARN, "SMTP server is not configured, hence Email Action will not work!");
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Action getAction(Rule rule, ActionDef actionDef) {
		return new EmailActionImpl(rule, actionDef);
	}

	class EmailActionImpl extends AbstractActionImpl {
		
//		String alertText, alertDtls;

		public EmailActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return EmailAction.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent node) throws Exception {	
			Map<String,Object> actionAttributes=threadLocalOfAction.get();
			actionAttributes.clear();//remove previous values set by this thread.
			
			if (LOGGER.isEnabledFor(Level.INFO)) {
				LOGGER.log(Level.INFO, "EmailAction triggered, RuleName: %s MetricNodeKey: %s", rule.getName(), node
						.getMetricNode().getKey());
			}
			
			//check SMTP server is available or not.
			if (!isSmtpAvailable) {
				throw new RuntimeException("Cannot send email, SMTP server is not configured!");
			}
			String mailTo = (String) getFunctionParamValue("To").getValue();
			String mailCc = (String) getFunctionParamValue("Cc").getValue();
			String mailBcc = (String) getFunctionParamValue("Bcc").getValue();
			String mailSubject = (String) getFunctionParamValue("Subject").getValue();
			String mailBody = (String) getFunctionParamValue("Body").getValue();
			
			if (mailTo == null || mailTo.isEmpty() || mailSubject == null || mailSubject.isEmpty()) {
				// To and Subject are must for email sending.
				throw new RuntimeException("Can't send email with null/empty To or Subject");
			}

			//mailSubject = updateSubject(mailSubject, rule.getName());
			mailSubject = ActionUtil.substituteTokens(mailSubject, rule, node.getMetricNode(), isSetAction(), actionDef, false);
			mailBody = ActionUtil.substituteTokens(mailBody, rule, node.getMetricNode(), isSetAction(), actionDef, true);
						
			String alertText=ActionUtil.substituteAlertTextTokens((String)getFunctionParamValue(MetricAttribute.ALERT_TEXT).getValue(),rule,node.getMetricNode(),isSetAction(),actionDef,"");
			actionAttributes.put(ActionUtil.ACTION_ALERT_TEXT, alertText);
			
			MetricKeyImpl key=(MetricKeyImpl)node.getMetricNode().getKey();
			String alertDtls = alertText; //by default, in case key is null below.
			if(key!=null){		
				String entity = key.getDimensionLevelName();
				String entityName = (String)(key.getDimensionValue(entity)==null?"":key.getDimensionValue(entity).toString());
				String appName = MonitoringUtils.getEntityName(key,BEEntityDimensions.app.name());
				alertDtls = String.format("{Entity name=%s},{Entity Type=%s},{Cluster Name=%s},{To=%s},{Cc=%s},{Bcc=%s},{Subject=%s}", entityName, entity, appName,mailTo, mailCc, mailBcc, mailSubject);
			}
			actionAttributes.put(ActionUtil.ACTION_ALERT_DTLS, alertDtls);

			
			
			try {
//				sendMail(mailTo, mailCc, mailBcc, mailSubject, mailBody);
				IdempotentRetryTask emailRetryTask = new IdempotentRetryTask(emailRetryCount, emailRetryInterval, new EmailTask(mailTo, mailCc, mailBcc, mailSubject, mailBody));
				emailRetryTask.perform();				
								
				// log Alert.
				LogAction.logAlert(isSetAction(), rule, node,alertText);

			} catch (MessagingException me) {
				LOGGER.log(Level.ERROR, "An error occurred while sending email. Cause=" + me.getMessage());
				throw me;
			} catch (Throwable e) {
				LOGGER.log(Level.ERROR, "An error occurred while sending email. ErrorMessage=", e.getMessage());
				throw new RuntimeException("", e);
			}
		}

		@Override
		public String getAlertText() {
			String alertText = (String) threadLocalOfAction.get().get(ActionUtil.ACTION_ALERT_TEXT);
			return alertText;
		}

		@Override
		public String getAlertDetails() {
			String alertDtls = (String) threadLocalOfAction.get().get(ActionUtil.ACTION_ALERT_DTLS);
			return alertDtls;
		}

        @Override
        public String getAlertType() {
            return "Email-Action";
        }
    }


	private String updateSubject(String subject, String ruleName) {
		if (subject != null) {
			return subject.replace(ActionUtil.getExpression(ActionUtil.RULE_NAME_TOKEN), ruleName);
		}
		return "";
	}



	private void sendMail(String to, String cc, String bcc, String subject, String body) throws MessagingException,
			IOException {

		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "EmailAction sending Mail To = %s CC = %s BCC = %s Subject = %s ", to, cc, bcc,
					subject);
		}
		
		Session mailSession = Session.getInstance(mailServerProps, authenticator);
		Message message = new MimeMessage(mailSession);
		message.setHeader("Content-Type", "text/html");

		// Message FROM
		if (mailFrom == null || mailFrom.isEmpty()) {
			message.setFrom(InternetAddress.getLocalAddress(mailSession));
		} else {
			message.setFrom(new InternetAddress(mailFrom));
		}

		// Message TO
		if (to != null && !to.isEmpty()) {
			InternetAddress[] toArray = getInternetAddressArray(to);
			if (toArray.length == 0) {
				throw new RuntimeException("Cannot send mail, No Valid 'To' address.");
			}
			message.setRecipients(Message.RecipientType.TO, toArray);
		}
		// Message CC
		if (cc != null && !cc.isEmpty()) {
			InternetAddress[] ccArray = getInternetAddressArray(cc);
			message.setRecipients(Message.RecipientType.CC, ccArray);
		}
		// Message BCC
		if (bcc != null && !bcc.isEmpty()) {
			InternetAddress[] bccArray = getInternetAddressArray(bcc);
			message.setRecipients(Message.RecipientType.BCC, bccArray);
		}

		// Message SUBJECT
		if (subject != null && !subject.isEmpty()) {
			// encode the subject using UTF-8
			try {
				subject = MimeUtility.encodeText(subject, "UTF-8", "Q");
			} catch (UnsupportedEncodingException e) {
				// no encoding if not supported.
			}

			// set subject
			message.setSubject(subject);
			message.setHeader("Content-Transfer-Encoding", "UTF-8");

		}

		// Message CONTENT or BODY
		if (body == null || body.isEmpty()) {
			body = "";
		}
		
		message.setContent(body, "text/html");
		message.saveChanges();
		
		// Add Transport Listener to Report the mail status.
		TransportListener tListner = new TransportAdapter() {
			public void messageNotDelivered(TransportEvent e) {
				try {
					LOGGER.log(Level.WARN, "EmailAction Message NOT Delivered, Message Subject = %s ", e.getMessage()
							.getSubject());
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
			}

			public void messagePartiallyDelivered(TransportEvent e) {

				try {
					LOGGER.log(Level.WARN, "EmailAction Message Partially Delivered, Message Subject = %s ", e
							.getMessage().getSubject());
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
			}
		};

		Transport mailTransport = mailSession.getTransport("smtp");
		mailTransport.addTransportListener(tListner);
		Transport.send(message);
	}

	private InternetAddress[] getInternetAddressArray(String emailAddresses) throws AddressException {
		String tokenizer = ";";
		if (!emailAddresses.contains(tokenizer)) {
			tokenizer = ",";
		}
		List<InternetAddress> list = new ArrayList<InternetAddress>();
		StringTokenizer st = new StringTokenizer(emailAddresses, tokenizer);
		while (st.hasMoreTokens()) {
			String eAddress = st.nextToken().trim();
			if (EmailAddress.isValidEmailAddress(eAddress)) {
				list.add(new InternetAddress(eAddress));
			} else {
				LOGGER.log(Level.ERROR, "Invalid Email address [%s], ignored while sending email.", eAddress);
			}
		}
		InternetAddress[] address = new InternetAddress[list.size()];
		return list.toArray(address);
	}
	
	class SmtpMonitor implements Runnable {
		private int pingTime;
		private int retryTime;

		SmtpMonitor(int pingTime, int retryTime) {
			this.pingTime = pingTime * 1000;
			this.retryTime = retryTime * 1000;
		}

		public void run() {
			if (pingTime <= 0 || retryTime <= 0) {
				// If configured time is zero or negative, No need to check
				// whether SMTP server is available or not
				isSmtpAvailable = true;
				if (LOGGER.isEnabledFor(Level.INFO)) {
					LOGGER.log(Level.INFO, "SMTP monitoring thread is Disabled.");
				}
				return;
			}
			boolean wasPreviouslyDown = false;
			while (true) {
				try {
					
					InetAddress host = InetAddress.getByName(smtpHost);
					isSmtpAvailable = host.isReachable(pingTime);

					if (!isSmtpAvailable) {
						wasPreviouslyDown = true;
						LOGGER.log(Level.WARN, "SMTP server is NOT Available, re-trying...");
					}
					// Log available if it was retrying.
					if (LOGGER.isEnabledFor(Level.INFO) && wasPreviouslyDown && isSmtpAvailable) {
						LOGGER.log(Level.INFO, "SMTP server is now Available.");
						wasPreviouslyDown = false;
					}
				} catch (Exception e) {
					isSmtpAvailable = false;
					LOGGER.log(Level.ERROR, "An error occurred, while reaching SMTP Server : %s", e, smtpHost);
				}
				// Wait for few seconds (configured).
				try {
					Thread.sleep(retryTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class EmailTask implements Task {
		
		private String to;
		private String cc;
		private String bcc;
		private String subject;
		private String body;
		
		EmailTask(String to, String cc, String bcc, String subject, String body) {
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.subject = subject;
			this.body = body;

		}

		@Override
		public Object perform() throws Throwable {
			sendMail(to, cc, bcc, subject, body);
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "EmailAction, mail sent successfully!");
			}
			return null;
		}

		@Override
		public String getTaskName() {
			return "EmailActionTask";
		}
		
	}

	public static void main(String args[]) throws Throwable {
		new EmailAction().testMail();
	}

	private void testMail() throws Throwable {
		smtpHost="<>";
		mailServerProps.put("mail.host", smtpHost);
		mailFrom = "<>";
		String to = "<>";
		String subject = "SPM Alert for Rule: DemoRule1";
		String content = "<html><body> Hello,<br/><br/>This alert message has been generated for rule: DemoRule1 <br/> <br/> regards <br/> SPM Administrator</body></html>";
//		Thread monThread = new Thread(new SmtpMonitor(), "SmtpMonitorThread");
//		monThread.start();
//		sendMail(to, null, null, subject, content);
		IdempotentRetryTask emailRetryTask = new IdempotentRetryTask(3, 3000L, new EmailTask(to, null, null, subject, content));
		emailRetryTask.perform();
	}	
	
}
