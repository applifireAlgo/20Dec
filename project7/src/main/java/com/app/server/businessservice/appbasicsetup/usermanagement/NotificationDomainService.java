package com.app.server.businessservice.appbasicsetup.usermanagement;
import com.app.bean.EmailBean;

import com.app.bean.EmailTemplate;

import com.app.shared.appbasicsetup.usermanagement.ArtAppNotificationTemplate;

import com.app.server.repository.appbasicsetup.usermanagement.ArtAppNotificationTemplateRepository;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.athena.config.appsetUp.interfaces.AppConfigurationInterface;


/**
 * CLASS IS USED TO SEND EMAIL AND THIS DOMAIN SERVICE IS EXPOSED IN DOMAIN-SERVICE CREATION MODULE IN RAD, USER CAN CREATE THE BEAN HAVING ALL INPUTS
 * EG. EMAIL-SUBJECT, EMAIL-BODY AND RECIPIENTS AND PASS TO SEND-EMAIL METHOD TO AUTOMATE THE PROCESS OF EMAIL SENDING
 */
@Component
public class NotificationDomainService {
	
	@Autowired
	private AppConfigurationInterface appConfig;
	
	@Autowired
	private ArtAppNotificationTemplateRepository artAppEmailTemplateRepository;

	/** METHOD USED TO INITIATE THE SESSION TO SEND EMAILS */
	private Session getSession(Properties properties) {
		javax.mail.Authenticator authenticator = new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(appConfig.getMailConfig().getUsername(), appConfig.getMailConfig().getPassword());
			}
		};
		return Session.getInstance(properties, authenticator);
	}

	/** METHOD USED TO PREPARE EMAIL CONFIGURATION PRPERTIES */
	private Properties getProperties(String emailSubject, String emailBody) {
		Properties properties = new Properties();
		properties.put("mail.smtp.user", appConfig.getMailConfig().getUsername());
		properties.put("mail.smtp.host", appConfig.getMailConfig().getHost());
		properties.put("mail.smtp.port", appConfig.getMailConfig().getSmtpPort());
		properties.put("mail.smtp.auth", appConfig.getMailConfig().isSmtpAuth());
		properties.put("mail.smtp.starttls.enable", appConfig.getMailConfig().isSmtpTls());
		// properties.put("mail.debug", "true");
		if (appConfig.getMailConfig().isSmtpSsl()) {
			properties.put("mail.smtp.ssl.enable", appConfig.getMailConfig().isSmtpSsl());
			properties.setProperty("mail.smtp.port", String.valueOf(appConfig.getMailConfig().getSmtpsPort()));
		}
		properties.put("EMAIL_SUBJECT", emailSubject);
		properties.put("EMAIL_BODY", emailBody);
		return properties;
	}

	/** METHOD USED TO SEND EMAIL - INPUTS EMAIL-SUBJECT, EMAIL-BODY AND RECIPIENT, WILL ACCEPT ONLY ONE RECIPIENT AT A TIME */
	public void sendMail(EmailBean emailBean) throws Exception {
		if (emailBean.sizeOfRecipients() == 0) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}

		Properties properties = getProperties(emailBean.getEmailSubject(), emailBean.getEmailBody());
		boolean emailStatus = sendEmail(properties, emailBean.getRecipients());
		if (emailStatus == false) {
			throw new Exception("Due to some cause email sending is failed, Please check network connectivity while sending emails");
		}
	}

	/** SEND-EMAIL OVERLOADED METHOD USED TO PREPARE AND TRANSPORT MESSAGE TO MULTIPLE RECIPIENT */
	private boolean sendEmail(Properties properties, List<String> receipents) {
		try {
			Message message = new MimeMessage(getSession(properties));
			message.setFrom(new InternetAddress(appConfig.getMailConfig().getUsername()));

			StringBuffer receipentslist = new StringBuffer();
			receipentslist.append(receipents.get(0));
			for (int i = 1; i < receipents.size(); i++) {
				receipentslist.append("," + receipents.get(i));
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipentslist.toString()));
			message.setSubject(properties.getProperty("EMAIL_SUBJECT"));
			message.setContent(properties.getProperty("EMAIL_BODY"), "text/html");

			Transport.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/** METHOD USED TO SEND EMAIL - INPUTS EMAIL-SUBJECT, EMAIL-BODY AND RECIPIENT, WILL ACCEPT ONLY ONE RECIPIENT AT A TIME */
	public void sendMail(EmailBean emailBean, EmailTemplate emailTemplate) throws Exception {
		if (emailBean.sizeOfRecipients() == 0) {
			throw new com.spartan.pluggable.exception.security.InvalidDataException();
		}
		
		/** PROCESSING THE EMAIL BODY AND REPLACE THE TEMPLATE KEYS BY ITS ACTUAL VALUES USING EMAIL-TEMPLATE BEAN */
		String emailBody = prepareEmailBodyByTemplate(emailTemplate);

		Properties properties = getProperties(emailBean.getEmailSubject(), emailBody);
		boolean emailStatus = sendEmail(properties, emailBean.getRecipients());
		if (emailStatus == false) {
			throw new Exception("Due to some cause email sending is failed, Please check network connectivity while sending emails");
		}
	}
	
	public String prepareEmailBodyByTemplate(EmailTemplate emailTemplate) throws  Exception {
		ArtAppNotificationTemplate artAppEmailTemplate = artAppEmailTemplateRepository.findById(emailTemplate.getTemplateId());
		String emailBody = artAppEmailTemplate.getTemplate();
		
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("object", emailTemplate.getReferences());
		
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(velocityContext, writer, "", emailBody);
		return writer.toString();
	}
}
