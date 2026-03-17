package com.financialgenerator.service;

import com.financialgenerator.model.InvoiceModel;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final TemplateRenderer templateRenderer;
    private final PdfGenerator pdfGenerator;

    public InvoiceService(TemplateRenderer templateRenderer,
                          PdfGenerator pdfGenerator) {
        this.templateRenderer = templateRenderer;
        this.pdfGenerator = pdfGenerator;
    }

    public byte[] generateInvoice(InvoiceModel model) {

        String html = templateRenderer.renderInvoice(model);

        return pdfGenerator.generate(html);
    }
}