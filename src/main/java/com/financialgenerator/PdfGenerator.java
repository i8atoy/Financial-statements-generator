package com.financialgenerator;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {

    public byte[] generate(String html) {

        try {
            var doc = Jsoup.parse(html);
            doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();

            SharedContext context = renderer.getSharedContext();
            context.setPrint(true);
            context.setInteractive(false);

            renderer.setDocumentFromString(doc.html());
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}