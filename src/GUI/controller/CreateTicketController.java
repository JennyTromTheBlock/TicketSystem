package GUI.controller;

import BE.Event;
import BE.Ticket;
import GUI.util.DateConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.ZoneId;
import java.util.Date;


public class CreateTicketController extends BaseController {
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice;

    public CreateTicketController() {
    }


    public void handleCreateTicket2() throws Exception {
        Ticket newTicket = createTicketFromFields();
        getModelsHandler().getTicketModel().createTicket(newTicket);
        getModelsHandler().getEventModel()

    }

    private Ticket createTicketFromFields() {


        String customerName = txtfCustomerName.getText();
        String customerEmail = txtfCustomerEmail.getText();
        System.out.println(customerEmail);
        System.out.println(customerName);

        return new Ticket(customerName, customerEmail);
    }
    public void setContent(Event event){


    }
    public void handleCancleTicket(ActionEvent actionEvent) {
    }
}
