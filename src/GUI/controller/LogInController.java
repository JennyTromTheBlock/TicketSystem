package GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController extends BaseController implements Initializable {
    public ImageView logoImg;
    public Label sideField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image searchSymbol = new Image("symbols/EASYDVEST.png");
       logoImg.setImage(searchSymbol);
    }

    public void logInBtn(ActionEvent actionEvent) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            displayError(new Exception("Failed to load the main view", e));
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/GUI/css/Style.css").toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        Stage s = (Stage) sideField.getScene().getWindow();
        s.close();
    }
}
