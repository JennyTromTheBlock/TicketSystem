package GUI.controller;

import BE.*;
import BE.Event;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


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
    @FXML
    private ListView<String> listviewSelectedSpecialTickets, listviewAvailableSpecialTickets;

    private Event selectedEvent;


    public void handleCreateTicket() {
        if (areInputFieldsValid()) {
            try {

                Ticket ticket = createTicketFromFields();
                List<SpecialTicket> selectedSpecialTickets = createSpecialTicketsFromField();
                int amount = Integer.parseInt(txtfAmount.getText());

                List<Ticket> newTickets = new ArrayList<>();

                if (selectedSpecialTickets.isEmpty()) {

                    newTickets = getModelsHandler().getTicketModel().createTicket(ticket, amount);
                }
                else {

                    newTickets = getModelsHandler().getTicketModel().createTicket(ticket, amount, selectedSpecialTickets);
                }

                openPdfTickets(newTickets);
            }
            catch (Exception e) {
                displayError(e);
            }
        }
    }

    private void openPdfTickets(List<Ticket> tickets) {
        if (!Desktop.isDesktopSupported()) return;

        for (Ticket ticket : tickets) {

            try {

                if (!ticket.getPdfTicketPath().isEmpty()) {

                    File newTicketPdfFile = new File(ticket.getPdfTicketPath());

                    Desktop.getDesktop().open(newTicketPdfFile);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                displayError(new Exception("Failed to open the newly created ticket", e));
            }
        }
    }

    private Ticket createTicketFromFields() {
        String customerName = txtfCustomerName.getText();
        String customerEmail = txtfCustomerEmail.getText();

        return new Ticket(customerName, customerEmail, selectedEvent);
    }

    private List<SpecialTicket> createSpecialTicketsFromField() {

        List<SpecialTicket> selectedSpecialTickets = new ArrayList<>();

        listviewSelectedSpecialTickets.getItems().forEach(typeName -> {

            SpecialTicketType specialTicketType = selectedEvent.getTypeFromName(typeName);

            if (specialTicketType != null) {

                SpecialTicket specialTicket = new SpecialTicket(specialTicketType, selectedEvent);

                selectedSpecialTickets.add(specialTicket);
            }
        });

        return selectedSpecialTickets;
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
       initializeAvailableSpecialTicketTypes();
    }

    private void initializeAvailableSpecialTicketTypes() {
        try {
            List<String> availableTypes = getModelsHandler()
                    .getEventModel()
                    .getAvailableSpecialTicketTypesOnEvent(selectedEvent.getId())
                    .stream()
                    .map(SpecialTicketType::getTypeName)
                    .toList();

            listviewAvailableSpecialTickets.setItems(FXCollections.observableArrayList(availableTypes));

            setAvailableSpecialTicketTypesListener();
        }
        catch (Exception e) {
            displayError(e);
        }
    }

    private void setAvailableSpecialTicketTypesListener() {
        listviewAvailableSpecialTickets.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {

                String typeName = listviewAvailableSpecialTickets.getSelectionModel().getSelectedItem();

                if (typeName != null) {
                    listviewAvailableSpecialTickets.getItems().remove(typeName);
                    listviewSelectedSpecialTickets.getItems().add(typeName);
                }
            }
        });
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
