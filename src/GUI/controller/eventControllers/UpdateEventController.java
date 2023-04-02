package GUI.controller.eventControllers;

import BE.Event;
import GUI.controller.BaseController;
import GUI.models.EventModel;
import GUI.util.DateConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class UpdateEventController extends BaseController {
    @FXML
    private Label lblInputFieldValidation, lblLocationFieldValidation, lblDateFieldValidation;
    @FXML
    private TextField txtfPriceField, txtfMaxTicketsField, txtfLocationField, txtfEventNameField, txtfTimeField;
    @FXML
    private TextArea txtaDescriptionField;
    @FXML
    private DatePicker dateField;

    private Event eventToUpdate; //Keeps track of what specific event we're editing

    public void handleUpdateEvent(ActionEvent actionEvent) {
        if (isInputFieldsFilled()) {
            Event updatedEvent = new Event(eventToUpdate.getId(), createEventFromFields());

            try {
                getModelsHandler().getEventModel().updateEvent(updatedEvent);

                handleCancel();
            }
            catch (Exception e) {
                displayError(e);
            }
        }
    }

    private Event createEventFromFields() {
        //gets all parameters for the event object
        String eventName = txtfEventNameField.getText();

        String description = txtaDescriptionField.getText();

        String location = txtfLocationField.getText();

        Date date = DateConverter.dateConverter(dateField.getValue(), txtfTimeField.getText());

        int maxTickets = !txtfMaxTicketsField.getText().isEmpty() ? Integer.parseInt(txtfMaxTicketsField.getText()) : 0;

        int price = !txtfPriceField.getText().isEmpty() ? Integer.parseInt(txtfPriceField.getText()) : 0;

        return new Event(eventName, description, location, date, maxTickets, price);
    }

    /**
     * Checks if all the input Fields are filled with valid data
     * @return true if all the input fields are valid, false otherwise
     */
    private boolean isInputFieldsFilled() {
        return isEventNameLegit() &&
                isLocationLegit() &&
                isDateLegit() &&
                isTimeLegit();
    }

    //todo check if format of time is correct fx 19:00 or 1900 or 19.00 osv
    private boolean isTimeLegit() {
        if (txtfTimeField.getText().isEmpty()) {
            lblDateFieldValidation.setText("You must set a start time");
            return false;
        }

        lblDateFieldValidation.setText("");
        return true;
    }

    private boolean isDateLegit() {
        if (dateField.getEditor().getText().isEmpty()) {
            lblDateFieldValidation.setText("You must set an event date");
            return false;
        }

        lblDateFieldValidation.setText("");
        return true;
    }

    private boolean isLocationLegit() {
        if (txtfLocationField.getText().isEmpty()) {
            lblLocationFieldValidation.setText("You must set a location");
            return false;
        }

        lblLocationFieldValidation.setText("");
        return true;
    }

    private boolean isEventNameLegit() {
        if (txtfEventNameField.getText().isEmpty()){
            lblInputFieldValidation.setText("You must set an event name");
            return false;
        }

        lblInputFieldValidation.setText("");
        return true;
    }

    public void setContent(Event event) {
        eventToUpdate = event;

        txtfEventNameField.setText(event.getEventName());

        txtaDescriptionField.setText(event.getDescription());

        txtfLocationField.setText(event.getLocation());

        dateField.setValue(event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        txtfPriceField.setText(""+event.getPrice());

        txtfMaxTicketsField.setText(""+event.getMaxParticipant());

        txtfTimeField.setText(DateConverter.getTimeFromDate(event.getDate()));
    }

    public void handleCancel() {
        //closes the window
        Stage stage = (Stage) txtfEventNameField.getScene().getWindow();
        stage.close();
    }
}
