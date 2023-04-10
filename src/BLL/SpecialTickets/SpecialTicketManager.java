package BLL.SpecialTickets;

import BE.Event;
import BE.SpecialTicketType;
import BLL.DALFacades.SpecialTicketsFacade;

import java.util.List;

public class SpecialTicketManager implements ISpecialTicketManager {
    private SpecialTicketsFacade specialTicketsFacade;

    public SpecialTicketManager() throws Exception {
        specialTicketsFacade = new SpecialTicketsFacade();
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
}
