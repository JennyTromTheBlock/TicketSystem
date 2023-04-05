package GUI.controller.adminControllers;

import BE.SystemUser;
import GUI.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController extends BaseController implements Initializable {

    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Button btnDeleteUser;

    private SystemUser selectedUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBtnDeleteUserVisible(false);
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
        try {
            getModelsHandler().getSystemUserModel().deleteSystemUser(selectedUser);
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void setUser(SystemUser user){
        lblName.setText(user.getFirstName() + " " + user.getLastName());
        lblEmail.setText(user.getEmail());
        setBtnDeleteUserVisible(true);
        selectedUser = user;
    }

    public void setBtnDeleteUserVisible(Boolean b) {
        btnDeleteUser.setVisible(b);
    }
}
