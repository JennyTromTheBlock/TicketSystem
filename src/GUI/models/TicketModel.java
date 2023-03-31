package GUI.models;

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

}
