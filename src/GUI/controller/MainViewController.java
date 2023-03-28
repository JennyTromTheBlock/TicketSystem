package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
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
import java.util.Date;
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
    private TableView<Event> tvEvents;
    @FXML
    private TableColumn<Event, String> tcTitle, tcLocation;
    @FXML
    private TableColumn<Event, Integer> tcMaxParticipants, tcPrice;
    @FXML
    private TableColumn<Event, Date> tcDate;
    @FXML
    private Label lblLocation, lblDate, lblTitle, lblPrice, lblTicketsLeft;
    @FXML
    private Button btnCreateEvent, btnSpecialTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadImages();
        loadTableColumns();

        loadAllEvents();

        tableViewEventHandlers();
    }

    /**
     * Adds event handlers to the TableView that checks for interaction with an event
     */
    private void tableViewEventHandlers() {
        eventInfoOnEnter();

        //Opens event info on double click
        int timesToClick = 2;
        eventInfoOnClick(timesToClick);

        selectedEventInfoInSidebar();
    }

    /**
     * Listens for changes in selected event (fx. when switching between events with Up/Down key)
     */
    private void selectedEventInfoInSidebar() {
        tvEvents.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (isSelectedItemInTableView(tvEvents)) {
                handleViewEventInMain(tvEvents.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Opens event info if the left mouse button is clicked X amount of times
     * @param timesToClick The times the left mouse button should be clicked
     */
    private void eventInfoOnClick(int timesToClick) {
        tvEvents.setOnMouseClicked(mouseEvent -> {

            boolean isLeftMouseClick = mouseEvent.getButton().equals(MouseButton.PRIMARY);
            boolean selectedItemExists = isSelectedItemInTableView(tvEvents);
            boolean correctClickCount = mouseEvent.getClickCount() == timesToClick;

            if(isLeftMouseClick && selectedItemExists && correctClickCount){
                handleViewEvent(tvEvents.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Opens event info if the Enter key is pressed
     */
    private void eventInfoOnEnter() {
        tvEvents.setOnKeyPressed(keyEvent -> {

            boolean keyEqualsEnter = keyEvent.getCode().equals(KeyCode.ENTER);
            boolean selectedItemExists = isSelectedItemInTableView(tvEvents);

            if(keyEqualsEnter && selectedItemExists) {
                handleViewEvent(tvEvents.getSelectionModel().getSelectedItem());
            }
        });
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Shows preview of event information in the right sidebar
     * @param event to display
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


    private void loadAllEvents() {
        try {
            tvEvents.setItems(getModelsHandler().getEventModel().getObservableEvent());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void handleCreateTicket() {
        openStage("/GUI/view/CreateTicketView.fxml", "");
    }

    public void calendarViewBtn(MouseEvent mouseEvent) {
        // TODO Can't this be moved into a method in the BaseController?  -- nope, it must set the content in the an VBox in MainView.
        // TODO Seems very plausible for us to need to to this again
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CalendarView.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CalendarController controller = loader.getController();
        controller.setMainViewController(this);
        setNodeInMainView(root);
    }

    private void setNodeInMainView(Parent root) {
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, root);
    }

    public void listViewBtn(MouseEvent mouseEvent) {
        loadImages();
        loadTableColumns();
        loadAllEvents();
        //adds the tableView to scene
        setNodeInMainView(tvEvents);
        tableViewEventHandlers();
    }


    public void handleCancleTicket(ActionEvent actionEvent) {
    }
}
