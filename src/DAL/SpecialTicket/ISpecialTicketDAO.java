package DAL.SpecialTicket;

import BE.Event;
import BE.SpecialTicket;

import java.util.List;

public interface ISpecialTicketDAO {
    SpecialTicket createSpecialTicket(SpecialTicket specialTicket) throws Exception;
}
