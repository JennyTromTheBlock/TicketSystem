package GUI.controller.eventControllers;

import BE.Event;
import GUI.controller.BaseController;
import GUI.controller.CreateTicketController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EventInfoInMainView extends BaseController implements Initializable {
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

    private Event event;

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
        this.event = event;

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
        FXMLLoader loader = openStage("/GUI/view/eventViews/EventView.fxml", "");
        EventController controller = loader.getController();
        controller.setContent(event);
    }

    public void handleEditEvent(ActionEvent actionEvent) {
        FXMLLoader loader= openStage("/GUI/view/eventViews/UpdateEventView.fxml", "update Event");
        UpdateEventController updateEventController = loader.getController();
        updateEventController.setContent(event);
    }

    public void handleSellTicket(ActionEvent actionEvent) {
            FXMLLoader loader = openStage("/GUI/view/ticketsViews/CreateTicketView.fxml", "update Ticket");
            CreateTicketController controller = loader.getController();
            controller.setContent(event);
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }
}

