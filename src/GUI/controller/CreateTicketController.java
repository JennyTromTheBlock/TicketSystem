package GUI.controller;

import BE.Event;
import BE.Ticket;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
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
    private static String imgBarcode = "resourses/symbols/barcodes.png";


    public CreateTicketController() {

    }

    public void generatePDF() throws BadElementException, IOException {

        //created PDF document instance
        Document doc = new Document();

        try {
            //generate a PDF at the specified location
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(FILE));
            System.out.println("PDF created.");


            // Creating an ImageData object
            //ImageData data = ImageDataFactory.create(imgBarcode);
            //Image img = new Image(data);

            //opens the PDF
            doc.open();

            //adds paragraph to the PDF file
            doc.add(new Paragraph("Name: " + txtfCustomerName.getText()));
            doc.add(new Paragraph("Email: " + txtfCustomerEmail.getText()));
            doc.add(new Paragraph(lblTicketEventName.getText()));
            doc.add(new Paragraph(lblTicketEventName.getText()));
            doc.add(new Paragraph("Event Description"));
            doc.add(new Paragraph(lblTcketEventLocation.getText()));
            doc.add(new Paragraph(lblTicketPrice.getText()));
            //doc.add((Element) img);
            //close the PDF file
            doc.close();

            //closes the writer
            writer.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
}
