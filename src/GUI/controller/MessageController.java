package GUI.controller;

import BE.Note;
import javafx.scene.control.Label;

public class MessageController {

    public Label lblSender;
    public Label lblMessage;

    public void setText(Note note){
        lblSender.setText(note.getSender().getFirstName() + " "  +note.getSender().getLastName() + " kl " + note.getSendTime());
        lblMessage.setText(note.getMessage());
    }
}
