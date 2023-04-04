package BLL;

import BE.Event;
import BE.Note;

import java.util.List;

public interface IEventManager {


    Event createEvent(Event event) throws Exception;

    List<Event> getAllEvents() throws Exception;

    Event updateEvent(Event event) throws Exception;

    List<Event> getUpcomingEvents(List<Event> events) throws Exception;

    List<Event> getHistoricEvents(List<Event> events) throws Exception;

    Note createNote(Note note) throws Exception;

    List<Note> getNotesFromEvent(Event event) throws Exception;

}
