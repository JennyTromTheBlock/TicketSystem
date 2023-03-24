package GUI.models;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventModel {

    private ObservableList<Event> allEvents;

    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new eventManager();
        allEvents = FXCollections.observableList(getAllEvents());
    }

    public ObservableList<Event> getObservableEvent() {
        return allEvents;
    }

    public Event createEvent(Event event) throws Exception {
        Event newEvent =  eventManager.createEvent(event);

        allEvents.add(newEvent);

        return newEvent;
    }

    public List<Event> getAllEvents() throws Exception {
        return eventManager.getAllEvents();
    }

    public Event updateEvent(Event eventToUpdate) throws Exception {
        Event updatedEvent = eventManager.updateEvent(eventToUpdate);

        if (updatedEvent != null) {
            allEvents.clear();
            allEvents.addAll(eventManager.getAllEvents());
        }
        return updatedEvent;
    }
}
