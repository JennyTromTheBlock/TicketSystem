package GUI.BLLFacades;

import BE.Event;
import BE.SpecialTicketType;
import BLL.SpecialTickets.ISpecialTicketManager;
import BLL.SpecialTickets.SpecialTicketManager;

import java.util.List;

public class SpecialTicketsFacade {
    private ISpecialTicketManager specialTicketManager;

    public SpecialTicketsFacade() throws Exception {
        specialTicketManager = new SpecialTicketManager();
    }

    public SpecialTicketType addNewSpecialTicketType(SpecialTicketType newType) throws Exception {
        return specialTicketManager.newSpecialTicketType(newType);
    }

    public List<SpecialTicketType> availableSpecialTicketTypes() throws Exception {
        return specialTicketManager.availableSpecialTicketTypes();
    }

    public List<SpecialTicketType> retrieveSpecialTicketTypesOnEvent(Event event) throws Exception {
        return specialTicketManager.retrieveSpecialTicketTypesOnEvent(event);
    }
}
