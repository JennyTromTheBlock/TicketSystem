package DAL;

import BE.Event;
import BE.Note;
import BE.SpecialTicketOnEvent;
import BE.SpecialTicketType;

import java.util.List;

public interface IEventDAO {

    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    Event deleteEvent(Event event) throws Exception;

    void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception;

    void removeSpecialTicketFromEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception;

    List<SpecialTicketType> getAllSpecialTicketTypesOnEvent(int eventID) throws Exception;
}
