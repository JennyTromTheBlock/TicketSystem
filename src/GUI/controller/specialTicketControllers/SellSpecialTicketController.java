package GUI.controller.specialTicketControllers;

import BE.SpecialTicket;
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
    private Label lblTicketType, lblPriceAmount;
    @FXML
    private Button btnAddTicketAmount, btnSubtractTicket;
    @FXML
    private TextField txtfCustomerName, txtfCustomerEmail, txtfAmount;
    private SpecialTicketType selectedType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTicketTypes();
        addAmountListener();
    }

    private void addAmountListener() {
        txtfAmount.textProperty().addListener((obs, o, n) -> {
            if(!txtfAmount.getText().isEmpty() && selectedType != null) {
                int amount = Integer.parseInt(txtfAmount.getText());
                lblPriceAmount.setText("" + selectedType.getPrice() * amount);
            }
        });
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
                selectedType = specialTicketType;
                handleAmountAdd();
            });
            mbTicketType.getItems().add(type);
        }
    }

    public void handleConfirm() {
        SpecialTicket specialTicket = new SpecialTicket(selectedType, null);

    }

    public void handleCancelTicket() {
        Stage stage = (Stage) txtfCustomerName.getScene().getWindow();
        stage.close();
    }

    public void handleCreateSpecialTicket() {
        openStage("/GUI/view/specialTicketViews/CreateSpecialTicketView.fxml", "");
    }

    public void handleAmountSubtract() {
        int amount = Integer.parseInt(txtfAmount.getText());
        if(amount > 0)
            txtfAmount.setText(String.valueOf(amount - 1));
    }

    public void handleAmountAdd() {
        int amount = Integer.parseInt(txtfAmount.getText());
        txtfAmount.setText(String.valueOf(amount + 1));
    }
}
