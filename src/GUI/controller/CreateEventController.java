package GUI.controller;

import BE.Event;
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

public class CreateEventController {

    @FXML
    private TextField priceField, maxTicketsField, locationField, eventNameField, timeField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker dateField;
    @FXML
    private Label inputFieldValidation, locationFieldValidation, dateFieldValidation;

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    private EventModel eventModel;




    /**
     * checks if all important fields are filled, and create an event object
     * todo call create method in model
     * @param actionEvent
     */
    public void handleCreateEvent(ActionEvent actionEvent) throws Exception {
        if(isInputFieldsFilled()){
            Event eventWithoutId = createEventFromFields();
            eventModel.createEvent(eventWithoutId);
            //closes the window
            handleCancel();
        }
    }

    private Event createEventFromFields() throws Exception {
        //gets all parameters for the event object
        String eventName = eventNameField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        Date date = DateConverter.dateConverter(dateField.getValue(), timeField.getText());
        int maxTickets = !maxTicketsField.getText().isEmpty()? Integer.parseInt(maxTicketsField.getText()) : 0;
        int price = !priceField.getText().isEmpty()? Integer.parseInt(priceField.getText()) : 0;

        return new Event(eventName, description, location, date, maxTickets, price);
    }

    public void handleCancel() {
        //closes the window
        Stage stage = (Stage) eventNameField.getScene().getWindow();
        stage.close();
    }

    /**
     * checks if all inputFields are filled correct.
     * @return
     */
    private boolean isInputFieldsFilled(){
        //checks if all important fields are filled.
        //returns false if one of the inputs are incorrect
        return isEventNameLegit() &&
                isLocationLegit() &&
                isDateLegit() &&
                isTimeLegit();
    }

    //todo check if format of time is correct fx 19:00 or 1900 or 19.00 osv
    private boolean isTimeLegit() {
        if (timeField.getText().isEmpty()){
            dateFieldValidation.setText("you must set a start time");
            return false;
        } else{
            dateFieldValidation.setText("");
        }
        return true;
    }

    private boolean isDateLegit() {
        if (dateField.getEditor().getText().isEmpty()){
            dateFieldValidation.setText("you must set a event date");
            return false;
        }else{
            dateFieldValidation.setText("");
        }
        return true;
    }

    private boolean isLocationLegit() {
        if (locationField.getText().isEmpty()){
            locationFieldValidation.setText("you must set a location");
            return false;
        }else {
            locationFieldValidation.setText("");
        }
        return true;
    }

    private boolean isEventNameLegit() {
        if (eventNameField.getText().isEmpty()){
            inputFieldValidation.setText("you must set a event name");
            return false;
        }else {
            inputFieldValidation.setText("");
        }
        return true;
    }

    public void setContent(Event event) {
        eventNameField.setText(event.getEventName());
        descriptionField.setText(event.getDescription());
        locationField.setText(event.getLocation());
        dateField.setValue(event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeField.setText(""+event.getDate().getTime());
        priceField.setText(""+event.getPrice());
        maxTicketsField.setText(""+event.getMaxParticipant());

    }
}