package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;

import java.util.List;

public interface ISpecialTicketTypeDAO {
    SpecialTicketType createSpecialTicketType(SpecialTicketType type) throws Exception;

    List<SpecialTicketType> getSpecialTicketTypes() throws Exception;
}
