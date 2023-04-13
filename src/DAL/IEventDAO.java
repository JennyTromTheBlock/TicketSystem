package DAL;

import BE.Event;
import BE.Note;
import BE.SpecialTicketOnEvent;

import java.util.List;

public interface IEventDAO {

    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    Event deleteEvent(Event event) throws Exception;

    void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception;
}
