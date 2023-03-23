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
    public SystemUser SystemUserValidLogin(SystemUser user) throws Exception {
        SystemUser systemUser = null;

        String sql = "SELECT * FROM SystemUsers WHERE Email = ? AND Password = ?";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");

                systemUser = new SystemUser(user.getEmail(), firstName, lastName, user.getPassword());
            }

            return systemUser;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to validate login", e);
        }
    }
}
