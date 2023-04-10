package GUI.models;

import BE.Event;
import BE.Ticket;
import BLL.ITicketManager;
import BLL.TicketManager;

public class TicketModel {

    private ITicketManager ticketManager;

    public TicketModel() throws Exception {
        this.ticketManager = new TicketManager();
    }


    public Ticket createTicket(Ticket ticket) throws Exception {
        Ticket newTicket = ticketManager.createTicket(ticket);

        return newTicket;
    }

    public Event deleteEventFrom(Event event) throws Exception {
        return ticketManager.deleteTicketsConnectedToEvent(event);
    }

}
