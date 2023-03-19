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

    private int maxParticipant;
    private int price;

    //maybe also available tickets
}
