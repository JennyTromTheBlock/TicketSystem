package DAL;

import BE.Ticket;
import DAL.Connectors.IConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;
import java.util.Date;

public class TicketDAO implements ITicketDAO {

    IConnector connector;


    public TicketDAO() throws Exception {
        connector = new SqlConnector();
    }
    public Ticket createTicket(Ticket ticket) throws Exception {

        Ticket newTicket = null;

        String sql = "INSERT INTO Tickets " +
                "(CustomerEmail, CustomerName)" + "VALUES (?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            String customerEmail = ticket.getCustomerEmail();
            String customerName = ticket.getCustomerName();
            String eventName = ticket.getEventName();
            int price = ticket.getPrice();
            String eventLocation = ticket.getEventLocation();
            Date eventDate = ticket.getEventDate();

            //todo ticket be should contain event id so it can be set in db
            statement.setString(1, customerEmail);
            statement.setString(2, customerName);


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
