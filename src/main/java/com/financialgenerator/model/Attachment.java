package com.financialgenerator.model;

public record Attachment(
        String filename,
        String mimeType,
        byte[] content
) {}
