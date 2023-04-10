package BLL.SpecialTickets;

import BE.Event;
import BE.SpecialTicketType;

import java.util.List;

public interface ISpecialTicketManager {
    SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception;

    List<SpecialTicketType> availableSpecialTicketTypes() throws Exception;

    List<SpecialTicketType> retrieveSpecialTicketTypesOnEvent(Event event) throws Exception;
}
