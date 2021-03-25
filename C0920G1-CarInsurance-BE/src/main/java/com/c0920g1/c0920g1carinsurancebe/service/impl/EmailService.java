package com.c0920g1.c0920g1carinsurancebe.service.impl;


import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService{

    // goi mail cua trang

    public void setMailTo(String mailTo, String otp){
        MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
        Multipart multipart = null;
        try {
            mimeBodyPart1.setContent(otp,"text/html");
            multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart1);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sendMail(mailTo, multipart);
    }
    public void sendMail(String mailTo, Multipart file) {
        System.out.println("Preparing to send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
    private static Message prepareMessage(Session session, String mailSend, String mailTo, Multipart file) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailSend));
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        try {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        try {
            message.setSubject("C0920G1 - Mã OTP của quý khách là: ");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        try {
            message.setContent(file);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }


}