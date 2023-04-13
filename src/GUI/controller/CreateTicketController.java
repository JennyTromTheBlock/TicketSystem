package GUI.controller;

import BE.Event;
import BE.SystemUser;
import BE.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;


public class CreateTicketController extends BaseController {
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAvailableTickets;
    @FXML
    private Button btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail, txtfAmount;
    @FXML
    private Label lblTicketAmount, lblPriceTotal, lblPriceAmount, lblTicketEventName, lblTicketEventDate, lblTcketEventLocation, lblTicketPrice, lblCustomerNameValidation, lblCustomerEmailValidation, lblAmountValidation;

    private Event selectedEvent;


    public void handleCreateTicket() {
        if (areInputFieldsValid()) {
            try {
                List<Ticket> newTickets = getModelsHandler().getTicketModel().createTicket(createTicketFromFields(), Integer.parseInt(txtfAmount.getText()));

                //TODO Show view with the newly created ticket. There should be an option for printing or sending by Email.
            }
            catch (Exception e) {
                displayError(e);
            }
        }
    }

    private Ticket createTicketFromFields() {
        String customerName = txtfCustomerName.getText();
        String customerEmail = txtfCustomerEmail.getText();

        return new Ticket(customerName, customerEmail, selectedEvent);
    }

    private boolean areInputFieldsValid() {
        return isCustomerNameValid() && isCustomerEmailValid() && isAmountValid();
    }

    private boolean isAmountValid() {
        try {
            int intAmount = Integer.parseInt(txtfAmount.getText());

            if (intAmount > 0) {
                lblAmountValidation.setText("");
                return true;
            }
        }
        catch (NumberFormatException e) {
            lblAmountValidation.setText("The amount of tickets must be a positive number");
        }

        return false;
    }

    private boolean isCustomerNameValid() {
        if (txtfCustomerName.getText().isBlank()) {
            lblCustomerNameValidation.setText("You must enter a valid name");
            return false;
        }

        lblCustomerNameValidation.setText("");
        return true;
    }

    private boolean isCustomerEmailValid() {
        if (!SystemUser.isEmailValid(txtfCustomerEmail.getText())) {
            lblCustomerEmailValidation.setText("You must enter a valid E-mail");
            return false;
        }

        lblCustomerEmailValidation.setText("");
        return true;
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

    public void handleCancelTicket(ActionEvent actionEvent) {
       Stage s = (Stage) lblTicketEventDate.getScene().getWindow();
       s.close();
    }


}
