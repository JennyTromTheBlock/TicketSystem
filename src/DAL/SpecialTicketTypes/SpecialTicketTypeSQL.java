package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        String sql = "INSERT INTO SpecialTicketTypes (Type) VALUES (?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, type.getType());

            statement.executeUpdate();
        }
        catch (SQLServerException e) {
            e.printStackTrace();
            throw new Exception("Failed to create special ticket type", e);
        }

        return type;
    }
}
