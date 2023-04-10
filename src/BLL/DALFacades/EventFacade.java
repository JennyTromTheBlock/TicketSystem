package BLL.DALFacades;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import DAL.EventNotes.EventNoteDAO;
import DAL.EventNotes.IEventNoteDAO;
import DAL.UsersOnEvents.IUsersOnEventsDAO;
import DAL.UsersOnEvents.UsersOnEventDAO;

import java.util.List;

public class EventFacade {
    private IEventNoteDAO eventNoteDAO;
    private IUsersOnEventsDAO usersOnEventsDAO;

    public EventFacade() throws Exception {
        eventNoteDAO = new EventNoteDAO();
        usersOnEventsDAO = new UsersOnEventDAO();
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

    public Event removeAllUsersAssignedToEvent(Event event) throws Exception {
        return usersOnEventsDAO.deleteEvent(event);
    }
}
