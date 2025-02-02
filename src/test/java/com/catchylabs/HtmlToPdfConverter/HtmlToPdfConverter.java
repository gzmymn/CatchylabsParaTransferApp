package com.catchylabs.HtmlToPdfConverter;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*;

public class HtmlToPdfConverter {
    public static void main(String[] args) {
        String htmlPath = "reports/extent-report.html"; // HTML raporun yolu
        String pdfPath = "reports/extent-report.pdf"; // PDF kaydedilecek yol
        HtmlToPdfConverter.convertHtmlToPdf(htmlPath, pdfPath);
        }

    public static void convertHtmlToPdf(String htmlFilePath, String pdfFilePath) {
        try { // HTML dosyasını oku
            StringBuilder htmlContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(htmlFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line); } reader.close();
            //PDF oluştur
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open(); ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent.toString());
            renderer.layout();
            renderer.createPDF(new FileOutputStream(pdfFilePath));
            document.close(); writer.close();
            System.out.println("✅ PDF Raporu oluşturuldu: " + pdfFilePath); }
        catch (Exception e)
        { e.printStackTrace();
        }
    }
}
