package com.financialgenerator.config;

import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration // Tells Spring to manage this class
public class MailConfig {

    // Injecting values from application.properties
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${app.mail.imap.host}")
    private String imapHost;

    @Value("${app.mail.imap.port}")
    private String imapPort;

    // Notice 'static' is removed!
    public Store establishConnection() throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", imapHost);
        props.setProperty("mail.imaps.port", imapPort);
        props.setProperty("mail.imaps.ssl.enable", "true");
        props.setProperty("mail.imaps.connectiontimeout", "5000");
        props.setProperty("mail.imaps.timeout", "5000");

        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imaps");

        store.connect(imapHost, username, password);
        return store;
    }
}