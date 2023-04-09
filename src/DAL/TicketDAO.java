package DAL;

import BE.Ticket;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;

public class TicketDAO implements ITicketDAO {


    AbstractConnector connector;


    public TicketDAO() throws Exception {
        connector = new SqlConnector();
    }
    public Ticket createTicket(Ticket ticket) throws Exception {
        Ticket newTicket = null;

        String sql = "INSERT INTO Tickets " +
                "(EventID, Email, Name)" + "VALUES (?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String customerEmail = ticket.getCustomerEmail();
            String customerName = ticket.getCustomerName();
            int eventId = ticket.getEvent().getId();

            statement.setInt(1, eventId);
            statement.setString(2, customerEmail);
            statement.setString(3, customerName);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newTicket = new Ticket(id, customerName, customerEmail, ticket.getEvent());
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to create ticket", e);
            }

        return newTicket;
    }


}
