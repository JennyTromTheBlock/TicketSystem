package GUI.controller.eventControllers;

import BE.Event;
import GUI.controller.BaseController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EventListController extends BaseController implements Initializable {
    @FXML
    private TableView<Event> tvEvent;
    @FXML
    private TableColumn<Event, String> tcTitle, tcLocation;
    @FXML
    private TableColumn<Event, Integer> tcMaxParticipants, tcPrice;
    @FXML
    private TableColumn<Event, Date> tcDate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        tcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tcMaxParticipants.setCellValueFactory(new PropertyValueFactory<>("maxParticipant"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            tvEvent.setItems(getModelsHandler().getEventModel().getObservableEvents());
        }
        catch (Exception e) {
            displayError(e);
        }

        tableViewEventHandlers();

    }

    private void tableViewEventHandlers() {
        eventInfoOnEnter();

        //Opens event info on double click
        int timesToClick = 2;
        eventInfoOnClick(timesToClick);


        selectedEventInfoInSidebar();
    }

    /**
     * Opens event info if the Enter key is pressed
     */
    private void eventInfoOnEnter() {
        tvEvent.setOnKeyPressed(keyEvent -> {

            boolean keyEqualsEnter = keyEvent.getCode().equals(KeyCode.ENTER);
            boolean selectedItemExists = isSelectedItemInTableView(tvEvent);

            if(keyEqualsEnter && selectedItemExists) {
                viewSelectedInMain();
            }
        });
    }
    private void viewSelectedInMain(){
        try {
            FXMLLoader loader =loadMainViewHandler().getController().setNodeInRightBorder("/GUI/view/eventViews/EventInfoInMainView.fxml");
            EventInfoInMainView controller = loader.getController();
            controller.setEvent(tvEvent.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Listens for changes in selected event (fx. when switching between events with Up/Down key)
     */
    private void selectedEventInfoInSidebar() {
        tvEvent.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (isSelectedItemInTableView(tvEvent)) {
                FXMLLoader loader;
                try {
                    loader = loadMainViewHandler().getController().setNodeInRightBorder("/GUI/view/eventViews/EventInfoInMainView.fxml");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EventInfoInMainView controller = loader.getController();
                controller.setEvent(tvEvent.getSelectionModel().getSelectedItem());
            }
        });
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Opens event info if the left mouse button is clicked X amount of times
     * @param timesToClick The times the left mouse button should be clicked
     */
    private void eventInfoOnClick(int timesToClick) {
        tvEvent.setOnMouseClicked(mouseEvent -> {
            boolean isLeftMouseClick = mouseEvent.getButton().equals(MouseButton.PRIMARY);
            boolean selectedItemExists = isSelectedItemInTableView(tvEvent);
            boolean correctClickCount = mouseEvent.getClickCount() == timesToClick;

            if(isLeftMouseClick && selectedItemExists && correctClickCount){
                try {
                    loadMainViewHandler().getController().handleViewEvent(tvEvent.getSelectionModel().getSelectedItem());
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    public void loadEvents(ObservableList<Event> eventList) {
        try {
            tvEvent.setItems(eventList);
        }
        catch (Exception e) {
            displayError(e);
        }
    }
}
