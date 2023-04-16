package GUI.controller.eventControllers;

import BE.Event;
import BE.Role;
import GUI.controller.BaseController;
import GUI.controller.CreateTicketController;
import GUI.util.ConfirmDelete;
import GUI.util.ViewPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class EventInfoInMainView extends BaseController {
    @FXML
    private Label lblTitle, lblDate, lblLocation, lblPrice, lblTicketsLeft;
    @FXML
    private ImageView ivEventDate, ivEventSelected, ivEventPrice, ivEventTickets, ivBtnSellTicket, ivBtnViewInfo, ivBtnEditEvent;
    @FXML
    private VBox eventButtonContainer;
    @FXML
    private Button btnSellTicket, btnViewInfo, btnEditEvent;


    private Event event;

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

        setDeleteBtn(event);
    }

    private void setDeleteBtn(Event event) {
        try {
            if (getModelsHandler().getSystemUserModel().getLoggedInSystemUser().getValue().getRoles().contains(Role.ADMINISTRATOR)) {
                eventButtonContainer.getChildren().remove(0);

                Button deleteEvent = new Button("Delete Event");
                Image imgDeleteEvent = new Image("symbols/icon_trash-can.png");
                ImageView ivBtnDeleteEvent = new ImageView(imgDeleteEvent);
                ivBtnDeleteEvent.setFitHeight(30);
                ivBtnDeleteEvent.setFitWidth(30);

                deleteEvent.setId("btnDeleteEvent");
                deleteEvent.setGraphic(ivBtnDeleteEvent);
                deleteEvent.setPrefSize(200, 60);
                eventButtonContainer.getChildren().add(0, deleteEvent);

                handleDeleteBtn(deleteEvent, event);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleDeleteBtn(Button deleteEvent, Event event) {
        deleteEvent.setOnAction(event1 -> {
            try {
                //todo should warn if there are any tickets on event
                String header = "Are you sure you want to delete this event?";
                String content = event.getEventName();
                boolean deleteEventConfirmation = ConfirmDelete.confirm(header, content);

                if(deleteEventConfirmation) {
                    getModelsHandler().getTicketModel().deleteEventFrom(event);
                    getModelsHandler().getEventModel().safeDeleteEvent(event);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void showSymbolsForEventInSidebar() {
        ivEventDate.setImage(new Image("symbols/callender.png"));
        ivEventPrice.setImage(new Image("symbols/price.png"));
        ivEventSelected.setImage(new Image("symbols/location.png"));
        ivEventTickets.setImage(new Image("symbols/ticket.png"));
        ivBtnSellTicket.setImage(new Image(("symbols/icon_add-ticket.png")));
        ivBtnViewInfo.setImage(new Image("symbols/info.png"));
        ivBtnEditEvent.setImage(new Image("symbols/icon_edit.png"));
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
        FXMLLoader loader = openStage(ViewPaths.CREATE_TICKET_VIEW, "Sell Ticket");
        CreateTicketController controller = loader.getController();
        controller.setContent(event);
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }
}