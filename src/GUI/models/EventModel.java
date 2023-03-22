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

    public Event updateEvent(Event eventToUpdate) throws Exception {
        //TODO Remove this line and uncomment the code below,

        return eventToUpdate;
        /*Event updatedEvent = eventManager.updateEvent(eventToUpdate);

        if (updatedEvent != null) {
            //TODO update this models list of events to reflect the changes.
        }

        return updatedEvent;*/
    }
}
