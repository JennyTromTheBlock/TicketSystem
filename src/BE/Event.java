package BE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private String eventName;
    private String description;
    private String location;
    private Date date;
    private int maxParticipant;
    private int price;
    private List<SpecialTicketType> availableSpecialTicketTypes;


    public Event(String eventName, String description, String location, Date date, int maxParticipant, int price){
        setEventFields(eventName, description, location, date, maxParticipant, price);
    }

    public Event(int id, String eventName, String description, String location, Date date, int maxParticipant, int price){
        this.id = id;
        setEventFields(
                eventName,
                description,
                location,
                date,
                maxParticipant,
                price);
    }

    public Event(int id, Event eventToCopy) {
        this.id = id;
        setEventFields(
                eventToCopy.getEventName(),
                eventToCopy.getDescription(),
                eventToCopy.getLocation(),
                eventToCopy.getDate(),
                eventToCopy.getMaxParticipant(),
                eventToCopy.getPrice());
    }

    private void setEventFields(String eventName, String description, String location, Date date, int maxParticipant, int price) {
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.maxParticipant = maxParticipant;
        this.price = price;
        this.availableSpecialTicketTypes = new ArrayList<>();
    }

    public SpecialTicketType getTypeFromName(String typeName) {

        for (SpecialTicketType type : availableSpecialTicketTypes) {

            if (type.getTypeName().equalsIgnoreCase(typeName)) {

                return type;
            }
        }

        return null;
    }

    public List<SpecialTicketType> getAvailableSpecialTicketTypes() {
        return availableSpecialTicketTypes;
    }

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

    public int getId() {
        return id;
    }
}
