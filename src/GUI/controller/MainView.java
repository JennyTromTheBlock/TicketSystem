package GUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
    public Label createEvent;
    public BorderPane background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image i = new Image("searchSymbol.png");
        searchButton.setImage(i);



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/EventView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        EventController controller = loader.getController();

        background.setCenter(root);
    }
}
