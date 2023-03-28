package GUI.controller;

import BE.Event;
import javafx.scene.control.Label;

import java.util.List;

public class CalendarField{
    public Label header;
    public Label lblFirstEvent;
    public Label lblSecondEvent;
    public Label lblMoreEvents;

    Event first;
    Event second;

    public void setDate(String header){
        this.header.setText(header);
    }

    public void setEvent(List<Event> events){
        for(int i = 0; events.size() > i; i++ ){
            if(i == 0){
                lblFirstEvent.setText(createEventString(events.get(i)));

            } else if (i == 1) lblSecondEvent.setText(createEventString(events.get(i)));

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
