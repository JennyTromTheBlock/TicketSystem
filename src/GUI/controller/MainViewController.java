package GUI.controller;

import BE.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    @FXML
    private HBox topBar, hBoxSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private BorderPane background;
    @FXML
    private ImageView ivList, ivCalendar, ivSearchBtn, ivLogo;
    @FXML
    private VBox contentArea, sidebar;
    @FXML
    private TableView tvEvents;
    @FXML
    private TableColumn tcTitle, tcLocation, tcMaxParticipants, tcPrice, tcDate;
    @FXML
    private Label lblLocation, lblDate, lblTitle, lblPrice, lblTicketsLeft;
    @FXML
    private Button btnCreateEvent, btnSpecialTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadImages();
        loadTableColumns();

        try {
            loadAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tableViewEventHandlers();
    }

    /**
     * Adds event handlers to the TableView that checks for interaction with an event
     */
    private void tableViewEventHandlers() {
        //Opens event info on Enter
        tvEvents.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER) && tvEvents.getSelectionModel().getSelectedItem() != null) {
                handleViewEvent((Event) tvEvents.getSelectionModel().getSelectedItem());
            }
        });

        //Opens event info on double click
        tvEvents.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && tvEvents.getSelectionModel().getSelectedItem() != null){
                if(mouseEvent.getClickCount()==2) {
                    handleViewEvent((Event) tvEvents.getSelectionModel().getSelectedItem());
                }
            }
        });

        //Listens for changes in selected event (fx. when switching between events with Up/Down key)
        tvEvents.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->
                handleViewEventInMain((Event) tvEvents.getSelectionModel().getSelectedItem()));
    }

    /**
     * Shows preview of event information in the right sidebar
     * @param event, the selected event
     */
    public void handleViewEventInMain(Event event) {
        lblTitle.setText(event.getEventName());
        lblDate.setText(String.valueOf(event.getDate()));
        lblLocation.setText(event.getLocation());
        lblPrice.setText(event.getPrice() + " DKK");
        lblTicketsLeft.setText(event.getMaxParticipant() + " tickets available"); //TODO subtract sold tickets from max part.
    }

    private void loadImages() {
        ivSearchBtn.setImage(new Image("symbols/searchSymbol.png"));
        ivCalendar.setImage(new Image("symbols/callender.png"));
        ivList.setImage(new Image("symbols/listView.png"));
        ivLogo.setImage(new Image("symbols/EASYDVEST.png"));
    }

    private void loadAllEvents() throws Exception {
        tvEvents.setItems(getModelsHandler().getEventModel().getObservableEvent());
    }

    /**
     * Extracts the value from a given TableView row item, using the given property name.
     */
    private void loadTableColumns() {
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        tcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tcMaxParticipants.setCellValueFactory(new PropertyValueFactory<>("maxParticipant"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void handleCreateEvent() {
        openStage("/GUI/View/CreateEvent.fxml", "");
    }

    public void handleViewEvent(Event event) {
        FXMLLoader loader = openStage("/GUI/View/EventView.fxml", "");
        EventController controller = loader.getController();
        controller.setContent(event);
    }

    public void calendarViewBtn(MouseEvent mouseEvent) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CalendarView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CalendarController controller = loader.getController();
        controller.setMainViewController(this);
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, root);
    }


    public void listViewBtn(MouseEvent mouseEvent) {
        //sets all image symbols
        loadImages();
        loadTableColumns();

        try {
            loadAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //adds the tableView to scene
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, tvEvents);
        tableViewEventHandlers();
    }
}
