package BLL.DALFacades;

import BE.Event;
import BE.Note;
import DAL.EventNotes.EventNoteDAO;
import DAL.EventNotes.IEventNoteDAO;

import java.util.List;

public class EventFacade {
    private IEventNoteDAO eventNoteDAO;

    public EventFacade() throws Exception {
        eventNoteDAO = new EventNoteDAO();
    }

    public Note addNoteToEvent(Note note) throws Exception {
        return eventNoteDAO.createNote(note);
    }

    public List<Note> retrieveAllNotesOfEvent(Event event) throws Exception {
        return eventNoteDAO.getAllNotesFromEvent(event);
    }
}
