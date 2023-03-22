package GUI.models;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;

public class EventModel {

    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new eventManager();
    }


    public Event createEvent(Event event) throws Exception {
        return eventManager.createEvent(event);
    }
}
