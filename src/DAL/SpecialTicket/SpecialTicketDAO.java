package DAL.SpecialTicket;

import BE.Event;
import BE.SpecialTicket;
import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;

public class SpecialTicketDAO implements ISpecialTicketDAO {
    AbstractConnector connector;

    public SpecialTicketDAO() throws Exception {
        connector = new SqlConnector();
    }

    @Override
    public SpecialTicket createSpecialTicket(SpecialTicket specialTicket) throws Exception {
        SpecialTicket newTicket = null;

        String sql = "INSERT INTO SpecialTickets " +
                "(Type, EventID)" + "VALUES (?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            SpecialTicketType type = specialTicket.getType();
            Event event = specialTicket.getAssignedEvent();

            statement.setString(1, type.getTypeName());
            if(event != null) {
                statement.setInt(2, event.getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newTicket = new SpecialTicket(id, type, event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create special ticket", e);
        }

        return newTicket;
    }
}
