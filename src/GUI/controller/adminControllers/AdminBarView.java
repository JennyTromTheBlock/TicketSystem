package GUI.controller.adminControllers;

import GUI.controller.BaseController;
import javafx.event.ActionEvent;

public class AdminBarView extends BaseController {
    public void handleCreateUser(ActionEvent actionEvent) {
        openStage("/GUI/view/CreateSystemUserView.fxml", "");
    }

    public void handleSeeEvents(ActionEvent actionEvent) {

    }

    public void handleSeeUsers(ActionEvent actionEvent) {
    }
}
