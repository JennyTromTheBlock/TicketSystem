package GUI.controller.eventControllers;

import BE.Event;
import GUI.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
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
    }

    public void handleClose() {
        Stage stage = (Stage) lblEventName.getScene().getWindow();
        stage.close();
    }

    public void handleEditEvent() {
        FXMLLoader loader = openStage("/GUI/View/UpdateEventView.fxml", "Edit event");
        UpdateEventController controller = loader.getController();
        controller.setContent(event);

    }
}
