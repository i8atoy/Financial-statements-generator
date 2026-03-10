package com.financialgenerator;

public record Attachment(
        String filename,
        String mimeType,
        byte[] content
) {}
