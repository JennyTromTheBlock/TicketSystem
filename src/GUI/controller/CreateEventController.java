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

public class CreateEventController extends BaseController {

    @FXML
    private Label lblInputFieldValidation, lblLocationFieldValidation, lblDateFieldValidation;
    @FXML
    private TextField txtfPrice, txtfMaxTickets, txtfLocation, txtfEventName, txtfTimeField;
    @FXML
    private TextArea txtaDescriptionField;
    @FXML
    private DatePicker dateField;


    /**
     * checks if all important fields are filled, and create an event object
     * todo call create method in model
     * @param actionEvent
     */
    public void handleCreateEvent(ActionEvent actionEvent) throws Exception {
        if (isInputFieldsFilled()) {
            Event eventWithoutId = createEventFromFields();
            getModelsHandler().getEventModel().createEvent(eventWithoutId);
            handleCancel();
        }
    }

    private Event createEventFromFields() throws Exception {
        //gets all parameters for the Event object
        String eventName = txtfEventName.getText();
        String description = txtaDescriptionField.getText();
        String location = txtfLocation.getText();
        Date date = DateConverter.dateConverter(dateField.getValue(), txtfTimeField.getText());
        int maxTickets = !txtfMaxTickets.getText().isEmpty()? Integer.parseInt(txtfMaxTickets.getText()) : 0;
        int price = !txtfPrice.getText().isEmpty()? Integer.parseInt(txtfPrice.getText()) : 0;

        return new Event(eventName, description, location, date, maxTickets, price);
    }

    public void handleCancel() {
        //closes the window
        Stage stage = (Stage) txtfEventName.getScene().getWindow();
        stage.close();
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
        }else {
            lblDateFieldValidation.setText("");
        }
        return true;
    }

    private boolean isLocationLegit() {
        if (txtfLocation.getText().isEmpty()) {
            lblLocationFieldValidation.setText("you must set a location");
            return false;
        }else {
            lblLocationFieldValidation.setText("");
        }
        return true;
    }

    private boolean isEventNameLegit() {
        if (txtfEventName.getText().isEmpty()) {
            lblInputFieldValidation.setText("you must set a event name");
            return false;
        }else {
            lblInputFieldValidation.setText("");
        }
        return true;
    }

    public void setContent(Event event) {
        txtfEventName.setText(event.getEventName());
        txtaDescriptionField.setText(event.getDescription());
        txtfLocation.setText(event.getLocation());
        dateField.setValue(event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        txtfTimeField.setText(""+event.getDate().getTime());
        txtfPrice.setText(""+event.getPrice());
        txtfMaxTickets.setText(""+event.getMaxParticipant());
    }
}