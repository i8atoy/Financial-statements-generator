package com.financialgenerator.service;

import com.financialgenerator.config.MailConfig;
import com.financialgenerator.exception.EmailNotFoundException;
import com.financialgenerator.exception.MailReceiverException;
import com.financialgenerator.model.Email;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class MailReceiver implements MailReceiverInterface {

    @Autowired
    private MailConfig mailConfig;

    @Override
    public Email getLatestEmailFrom(String sender, String subject) throws EmailNotFoundException, MailReceiverException {
        return null;
    }

    public void printEmailCounts() throws MailReceiverException {
        Store store = null;
        try {
            store = mailConfig.establishConnection();

            Folder inbox = store.getFolder("inbox");
            Folder spam = store.getFolder("[Gmail]/Spam");

            inbox.open(Folder.READ_ONLY);
            spam.open(Folder.READ_ONLY); // Don't forget to open the spam folder before reading counts!

            System.out.println("No of Messages : " + inbox.getMessageCount());
            System.out.println("No of Unread Messages : " + inbox.getUnreadMessageCount());
            System.out.println("No of Messages in spam : " + spam.getMessageCount());
            System.out.println("No of Unread Messages in spam : " + spam.getUnreadMessageCount());

            inbox.close(false);
            spam.close(false);

        } catch (MessagingException e) {
            throw new MailReceiverException("Failed to fetch email counts");
        } finally {
            try {
                if (store != null && store.isConnected()) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void readEmails() throws MessagingException, IOException {
        Store store = null;
        try {
            store = mailConfig.establishConnection();


            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            if (messages.length > 0) {
                Message message = messages[0];
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + Arrays.toString(message.getFrom()));
                System.out.println("Text: " + message.getContent());
            }
            inbox.close(true);
        }catch (MessagingException e) {
            throw new MailReceiverException("Failed to fetch email counts");
        } finally {
            try {
                if (store != null && store.isConnected()) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}