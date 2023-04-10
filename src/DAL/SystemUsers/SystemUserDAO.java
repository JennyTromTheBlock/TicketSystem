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

    public SystemUser createSystemUser(SystemUser systemUser) throws Exception {
        SystemUser user = null;
        String sql = "INSERT INTO SystemUsers " +
                "(Email, FirstName, LastName, Password)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, systemUser.getEmail());
            statement.setString(2, systemUser.getFirstName());
            statement.setString(3, systemUser.getLastName());
            statement.setString(4, systemUser.getPassword());
            statement.executeUpdate();
            user = systemUser;
        }
        catch (Exception e) {
            throw new Exception("Failed create system user", e);
        }

        return user;
    }

    public List<SystemUser> getAllSystemUsers() throws Exception {

        ArrayList<SystemUser> allUsers = new ArrayList<>();

        try (Connection connection = connector.getConnection();

            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM SystemUsers;";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String email = rs.getString("Email");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String password = rs.getString("Password");

                SystemUser systemUser = new SystemUser(email, firstName, lastName, password);
                allUsers.add(systemUser);
            }
        } catch (Exception e){
            throw new Exception("Failed to retrieve all Users", e);
        }
        return allUsers;
    }

    @Override
    public SystemUser deleteSystemUser(SystemUser systemUser) throws Exception {
        String sql = "DELETE FROM SystemUsers WHERE Email = ?;";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, systemUser.getEmail());

            int changes = statement.executeUpdate();

            if (changes > 0) return systemUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete User", e);
        }

        return null;
    }

    @Override
    public SystemUser getSystemUserById(String id) throws Exception {
        ArrayList<SystemUser> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM SystemUsers WHERE Email = ?;";

        try (Connection connection = connector.getConnection();
             PreparedStatement s = connection.prepareStatement(sql)) {
            s.setString(1, id);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                String email = rs.getString("Email");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String password = rs.getString("Password");

                SystemUser systemUser = new SystemUser(email, firstName, lastName, password);
                return systemUser;
            }
        } catch (Exception e){
            throw new Exception("Failed to retrieve all Users", e);
        }
        return null;
    }
}
