package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {
    @FXML
    private Label labelEventName, labelEventDate, labelEventLocation, labelDescription, labelEventPrice, labelEventAttending, labelEventTickets;
    @FXML
    private ImageView imageEventDate, imageEventLocation, imageEventPrice, imageEventTicket;

    private Event event;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load in icons
        imageEventDate.setImage(new Image("symbols/callender.png"));
        imageEventLocation.setImage(new Image("symbols/location.png"));
        imageEventPrice.setImage(new Image("symbols/price.png"));
        imageEventTicket.setImage(new Image("symbols/ticket.png"));

    }

    public void setContent(Event event) {
        this.event = event;
        labelEventName.setText(event.getEventName());
        labelDescription.setText(event.getDescription());
        labelEventDate.setText(String.valueOf(event.getDate()));
        labelEventLocation.setText(event.getLocation());
        labelEventPrice.setText(event.getPrice() + " DKK");
        labelEventTickets.setText("? / " + event.getMaxParticipant());
    }

    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage) labelEventName.getScene().getWindow();
        stage.close();
    }

    public void handleEditEvent(ActionEvent actionEvent) throws Exception {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/UpdateEventView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Edit event");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        UpdateEventController controller = loader.getController();
        controller.setContent(event);

    }


}
