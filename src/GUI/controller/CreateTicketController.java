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
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;



public class CreateTicketController extends BaseController {
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAvailableTickets;
    @FXML
    private Button btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice;

    private Event selectedEvent;


    public void handleCreateTicket() throws Exception {
        Ticket ticket = getModelsHandler().getTicketModel().createTicket(createTicketFromFields());

        //TODO Show view with the newly created ticket. There should be an option for printing or sending by Email.
    }

    private Ticket createTicketFromFields() {
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
