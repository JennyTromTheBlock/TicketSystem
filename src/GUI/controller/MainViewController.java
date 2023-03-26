package GUI.controller;

import BE.Event;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    public HBox topBar, searchHBox;
    public TextField textField;
    public Label createEvent;
    public BorderPane background;
    public ImageView listViewImage, calendarView,  searchButton, imageLogo;
    public VBox contentArea, sidebar;
    public Label locationOfSelectedEvent, createSpecialTicketBtn, dateOfSelectedEvent, titleOfSelectedEvent;

    private TableView tableView;
    TableColumn<Event, String> column1, column2, column3, column4, column5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //sets all image symbols
        createSymbolsForBtns();
        createColumnBoard();

        try {
            loadAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //adds the tableView to scene
        contentArea.getChildren().add(1, tableView);
        tableViewEventHandlers();
    }

    private void tableViewEventHandlers() {
        //Opens event info on Enter or double click
        tableView.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER) && tableView.getSelectionModel().getSelectedItem() != null) {
                handleViewEvent((Event) tableView.getSelectionModel().getSelectedItem());
            }
        });
        tableView.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && tableView.getSelectionModel().getSelectedItem() != null){
                handleViewEventInMain((Event) tableView.getSelectionModel().getSelectedItem());
                if(mouseEvent.getClickCount()==2) {
                    handleViewEvent((Event) tableView.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    private void handleViewEventInMain(Event event) {
        titleOfSelectedEvent.setText(event.getEventName());
        dateOfSelectedEvent.setText(String.valueOf(event.getDate()));
        locationOfSelectedEvent.setText(event.getLocation());
    }

    private void createSymbolsForBtns() {
        Image searchSymbol = new Image("symbols/searchSymbol.png");
        searchButton.setImage(searchSymbol);

        Image calendarImage = new Image("symbols/callender.png");
        calendarView.setImage(calendarImage);

        Image listViewLogo = new Image("symbols/listView.png");
        listViewImage.setImage(listViewLogo);

        Image logo = new Image("symbols/EASYDVEST.png");
        imageLogo.setImage(logo);
    }

    private void loadAllEvents() throws Exception {
        createColumnBoard();
        tableView.setItems(getModelsHandler().getEventModel().getObservableEvent());
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
    }

    public void handleViewEvent(Event event) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Event information");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        EventController controller = loader.getController();
        controller.setContent(event);
    }
}
