package GUI.controller.eventControllers;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.controller.MessageController;
import GUI.util.SymbolPaths;
import GUI.util.ViewPaths;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
    public Button handleAddUserBtn;

    public ListView<String> listviewUsersOnEvent;
    public ListView<String> listviewAllUsers;
    public Button btnDoneAssigningUsers;
    public VBox vBoxDialogPane;

    @FXML
    private TextArea textfMessageInput;
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
        ivDate.setImage(new Image(SymbolPaths.CALENDAR));
        ivLocation.setImage(new Image(SymbolPaths.LOCATION));
        ivPrice.setImage(new Image(SymbolPaths.PRICE));
        ivTicket.setImage(new Image(SymbolPaths.TICKET));
    }

    public void setContent(Event event) {
        this.event = event;
        lblEventName.setText(event.getEventName());
        lblDescription.setText(event.getDescription());
        lblEventDate.setText(String.valueOf(event.getDate()));
        lblEventLocation.setText(event.getLocation());
        lblEventPrice.setText(event.getPrice() + " DKK");
        lblEventTickets.setText("? / " + event.getMaxParticipant());

        getEventNotes();
    }

    private void getEventNotes() {
        try {
            List<Note> eventNotes = getModelsHandler().getEventModel().addAllNotesToEvent(event);

            for (Note note : eventNotes) {
                addNoteToList(note);
            }
        }
        catch (Exception e) {
            displayError(e);
        }
    }

    private void addNoteToList(Note note) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.MESSAGE_VIEW));
            Parent root;
            root = loader.load();
            MessageController controller = loader.getController();
            controller.setText(note);
            vBoxDialogPane.getChildren().add(root);
        }
        catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }


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
        FXMLLoader loader = openStage(ViewPaths.UPDATE_EVENT_VIEW, "Edit event");
        UpdateEventController controller = loader.getController();
        controller.setContent(event);
    }


    public void handleSendMessage(ActionEvent actionEvent) {
        try {
            SystemUser user = getModelsHandler().getSystemUserModel().getLoggedInSystemUser().getValue();

            Note note = new Note(user, event, textfMessageInput.getText(), new Timestamp(System.currentTimeMillis()));

            getModelsHandler().getEventModel().addNoteToEvent(note);

            addNoteToList(note);
        }
        catch (Exception e) {
            displayError(e);
        }

    }

    public void handleDoneAssigningUsers(ActionEvent actionEvent) {
        listviewAllUsers.getItems().clear();
        listviewAllUsers.setPrefHeight(0);
        btnDoneAssigningUsers.setVisible(false);
    }

    public void handleAddUsersBtn(ActionEvent actionEvent) {
        try {
            btnDoneAssigningUsers.setVisible(true);
            //todo should not include the coordinators already assigned to event
            listviewAllUsers.setPrefHeight(150);
            listviewAllUsers.getItems().clear();

            ObservableList<SystemUser> users =  getModelsHandler().getSystemUserModel().getAllUsers();

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
                if(event1.getClickCount() == 2) {
                    SystemUser user = users.get(listviewAllUsers.getSelectionModel().getSelectedIndex());
                    try {
                        getModelsHandler().getEventModel().assignUserToEvent(user, event);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    listviewAllUsers.getItems().remove(listviewAllUsers.getSelectionModel().getSelectedIndex());
                    listviewUsersOnEvent.getItems().add(listviewAllUsers.getSelectionModel().getSelectedItem());
                }
            }

        });
    }
}
