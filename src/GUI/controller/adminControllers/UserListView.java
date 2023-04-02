package GUI.controller.adminControllers;

import BE.SystemUser;
import GUI.controller.BaseController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserListView extends BaseController implements Initializable {
    public TableColumn<SystemUser, String> tcFirstName;
    public TableColumn<SystemUser, String> tcLastName;
    public TableColumn<SystemUser, String> tcEmail;
    public TableView tvUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            tvUsers.setItems(getModelsHandler().getSystemUserModel().getAllUsers());
        }
        catch (Exception e) {
            displayError(e);

        }
    }
}
