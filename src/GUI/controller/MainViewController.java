package GUI.controller;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;
import GUI.models.EventModel;
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
    public HBox topBar, searchHBox;
    public TextField textField;
    public ObservableList<Object> allEvents;
    public Label createEvent;
    public Label yourEvents;
    public BorderPane background;
    public ImageView listViewImage, cardViewImage, calendarView,  searchButton;
    public VBox contentArea, myEventSideBar, upcomingEventSideBar, sidebar;


    private TableView tableView;

    private EventModel eventmodel;

    TableColumn<Event, String> column1, column2, column3, column4, column5;

    private IEventManager eventManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //sets all image symbols
        createSymbolsForBtns();
        createColumnBoard();

        try {
            eventmodel = new EventModel();
            loadAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //adds the tableView to scene
        contentArea.getChildren().add(tableView);
    }

    private void createSymbolsForBtns() {
        Image searchSymbol = new Image("searchSymbol.png");
        searchButton.setImage(searchSymbol);

        Image calendarImage = new Image("callender.png");
        calendarView.setImage(calendarImage);

        Image listViewLogo = new Image("listView.png");
        listViewImage.setImage(listViewLogo);

        Image cardLogo = new Image("CardList.png");
        cardViewImage.setImage(cardLogo);
    }

    private void loadAllEvents() throws Exception {
        createColumnBoard();

        tableView.setItems(eventmodel.getAllEvents());
    }

    private void createColumnBoard() {
        tableView = new TableView();
        tableView.setPrefHeight(800);

        //create columns
        column1 =
                new TableColumn<>("Title");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("eventName"));

        column2 =
                new TableColumn<>("location");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("location"));

        column3 =
                new TableColumn<>("Max participants");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("maxParticipant"));

        column4 =
                new TableColumn<>("Price");
        column4.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        column5 =
                new TableColumn<>("date");
        column5.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        //adds all columns to tableView
        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
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

        CreateEventController controller = loader.getController();
        controller.setEventModel(eventmodel);
    }
}
