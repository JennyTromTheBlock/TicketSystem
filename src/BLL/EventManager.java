package BLL;

import BE.Event;
import DAL.EventDAO;
import DAL.IEventDAO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EventManager implements IEventManager {

    private final IEventDAO databaseAccess;


    public EventManager() throws Exception {
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
}
