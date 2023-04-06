package GUI.controller.eventControllers;

import BE.Event;
import BE.SystemUser;
import GUI.controller.BaseController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
    public Button handleAddUserBtn;

    public ListView<String> listviewUsersOnEvent;
    public ListView<String> listviewAllUsers;
    @FXML
    private Label lblEventName, lblEventDate, lblEventLocation, lblDescription, lblEventPrice, lblEventAttending, lblEventTickets;
    @FXML
    private ImageView ivDate, ivLocation, ivPrice, ivTicket;
    private Event event;

    private ObservableList<SystemUser> assignedUsers = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadImages();

    }

    private void loadImages() {
        ivDate.setImage(new Image("symbols/callender.png"));
        ivLocation.setImage(new Image("symbols/location.png"));
        ivPrice.setImage(new Image("symbols/price.png"));
        ivTicket.setImage(new Image("symbols/ticket.png"));
    }

    public void setContent(Event event) {
        this.event = event;
        lblEventName.setText(event.getEventName());
        lblDescription.setText(event.getDescription());
        lblEventDate.setText(String.valueOf(event.getDate()));
        lblEventLocation.setText(event.getLocation());
        lblEventPrice.setText(event.getPrice() + " DKK");
        lblEventTickets.setText("? / " + event.getMaxParticipant());

        try {
            assignedUsers = getModelsHandler().getEventModel().usersAssignedToEvent(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (SystemUser user: assignedUsers) {
            listviewUsersOnEvent.getItems().add(user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
        }
    }

    public void handleClose() {
        Stage stage = (Stage) lblEventName.getScene().getWindow();
        stage.close();
    }

    public void handleEditEvent() {
        FXMLLoader loader = openStage("/GUI/view/eventViews/UpdateEventView.fxml", "Edit event");
        UpdateEventController controller = loader.getController();
        controller.setContent(event);

    }

    public void handleDoneAssigningUsers(ActionEvent actionEvent) {
        listviewAllUsers.setPrefHeight(0);
    }

    public void handleAddUsersBtn(ActionEvent actionEvent) {
        try {
            //todo should not include the coordinators already assigned to event
            listviewAllUsers.setPrefHeight(150);
            listviewAllUsers.getItems().clear();
            ObservableList<SystemUser> users =  getModelsHandler().getSystemUserModel().getAllUsers();
            users.stream().findFirst();

            for (SystemUser user:users) {
                    listviewAllUsers.getItems().add(user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
            }

            addListenerForListAllUsers(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addListenerForListAllUsers(ObservableList<SystemUser> users) {
        listviewAllUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event1) {
                SystemUser user = users.get(listviewAllUsers.getSelectionModel().getSelectedIndex());
                try {
                    getModelsHandler().getEventModel().assignUserToEvent(user, event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                listviewAllUsers.getItems().remove(listviewAllUsers.getSelectionModel().getSelectedIndex());
            }
        });
    }
}
