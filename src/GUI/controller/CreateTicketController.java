package GUI.controller;

import BE.Event;
import BE.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CreateTicketController extends BaseController {
    public Label lblCustomerName;
    public Label lblAvailableTickets;
    public Button btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice;

    private Event selectedEvent;

    public CreateTicketController() {
    }


    public void handleCreateTicket() throws Exception {
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
