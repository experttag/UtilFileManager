
package com.experttag.util.communication.mail;

import com.experttag.util.constant.MailConstant;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

/**
 * This class will hold methods for mail sending
 * @author NANDUS
 */
public class DirectMail {

   private SMTPAuthenticator auth;
   private Session session ;
   private  Message msg;

   public DirectMail(){

    boolean debug = false;

     //Set the host smtp address
     Properties props = new Properties();
     props.put("mail.smtp.host", MailConstant.SMTP_HOST_NAME);
     props.put("mail.smtp.auth", "true");
     props.setProperty("mail.smtp.port", MailConstant.SMTP_HOST_PORT);
     props.setProperty("mail.smtp.starttls.enable", "true");

     //create SMTP authenticater
     auth= new SMTPAuthenticator();

     //create default session with mail server
     session = Session.getDefaultInstance(props, auth);
     session.setDebug(debug);


   }

   public void postMail( String recipient, String subject, String message) throws MessagingException{

    msg = new javax.mail.internet.MimeMessage(session);

    // set the from and to address    
    msg.setFrom(new InternetAddress(MailConstant.SMTP_HOST_NAME));
    msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(MailConstant.PERMANENT_BCC_USER));
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/plain");

     //send the message
     Transport.send(msg);

   }


   /**
    * This method sends mails in bulk
    * @param recipient
    * @param subject
    * @param message
    * @throws javax.mail.MessagingException
    */
   public void postMails( String recipient[], String subject, String message[]) throws MessagingException{

    for(int i=0;i<message.length;i++){
        
            msg = new javax.mail.internet.MimeMessage(session);

            // set the from and to address
            msg.setFrom(new InternetAddress(MailConstant.SMTP_HOST_NAME));
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(MailConstant.PERMANENT_BCC_USER));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient[i]));

            // Setting the Subject and Content Type
            msg.setSubject(subject);
            msg.setContent(message[i], "text/plain");

             //send the message
             Transport.send(msg);
             
     }
   }


/**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator {
        
        @Override
    public PasswordAuthentication getPasswordAuthentication() {
        
        return new PasswordAuthentication(MailConstant.SMTP_AUTH_USER, MailConstant.SMTP_AUTH_PWD);
    }
}


}
