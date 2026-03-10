package com.financialgenerator;
import java.util.List;

public record Email(
        String from,
        String to,
        String subject,
        String body,
        List<Attachment> attachments
) {}
