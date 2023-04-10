package DAL.UsersOnEvents;

import BE.Event;
import BE.SystemUser;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersOnEventDAO implements IUsersOnEventsDAO {

    private AbstractConnector connector;


    public UsersOnEventDAO() throws Exception {
        connector = new SqlConnector();
    }


    @Override
    public void assignUserToEvent(SystemUser user, Event event) throws Exception {
        String sql = "INSERT INTO UsersAssignedToEvent (UserEmail, EventID) VALUES (?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setInt(2, event.getId());

            statement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to assign system user to event", e);
        }
    }
    @Override
    public List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception {

        String sql = "SELECT * FROM UsersAssignedToEvent " +
                "INNER JOIN SystemUsers " +
                "ON SystemUsers.Email=UsersAssignedToEvent.UserEmail " +
                "WHERE EventID = ?;";

        List<SystemUser> allUsersAssignedToEvent = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, event.getId());

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");

                SystemUser user = new SystemUser(email, firstName, lastName);

                allUsersAssignedToEvent.add(user);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to retrieve all events", e);
        }

        return allUsersAssignedToEvent;
    }

    @Override
    public List<Event> getEventsAssignedToUser(SystemUser loggedInUser) throws Exception {
        String sql = "SELECT * FROM  UsersAssignedToEvent INNER JOIN Event ON Event.ID=UsersAssignedToEvent.EventID WHERE UserEmail = ?;";

        ArrayList<Event> allEventsAssignedToUser = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loggedInUser.getEmail());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("ID");
                String eventName = rs.getString("EventName");
                String description = rs.getString("EventDescription");
                String location = rs.getString("EventLocation");
                Date date = rs.getTimestamp("EventDate");
                int maxParticipant = rs.getInt("maxParticipant");
                int price = rs.getInt("Price");

                Event event = new Event(id, eventName, description, location, date, maxParticipant, price);
                allEventsAssignedToUser.add(event);
            }
        } catch (Exception e){
            throw new Exception("Failed to retrieve all events", e);
        }
        return allEventsAssignedToUser;
    }

    @Override
    public Event deleteEvent(Event event) throws Exception {
        Event deletedEvent = null;
        String sql = "DELETE FROM UsersAssignedToEvent WHERE EventID =?;";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, event.getId());
            statement.executeUpdate();

            deletedEvent = event;
        } catch (SQLException e) {
            throw new Exception("Failed to delete the event", e);
        }
        return deletedEvent;
    }
}
