package BLL.DALFacades;

import BE.*;
import DAL.EventDAO;
import DAL.EventNotes.EventNoteDAO;
import DAL.EventNotes.IEventNoteDAO;
import DAL.IEventDAO;
import DAL.UsersOnEvents.IUsersOnEventsDAO;
import DAL.UsersOnEvents.UsersOnEventDAO;

import java.util.List;

public class EventFacade {
    private IEventNoteDAO eventNoteDAO;
    private IUsersOnEventsDAO usersOnEventsDAO;
    private IEventDAO eventDAO;

    public EventFacade() throws Exception {
        eventNoteDAO = new EventNoteDAO();
        usersOnEventsDAO = new UsersOnEventDAO();
        eventDAO = new EventDAO();
    }

    public void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        eventDAO.createSpecialTicketTypeOnEvent(specialTicketOnEvent);
    }

    public void removeSpecialTicketFromEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        eventDAO.removeSpecialTicketFromEvent(specialTicketOnEvent);
    }


    public List<SpecialTicketType> getAllAvailableSpecialTicketTypesOnEvent(int eventID) throws Exception {
        return eventDAO.getAllSpecialTicketTypesOnEvent(eventID);
    }

    public void assignUserToEvent(SystemUser user, Event event) throws Exception {
        usersOnEventsDAO.assignUserToEvent(user, event);
    }

    public List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception {
        return usersOnEventsDAO.getUsersAssignedToEvent(event);
    }

    public Note addNoteToEvent(Note note) throws Exception {
        return eventNoteDAO.createNote(note);
    }

    public Event deleteNotesFromEvent(Event event) throws Exception {
        return eventNoteDAO.deleteNotesOnEvent(event);
    }

    public List<Note> retrieveAllNotesOfEvent(Event event) throws Exception {
        return eventNoteDAO.getAllNotesFromEvent(event);
    }

    public List<Event> getMyEvents(SystemUser selectedUser) throws Exception {
        return usersOnEventsDAO.getEventsAssignedToUser(selectedUser);
    }

    public Event removeAllUsersAssignedToEvent(Event event) throws Exception {
        return usersOnEventsDAO.deleteEvent(event);
    }
}
