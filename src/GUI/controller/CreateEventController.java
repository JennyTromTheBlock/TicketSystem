package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CreateEventController {

    public TextField priceField, maxTicketsField, locationField, eventNameField, timeField;
    public TextArea descriptionField;
    public DatePicker dateField;
    public Label inputFieldValidation, locationFieldValidation, dateFieldValidation;

    /**
     * checks if all important fields are filled, and create an event object
     * todo call create method in model
     * @param actionEvent
     */
    public void handleCreateEvent(ActionEvent actionEvent) {
        if(isInputFieldsFilled()){
            //gets all parameters for the event object
            String eventName = eventNameField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            Date date = dateConverter(dateField.getValue(), timeField.getText());
            int maxTickets = !maxTicketsField.getText().isEmpty()? Integer.parseInt(maxTicketsField.getText()) : 0;
            int price = !priceField.getText().isEmpty()? Integer.parseInt(priceField.getText()) : 0;

            Event event = new Event(eventName, description, location, date, maxTickets, price);
            //closes the window
            handleCancel();
        }
    }

    public void handleCancel() {
        //closes the window
        Stage stage = (Stage) eventNameField.getScene().getWindow();
        stage.close();
    }

    /**
     * takes the local date and time and combines them into one date
     * @param date
     * @param time
     * @return
     */
    private Date dateConverter(LocalDate date, String time) {
        //creates a calendar and sets the date
        Date resultDate = java.sql.Date.valueOf(date);
        Calendar timeOfDay = Calendar.getInstance();
        timeOfDay.setTime(resultDate);
        //splits the time string into hours and minutes
        String hours = time.substring(0, 2);
        String minutes = time.substring(Math.max(time.length() - 2, 0));
        //sets the time of day in the calendar and converts it to a date and returns it
        timeOfDay.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
        timeOfDay.set(Calendar.MINUTE, Integer.parseInt(minutes));
        resultDate = timeOfDay.getTime();
        return resultDate;
    }

    /**
     * checks if all inputFields are filled correct.
     * @return
     */
    private boolean isInputFieldsFilled(){
        //checks if all important fields are filled.
        if(isEventNameLegit()&&
        isLocationLegit()&&
        isDateLegit() &&
        isTimeLegit()){
            return true;
        }
        //returns false if one of the inputs are incorrect
        return false;
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
}