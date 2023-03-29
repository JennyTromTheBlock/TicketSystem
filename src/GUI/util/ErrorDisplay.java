package GUI.util;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class ErrorDisplay {

    public static void displayError(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().removeAll();
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(throwable.getLocalizedMessage());
        alert.showAndWait();
    }
}
