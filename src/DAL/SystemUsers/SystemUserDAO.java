package DAL.SystemUsers;

import BE.SystemUser;
import DAL.Connectors.IConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;

public class SystemUserDAO implements ISystemUserDAO {
    private IConnector connector;

    public SystemUserDAO() throws Exception {
        connector = new SqlConnector();
    }

    @Override
    public boolean login(SystemUser user) throws Exception {
        String sql = "SELECT Email FROM SystemUsers WHERE Email = ? AND Password = ?";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to validate login", e);
        }
    }
}
