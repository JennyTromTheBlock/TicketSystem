package GUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControllerHandler {

    private static MainControllerHandler handler;

    private MainViewController mainController;

    private MainControllerHandler(){
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/GUI/css/Style.css").toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        mainController = loader.getController();
    }

    public static MainControllerHandler getInstance(){
        if (handler == null) {
            handler = new MainControllerHandler();
        }
        return handler;
    }
    public MainViewController getController() {
        return mainController;
    }
}
