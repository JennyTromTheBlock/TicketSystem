package BLL.SpecialTickets;

import BE.SpecialTicketType;

import java.util.List;

public interface ISpecialTicketManager {
    SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception;

    List<SpecialTicketType> availableSpecialTicketTypes() throws Exception;
}
