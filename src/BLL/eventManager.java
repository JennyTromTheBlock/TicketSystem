package BLL;

import BE.Event;
import DAL.EventDAO;
import DAL.IEventDAO;

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


}
