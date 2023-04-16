package GUI.controller.specialTicketControllers;

import BE.SpecialTicketType;
import GUI.controller.BaseController;
import GUI.util.SymbolPaths;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateSpecialTicketTypeController extends BaseController implements Initializable {
    @FXML
    private TextField txtfTypeName;
    @FXML
    private TextField txtfPrice;
    @FXML
    private Button btnAdd;
    @FXML
    private ImageView ivBtnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivBtnAdd.setImage(new Image(SymbolPaths.PLUS));
        disableAdd();

        setInputChangeValidListener();
    }

    @FXML
    private void handleOnAddNewType(ActionEvent actionEvent) {
        if (isInputValid()) {
            String typeName = txtfTypeName.getText();
            int price = Integer.parseInt(txtfPrice.getText());

            SpecialTicketType newType = new SpecialTicketType(typeName, price);


            try {
                if (!getModelsHandler().getSpecialTicketModel().typeExistsAlready(newType)) {

                    getModelsHandler().getSpecialTicketModel().addNewSpecialTicketType(newType);

                    close();
                }
                else {
                    //TODO Display a message saying that the name already exists.
                }
            }
            catch (Exception e) {
                displayError(e);
            }
        }
    }

    private boolean isInputValid() {
        return isTypeNameValid() && isPriceValid();
    }

    private boolean isTypeNameValid() {
        return !txtfTypeName.getText().isEmpty() &&
                !txtfTypeName.getText().isBlank();
    }

    private boolean isPriceValid() {
        int price;
        try {
            price = Integer.parseInt(txtfPrice.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }
        return price >= 0;
    }

    private void disableAdd() {
        btnAdd.setDisable(true);
    }

    private void enableAdd() {
        btnAdd.setDisable(false);
    }

    private void setInputChangeValidListener() {
        ChangeListener<String> inputChangeValidListener = (observable, oldValue, newValue) -> {
            if (isInputValid()) {
                enableAdd();
            }
            else {
                disableAdd();
            }
        };

        txtfTypeName.textProperty().addListener(inputChangeValidListener);
        txtfPrice.textProperty().addListener(inputChangeValidListener);
    }

    private void close() {
        Stage stage = (Stage) txtfTypeName.getScene().getWindow();
        stage.close();
    }
}
