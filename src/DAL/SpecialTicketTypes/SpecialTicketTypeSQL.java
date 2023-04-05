package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
}
