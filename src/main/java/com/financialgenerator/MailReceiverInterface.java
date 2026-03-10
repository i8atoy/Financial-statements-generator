package com.financialgenerator;



public interface MailReceiverInterface{
    public Email getLatestEmailFrom(String sender, String subject) throws EmailNotFoundException, MailReceiverException;
}
