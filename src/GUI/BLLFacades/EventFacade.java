package GUI.BLLFacades;

import BE.Event;
import BE.Note;
import BLL.EventManager;
import BLL.IEventManager;

import java.util.List;

public class EventFacade {
    private IEventManager eventManager;

    public EventFacade() throws Exception {
        eventManager = new EventManager();
    }

    public Note addNoteToEvent(Note note) throws Exception {
        return eventManager.addNoteToEvent(note);
    }

    public List<Note> retrieveAllNotesOfEvent(Event event) throws Exception {
        return eventManager.retrieveAllNotesOfEvent(event);
    }
}
