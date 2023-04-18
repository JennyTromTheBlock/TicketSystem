package BE;

public class SpecialTicket {
    private int id;
    private SpecialTicketType type;

    // Specifies what event this ticket is assigned.
    // If it isn't assigned to any, then this value is null.
    private Event assignedEvent;
    private String pdfSpecialTicketPath;

    public SpecialTicket(int id, SpecialTicketType type, Event assignedEvent) {
        this.id = id;
        this.type = type;
        this.assignedEvent = assignedEvent;
    }

    public SpecialTicket(SpecialTicketType type, Event assignedEvent) {
        this.type = type;
        this.assignedEvent = assignedEvent;
    }

    public int getId() {
        return id;
    }

    public SpecialTicketType getType() {
        return type;
    }

    public Event getAssignedEvent() {
        return assignedEvent;
    }

    public String getPdfSpecialTicketPath() {
        return pdfSpecialTicketPath;
    }

    public void setPdfSpecialTicketPath(String pdfSpecialTicketPath) {
        this.pdfSpecialTicketPath = pdfSpecialTicketPath;
    }
}
