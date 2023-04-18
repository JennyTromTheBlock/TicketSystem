package BLL.Util;

import java.io.FileOutputStream;
import java.util.List;

import BE.SpecialTicket;
import BE.SpecialTicketType;
import BE.Ticket;
import GUI.util.ResourcePaths;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class PDFGenerator {
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    private Image generateQRCode(String code, int width, int height) throws Exception {
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(code, width, height, null);

        try {
            return barcodeQRCode.getImage();
        }
        catch (BadElementException e) {
            e.printStackTrace();
            throw new Exception("Failed to generate QR-Code", e);
        }
    }

    private Image generateBarCode(String code, PdfContentByte contentByte) {
        Barcode128 barcode128 = new Barcode128();

        barcode128.setCode(code);

        return barcode128.createImageWithBarcode(contentByte, null, null);
    }

    public String generateTicketForEvent(Ticket ticket, List<SpecialTicket> specialTicketsToAppend) throws Exception {
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

            String ticketId = String.valueOf(ticket.getId());

            // Adding bar code to the document
            doc.add(generateBarCode(ticketId, writer.getDirectContent()));

            // Adding qr code to the document
            doc.add(generateQRCode(ticketId, 100, 100));



            for (SpecialTicket specialTicket : specialTicketsToAppend) {

                doc.add(Chunk.NEWLINE);

                Paragraph specialTicketTitle = new Paragraph("Special ticket " + specialTicket.getType().getTypeName(), catFont);
                specialTicketTitle.setAlignment(Element.ALIGN_CENTER);

                doc.add(specialTicketTitle);

                String specialTicketId = String.valueOf(specialTicket.getId());

                doc.add(generateBarCode(specialTicketId, writer.getDirectContent()));

                doc.add(generateQRCode(specialTicketId, 100, 100));
            }
        }
        catch (DocumentException e) {
            e.printStackTrace();
            throw new Exception("Failed to create ticket PDF", e);
        }
        finally {
            if (doc != null) doc.close();

            if (writer != null) writer.close();
        }

        return pdfPath;
    }

    public String generateTicketForEvent(Ticket ticket) throws Exception {
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

            String stringId = String.valueOf(ticket.getId());

            // Adding bar code to the document
            doc.add(generateBarCode(stringId, writer.getDirectContent()));

            // Adding qr code to the document
            doc.add(generateQRCode(stringId, 100, 100));
        }
        catch (DocumentException e) {
            e.printStackTrace();
            throw new Exception("Failed to create ticket PDF", e);
        }
        finally {
            if (doc != null) doc.close();

            if (writer != null) writer.close();
        }

        return pdfPath;
    }


    public String generateSpecialTicketForEvent(SpecialTicketType specialTicketType) throws Exception {
        final String pdfFileName = specialTicketType.getTypeName() + "-.pdf";
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

            Paragraph titleParagraph = new Paragraph("Special Ticket " + specialTicketType.getTypeName(), catFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);

            doc.add(titleParagraph);
            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("Type: " + specialTicketType.getTypeName()));
            doc.add(new Paragraph("Price: " + specialTicketType.getPrice()));

            // Adding bar code to the document
            doc.add(generateBarCode(specialTicketType.getTypeName(), writer.getDirectContent()));

            // Adding qr code to the document
            doc.add(generateQRCode(specialTicketType.getTypeName(), 100, 100));
        }
        catch (DocumentException e) {
            e.printStackTrace();
            throw new Exception("Failed to create special ticket PDF", e);
        }
        finally {
            if (doc != null) doc.close();

            if (writer != null) writer.close();
        }

        return pdfPath;
    }


}
