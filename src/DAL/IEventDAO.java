package DAL;

import BE.Event;

import java.util.List;

public interface IEventDAO {

    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    void updateEvent(Event event) throws Exception;
}
