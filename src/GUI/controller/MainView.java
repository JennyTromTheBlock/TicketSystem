package GUI.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    public ImageView searchButton;
    public HBox topBar;
    public TextField textField;
    public HBox searchHBox;
    public VBox sidebar;
    public Label allEvents;
    public Label yourEvents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image i = new Image("searchSymbol.png");
        searchButton.setImage(i);
    }
}
