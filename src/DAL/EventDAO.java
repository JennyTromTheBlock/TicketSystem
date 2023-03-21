package DAL;

import BE.Event;
import DAL.Connectors.IConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO {
    IConnector connector;

    public EventDAO() throws Exception {
        connector = new SqlConnector();
    }

    public Event createEvent(Event event) throws Exception {
        Event newEvent = null;
        String sql = "INSERT INTO EVENT " +
                "(EventName, EventDescription, EventLocation, EventDate, MaxParticipant, Price)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, event.getEventName());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getLocation());
            // Converting the util.Date class to sql.Date.
            statement.setDate(4, new Date(event.getDate().getTime()));
            statement.setInt(5, event.getMaxParticipant());
            statement.setInt(6, event.getPrice());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newEvent = new Event(id, event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            throw new Exception("Failed to create event", e);
        }

        return newEvent;
    }


    public List<Event> getAllEvents() throws Exception {

        ArrayList<Event> allEvents = new ArrayList<>();

        try (Connection mConnection = connector.getConnection();
            Statement mStatement = mConnection.createStatement()) {

            String sql = "SELECT * FROM EVENT;";
            ResultSet rs = mStatement.executeQuery(sql);

            while(rs.next()) {

                int id = rs.getInt("ID");
                String eventName = rs.getString("EventName");
                String description = rs.getString("EventDescription");
                String location = rs.getString("EventLocation");
                Date date = rs.getDate("EventDate");
                int maxParticipant = rs.getInt("maxParticipant");
                int price = rs.getInt("Price");

                Event event = new Event(eventName, description, location, date, maxParticipant, price);
                allEvents.add(event);
            }
        } catch (Exception e){
                e.printStackTrace();
                throw new Exception("Failed to retrieve all events", e);
        }
        return allEvents;

    }


}
