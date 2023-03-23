package GUI.models;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel {

    private ObservableList<Event> allEvents;

    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new eventManager();
        allEvents = FXCollections.observableList(eventManager.getAllEvents());

    }

    public ObservableList<Event> getObservableEvent() {
        return allEvents;
    }

    public Event createEvent(Event event) throws Exception {
        Event finalEvent = eventManager.createEvent(event);
        allEvents.add(finalEvent);
        return finalEvent;
    }

    public ObservableList<Event> getAllEvents() throws Exception {
        return allEvents;
    }

    public Event updateEvent(Event eventToUpdate) throws Exception {
        Event updatedEvent = eventManager.updateEvent(eventToUpdate);

        if (updatedEvent != null) {
            //TODO Update allEvents list.
        }
        return updatedEvent;
    }
}
