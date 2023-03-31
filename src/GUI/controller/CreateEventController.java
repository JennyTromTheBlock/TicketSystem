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
    public void handleCreateEvent(ActionEvent actionEvent) {
        if(isInputFieldsFilled()){
            Event eventWithoutId = createEventFromFields();;
            try {
                getModelsHandler().getEventModel().createEvent(eventWithoutId);
            }
            catch (Exception e) {
                displayError(e);
            }
            handleCancel();
        }
    }

    private Event createEventFromFields() {
        //gets all parameters for the Event object
        String eventName = txtfEventName.getText();

        String description = txtaDescriptionField.getText();

        String location = txtfLocation.getText();

        Date date = DateConverter.dateConverter(dateField.getValue(), txtfTimeField.getText());

        int maxTickets = !txtfMaxTickets.getText().isEmpty() ? Integer.parseInt(txtfMaxTickets.getText()) : 0;

        int price = !txtfPrice.getText().isEmpty() ? Integer.parseInt(txtfPrice.getText()) : 0;

        return new Event(eventName, description, location, date, maxTickets, price);
    }

    public void handleCancel() {
        //closes the window
        Stage stage = (Stage) txtfEventName.getScene().getWindow();
        stage.close();
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
        if (txtfTimeField.getText().isEmpty()){
            lblDateFieldValidation.setText("you must set a start time");
            return false;
        }

        lblDateFieldValidation.setText("");
        return true;
    }

    private boolean isDateLegit() {
        if (dateField.getEditor().getText().isEmpty()){
            lblDateFieldValidation.setText("you must set a event date");
            return false;
        }

        lblDateFieldValidation.setText("");
        return true;
    }

    private boolean isLocationLegit() {
        if (txtfLocation.getText().isEmpty()) {
            lblLocationFieldValidation.setText("you must set a location");
            return false;
        }

        lblLocationFieldValidation.setText("");
        return true;
    }

    private boolean isEventNameLegit() {
        if (txtfEventName.getText().isEmpty()) {
            lblInputFieldValidation.setText("you must set a event name");
            return false;
        }

        lblInputFieldValidation.setText("");
        return true;
    }
}