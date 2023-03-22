package GUI.models;

import BE.Event;
import BLL.IEventManager;
import BLL.eventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EventModel {

    private ObservableList<Event> allEvents;

    private IEventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new eventManager();
    }

    public ObservableList<Event> getObservableEvent() {
        return allEvents;
    }

    public Event createEvent(Event event) throws Exception {
        return eventManager.createEvent(event);
    }

    public ObservableList<Event> getAllEvents() throws Exception {

        ObservableList<Event> list = FXCollections.observableList(eventManager.getAllEvents());
        return list;
    }
}
