package com.financialgenerator;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MailReceiverFake implements MailReceiverInterface {



    @Override
    public Email getLatestEmailFrom(String sender, String subject) {

        Attachment attachment = new Attachment("1.pdf", "application/pdf", new byte[]{});
        return new Email("josh@email.com", "elvin@email.com", "bankstmt", "body....", List.of(attachment));
    }
}
