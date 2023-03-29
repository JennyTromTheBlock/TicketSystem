package BLL;

import BE.Event;
import DAL.EventDAO;
import DAL.IEventDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class eventManager implements IEventManager {

    private final IEventDAO databaseAccess;


    public eventManager() throws Exception {
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


    public List<Event> getUpcomingEvents() throws Exception {
        Iterator<Event> iterator = getAllEvents().iterator();
        List<Event> upcomingEvents = new ArrayList<>();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if(e.getDate().after(Date.from(Instant.now()))) {
                upcomingEvents.add(e);
            }
        }
        return upcomingEvents;
    }
}
