package com.financialgenerator.model;

import java.math.BigDecimal;

public record InvoiceModel(
        String invoiceDate,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal valueUsd,
        BigDecimal valueRon,
        BigDecimal totalUsd,
        BigDecimal totalRon,
        BigDecimal exchangeRate,
        String exchangeDate
) {}