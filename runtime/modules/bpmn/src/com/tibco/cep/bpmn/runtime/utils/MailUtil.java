package com.tibco.cep.bpmn.runtime.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {

	private String protocol;
	private String host;
	private String userid;
	private String password;

	public MailUtil(String protocol, String host, String userid, String password) {
		this.protocol = protocol;
		this.host = host;
		this.userid = userid;
		this.password = password;

	}

	public MailUtil(String host, String userid, String password) {
		this("smtp", host, userid, password);
	}

	public void send(String sourceEmail,String destinationEmail,String subject) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol);
		props.setProperty("mail.host", this.host);
		props.setProperty("mail.user", this.userid);
		props.setProperty("mail.password", this.password);

		Session mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject(subject);
		message.setFrom(new InternetAddress(sourceEmail));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinationEmail));

		//
		// This HTML mail have to 2 part, the BODY and the embedded image
		//
		MimeMultipart multipart = new MimeMultipart("related");

		// first part (the html)
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
		messageBodyPart.setContent(htmlText, "text/html");

		// add it
		multipart.addBodyPart(messageBodyPart);

		// second part (the image)
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource("C:\\images\\jht.gif");
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<image>");

		// add it
		multipart.addBodyPart(messageBodyPart);

		// put everything together
		message.setContent(multipart);

		transport.connect();
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

}
