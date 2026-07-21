package com.nhom4.hotel.email;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailService {

    private static final String EMAIL = "phuhmts02625@gmail.com";
    private static final String PASSWORD = "ofno rboi aorc jfps";

    private static Session getSession() {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(
                        EMAIL,
                        PASSWORD);

            }

        });

    }

    public static void send(String to,
                            String subject,
                            String html) {

        try {

            MimeMessage message =
                    new MimeMessage(getSession());

            message.setFrom(new InternetAddress(EMAIL));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);

            message.setContent(
                    html,
                    "text/html;charset=UTF-8");

            Transport.send(message);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}