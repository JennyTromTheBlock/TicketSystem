package GUI.models;

import BE.Event;
import BE.SpecialTicket;
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

    public List<Ticket> createTicket(Ticket ticket, int amount, List<SpecialTicket> specialTicketsToAppend) throws Exception {
        return ticketManager.createTicket(ticket, amount, specialTicketsToAppend);
    }

    public Event deleteEventFrom(Event event) throws Exception {
        return ticketManager.deleteTicketsConnectedToEvent(event);
    }

}
