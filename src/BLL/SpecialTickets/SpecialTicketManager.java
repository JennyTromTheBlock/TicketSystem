package BLL.SpecialTickets;

import BE.Event;
import BE.SpecialTicket;
import BE.SpecialTicketType;
import BLL.DALFacades.SpecialTicketsFacade;
import BLL.Util.PDFGenerator;

import java.util.List;

public class SpecialTicketManager implements ISpecialTicketManager {
    private SpecialTicketsFacade specialTicketsFacade;
    private PDFGenerator pdfGenerator;

    public SpecialTicketManager() throws Exception {
        specialTicketsFacade = new SpecialTicketsFacade();
        pdfGenerator = new PDFGenerator();

    }

    @Override
    public SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception {
        return specialTicketsFacade.newSpecialTicketType(type);
    }

    @Override
    public List<SpecialTicketType> availableSpecialTicketTypes() throws Exception {
        return specialTicketsFacade.availableSpecialTickets();
    }

    @Override
    public List<SpecialTicketType> retrieveSpecialTicketTypesOnEvent(Event event) throws Exception {
        return specialTicketsFacade.retrieveSpecialTicketTypesOnEvent(event);
    }

    @Override
    public SpecialTicket createSpecialTicket(SpecialTicket specialTicket) throws Exception {
        pdfGenerator.generateSpecialTicketForEvent(specialTicket.getType());
        return specialTicketsFacade.createSpecialTicket(specialTicket);
    }
}