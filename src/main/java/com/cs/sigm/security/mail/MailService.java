package com.cs.sigm.security.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.config.CmsConfig;

@Service
public class MailService {
	
	@Autowired
	private CmsConfig formsConfig;

	public void sendMail(MailMessage msg) throws MessagingException {
		final Message message = new MimeMessage(getSession());
		message.setFrom(new InternetAddress(msg.getAddressFrom()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(msg.getAddressTo()));
		message.setSubject(msg.getSubject());
		final MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg.getMessage(), "text/html");
		final Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
		message.setContent(multipart);
		Transport.send(message);
	}

	private Session getSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", formsConfig.isMailAuth());
		prop.put("mail.smtp.starttls.enable", formsConfig.isMailTLSEnabled());
		prop.put("mail.smtp.host", formsConfig.getMailHost());
		prop.put("mail.smtp.port", formsConfig.getMailPort());
		prop.put("mail.smtp.ssl.trust", formsConfig.getMailHost());
		return Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(formsConfig.getMailUsername(), formsConfig.getMailPassword());
			}
		});
	}
	
}
