package com.financialgenerator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class FinancialGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialGeneratorApplication.class, args);
    }

    // This tells Spring to run this code immediately after starting up
    @Bean
    public CommandLineRunner generatePdfOnStartup(InvoiceService invoiceService) {
        return args -> {
            InvoiceModel model = new InvoiceModel("10-02-2027", 1, new BigDecimal("1.0"),
                    new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("1.0"),
                    new BigDecimal("1.0"), new BigDecimal("5.0"), "10-02-2027");

            byte[] pdf = invoiceService.generateInvoice(model);

            Files.write(Path.of("test_invoice.pdf"), pdf);

            System.out.println("================================================");
            System.out.println("PDF GENERATED SUCCESSFULLY! Check your project root!");
            System.out.println("================================================");

            // Shuts the app down after we get our file
            System.exit(0);
        };
    }
}