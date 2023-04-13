package BLL;

import BE.Event;
import BE.Ticket;

import java.util.List;

public interface ITicketManager {

    List<Ticket> createTicket(Ticket ticket, int amount) throws Exception;

    Event deleteTicketsConnectedToEvent(Event event) throws Exception;
}
