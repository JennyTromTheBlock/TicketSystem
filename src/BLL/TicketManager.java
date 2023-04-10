package BLL;

import BE.Event;
import BE.Ticket;
import BLL.Util.PDFGenerator;
import DAL.ITicketDAO;
import DAL.TicketDAO;

public class TicketManager implements ITicketManager {

    private final ITicketDAO databaseAccess;
    PDFGenerator pdfGenerator;

    public TicketManager() throws Exception {
        this.databaseAccess = new TicketDAO();
        pdfGenerator = new PDFGenerator();
    }

    @Override
    public Ticket createTicket(Ticket ticket) throws Exception {
        pdfGenerator.generateTicketForEvent(ticket);
        return databaseAccess.createTicket(ticket);
    }

    @Override
    public Event deleteTicketsConnectedToEvent(Event event) throws Exception {
        return databaseAccess.deleteTicketsConnectedToEvent(event);
    }

}
