package BLL;

import BE.*;

import java.util.List;

public interface IEventManager {


    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    Event deleteEvent(Event event) throws Exception;

    List<Event> getUpcomingEvents(List<Event> events) throws Exception;

    List<Event> getHistoricEvents(List<Event> events) throws Exception;

    void assignUserToEvent(SystemUser user, Event event) throws Exception;

    Event removeAllUsersFromEvent(Event event) throws Exception;

    List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception;
    Note addNoteToEvent(Note note) throws Exception;

    List<Note> retrieveAllNotesOfEvent(Event event) throws Exception;
    Event deleteAllNotesOnEvent(Event event) throws Exception;

    List<Event> getMyEvents(SystemUser user) throws Exception;

    List<Event> search(List<Event> searchBase, String query);

    List<SpecialTicketType> getAvailableSpecialTicketTypesOnEvent(int eventID) throws Exception;
    void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception;
    void removeSpecialTicketFromEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception;
}
