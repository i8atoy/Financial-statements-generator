package com.financialgenerator.service;


import com.financialgenerator.exception.MailReceiverException;
import com.financialgenerator.exception.EmailNotFoundException;
import com.financialgenerator.model.Email;

public interface MailReceiverInterface{
    public Email getLatestEmailFrom(String sender, String subject) throws EmailNotFoundException, MailReceiverException;
}
