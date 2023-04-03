package DAL;

import BE.Ticket;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ITicketDAO {

    Ticket createTicket(Ticket ticket) throws Exception;

}
