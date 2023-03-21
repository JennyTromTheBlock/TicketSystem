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
    public List<Event> getAllEvents() throws Exception {
        return databaseAccess.getAllEvents();
    }
}
