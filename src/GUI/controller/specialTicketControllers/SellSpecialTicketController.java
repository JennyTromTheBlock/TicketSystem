package GUI.controller.specialTicketControllers;

import BE.SpecialTicketType;
import GUI.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SellSpecialTicketController extends BaseController implements Initializable {
    @FXML
    private MenuButton mbTicketType;
    @FXML
    private Label lblTicketType, lblTicketAmount, lblPriceAmount;
    @FXML
    private Button btnAddTicketAmount, btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTicketTypes();
    }

    private void loadTicketTypes() {
        List<SpecialTicketType> allSpecialTickets;
    }

    public void handleConfirm(ActionEvent actionEvent) {
    }

    public void handleCancelTicket(ActionEvent actionEvent) {
    }

    public void handleCreateSpecialTicket(ActionEvent actionEvent) {
        openStage("/GUI/view/specialTicketViews/CreateSpecialTicketView.fxml", "");
    }
}
