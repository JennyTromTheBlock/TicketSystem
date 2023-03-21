package BLL;

import BE.Event;

import java.util.List;

public interface IEventManager {

    List<Event> getAllEvents() throws Exception;

}
