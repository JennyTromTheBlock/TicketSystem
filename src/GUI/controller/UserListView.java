package GUI.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserListView extends BaseController {
    public TableColumn tcFirstName;
    public TableColumn tcLastName;
    public TableColumn tcEmail;
    public TableView tvUsers;

    public UserListView(){
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        try {
            tvUsers.setItems(getModelsHandler().getSystemUserModel().getAllUsers());
        }
        catch (Exception e) {
            displayError(e);
        }
    }
      
}
