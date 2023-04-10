package BLL;

import BE.Event;
import BE.Ticket;

public interface ITicketManager {

    Ticket createTicket(Ticket ticket) throws Exception;

    Event deleteTicketsConnectedToEvent(Event event) throws Exception;
}
