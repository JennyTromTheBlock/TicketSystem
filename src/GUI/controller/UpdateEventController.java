package GUI.controller;

import BE.Event;
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

    private int eventId; //Keeps track of what specific event we're editing

    public void handleUpdateEvent(ActionEvent actionEvent) {
        if (isInputFieldsFilled()) {
            Event eventToUpdate = new Event(eventId, createEventFromFields());

            try {
                Event updatedEvent = getModelsHandler().getEventModel().updateEvent(eventToUpdate);

                if (updatedEvent != null) {
                    handleCancel();
                }
            }
            catch (Exception e) {
                //TODO display the error for the system user
            }
        }
    }

    private Event createEventFromFields() {
        //gets all parameters for the event object
        String eventName = txtfEventNameField.getText();
        String description = txtaDescriptionField.getText();
        String location = txtfLocationField.getText();
        Date date = DateConverter.dateConverter(dateField.getValue(), txtfTimeField.getText());
        int maxTickets = !txtfMaxTicketsField.getText().isEmpty()? Integer.parseInt(txtfMaxTicketsField.getText()) : 0;
        int price = !txtfPriceField.getText().isEmpty()? Integer.parseInt(txtfPriceField.getText()) : 0;

        return new Event(eventName, description, location, date, maxTickets, price);
    }

    /**
     * checks if all inputFields are filled correct.
     * @return
     */
    private boolean isInputFieldsFilled() {
        //checks if all important fields are filled.
        //returns false if one of the inputs are incorrect
        return isEventNameLegit() &&
                isLocationLegit() &&
                isDateLegit() &&
                isTimeLegit();
    }

    //todo check if format of time is correct fx 19:00 or 1900 or 19.00 osv
    private boolean isTimeLegit() {
        if (txtfTimeField.getText().isEmpty()){
            lblDateFieldValidation.setText("you must set a start time");
            return false;
        } else {
            lblDateFieldValidation.setText("");
        }
        return true;
    }

    private boolean isDateLegit() {
        if (dateField.getEditor().getText().isEmpty()){
            lblDateFieldValidation.setText("you must set a event date");
            return false;
        } else {
            lblDateFieldValidation.setText("");
        }
        return true;
    }

    private boolean isLocationLegit() {
        if (txtfLocationField.getText().isEmpty()){
            lblLocationFieldValidation.setText("you must set a location");
            return false;
        } else {
            lblLocationFieldValidation.setText("");
        }
        return true;
    }

    private boolean isEventNameLegit() {
        if (txtfEventNameField.getText().isEmpty()){
            lblInputFieldValidation.setText("you must set a event name");
            return false;
        } else {
            lblInputFieldValidation.setText("");
        }
        return true;
    }

    public void setEvent(Event event) {
        this.eventId = event.getId();

        setContent(event);
    }

    private void setContent(Event event) {
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
