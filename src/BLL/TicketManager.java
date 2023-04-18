package BLL;

import BE.Event;
import BE.SpecialTicket;
import BE.Ticket;
import BLL.Util.PDFGenerator;
import DAL.ITicketDAO;
import DAL.SpecialTicket.ISpecialTicketDAO;
import DAL.SpecialTicket.SpecialTicketDAO;
import DAL.TicketDAO;

import java.util.ArrayList;
import java.util.List;

public class TicketManager implements ITicketManager {

    private final ITicketDAO databaseAccess;
    private ISpecialTicketDAO specialTicketDAO;
    private PDFGenerator pdfGenerator;

    public TicketManager() throws Exception {
        this.databaseAccess = new TicketDAO();
        specialTicketDAO = new SpecialTicketDAO();
        pdfGenerator = new PDFGenerator();
    }

    @Override
    public List<Ticket> createTicket(Ticket ticket, int amount) throws Exception {
        List<Ticket> newTickets = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Ticket newTicket = databaseAccess.createTicket(ticket);

            newTickets.add(newTicket);

            newTicket.setPdfTicketPath(pdfGenerator.generateTicketForEvent(newTicket));
        }

        return newTickets;
    }

    @Override
    public List<Ticket> createTicket(Ticket ticket, int amount, List<SpecialTicket> specialTicketsToAppend) throws Exception {
        List<Ticket> newTickets = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            Ticket newTicket = databaseAccess.createTicket(ticket);
            List<SpecialTicket> newSpecialTicketsToAppend = new ArrayList<>();

            for (SpecialTicket specialTicket : specialTicketsToAppend) {

                newSpecialTicketsToAppend.add(specialTicketDAO.createSpecialTicket(specialTicket));
            }

            newTickets.add(newTicket);

            newTicket.setPdfTicketPath(pdfGenerator.generateTicketForEvent(newTicket, newSpecialTicketsToAppend));
        }

        return newTickets;
    }

    @Override
    public Event deleteTicketsConnectedToEvent(Event event) throws Exception {
        return databaseAccess.deleteTicketsConnectedToEvent(event);
    }

}
