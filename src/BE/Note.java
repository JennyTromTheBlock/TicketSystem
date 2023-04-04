package BE;

import java.util.Date;

public class Note {

    private SystemUser sender;

    private Event event;

    private String message;

    private Date sendTime;

    private int messageId;

    //for loading message
    public Note(SystemUser sender, Event event, String message, Date sendTime, int messageId) {
        this.sender = sender;
        this.event = event;
        this.message = message;
        this.sendTime = sendTime;
        this.messageId = messageId;
    }
    //for creating new message
    public Note(SystemUser sender, Event event, String message, Date sendTime) {
        this.sender = sender;
        this.event = event;
        this.message = message;
        this.sendTime = sendTime;
    }

    public SystemUser getSender() {
        return sender;
    }

    public void setSender(SystemUser sender) {
        this.sender = sender;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
