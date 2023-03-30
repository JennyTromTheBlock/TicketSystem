package GUI.controller;

import BE.Event;
import BE.SystemUser;
import GUI.util.DateConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class CreateSystemUserController {
    @FXML
    private TextField txtfFirstName, txtfLastName, txtfEmail;
    @FXML
    private Label lblFirstNameValidation, lblLastNameValidation, lblEmailValidation, lblPasswordValidation;
    public PasswordField pwfPassword;

    public void handleCreateUser() {
        SystemUser user = createSystemUserFromFields();
        //TODO send to model and delete sout
        System.out.println(user);
        handleCancel();
    }

    private SystemUser createSystemUserFromFields() {
        String firstName = txtfFirstName.getText();
        String lastName = txtfFirstName.getText();
        String email = txtfEmail.getText();
        String password = pwfPassword.getText();

        return new SystemUser(email, firstName, lastName, password);
    }

    public void handleCancel() {
        Stage stage = (Stage) txtfFirstName.getScene().getWindow();
        stage.close();
    }
}
