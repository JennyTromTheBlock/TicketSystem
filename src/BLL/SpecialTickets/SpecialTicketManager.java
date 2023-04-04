package BLL.SpecialTickets;

import BE.SpecialTicketType;
import BLL.DALFacades.SpecialTicketsFacade;

public class SpecialTicketManager implements ISpecialTicketManager {
    private SpecialTicketsFacade specialTicketsFacade;

    public SpecialTicketManager() throws Exception {
        specialTicketsFacade = new SpecialTicketsFacade();
    }

    @Override
    public SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception {
        return specialTicketsFacade.newSpecialTicketType(type);
    }
}
