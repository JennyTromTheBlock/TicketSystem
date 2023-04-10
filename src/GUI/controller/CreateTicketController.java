package GUI.controller;

import BE.Event;
import BE.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;



public class CreateTicketController extends BaseController {
    public Label lblCustomerName;
    public Label lblAvailableTickets;
    public Button btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice;

    private Event selectedEvent;

    private static String FILE = "resourses/PDFs/temp.pdf";


    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);


    public CreateTicketController() {


    }


    public void handleCreateTicket() throws Exception {
        generatePDF();
        Ticket newTicket = createTicketFromFields();
        Ticket ticket = getModelsHandler().getTicketModel().createTicket(newTicket);
    }

    private Ticket createTicketFromFields() {

        int eventId = selectedEvent.getId();
        String customerName = txtfCustomerName.getText();
        String customerEmail = txtfCustomerEmail.getText();

        return new Ticket(customerName, customerEmail, selectedEvent);
    }

    public void setContent(Event event) {
       selectedEvent = event;

       setEventInfoLabels();

    }

    private void setEventInfoLabels() {
        lblTicketEventName.setText(selectedEvent.getEventName());
        lblTicketPrice.setText(selectedEvent.getPrice() + " KR");
        lblAvailableTickets.setText(selectedEvent.getMaxParticipant() + " Tickets");
        lblTcketEventLocation.setText(selectedEvent.getLocation());
        lblTicketEventDate.setText(selectedEvent.getDate().toString());

    }

    public void handleCancleTicket(ActionEvent actionEvent) {
       Stage s = (Stage) lblTicketEventDate.getScene().getWindow();
       s.close();
    }

    public void generatePDF() throws IOException, DocumentException {

        Image barcode = new Image("symbols/barcodes.png");
        //created PDF document instance
        Document doc = new Document();

        //generate a PDF at the specified location
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(FILE));
        System.out.println("PDF created.");
        //opens the PDF
        doc.open();

        Paragraph titleParagraph = new Paragraph("Ticket " + lblTicketEventName.getText(), catFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);

        doc.add(titleParagraph);
        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph("Name: " + txtfCustomerName.getText()));
        doc.add(new Paragraph("Email: " + txtfCustomerEmail.getText()));
        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph(lblTicketEventName.getText()));
        doc.add(new Paragraph(lblTcketEventLocation.getText()));
        doc.add(new Paragraph(lblTicketEventDate.getText()));
        doc.add(new Paragraph("Event Description"));
        doc.add(new Paragraph(lblTicketPrice.getText()));

        
        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("resourses/symbols/barcodes.png");

        // Adding image to the document
        doc.add( image);

        //addContent(doc);

        //close the PDF file
        doc.close();
        //closes the writer
        writer.close();

            // Creating an ImageData object
            //ImageData data = ImageDataFactory.create(imgBarcode);
            //Image img = new Image(data);
            //doc.add((Element) img);
    }


    /*
    private void addContent(Document doc) {

    }
    */

}
