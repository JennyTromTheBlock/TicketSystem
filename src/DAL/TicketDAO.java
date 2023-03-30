package DAL;

import BE.Ticket;
import DAL.Connectors.IConnector;

import java.sql.*;
import java.util.Date;

public class TicketDAO implements ITicketDAO {

    IConnector connector;


    public Ticket createTicket(Ticket ticket) throws Exception {

        Ticket newTicket = null;

        String sql = "INSERT INTO TICKET " +
                "(Email, Name, ID)" + "VALUES (?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            String customerName = ticket.getCustomerName();
            String customerEmail = ticket.getCustomerEmail();
            String eventName = ticket.getEventName();
            int price = ticket.getPrice();
            String eventLocation = ticket.getEventLocation();
            Date eventDate = ticket.getEventDate();

            statement.setString(1, customerName);
            statement.setString(2, customerEmail);
            statement.setString(3, eventName);
            statement.setInt(4, price);
            statement.setString(5, eventLocation);
            statement.setDate(6, (java.sql.Date) eventDate);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newTicket = new Ticket(id, customerName, customerEmail, eventName, price, eventLocation, eventDate);
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Failed to create ticket", e);
            }

        return newTicket;
    }


}
