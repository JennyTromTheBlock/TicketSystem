package GUI.controller;

import GUI.models.ModelsHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class BaseController {

        public void displayError(Throwable throwable) {
            FXMLLoader loader = openStage("/GUI/View/ErrorDisplayView.fxml", "");
            ErrorDisplay controller = loader.getController();
            controller.setContent(throwable);
        }

    public ModelsHandler getModelsHandler() throws Exception {
        return ModelsHandler.getInstance();
    }

    /**
     * Opens a new window
     * @param fxmlPath, the path of the FXML to load
     * @param sceneTitle, the title for the scene
     * @return FXMLLoader, in case a handle to the new controller is needed
     */
    public FXMLLoader openStage(String fxmlPath, String sceneTitle) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            displayError(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(sceneTitle);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        return loader;
    }
}
