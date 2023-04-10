package GUI.controller.specialTicketControllers;

import BE.SpecialTicketType;
import GUI.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        List<SpecialTicketType> allSpecialTickets = null;
        try {
            allSpecialTickets = getModelsHandler().getSpecialTicketModel().getSpecialTicketTypes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for(SpecialTicketType specialTicketType : allSpecialTickets) {
            MenuItem type = new MenuItem(specialTicketType.getTypeName());
            type.setOnAction(event -> {
                lblTicketType.setText(specialTicketType.getTypeName());
            });
            mbTicketType.getItems().add(type);
        }
    }

    public void handleConfirm(ActionEvent actionEvent) {
    }

    public void handleCancelTicket(ActionEvent actionEvent) {
        Stage stage = (Stage) txtfCustomerName.getScene().getWindow();
        stage.close();
    }

    public void handleCreateSpecialTicket(ActionEvent actionEvent) {
        openStage("/GUI/view/specialTicketViews/CreateSpecialTicketView.fxml", "");
    }
}
