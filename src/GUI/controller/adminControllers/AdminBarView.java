package GUI.controller.adminControllers;

import GUI.controller.BaseController;
import GUI.util.SymbolPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminBarView extends BaseController implements Initializable {
    @FXML
    private Button btnCreateUser, btnSeeEvents, btnSeeUsers;
    @FXML
    private ImageView ivBtnCreateUser, ivBtnSeeEvents, ivBtnSeeUsers;

    public void handleCreateUser(ActionEvent actionEvent) {
        openStage("/GUI/view/adminView/CreateSystemUserView.fxml", "");
    }

    public void handleSeeEvents(ActionEvent actionEvent) throws Exception {
        loadMainViewHandler().getController().loadListView(getModelsHandler().getEventModel().getObservableEvents());
        loadMainViewHandler().getController().setNodeInRightBorder("/GUI/view/eventViews/EventInfoInMainView.fxml");
    }

    public void handleSeeUsers(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/adminView/UserListView.fxml"));
        Parent root;

        try {
            root = loader.load();
            loadMainViewHandler().getController().setNodeInMainView(root);
            loadMainViewHandler().getController().setNodeInRightBorder("/GUI/view/adminView/UserInfoView.fxml");
        } catch (Exception e) {
            displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ivBtnCreateUser.setImage(new Image(SymbolPaths.PLUS));
        ivBtnSeeEvents.setImage(new Image(SymbolPaths.LIST));
        ivBtnSeeUsers.setImage(new Image(SymbolPaths.USER));
    }
}
