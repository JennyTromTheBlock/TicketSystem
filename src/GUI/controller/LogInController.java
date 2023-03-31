package GUI.controller;

import GUI.util.SymbolPaths;
import GUI.util.ViewPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController extends BaseController implements Initializable {
    @FXML
    private ImageView logoImg;
    @FXML
    private Label sideField;
    @FXML
    private TextField txtfEmail;
    @FXML
    private PasswordField pwfPassword;
    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image searchSymbol = new Image(SymbolPaths.EASYDVEST_LOGO);
        logoImg.setImage(searchSymbol);
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        disableLoginBtn();

        String email = txtfEmail.getText();
        String password = pwfPassword.getText();
        boolean isLoggedIn = false;

        try {
            isLoggedIn = getModelsHandler().getSystemUserModel().login(email, password);
        }
        catch (Exception e) {
            displayError(e);
        }

        if (isLoggedIn) {
            openStage(ViewPaths.MAIN_VIEW, "");

            close();
        }
        else {
            enableLoginBtn();

            //TODO Display incorrect Email or Password message.
        }
    }
    private void close() {
        Stage stage = (Stage) sideField.getScene().getWindow();
        stage.close();
    }

    private void enableLoginBtn() {
        btnLogin.setDisable(false);
    }

    private void disableLoginBtn() {
        btnLogin.setDisable(true);
    }
}
