package com.smart.service;

import java.util.Properties;

import org.springframework.stereotype.Service;



import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	public boolean sendEmail(String subject,String message,String to) {
		
		String from = "subhadippati30@gmail.com";
		String host = "smtp.gmail.com";
		boolean f = false;
		// Get the system properties
		Properties properties = new Properties();
		System.out.print("Properties "+properties);
		
		//Host Set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		// Get the session object 
		
	Session session = Session.getInstance(properties,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("subhadippati30@gmail.com", "ksgv xqkw brfp dbuz");
			}
			
			
			
			
		});
		// Step 2 : Compose the message
	
	session.setDebug(true);
	
	 MimeMessage m = new MimeMessage(session);
		
	 try {
		 
		 m.setFrom(from);
		 
		 m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		 
		 m.setSubject(subject);
		 
		// m.setText(message);
		 
		 m.setContent(message,"text/html");
		 
		 
		 
		 //send the message using transport class
		 Transport.send(m);
		 System.out.println("Send Successfully.......");
		 f = true;
	 }catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
	}
	 return f;
	
		
	}
}
