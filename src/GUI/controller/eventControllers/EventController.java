package GUI.controller.eventControllers;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.controller.MessageController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
    public TextArea textfMessageInput;
    public VBox vBoxDialogPane;
    @FXML
    private Label lblEventName, lblEventDate, lblEventLocation, lblDescription, lblEventPrice, lblEventAttending, lblEventTickets;
    @FXML
    private ImageView ivDate, ivLocation, ivPrice, ivTicket;
    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadImages();
        //test
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
            ObservableList<Note> notes = getModelsHandler().getEventModel().getAllNotesFromEvent(event);
            for (Note note: notes){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/eventViews/MessageView.fxml"));
                Parent root;
                root = loader.load();
                MessageController controller = loader.getController();
                controller.setText(note);
                vBoxDialogPane.getChildren().add(root);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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


    public void handleSendMessage(ActionEvent actionEvent) {
        Note note = null;
        try {
            SystemUser user = getModelsHandler().getSystemUserModel().getLoggedInSystemUser().getValue();
            note = new Note(user, event, textfMessageInput.getText(), new Timestamp(System.currentTimeMillis()));
            getModelsHandler().getEventModel().createNote(note);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
