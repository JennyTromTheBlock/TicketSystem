package GUI.models;

import BE.Ticket;
import BLL.ITicketManager;

public class TicketModel {

    private ITicketManager ticketManager;

    public TicketModel() {
        this.ticketManager = ticketManager;
    }


    public Ticket createTicket(Ticket ticket) throws Exception {
        Ticket newTicket = ticketManager.createTicket(ticket);

        return newTicket;
    }

}
