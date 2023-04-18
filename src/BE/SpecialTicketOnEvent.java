package BE;

public class SpecialTicketOnEvent {
    private int eventID;
    private String SpecialTicketType;

    public SpecialTicketOnEvent(int eventID, String specialTicketType) {
        this.eventID = eventID;
        SpecialTicketType = specialTicketType;
    }

    public int getEventID() {
        return eventID;
    }

    public String getSpecialTicketType() {
        return SpecialTicketType;
    }
}
