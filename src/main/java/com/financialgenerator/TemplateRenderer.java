package com.financialgenerator;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class TemplateRenderer {

    private final TemplateEngine templateEngine;

    public TemplateRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderInvoice(InvoiceModel model) {

        Context context = new Context();

        context.setVariable("invoiceDate", model.invoiceDate());
        context.setVariable("quantity", model.quantity());
        context.setVariable("unitPrice", model.unitPrice());
        context.setVariable("valueUsd", model.valueUsd());
        context.setVariable("valueRon", model.valueRon());
        context.setVariable("totalUsd", model.totalUsd());
        context.setVariable("totalRon", model.totalRon());
        context.setVariable("exchangeRate", model.exchangeRate());
        context.setVariable("exchangeDate", model.exchangeDate());

        return templateEngine.process("InvoiceTemplate", context);
    }
}