package DAL.SystemUsers;

import BE.Role;
import BE.SystemUser;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemUserDAO implements ISystemUserDAO {
    private AbstractConnector connector;

    public SystemUserDAO() throws Exception {
        connector = new SqlConnector();
    }

    public SystemUserDAO(AbstractConnector connector) {
        this.connector = connector;
    }

    @Override
    public SystemUser systemUserValidLogin(SystemUser user) throws Exception {
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

                List<Role> systemUsersRoles = retrieveRolesForSystemUser(conn, systemUser);

                systemUser.getRoles().addAll(systemUsersRoles);
            }

            return systemUser;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to validate login", e);
        }
    }

    private List<Role> retrieveRolesForSystemUser(Connection conn, SystemUser systemUser) throws Exception {
        List<Role> systemUsersRoles = new ArrayList<>();

        String sql = "SELECT RoleName FROM SystemUserRoles WHERE SystemUserEmail = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, systemUser.getEmail());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = Role.getRole(resultSet.getString(1));

                if (role != null) systemUsersRoles.add(role);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve roles for system user");
        }

        return systemUsersRoles;
    }
}
