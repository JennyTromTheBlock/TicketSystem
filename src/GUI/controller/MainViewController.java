package GUI.controller;

import BE.Event;
import GUI.controller.eventControllers.EventController;
import GUI.controller.eventControllers.EventListController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    @FXML
    private Label lblLoggedInUser;
    @FXML
    private MenuButton mbFilter;
    @FXML
    private CheckMenuItem cmiUpcoming, cmiHistoric, cmiMyEvents;
    @FXML
    private HBox topBar, hBoxSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private BorderPane background;
    @FXML
    private ImageView ivList, ivCalendar, ivSearchBtn, ivLogo, ivBtnCreateEvent, ivBtnSpecialTicket;
    @FXML
    private VBox contentArea, eventButtonContainer;
    @FXML
    private Button btnCreateEvent, btnSpecialTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadImages();

        listViewBtn();//sets the listView With All Events In

        checkMenuListener();

        setNodeInRightBorder("/GUI/view/eventViews/EventInfoInMainView.fxml");

        searchListener();
    }

    private void loadImages() {
        ivSearchBtn.setImage(new Image("symbols/searchSymbol.png"));
        ivCalendar.setImage(new Image("symbols/callender.png"));
        ivList.setImage(new Image("symbols/listView.png"));
        ivLogo.setImage(new Image("symbols/EASYDVEST.png"));
        try {
            lblLoggedInUser.setText(getModelsHandler().getSystemUserModel().getLoggedInSystemUser().getValue().getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ivBtnCreateEvent.setImage(new Image("symbols/icon_plus.png"));
        ivBtnSpecialTicket.setImage(new Image("symbols/icon_special-ticket.png"));
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
        Parent root = loadFXMLFile("/GUI/view/calendarViews/CalendarView.fxml");
        setNodeInMainView(root);
    }

    public void setNodeInMainView(Parent root) {
        contentArea.getChildren().remove(0);
        contentArea.getChildren().add(0, root);
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
        setNodeInMainView(root);
        EventListController controller = loader.getController();
        controller.loadEvents(listOfEvents);
        return loader;
    }

    private void checkMenuListener() {
        cmiUpcoming.selectedProperty().addListener((obs, o, n) -> {
            if(cmiUpcoming.isSelected()) {
                    try {
                        showMyEventsOnly();
                        getModelsHandler().getEventModel().setFutureEventsSelected(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                cmiHistoric.setSelected(false);
                showUpcomingOnly();
            } else {
                listViewBtn();//sets the listView With All Events In
                try {
                    getModelsHandler().getEventModel().setFutureEventsSelected(false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cmiHistoric.selectedProperty().addListener((obs, o, n) -> {
            if(cmiHistoric.isSelected()) {
                try {
                    showMyEventsOnly();
                    getModelsHandler().getEventModel().setHistoricEventsSelected(true);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                cmiUpcoming.setSelected(false);
                showHistoricOnly();
            } else {
                listViewBtn();//sets the listView With All Events I
                try {
                    getModelsHandler().getEventModel().setHistoricEventsSelected(false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        cmiMyEvents.selectedProperty().addListener((obs, o, n) -> {
            if(cmiMyEvents.isSelected()) {
                try {
                    getModelsHandler().getEventModel().setMyEventsSelected(true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                showMyEventsOnly();
            } else {
                try {
                    getModelsHandler().getEventModel().setMyEventsSelected(false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(cmiHistoric.isSelected()){
                        showHistoricOnly();

                }else if(cmiUpcoming.isSelected()){
                        showUpcomingOnly();

                }else {
                    listViewBtn();
                }

            }
        });
    }

    private void showMyEventsOnly() {
        try {
            loadListView(getModelsHandler().getEventModel().getMyEvents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void setAdminContent(){
        Parent root = loadFXMLFile("/GUI/view/adminView/AdminBarView.fxml");
        contentArea.getChildren().remove(1);
        contentArea.getChildren().add(1, root);
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

    public void handleSpecialTicket(ActionEvent actionEvent) {
        openStage("/GUI/view/specialTicketViews/SpecialTicketView.fxml", "");
    }

    public void handleSearch(MouseEvent mouseEvent) {
        try {
            getModelsHandler().getEventModel().search(txtSearch.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void searchListener() {
        txtSearch.setOnKeyTyped(event -> {
            try {
                getModelsHandler().getEventModel().search(txtSearch.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}