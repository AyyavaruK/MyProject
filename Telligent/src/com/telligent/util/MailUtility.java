package com.telligent.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.telligent.model.db.Generic;

/**
 * 
 * @author spothu
 */
public class MailUtility {

	public static final Logger logger = Logger.getLogger(MailUtility.class);
	static Map prop=new Generic().getPropertyNameValue("telligent.properties");
	
	public static void sendMail(String toAddresses, String ccAddresses, String bccAddresses, String fromAddress, String subject, String body, String type){
		final String username = prop.get("smtpUsername").toString();
		final String password = prop.get("smtpPassword").toString();
		fromAddress = prop.get("fromAddress").toString();
		Properties props = null;
		Session session = null;
		Message message = null;
		try{		
			props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			/* 
			Port for TLS/STARTTLS: 587
			Port for SSL: 465
			Uncomment below lines to use SSL
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");			
			props.put("mail.smtp.port", "465");
			*/
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
			
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(toAddresses));
			message.setRecipients(Message.RecipientType.CC,	InternetAddress.parse(ccAddresses));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccAddresses));
			message.setSubject(subject);
			
			if(null!= type && type.equals("html"))
				message.setContent(body, "text/html");
			else
				message.setContent(body, "text/html");
			
			Transport.send(message); 			
		}catch (MessagingException mex) {
			mex.printStackTrace();
			logger.error("Exception in sendMail " + mex);
			while (mex.getNextException() != null) {
				Exception ex = mex.getNextException();
				ex.printStackTrace();
				if (!(ex instanceof MessagingException)) break;
				else mex = (MessagingException)ex;
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception in sendMail " + e);
		}finally{
			props = null;session=null;message=null;
		}
	}	
}
