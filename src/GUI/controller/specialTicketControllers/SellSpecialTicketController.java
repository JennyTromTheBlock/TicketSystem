package GUI.controller.specialTicketControllers;

import BE.SpecialTicket;
import BE.SpecialTicketType;
import BE.Ticket;
import GUI.controller.BaseController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private ObservableList<SpecialTicketType> allSpecialTickets;

    public SellSpecialTicketController() {

        try {
            allSpecialTickets = getModelsHandler().getSpecialTicketModel().getSpecialTicketTypes();
        } catch (Exception e) {
            displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSpecialTicketTypesListener();
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

    private void setSpecialTicketTypesListener() {
        if (allSpecialTickets != null) {

            allSpecialTickets.addListener((ListChangeListener<SpecialTicketType>) type -> loadTicketTypes());
        }
    }

    private void loadTicketTypes() {
        mbTicketType.getItems().clear();

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

        try {
            openPdfSpecialTicket(getModelsHandler().getSpecialTicketModel().createSpecialTicket(specialTicket));
        } catch (Exception e) {
            displayError(e);
        }

    }

    private void openPdfSpecialTicket(SpecialTicket specialTicket) {
        if (!Desktop.isDesktopSupported()) return;

        try {

            if (!specialTicket.getPdfSpecialTicketPath().isEmpty()) {

                File newTicketPdfFile = new File(specialTicket.getPdfSpecialTicketPath());

                Desktop.getDesktop().open(newTicketPdfFile);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            displayError(new Exception("Failed to open the newly created special ticket", e));
        }
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
