package GUI.models;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import BLL.EventManager;
import BLL.IEventManager;
import GUI.BLLFacades.EventFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventModel {

    private List<Event> allEvents;

    private ObservableList<Event> shownEvents;

    private EventFacade eventFacade;
    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventFacade = new EventFacade();
        eventManager = new EventManager();
        allEvents  = retrieveAllEvents();
        shownEvents = FXCollections.observableList(new ArrayList<>());
        shownEvents.addAll(allEvents);
    }

    //should be used when getting list in controller
    public ObservableList<Event> getObservableEvents() {
        return shownEvents;
    }

    public Event createEvent(Event event, SystemUser user) throws Exception {
        Event newEvent =  eventManager.createEvent(event);

        shownEvents.add(newEvent);

        eventFacade.assignUserToEvent(user, newEvent);//assign user to the created event
        return newEvent;
    }

    public List<Event> retrieveAllEvents() throws Exception {
        allEvents =eventManager.getAllEvents();
        return allEvents;
    }

    public void updateEvent(Event eventToUpdate) throws Exception {
        Event updatedEvent = eventManager.updateEvent(eventToUpdate);

        if (updatedEvent != null) {
            int oldEventIndex = indexOfEventId(updatedEvent.getId());

            shownEvents.set(oldEventIndex, updatedEvent);
        }
    }

    public Event deleteEvent(Event eventToDelete) throws Exception {
        Event deletedEvent = eventManager.deleteEvent(eventToDelete);

        if (deletedEvent != null) {
            shownEvents.remove(eventToDelete);
        }
        return deletedEvent;
    }

    /**
     * Gets the index of an event, with a given ID.
     * @param eventId the ID of the event to find.
     * @return The index of the given event, or -1 if none were found.
     */
    private int indexOfEventId(int eventId) {
        Optional<Event> optionalEvent = shownEvents.stream().filter(event -> event.getId() == eventId).findFirst();

        if (optionalEvent.isPresent()) {
            return shownEvents.indexOf(optionalEvent.get());
        }

        return -1;
    }

    public ObservableList<Event> getUpcomingEvents() throws Exception {
        return FXCollections.observableList(eventManager.getUpcomingEvents(shownEvents));
    }

    public ObservableList<Event> getHistoricEvents() throws Exception {
        return FXCollections.observableList(eventManager.getHistoricEvents(shownEvents));
    }

    public Note addNoteToEvent(Note note) throws Exception {
        return eventFacade.addNoteToEvent(note);
    }

    public List<Note> addAllNotesToEvent(Event event) throws Exception {
        return eventFacade.retrieveAllNotesOfEvent(event);
    }
    public void assignUserToEvent(SystemUser user, Event event) throws Exception {
        eventFacade.assignUserToEvent(user, event);
    }

    public ObservableList<SystemUser> getUsersAssignedToEvent(Event event) throws Exception {
        return FXCollections.observableList(eventManager.getUsersAssignedToEvent(event));
    }

    public ObservableList<Event> getMyEvents() throws Exception {
        return FXCollections.observableList(eventFacade.getMyEvents(ModelsHandler.getInstance().getSystemUserModel().getLoggedInSystemUser().getValue()));
    }

    public Event removeUsersAssignedToEvent(Event event) throws Exception {
        eventManager.removeAllUsersFromEvent(event);
        return null;
    }

    public Event removeNotesFromEvent(Event event) throws Exception {
        return eventManager.deleteAllNotesOnEvent(event);
    }

    public Event safeDeleteEvent(Event event) throws Exception {
        removeNotesFromEvent(event);
        removeUsersAssignedToEvent(event);
        return deleteEvent(event);
    }

    public void search(String query) throws Exception {
        List<Event> searchResults = eventManager.search(allEvents, query);
        shownEvents.clear();
        shownEvents.addAll(searchResults);
    }
}
