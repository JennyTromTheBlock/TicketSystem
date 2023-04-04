package DAL;

import BE.Event;
import BE.Note;

import java.util.List;

public interface IEventDAO {

    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    Note createNote(Note note) throws Exception;

    List<Note> getAllNotesFromEvent(Event event) throws Exception;
}
