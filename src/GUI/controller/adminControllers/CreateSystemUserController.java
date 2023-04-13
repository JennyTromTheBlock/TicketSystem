package GUI.controller.adminControllers;

import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.models.ModelsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateSystemUserController extends BaseController {
    @FXML
    private TextField txtfFirstName, txtfLastName, txtfEmail;
    @FXML
    private Label lblFirstNameValidation, lblLastNameValidation, lblEmailValidation, lblPasswordValidation;
    public PasswordField pwfPassword;

    public void handleCreateUser() {
        if(isInputFieldsFilled()) {
            SystemUser user = createSystemUserFromFields();

            try {
                user = ModelsHandler.getInstance().getSystemUserModel().createSystemUser(user);
                getModelsHandler().getSystemUserModel().getAllUsers().add(user);
            } catch (Exception e) {
                displayError(e);
            }
            handleCancel();
        }
    }

    private SystemUser createSystemUserFromFields() {
        String firstName = txtfFirstName.getText();
        String lastName = txtfLastName.getText();
        String email = txtfEmail.getText();
        String password = pwfPassword.getText();

        return new SystemUser(email, firstName, lastName, password);
    }

    /**
     * Checks if all the input Fields are filled with valid data
     * @return true if all the input fields are valid, false otherwise
     */
    private boolean isInputFieldsFilled() {
        return isFirstNameLegit() &&
                isLastNameLegit() &&
                isEmailLegit() &&
                isPasswordLegit();
    }

    private boolean isFirstNameLegit() {
        if (txtfFirstName.getText().isEmpty()) {
            lblFirstNameValidation.setText("You must enter your first name");
            return false;
        }
        lblFirstNameValidation.setText("");
        return true;
    }

    private boolean isLastNameLegit() {
        if (txtfLastName.getText().isEmpty()) {
            lblLastNameValidation.setText("You must enter your last name");
            return false;
        }
        lblLastNameValidation.setText("");
        return true;
    }

    private boolean isEmailLegit() {
        String email = txtfEmail.getText();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches() && !txtfEmail.getText().isEmpty()){
            lblEmailValidation.setText("");
            return true;
        }
        lblEmailValidation.setText("You must enter a valid email address");
        return false;
    }

    private boolean isPasswordLegit() {
        if (pwfPassword.getText().isEmpty()) {
            lblPasswordValidation.setText("You must enter a password");
            return false;
        }
        lblPasswordValidation.setText("");
        return true;
    }

    public void handleCancel() {
        Stage stage = (Stage) txtfFirstName.getScene().getWindow();
        stage.close();
    }
}