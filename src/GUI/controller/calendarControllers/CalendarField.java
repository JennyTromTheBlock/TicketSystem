package GUI.controller.calendarControllers;

import BE.Event;
import GUI.controller.BaseController;
import GUI.controller.eventControllers.EventInfoInMainView;
import GUI.controller.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.util.List;

public class CalendarField extends BaseController {
    public Label header;
    public Label lblFirstEvent;
    public Label lblSecondEvent;
    public Label lblMoreEvents;

    private MainViewController mainController;


    public void setDate(String header){
        this.header.setText(header);
    }

    public void setEvent(List<Event> events){
        try {
            mainController = loadMainViewHandler().getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; events.size() > i; i++ ){
            Event event = events.get(i);
            if(i == 0){
                lblFirstEvent.setVisible(true);
                lblFirstEvent.setText(createEventString(event));
                lblFirstEvent.setOnMouseClicked(mouseEvent -> {
                    String path = "/GUI/view/eventViews/EventInfoInMainView.fxml";
                    FXMLLoader loader =mainController.setNodeInRightBorder(path);
                    EventInfoInMainView controller = loader.getController();
                    controller.setEvent(event);
                    if(mouseEvent.getClickCount()==2) {
                        mainController.handleViewEvent(event);
                    }
                });
            } else if (i == 1) {
                lblSecondEvent.setVisible(true);
                lblSecondEvent.setText(createEventString(event));
                lblSecondEvent.setOnMouseClicked(mouseEvent -> {
                    String path = "/GUI/view/eventViews/EventInfoInMainView.fxml";
                    FXMLLoader loader =mainController.setNodeInRightBorder(path);
                    EventInfoInMainView controller = loader.getController();
                    controller.setEvent(event);
                    if(mouseEvent.getClickCount()==2) {
                        mainController.handleViewEvent(event);
                    }
                });
            }

            else {
                lblMoreEvents.setText("more events..");
                break;
            }
        }
    }


    private String createEventString(Event event){
        String result;

        if(event.getEventName().length() > 8) result = event.getEventName().substring(0, 8);
        else result = event.getEventName();
        result +=  " " +  event.getDate().getHours() + ":" + event.getDate().getMinutes();

        return result;
    }
}
