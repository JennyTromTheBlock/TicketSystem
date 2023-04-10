package GUI.BLLFacades;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import BLL.EventManager;
import BLL.IEventManager;

import java.util.List;

public class EventFacade {
    private IEventManager eventManager;

    public EventFacade() throws Exception {
        eventManager = new EventManager();
    }

    public void assignUserToEvent(SystemUser user, Event event) throws Exception {
        eventManager.assignUserToEvent(user, event);
    }

    public List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception {
        return eventManager.getUsersAssignedToEvent(event);
    }

    public Note addNoteToEvent(Note note) throws Exception {
        return eventManager.addNoteToEvent(note);
    }

    public List<Note> retrieveAllNotesOfEvent(Event event) throws Exception {
        return eventManager.retrieveAllNotesOfEvent(event);
    }
}
