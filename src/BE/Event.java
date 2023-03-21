package BE;

import java.util.Date;
import java.util.List;

public class Event {

    private String eventName;
    private String description;
    private String location;

    //made it a list as i thought an event should be able to contain multiple events
    private List<String> notes;

    private Date date;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int maxParticipant;
    private int price;
    //maybe also available tickets

    public Event(String eventName, String description, String location, Date date, int maxParticipant, int price){
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.maxParticipant = maxParticipant;
        this.price = price;
    }
}
