package GUI.models;

import BE.Event;
import BE.Ticket;
import BLL.Util.PDFGenerator;
import BLL.ITicketManager;
import BLL.TicketManager;

import java.util.List;

public class TicketModel {

    private ITicketManager ticketManager;

    public TicketModel() throws Exception {
        this.ticketManager = new TicketManager();
    }

    public List<Ticket> createTicket(Ticket ticket, int amount) throws Exception {
        return ticketManager.createTicket(ticket, amount);
    }

    public Event deleteEventFrom(Event event) throws Exception {
        return ticketManager.deleteTicketsConnectedToEvent(event);
    }

}
