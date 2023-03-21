package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    @FXML
    private Label labelEventName, labelEventDate, labelEventLocation, labelDescription, labelEventPrice, labelEventAttending, labelEventTickets;
    @FXML
    private ImageView imageEventDate, imageEventLocation, imageEventPrice, imageEventTicket;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load in icons
        imageEventDate.setImage(new Image("callender.png"));
        imageEventLocation.setImage(new Image("location.png"));
        imageEventPrice.setImage(new Image("price.png"));
        imageEventTicket.setImage(new Image("ticket.png"));

    }

    public void setContent(Event event) {
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
}
