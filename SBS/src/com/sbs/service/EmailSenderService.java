package com.sbs.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	private JavaMailSender emailSender;
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	/*public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}*/

	public void sendEmailWithAttachment(String from, String to, String subject, String body,FileSystemResource attachment){
		
		MimeMessage message = emailSender.createMimeMessage();
		
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			helper.addAttachment(attachment.getFilename(), attachment);	
			/*FileSystemResource file = new FileSystemResource("C:\\Users\\Sanchit Bapat\\Documents\\All In\\University\\Fall 15\\SS\\Project\\STSWorkspace\\PKITest\\abc.pfx");*/
			
		}
		catch (MessagingException e) {
			e.printStackTrace();
			throw new MailParseException(e);
		}
		emailSender.send(message);
		System.out.println("\n\n\nMessage Sent\n\n\n");
	}
	
	public void sendSimpleEmail(String to, String subject, String body){
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("group13ss2015@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		System.out.println("\n\n\nMessage Sent\n\n\n");
	}
}
