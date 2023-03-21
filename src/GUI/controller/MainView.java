package GUI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public ImageView calendarView;
    public ImageView listViewImage;
    public ImageView cardViewImage;
    public VBox contentArea;
    public VBox upcomingEventSideBar;
    public VBox myEventSideBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image searchSymbol = new Image("searchSymbol.png");
        searchButton.setImage(searchSymbol);

        Image calendarImage = new Image("callender.png");
        calendarView.setImage(calendarImage);

        Image listViewLogo = new Image("listView.png");
        listViewImage.setImage(listViewLogo);

        Image cardLogo = new Image("CardList.png");
        cardViewImage.setImage(cardLogo);

        //test
        List<String> s = new ArrayList<>();
        s.add("fjeo");
        s.add("foe");
        s.add("forgee");
        s.add("fogregrege");
        s.add("regregreg");
        s.add("h65hg4foe");
        ObservableList obs = FXCollections.observableList(s);
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(800);
        listView.setItems(obs);
        contentArea.getChildren().add(listView);


    }

    public void handleCreateEvent(MouseEvent mouseEvent) {

        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreateEvent.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Add new event");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
