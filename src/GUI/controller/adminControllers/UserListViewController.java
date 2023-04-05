package GUI.controller.adminControllers;

import BE.SystemUser;
import GUI.controller.BaseController;
import GUI.util.ViewPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class UserListViewController extends BaseController implements Initializable {
    @FXML
    private TableColumn<SystemUser, String> tcFirstName;
    @FXML
    private TableColumn<SystemUser, String> tcLastName;
    @FXML
    private TableColumn<SystemUser, String> tcEmail;
    @FXML
    private TableView<SystemUser> tvUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();

        try {
            tvUsers.setItems(getModelsHandler().getSystemUserModel().getAllUsers());
        }
        catch (Exception e) {
            displayError(e);
        }

        tableViewEventHandlers();
    }

    private void initializeTableColumns() {
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void tableViewEventHandlers() {
        selectedEventInfoInSidebar();
    }

    private void selectedEventInfoInSidebar() {
        tvUsers.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (isSelectedItemInTableView(tvUsers)) {
                FXMLLoader loader;

                try {
                    loader = loadMainViewHandler().getController().setNodeInRightBorder(ViewPaths.USER_INFO_VIEW);

                    UserInfoController controller = loader.getController();
                    controller.setUser(tvUsers.getSelectionModel().getSelectedItem());
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    private boolean isSelectedItemInTableView(TableView<?> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }
}
