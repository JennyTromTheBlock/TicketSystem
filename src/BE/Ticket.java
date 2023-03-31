package BE;

import java.util.Date;

public class Ticket {

    private int id;
    private String customerName;
    private String customerEmail;

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Ticket(int id, String customerName, String customerEmail, Event event) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.event = event;
    }

    public Ticket(String customerName, String customerEmail, Event event) {

        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.event = event;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }



}
