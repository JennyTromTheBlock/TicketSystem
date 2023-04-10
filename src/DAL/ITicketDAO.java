package DAL;

import BE.Event;
import BE.Ticket;

public interface ITicketDAO {

    Ticket createTicket(Ticket ticket) throws Exception;

    Event deleteTicketsConnectedToEvent(Event event) throws Exception;

}
