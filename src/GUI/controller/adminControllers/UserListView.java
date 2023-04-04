package GUI.controller.adminControllers;

import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.view.UserInfoController;
import javafx.fxml.FXMLLoader;
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
    public TableView<SystemUser> tvUsers;

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

        tableViewEventHandlers();

    }

    private void tableViewEventHandlers() {
        userInfoOnEnter();

        selectedEventInfoInSidebar();
    }

    private void userInfoOnEnter() {
    }

    private void selectedEventInfoInSidebar() {
        tvUsers.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (isSelectedItemInTableView(tvUsers)) {
                FXMLLoader loader;
                try {
                    loader = loadMainViewHandler().getController().setNodeInRightBorder("/GUI/View/UserInfoView.fxml");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                UserInfoController controller = loader.getController();
                controller.setUser(tvUsers.getSelectionModel().getSelectedItem());
            }
        });
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }
}
