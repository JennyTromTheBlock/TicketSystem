package GUI.controller.adminControllers;

import GUI.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AdminBarView extends BaseController {
    public void handleCreateUser(ActionEvent actionEvent) {
        openStage("/GUI/view/CreateSystemUserView.fxml", "");
    }

    public void handleSeeEvents(ActionEvent actionEvent) throws Exception {
        loadMainViewHandler().getController().loadListView(getModelsHandler().getEventModel().getObservableEvents());
    }

    public void handleSeeUsers(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/UserListView.fxml"));
        Parent root;

        try {
            root = loader.load();
            loadMainViewHandler().getController().setNodeInMainView(root);
        } catch (Exception e) {
            displayError(e);
        }

    }
}
