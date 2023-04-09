package DAL.EventNotes;

import BE.Event;
import BE.Note;

import java.util.List;

public interface IEventNoteDAO {
    Note createNote(Note note) throws Exception;

    List<Note> getAllNotesFromEvent(Event event) throws Exception;
}
