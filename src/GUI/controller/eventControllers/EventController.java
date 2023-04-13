package GUI.controller.eventControllers;

import BE.Event;
import BE.Note;
import BE.SpecialTicketType;
import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.controller.MessageController;
import GUI.util.SymbolPaths;
import GUI.util.ViewPaths;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
    public Button handleAddUserBtn;

    public ListView<String> listviewUsersOnEvent;
    public ListView<String> listviewAllUsers;
    public Button btnDoneAssigningUsers;
    public VBox vBoxDialogPane;
    public ListView listviewAllSpecialTickets;
    public ListView<String> listviewSelectedSpecialTickets;

    @FXML
    private TextArea textfMessageInput;
    @FXML
    private Label lblEventName, lblEventDate, lblEventLocation, lblDescription, lblEventPrice, lblEventAttending, lblEventTickets;
    @FXML
    private ImageView ivDate, ivLocation, ivPrice, ivTicket;
    private Event event;

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
        getUsersAssignedToEvent();
        getAllSpecialTicketTypes();
        loadSelectedSpecialTickets();
    }

    private void selectedSpecialTicketHandler() {

        listviewSelectedSpecialTickets.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() == 2) {
                int selectedUserIndex = listviewSelectedSpecialTickets.getSelectionModel().getSelectedIndex();
                //todo should remove special ticket from event
                try {
                    listviewSelectedSpecialTickets.getItems().remove(listviewSelectedSpecialTickets.getSelectionModel().getSelectedIndex());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void loadSelectedSpecialTickets() {
        try {
            List<SpecialTicketType> availableSpecialTicketTypes = getModelsHandler()
                    .getEventModel()
                    .getAvailableSpecialTicketTypesOnEvent(event.getId());

            for (SpecialTicketType type : availableSpecialTicketTypes) {
                listviewSelectedSpecialTickets.getItems().add(type.getTypeName());
            }
        } catch (Exception e) {
            displayError(e);
        }
        selectedSpecialTicketHandler();
    }

    private void getAllSpecialTicketTypes() {
        try {
            ObservableList<SpecialTicketType> specialTickets = getModelsHandler().getSpecialTicketModel().getSpecialTicketTypes();
            for (SpecialTicketType tickets: specialTickets) {
                listviewAllSpecialTickets.getItems().add(tickets.getTypeName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        allSpecialTicketsHandler();
    }

    private void allSpecialTicketsHandler() {
        listviewAllSpecialTickets.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() == 2) {
                int selectedUserIndex = listviewAllSpecialTickets.getSelectionModel().getSelectedIndex();
                try {
                    //todo should add special ticket to event in dao
                    listviewAllSpecialTickets.getItems().remove(selectedUserIndex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void getUsersAssignedToEvent() {
        try {
            List<SystemUser> usersAssignedToEvent = getModelsHandler().getEventModel().getUsersAssignedToEvent(event);

            for (SystemUser user: usersAssignedToEvent) {
                listviewUsersOnEvent.getItems().add(convertSystemUserToListViewItem(user));
            }
        }
        catch (Exception e) {
            displayError(e);
        }
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
    }

    public void handleClose() {
        Stage stage = (Stage) lblEventName.getScene().getWindow();
        stage.close();
    }

    public void handleEditEvent() {
        if(isUserAssignedToEvent()){
            FXMLLoader loader = openStage(ViewPaths.UPDATE_EVENT_VIEW, "Edit event");
            UpdateEventController controller = loader.getController();
            controller.setContent(event);
        }
    }


    public void handleSendMessage(ActionEvent actionEvent) {
        if(noteInputIsValid()){
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
        displayLocalError("the note is empty");
    }

    private boolean noteInputIsValid() {
        return !textfMessageInput.getText().isEmpty() &&
                !textfMessageInput.getText().isBlank();
    }

    public void handleDoneAssigningUsers(ActionEvent actionEvent) {
        listviewAllUsers.getItems().clear();
        listviewAllUsers.setPrefHeight(0);
        btnDoneAssigningUsers.setVisible(false);
    }

    public void handleAddUsersBtn() {
        try {
            btnDoneAssigningUsers.setVisible(true);
            listviewAllUsers.setPrefHeight(150);
            listviewAllUsers.getItems().clear();

            ArrayList<SystemUser> users =  new ArrayList<>(getModelsHandler().getSystemUserModel().getAllUsers());
            ObservableList<SystemUser> finalUsers =  getModelsHandler().getSystemUserModel().getAllUsers();

            for (SystemUser user : users) {
                for (SystemUser assignedUser : getModelsHandler().getEventModel().getUsersAssignedToEvent(event)) {
                    if (assignedUser.getEmail().equals(user.getEmail())) {
                        finalUsers.removeAll(user);
                    }
                }
            }


            for (SystemUser user : finalUsers) {
                listviewAllUsers.getItems().add(convertSystemUserToListViewItem(user));
                addListenerForListAllUsers(finalUsers);
            }

        }
        catch (Exception e) {
            displayError(e);
        }
    }

    private void addListenerForListAllUsers(ObservableList<SystemUser> users) {
        listviewAllUsers.setOnMouseClicked(event1 -> {

            try {
               if (event1.getClickCount() == 2 && isUserAssignedToEvent()) {
                            try {
                                SystemUser selectedUser = users.get(listviewAllUsers.getSelectionModel().getSelectedIndex());
                                getModelsHandler().getEventModel().assignUserToEvent(selectedUser, event);
                                listviewUsersOnEvent.getItems().add(convertSystemUserToListViewItem(selectedUser));

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }
    private boolean isUserAssignedToEvent(){

        try {
            for (SystemUser assignedUsers: getModelsHandler().getEventModel().getUsersAssignedToEvent(event)){
                if(assignedUsers.getEmail().contains(getModelsHandler().getSystemUserModel().getLoggedInSystemUser().getValue().getEmail())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        displayLocalError("you must be assigned to event");
        return false;
    }

    private String convertSystemUserToListViewItem(SystemUser user) {
        return user.getFirstName() + " " + user.getLastName() + " " + user.getEmail();
    }

    public void handleUseAsTemplate(ActionEvent actionEvent) {
        FXMLLoader loader =openStage("/GUI/view/eventViews/CreateEvent.fxml", "template");
        CreateEventController controller = loader.getController();
        controller.setInfoFromTemplate(event);
    }


}
