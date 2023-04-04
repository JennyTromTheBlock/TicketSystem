package BLL.DALFacades;

import BE.SpecialTicketType;
import DAL.SpecialTicketTypes.ISpecialTicketTypeDAO;
import DAL.SpecialTicketTypes.SpecialTicketTypeSQL;

public class SpecialTicketsFacade {
    private ISpecialTicketTypeDAO specialTicketTypeDAO;

    public SpecialTicketsFacade() throws Exception {
        specialTicketTypeDAO = new SpecialTicketTypeSQL();
    }

    public SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception {
        return specialTicketTypeDAO.createSpecialTicketType(type);
    }
}
