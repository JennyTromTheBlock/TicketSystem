package GUI.controller;

import GUI.models.ModelsHandler;
import GUI.util.MainControllerHandler;
import GUI.util.ViewPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    public void displayError(Throwable throwable) {
        FXMLLoader loader = openStage(ViewPaths.ERROR_DISPLAYER, "");
        ErrorDisplay controller = loader.getController();
        controller.setContent(throwable);
    }

    public void displayLocalError(String error){
        FXMLLoader loader = openStage(ViewPaths.ERROR_DISPLAYER, "");
        ErrorDisplay controller = loader.getController();
        controller.setMessage(error);
    }

    public ModelsHandler getModelsHandler() throws Exception {
        return ModelsHandler.getInstance();
    }

    public MainControllerHandler loadMainViewHandler() throws Exception {
        return MainControllerHandler.getInstance();
    }

    /**
     * Opens a new window
     *
     * @param fxmlPath,   the path of the FXML to load
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
            displayError(new Exception("Failed to open window", e));
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(sceneTitle);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getScene().getStylesheets().add(getClass().getResource("/GUI/css/Style.css").toExternalForm());
        stage.show();

        return loader;
    }

    public Parent loadFXMLFile(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return root;
    }
}