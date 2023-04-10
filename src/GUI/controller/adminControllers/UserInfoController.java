package GUI.controller.adminControllers;

import BE.Event;
import BE.SystemUser;
import GUI.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController extends BaseController implements Initializable {

    public ListView listviewAssignedEvents;
    public Label lblEventSOnUsers;
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

        try {
            for (Event event: getModelsHandler().getEventModel().getEventsAssignedToUser(user)) {
                listviewAssignedEvents.setVisible(true);
                lblEventSOnUsers.setVisible(true);
                listviewAssignedEvents.getItems().add(event.getEventName());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setBtnDeleteUserVisible(Boolean b) {
        btnDeleteUser.setVisible(b);
    }
}
