package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EventInfoInMainView implements Initializable {
    public Label lblTitle;
    public ImageView ivEventDate;
    public Label lblDate;
    public ImageView ivEventSelected;
    public Label lblLocation;
    public ImageView ivEventPrice;
    public Label lblPrice;
    public ImageView ivEventTickets;
    public Label lblTicketsLeft;
    public VBox eventButtonContainer;
    public Button btnSellTicket;
    public Button btnViewInfo;
    public Button btnEditEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEvent(Event event) {
        //sets all text fields
        lblTitle.setText(event.getEventName());
        lblDate.setText(String.valueOf(event.getDate()));
        lblLocation.setText(event.getLocation());
        lblPrice.setText(event.getPrice() + " DKK");
        lblTicketsLeft.setText(event.getMaxParticipant() + " tickets available"); //TODO subtract sold tickets from max part.

        //sets buttons and symbols visible
        setEventInfoBtnsVisible();
        showSymbolsForEventInSidebar();
    }

    private void showSymbolsForEventInSidebar() {
        ivEventDate.setImage(new Image("symbols/callender.png"));
        ivEventPrice.setImage(new Image("symbols/price.png"));
        ivEventSelected.setImage(new Image("symbols/location.png"));
        ivEventTickets.setImage(new Image("symbols/ticket.png"));
    }

    private void setEventInfoBtnsVisible() {
        btnEditEvent.setVisible(true);
        btnViewInfo.setVisible(true);
        btnSellTicket.setVisible(true);
        eventButtonContainer.setVisible(true);
    }



    public void handleViewInfo(ActionEvent actionEvent) {
    }

    public void handleEditEvent(ActionEvent actionEvent) {
    }

    public void handleSellTicket(ActionEvent actionEvent) {

    }
}


