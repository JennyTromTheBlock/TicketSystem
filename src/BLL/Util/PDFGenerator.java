package BLL.Util;

import java.io.FileOutputStream;

import BE.Ticket;
import GUI.util.ResourcePaths;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFGenerator {
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    private Image generateQRCode(Ticket ticket, int width, int height) throws Exception {
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(Integer.toString(ticket.getId()), width, height, null);

        try {
            return barcodeQRCode.getImage();
        }
        catch (BadElementException e) {
            e.printStackTrace();
            throw new Exception("Failed to generate QR-Code", e);
        }
    }

    private Image generateBarCode(Ticket ticket, PdfContentByte contentByte) {
        Barcode39 barcode39 = new Barcode39();

        barcode39.setCode(Integer.toString(ticket.getId()));

        return barcode39.createImageWithBarcode(contentByte, null, null);
    }

    public Document generateTicketForEvent(Ticket ticket) throws Exception {
        final String pdfFileName = ticket.getEvent().getEventName() + "-" + ticket.getId() + ".pdf";
        final String pdfPath = ResourcePaths.PDF_FOLDER + pdfFileName;

        Document doc = null;
        PdfWriter writer = null;

        try {
            //created PDF document instance
            doc = new Document();

            //generate a PDF at the specified location
            writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));

            //opens the PDF
            doc.open();

            Paragraph titleParagraph = new Paragraph("Ticket " + ticket.getEvent().getEventName(), catFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);

            doc.add(titleParagraph);
            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("Name: " + ticket.getCustomerName()));
            doc.add(new Paragraph("Email: " + ticket.getCustomerEmail()));
            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph(ticket.getEvent().getEventName()));
            doc.add(new Paragraph(ticket.getEvent().getLocation()));
            doc.add(new Paragraph(ticket.getEvent().getDate().toString()));
            doc.add(new Paragraph(ticket.getEvent().getDescription()));
            doc.add(new Paragraph(ticket.getEvent().getPrice()));

            // Adding bar code to the document
            doc.add(generateBarCode(ticket, writer.getDirectContent()));

            // Adding qr code to the document
            doc.add(generateQRCode(ticket, 100, 100));
        }
        catch (DocumentException e) {
            e.printStackTrace();
            throw new Exception("Failed to create PDF", e);
        }
        finally {
            if (doc != null) doc.close();

            if (writer != null) writer.close();
        }

        return doc;
    }
}
