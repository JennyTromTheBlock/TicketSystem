package BE;

import java.util.Date;

public class Note {

    private int id;

    private SystemUser sender;

    private Event event;

    private String message;

    private Date sendTime;

    //for loading message
    public Note(int id, SystemUser sender, Event event, String message, Date sendTime) {
        this.id = id;
        this.sender = sender;
        this.event = event;
        this.message = message;
        this.sendTime = sendTime;
    }
    //for creating new message
    public Note(SystemUser sender, Event event, String message, Date sendTime) {
        this.sender = sender;
        this.event = event;
        this.message = message;
        this.sendTime = sendTime;
    }

    public Note(int id, Note note) {
        this(id, note.getSender(), note.getEvent(), note.getMessage(), note.getSendTime());
    }

    public SystemUser getSender() {
        return sender;
    }

    public Event getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public int getId() {
        return id;
    }
}
