package BLL;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import BLL.DALFacades.EventFacade;
import DAL.EventDAO;
import DAL.IEventDAO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EventManager implements IEventManager {

    private EventFacade eventFacade;
    private final IEventDAO databaseAccess;

    public EventManager() throws Exception {
        eventFacade = new EventFacade();
        databaseAccess = new EventDAO();
    }

    @Override
    public Event createEvent(Event event) throws Exception {
        return databaseAccess.createEvent(event);
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        return databaseAccess.getAllEvents();
    }

    @Override
    public Event updateEvent(Event event) throws Exception {
        return databaseAccess.updateEvent(event);
    }

    @Override
    public Event deleteEvent(Event event) throws Exception {
        return databaseAccess.deleteEvent(event);
    }

    //todo should maybe be placed in model instead.
    public List<Event> getUpcomingEvents(List<Event> events) throws Exception {
        Iterator<Event> iterator = events.iterator();
        List<Event> upcomingEvents = new ArrayList<>();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if(e.getDate().after(Date.from(Instant.now()))) {
                upcomingEvents.add(e);
            }
        }
        return upcomingEvents;
    }

    public List<Event> getHistoricEvents(List<Event> events) throws Exception {
        Iterator<Event> iterator = events.iterator();
        List<Event> historicEvents = new ArrayList<>();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if(e.getDate().before(Date.from(Instant.now()))) {
                historicEvents.add(e);
            }
        }
        return historicEvents;
    }

    @Override
    public void assignUserToEvent(SystemUser user, Event event) throws Exception {
        eventFacade.assignUserToEvent(user, event);
    }

    @Override
    public Event removeAllUsersFromEvent(Event event) throws Exception {
        return eventFacade.removeAllUsersAssignedToEvent(event);
    }

    @Override
    public List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception {
        return eventFacade.getUsersAssignedToEvent(event);
    }

    @Override
    public Note addNoteToEvent(Note note) throws Exception {
        return eventFacade.addNoteToEvent(note);
    }

    @Override
    public Event deleteAllNotesOnEvent(Event event) throws Exception {
        return eventFacade.deleteNotesFromEvent(event);
    }

    @Override
    public List<Note> retrieveAllNotesOfEvent(Event event) throws Exception {
        return eventFacade.retrieveAllNotesOfEvent(event);
    }
}
