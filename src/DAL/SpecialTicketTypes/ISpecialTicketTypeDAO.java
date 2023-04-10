package DAL.SpecialTicketTypes;

import BE.Event;
import BE.SpecialTicketType;

import java.util.List;

public interface ISpecialTicketTypeDAO {
    SpecialTicketType createSpecialTicketType(SpecialTicketType type) throws Exception;

    List<SpecialTicketType> getAllSpecialTicketTypes() throws Exception;

    List<SpecialTicketType> getSpecialTicketTypesOnEvent(Event event) throws Exception;
}
