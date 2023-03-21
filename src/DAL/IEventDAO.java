package DAL;

import BE.Event;

import java.util.List;

public interface IEventDAO {


    public Event createEvent(Event event) throws Exception;

    public List<Event> getAllEvents() throws Exception;
}
