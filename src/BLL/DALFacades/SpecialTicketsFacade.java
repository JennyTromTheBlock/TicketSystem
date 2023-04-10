package BLL.DALFacades;

import BE.Event;
import BE.SpecialTicket;
import BE.SpecialTicketType;
import DAL.SpecialTicket.ISpecialTicketDAO;
import DAL.SpecialTicket.SpecialTicketDAO;
import DAL.SpecialTicketTypes.ISpecialTicketTypeDAO;
import DAL.SpecialTicketTypes.SpecialTicketTypeSQL;

import java.util.List;

public class SpecialTicketsFacade {
    private ISpecialTicketTypeDAO specialTicketTypeDAO;
    private ISpecialTicketDAO specialTicketDAO;

    public SpecialTicketsFacade() throws Exception {
        specialTicketTypeDAO = new SpecialTicketTypeSQL();
        specialTicketDAO= new SpecialTicketDAO();
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

    public SpecialTicket createSpecialTicket(SpecialTicket specialTicket) throws Exception{
        return specialTicketDAO.createSpecialTicket(specialTicket);
    }
}
