package GUI.models;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class EventModel {

    private ObservableList<Event> allEvents;

    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new eventManager();
        allEvents = FXCollections.observableList(retrieveAllEvents());
    }

    //should be used when getting list in controller
    public ObservableList<Event> getObservableEvents() {
        return allEvents;
    }

    public Event createEvent(Event event) throws Exception {
        Event newEvent =  eventManager.createEvent(event);

        allEvents.add(newEvent);

        return newEvent;
    }

    private List<Event> retrieveAllEvents() throws Exception {
        return eventManager.getAllEvents();
    }

    public void updateEvent(Event eventToUpdate) throws Exception {
        Event updatedEvent = eventManager.updateEvent(eventToUpdate);

        if (updatedEvent != null) {
            int oldEventIndex = indexOfEventId(updatedEvent.getId());

            allEvents.set(oldEventIndex, updatedEvent);
        }
    }

    /**
     * Gets the index of an event, with a given ID.
     * @param eventId the ID of the event to find.
     * @return The index of the given event, or -1 if none were found.
     */
    private int indexOfEventId(int eventId) {
        Optional<Event> optionalEvent = allEvents.stream().filter(event -> event.getId() == eventId).findFirst();

        if (optionalEvent.isPresent()) {
            return allEvents.indexOf(optionalEvent.get());
        }

        return -1;
    }
}
