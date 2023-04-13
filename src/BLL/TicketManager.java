package BLL;

import BE.Event;
import BE.Ticket;
import BLL.Util.PDFGenerator;
import DAL.ITicketDAO;
import DAL.TicketDAO;

import java.util.ArrayList;
import java.util.List;

public class TicketManager implements ITicketManager {

    private final ITicketDAO databaseAccess;
    PDFGenerator pdfGenerator;

    public TicketManager() throws Exception {
        this.databaseAccess = new TicketDAO();
        pdfGenerator = new PDFGenerator();
    }

    @Override
    public List<Ticket> createTicket(Ticket ticket, int amount) throws Exception {
        List<Ticket> newTickets = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Ticket newTicket = databaseAccess.createTicket(ticket);

            newTickets.add(newTicket);

            pdfGenerator.generateTicketForEvent(newTicket);
        }

        return newTickets;
    }

    @Override
    public Event deleteTicketsConnectedToEvent(Event event) throws Exception {
        return databaseAccess.deleteTicketsConnectedToEvent(event);
    }

}
