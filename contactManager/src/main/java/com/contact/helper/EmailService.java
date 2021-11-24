package com.contact.helper;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	public boolean sendEmail(String subject,String message,String to) {
		boolean sent=true;
		 String from="swapnilj07.sj@gmail.com";
		
		//variable for gmail.com
			String host="smtp.gmail.com";
			
			//get system properties
			Properties properties = System.getProperties();
			System.out.println(properties);
			
			//setting important information to properties object
			
			//host set
			//all key name should be same as mentioned
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.auth", "true");

			
			//step1:to get the session object...
			Session session=Session.getInstance(properties,new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication("swapnilj07.sj@gmail.com","Hemangee@001");
				}		
			});
			
			session.setDebug(true);
			
			//step2:compose the message(text,multimedia)
			MimeMessage mimeMessage = new MimeMessage(session);
			

			try {
				//set "from" of email
				mimeMessage.setFrom(from);
				
				//adding receipient to message
				//use array of address to send same mail to multiple recipients
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				
				//adding text to message
				//mimeMessage.setText(message);
				//to use html tag with text
				mimeMessage.setContent(message,"text/html");
				
				//set subject
				mimeMessage.setSubject(subject);
			
				//Step 3: send the Message using transport class
				Transport.send(mimeMessage);
				
				
				System.out.println("Mail Sent...................");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			return sent;
		}

	}
	

