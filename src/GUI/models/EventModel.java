package GUI.models;

import BE.*;
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

    private ArrayList<Event> filteredEvents;

    private EventFacade eventFacade;
    private IEventManager eventManager;
    private boolean isHistoricEventsSelected, isFutureEventsSelected, isMyEventsSelected;

    public void setHistoricEventsSelected(boolean historicEventsSelected) {
        isHistoricEventsSelected = historicEventsSelected;
    }

    public void setFutureEventsSelected(boolean futureEventsSelected) {
        isFutureEventsSelected = futureEventsSelected;
    }

    public void setMyEventsSelected(boolean myEventsSelected) {
        isMyEventsSelected = myEventsSelected;
    }
    
    public EventModel() throws Exception {
        eventFacade = new EventFacade();
        eventManager = new EventManager();
        allEvents  = retrieveAllEvents();
        shownEvents = FXCollections.observableList(new ArrayList<>());
        shownEvents.addAll(allEvents);
        filteredEvents = new ArrayList<>();
        filteredEvents.addAll(allEvents);
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

    public List<SpecialTicketType> getAvailableSpecialTicketTypesOnEvent(int eventID) throws Exception {
        Event event = shownEvents.get(indexOfEventId(eventID));
        List<SpecialTicketType> availableSpecialTicketTypes = event.getAvailableSpecialTicketTypes();


        if (availableSpecialTicketTypes.isEmpty()) {
            availableSpecialTicketTypes.addAll(eventManager.getAvailableSpecialTicketTypesOnEvent(eventID));
        }

        return availableSpecialTicketTypes;
    }

    public void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        eventManager.createSpecialTicketTypeOnEvent(specialTicketOnEvent);
    }

    public void removeSpecialTicketFromEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        eventManager.removeSpecialTicketFromEvent(specialTicketOnEvent);
    }


    public ObservableList<Event> getUpcomingEvents() throws Exception {
        if(isMyEventsSelected){
            return FXCollections.observableList(eventManager.getUpcomingEvents(filteredEvents));
        }
        return FXCollections.observableList(eventManager.getUpcomingEvents(allEvents));
    }

    public ObservableList<Event> getHistoricEvents() throws Exception {
        if(isMyEventsSelected){
            return FXCollections.observableList(eventManager.getHistoricEvents(filteredEvents));
        }
        return FXCollections.observableList(eventManager.getHistoricEvents(allEvents));
    }

    public ObservableList<Event> getMyEvents() throws Exception {
        filteredEvents = new ArrayList<>();
        filteredEvents.addAll(eventFacade.getMyEvents(ModelsHandler.getInstance().getSystemUserModel().getLoggedInSystemUser().getValue()));
        if(isHistoricEventsSelected){
            return getHistoricEvents();
        } else if (isFutureEventsSelected) {
            return getUpcomingEvents();
        }
        return FXCollections.observableList(filteredEvents);
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

    public ObservableList<Event> getEventsAssignedToUser(SystemUser user) throws Exception {
        ObservableList<Event> events = FXCollections.observableList(eventFacade.getMyEvents(user));
       return events;
    }
}
