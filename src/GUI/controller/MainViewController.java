package GUI.controller;

import BE.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
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
        //sets all image symbols
        Image searchSymbol = new Image("searchSymbol.png");
        searchButton.setImage(searchSymbol);

        Image calendarImage = new Image("callender.png");
        calendarView.setImage(calendarImage);

        Image listViewLogo = new Image("listView.png");
        listViewImage.setImage(listViewLogo);

        Image cardLogo = new Image("CardList.png");
        cardViewImage.setImage(cardLogo);



        List<Event> s = new ArrayList<>();
        ObservableList<Event> list = FXCollections.observableList(s);

        TableView tableView = new TableView();

        //create columns
        TableColumn<Event, String> column1 =
                new TableColumn<>("Title");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("eventName"));

        TableColumn<Event, String> column2 =
                new TableColumn<>("location");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("location"));

        TableColumn<Event, Integer> column3 =
                new TableColumn<>("Max participants");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("maxParticipant"));

        TableColumn<Event, Integer> column4 =
                new TableColumn<>("Price");
        column4.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn<Event, Date> column5 =
                new TableColumn<>("date");
        column5.setCellValueFactory(
                new PropertyValueFactory<>("date"));


        //adds all columns to tableView
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);



        //test

        Date date = new Date();
        Event event = new Event("fodboldewkopf fest", "kgowrpgeopwfe", "lige her lige nu", date, 50, 20 );
        Event event1 = new Event("foefmwlpdbold fest", "kgowrpgeopwfe", "lige her lige nu", date, 50, 20 );
        Event event2 = new Event("håndbold fest", "kgowrpgeopwfe", "lige her lige nu", date, 50, 20 );
        Event event3 = new Event("fodbold fest", "kgowrpgeopwfe", "lige her lige nu", date, 50, 20 );
        list.add(event);
        list.add(event1);
        list.add(event2);
        list.add(event3);
        System.out.println(list.size());
        System.out.println(tableView.getColumns().size());
        tableView.setItems(list);

        //adds the tableView to scene
        contentArea.getChildren().add(tableView);
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
