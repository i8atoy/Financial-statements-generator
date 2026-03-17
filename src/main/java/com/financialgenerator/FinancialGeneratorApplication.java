package com.financialgenerator;

import com.financialgenerator.exception.MailReceiverException;
import com.financialgenerator.service.MailReceiver;
import com.financialgenerator.service.MailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;

import java.io.IOException;

@SpringBootApplication
public class FinancialGeneratorApplication {
    @Autowired
    private MailReceiver mailReceiver;

    @Autowired
    private MailSenderService mailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(FinancialGeneratorApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void executeStartupTasks() {
        try {
            mailSenderService.sendEmail("p.elvinpop@proton.me", "pdf", "here is the pdf");
//            mailReceiver.readEmails();

        } catch (MailReceiverException | MessagingException e) {
            System.err.println("An error occurred while checking emails: " + e.getMessage());
            e.printStackTrace();
        }
    }
}