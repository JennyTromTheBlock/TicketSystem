package BLL;

import BE.Ticket;
import DAL.ITicketDAO;

public class TicketManager implements ITicketManager {

    private final ITicketDAO databaseAccess;

    public TicketManager(ITicketDAO databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public Ticket createTicket(Ticket ticket) throws Exception {
        return databaseAccess.createTicket(ticket);
    }

}
