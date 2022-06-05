package com.proiect.SCD.service;

import com.proiect.SCD.utils.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void send(String link, String emailUser) {
        String email = "paulbalas28361@gmail.com";
        String password = "Nephirius1";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email, password);
            }
        });
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
           mimeMessage.setFrom(new InternetAddress(email));
           mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailUser));
           mimeMessage.setText("Verification Link....");
           mimeMessage.setText("Click Here :: " + link);
            Transport.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("fail to send email");
        }
    }
}
