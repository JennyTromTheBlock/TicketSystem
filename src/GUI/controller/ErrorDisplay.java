package GUI.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorDisplay extends BaseController {


    public Label lblErrorDisplay;

    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage)  lblErrorDisplay.getScene().getWindow();
        stage.close();
    }

    public void handleOpenBing(ActionEvent actionEvent) {
        String url = "https://cornhub.website/";
        try {
            java.awt.Desktop.getDesktop().browse( java.net.URI.create(url));
        } catch (IOException e) {
            displayError(e);
        }
    }

    public void setContent(Throwable throwable) {
        String text = "Error:  ";
        if(!throwable.equals(null)){
            text += throwable.getCause().getLocalizedMessage() + "\n";
            text += throwable;
        }
        lblErrorDisplay.setText(text);
    }

    public void setMessage(String message){
        lblErrorDisplay.setText(message);
    }
}
