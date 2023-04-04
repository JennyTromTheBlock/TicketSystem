package BLL.SpecialTickets;

import BE.SpecialTicketType;

public interface ISpecialTicketManager {
    SpecialTicketType newSpecialTicketType(SpecialTicketType type) throws Exception;
}
