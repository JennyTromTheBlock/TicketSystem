package DAL.SpecialTicketTypes;

import BE.Event;
import BE.SpecialTicket;
import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialTicketTypeSQL implements ISpecialTicketTypeDAO {
    private AbstractConnector connector;

    public SpecialTicketTypeSQL() throws Exception {
        connector = new SqlConnector();
    }

    public SpecialTicketTypeSQL(AbstractConnector connector) {
        this.connector = connector;
    }

    @Override
    public SpecialTicketType createSpecialTicketType(SpecialTicketType type) throws Exception {
        String sql = "INSERT INTO SpecialTicketTypes (Type, Price) VALUES (?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, type.getTypeName());
            statement.setInt(2, type.getPrice());

            statement.executeUpdate();
        }
        catch (SQLServerException e) {
            e.printStackTrace();
            throw new Exception("Failed to create special ticket type", e);
        }

        return type;
    }

    @Override
    public List<SpecialTicketType> getAllSpecialTicketTypes() throws Exception {
        List<SpecialTicketType> types = new ArrayList<>();

        String sql = "SELECT * FROM SpecialTicketTypes";

        try (Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String typeName = resultSet.getString(1);
                int price = resultSet.getInt(2);
                SpecialTicketType specialTicketType = new SpecialTicketType(typeName, price);

                types.add(specialTicketType);
            }
        }
        catch (SQLServerException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve special ticket types", e);
        }
        return types;
    }

    @Override
    public List<SpecialTicketType> getSpecialTicketTypesOnEvent(Event event) throws Exception {
        List<SpecialTicketType> specialTicketTypes = new ArrayList<>();

        String sql = "SELECT * FROM SpecialTicketTypes " +
                "WHERE " +
                "SpecialTicketTypes.Type IN " +
                "(SELECT DISTINCT SpecialTickets.Type FROM SpecialTickets " +
                "WHERE SpecialTickets.EventID = ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, event.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                int price = resultSet.getInt("Price");

                SpecialTicketType specialTicketType = new SpecialTicketType(type, price);

                specialTicketTypes.add(specialTicketType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve special ticket types for event", e);
        }

        return specialTicketTypes;
    }
}
