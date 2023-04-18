package BLL;

import BE.Event;
import BE.SpecialTicket;
import BE.Ticket;

import java.util.List;

public interface ITicketManager {

    List<Ticket> createTicket(Ticket ticket, int amount) throws Exception;

    List<Ticket> createTicket(Ticket ticket, int amount, List<SpecialTicket> specialTicketsToAppend) throws Exception;

    Event deleteTicketsConnectedToEvent(Event event) throws Exception;
}
