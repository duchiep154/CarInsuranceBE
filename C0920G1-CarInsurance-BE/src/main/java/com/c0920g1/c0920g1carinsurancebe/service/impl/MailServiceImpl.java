package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.service.MailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class  MailServiceImpl implements MailService {
    @Override
    public void sendMail(String mailTo, Multipart file) {
        System.out.println("Preparing to send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        //Khanh them vao dong duoi
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String mailSend = "c0920g1.2021@gmail.com";
        String password = "_5anhemsieunhan";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSend, password);
            }
        });

        Message message = prepareMessage(session, mailSend, mailTo, file);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Send mail oke");
    }

    private Message prepareMessage(Session session, String mailSend, String mailTo, Multipart file) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailSend));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject("Công ty Bảo hiểm C09");
            message.setContent(file);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
