package com.financialgenerator.controller;


import com.financialgenerator.service.MailReceiverInterface;
import com.financialgenerator.model.Email;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetEmailController {
    MailReceiverInterface mailReceiver;

    public GetEmailController(MailReceiverInterface mailReceiver) {
        this.mailReceiver = mailReceiver;
    }

    @GetMapping("/email")
    public Email getEmail() {

        return mailReceiver.getLatestEmailFrom("josh@email.com", "ejaidjwa");
    }
}
