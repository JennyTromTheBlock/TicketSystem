package BLL;

import BE.Ticket;

public interface ITicketManager {

    Ticket createTicket(Ticket ticket) throws Exception;
}
