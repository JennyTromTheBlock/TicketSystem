package GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

    public void logInBtn(ActionEvent actionEvent) throws Exception {
        loadMainViewHandler().getController();
        Stage s = (Stage) sideField.getScene().getWindow();
        s.close();
    }
}
