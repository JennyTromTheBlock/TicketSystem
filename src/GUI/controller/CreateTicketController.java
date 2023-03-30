package GUI.controller;

import BE.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class CreateTicketController extends BaseController {
    @FXML
    public TextField txtfCustomerName, txtfCustomerEmail;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice;

    public CreateTicketController() {
    }


    public void handleCreateTicket2() throws Exception {
        Ticket ticket = createTicketFromFields();
        getModelsHandler().getTicketModel().createTicket(ticket);
    }

    private Ticket createTicketFromFields() {

        String customerName = txtfCustomerName.getText();
        String customerEmail = txtfCustomerEmail.getText();


        return new Ticket(customerName, customerEmail);
    }


    public void handleCancleTicket(ActionEvent actionEvent) {
    }
}
