package BLL;

import BE.Ticket;
import DAL.ITicketDAO;
import DAL.TicketDAO;

public class TicketManager implements ITicketManager {

    private final ITicketDAO databaseAccess;

    public TicketManager() throws Exception {
        this.databaseAccess = new TicketDAO();
    }

    @Override
    public Ticket createTicket(Ticket ticket) throws Exception {
        return databaseAccess.createTicket(ticket);
    }

}
