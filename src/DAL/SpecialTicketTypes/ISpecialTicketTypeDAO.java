package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;

public interface ISpecialTicketTypeDAO {
    SpecialTicketType createSpecialTicketType(SpecialTicketType type) throws Exception;
}
