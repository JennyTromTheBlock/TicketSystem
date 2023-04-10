package BLL.DALFacades;

import BE.Event;
import BE.SpecialTicketType;
import DAL.SpecialTicketTypes.ISpecialTicketTypeDAO;
import DAL.SpecialTicketTypes.SpecialTicketTypeSQL;

import java.util.List;

public class SpecialTicketsFacade {
    private ISpecialTicketTypeDAO specialTicketTypeDAO;

    public SpecialTicketsFacade() throws Exception {
        specialTicketTypeDAO = new SpecialTicketTypeSQL();
    }

    public List<SpecialTicketType> retrieveSpecialTicketTypesOnEvent(Event event) throws Exception {
        return specialTicketTypeDAO.getSpecialTicketTypesOnEvent(event);
    }

    public SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception {
        return specialTicketTypeDAO.createSpecialTicketType(type);
    }

    public List<SpecialTicketType> availableSpecialTickets() throws Exception {
        return specialTicketTypeDAO.getAllSpecialTicketTypes();
    }
}
