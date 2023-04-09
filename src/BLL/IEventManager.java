package BLL;

import BE.Event;
import BE.SystemUser;
import BE.Note;

import java.util.List;

public interface IEventManager {


    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    List<Event> getUpcomingEvents(List<Event> events) throws Exception;

    List<Event> getHistoricEvents(List<Event> events) throws Exception;

    void assignUserToEvent(SystemUser user, Event event) throws Exception;

    List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception;
    Note addNoteToEvent(Note note) throws Exception;

    List<Note> retrieveAllNotesOfEvent(Event event) throws Exception;

}
