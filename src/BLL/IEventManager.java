package BLL;

import BE.Event;

import java.util.List;

public interface IEventManager {


    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    List<Event> getUpcomingEvents() throws Exception;

    List<Event> getHistoricEvents() throws Exception;
}
