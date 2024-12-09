package com.example.demo.serviceImpl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfExtractionService {

    public String extractTextFromPdf(MultipartFile file) throws IOException {
        String extractedText;

        // Use Apache PDFBox to extract text from the PDF
        try (InputStream inputStream = file.getInputStream(); PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            extractedText = pdfStripper.getText(document);
        } catch (IOException e) {
            // Log the error and rethrow the exception to be handled by the controller
            throw new IOException("Failed to extract text from PDF", e);
        }

        return extractedText;
    }
}