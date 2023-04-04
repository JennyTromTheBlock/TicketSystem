package GUI.controller;

import BE.Event;
import GUI.controller.eventControllers.EventController;
import GUI.controller.eventControllers.EventListController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private VBox eventButtonContainer;
    @FXML
    private MenuButton mbFilter;
    @FXML
    private CheckMenuItem cmiUpcoming, cmiHistoric;
    @FXML
    private HBox topBar, hBoxSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private BorderPane background;
    @FXML
    private ImageView ivList, ivCalendar, ivSearchBtn, ivLogo, ivEventDate, ivEventSelected, ivEventPrice, ivEventTickets;
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
    private Button btnCreateEvent, btnSpecialTicket, btnEditEvent, btnViewInfo, btnSellTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadImages();

        listViewBtn();//sets the listView With All Events In

        checkMenuListener();

        String path = "/GUI/view/eventViews/EventInfoInMainView.fxml";
        setNodeInRightBorder(path);
    }

    private void loadImages() {
        ivSearchBtn.setImage(new Image("symbols/searchSymbol.png"));
        ivCalendar.setImage(new Image("symbols/callender.png"));
        ivList.setImage(new Image("symbols/listView.png"));
        ivLogo.setImage(new Image("symbols/EASYDVEST.png"));
    }

    public void handleCreateEvent() {
        openStage("/GUI/view/eventViews/CreateEvent.fxml", "");
    }

    public void handleViewEvent(Event event) {
        FXMLLoader loader = openStage("/GUI/view/eventViews/EventView.fxml", "");
        EventController controller = loader.getController();
        controller.setContent(event);
    }

    public void calendarViewBtn(MouseEvent mouseEvent) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/calendarViews/CalendarView.fxml"));
        Parent root;
        try {
            root = loader.load();
            setNodeInMainView(root);
        } catch (IOException e) {
            displayError(e);
        }
    }

    public void setNodeInMainView(Parent root) {
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, root);
    }

    public void listViewBtn() {
        try {
            loadListView(getModelsHandler().getEventModel().getObservableEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FXMLLoader loadListView(ObservableList<Event> listOfEvents){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/eventViews/EventListView.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, root);
        EventListController e = loader.getController();
        e.loadEvents(listOfEvents);
        return loader;
    }

    private void checkMenuListener() {
        cmiUpcoming.selectedProperty().addListener((obs, o, n) -> {
            if(cmiUpcoming.isSelected()) {
                cmiHistoric.setSelected(false);
                showUpcomingOnly();
            } else {
                listViewBtn();//sets the listView With All Events In
            }
        });

        cmiHistoric.selectedProperty().addListener((obs, o, n) -> {
            if(cmiHistoric.isSelected()) {
                cmiUpcoming.setSelected(false);
                showHistoricOnly();
            } else {
                listViewBtn();//sets the listView With All Events In
            }
        });
    }

    public void showUpcomingOnly() {
        try {
            loadListView(getModelsHandler().getEventModel().getUpcomingEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showHistoricOnly() {
        try {
            loadListView(getModelsHandler().getEventModel().getHistoricEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleOpenCreateTicketView(ActionEvent actionEvent) {
        openStage("/GUI/view/ticketsViews/CreateTicketView.fxml", "");
    }


    public void setAdminContent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/adminView/AdminBarView.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentArea.getChildren().remove(3);
        contentArea.getChildren().add(2, root);
    }

    public FXMLLoader setNodeInRightBorder(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        background.setRight(root);
        return loader;
    }
}