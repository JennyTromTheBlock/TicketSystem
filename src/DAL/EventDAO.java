package DAL;

import BE.Event;
import BE.SpecialTicketOnEvent;
import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO {
    AbstractConnector connector;

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

            bindEventInfo(event, statement);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newEvent = new Event(id, event);
            }
        }
        catch (Exception e) {
            throw new Exception("Failed to create event", e);
        }

        return newEvent;
    }


    public List<Event> getAllEvents() throws Exception {

        ArrayList<Event> allEvents = new ArrayList<>();

        try (Connection connection = connector.getConnection();
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM EVENT;";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("ID");
                String eventName = rs.getString("EventName");
                String description = rs.getString("EventDescription");
                String location = rs.getString("EventLocation");
                Timestamp date = rs.getTimestamp("EventDate");
                int maxParticipant = rs.getInt("maxParticipant");
                int price = rs.getInt("Price");

                Event event = new Event(id, eventName, description, location, date, maxParticipant, price);
                allEvents.add(event);
            }
        } catch (Exception e){
                throw new Exception("Failed to retrieve all events", e);
        }
        return allEvents;
    }

    public Event updateEvent(Event event) throws Exception {
        Event updatedEvent = null;
        String sql = "UPDATE EVENT SET EventName=?, EventDescription=?, EventLocation=?, EventDate=?, MaxParticipant=?, Price=? WHERE Id =?;";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            bindEventInfo(event, statement);
            statement.setInt(7, event.getId());
            statement.executeUpdate();

            updatedEvent = event;
        } catch (SQLException e) {
            throw new Exception("Failed to edit the event", e);
        }
        return updatedEvent;
    }

    @Override
    public Event deleteEvent(Event event) throws Exception {
        Event deletedEvent = null;
        String sql = "DELETE FROM EVENT WHERE Id =?;";

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

    @Override
    public void createSpecialTicketTypeOnEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        String sql = "INSERT INTO SpecialTicketTypesOnEvents (EventID, SpecialTicketType) VALUES (?, ?)";

        try (Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, specialTicketOnEvent.getEventID());
            statement.setString(2, specialTicketOnEvent.getSpecialTicketType());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add special ticket type to event");
        }
    }

    @Override
    public void removeSpecialTicketFromEvent(SpecialTicketOnEvent specialTicketOnEvent) throws Exception {
        String sql = "DELETE FROM SpecialTicketTypesOnEvents WHERE SpecialTicketType =? AND EventID = ?;";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, specialTicketOnEvent.getSpecialTicketType());
            statement.setInt(2, specialTicketOnEvent.getEventID());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove special ticket type from event");
        }
    }

    @Override
    public List<SpecialTicketType> getAllSpecialTicketTypesOnEvent(int eventID) throws Exception {
        List<SpecialTicketType> specialTicketTypesOnEvent = new ArrayList<>();

        String sql = "SELECT SpecialTicketTypes.[Type], SpecialTicketTypes.Price " +
                "FROM SpecialTicketTypes " +
                "WHERE SpecialTicketTypes.[Type] IN " +
                "(SELECT SpecialTicketTypesOnEvents.SpecialTicketType " +
                "FROM SpecialTicketTypesOnEvents WHERE SpecialTicketTypesOnEvents.EventID = ?);";

        try (Connection conn = connector.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                int price = resultSet.getInt("Price");

                SpecialTicketType specialTicketType = new SpecialTicketType(type, price);

                specialTicketTypesOnEvent.add(specialTicketType);
            }

            return specialTicketTypesOnEvent;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve special ticket types on event", e);
        }
    }


    private void bindEventInfo(Event event, PreparedStatement statement) throws SQLException {
        statement.setString(1, event.getEventName());
        statement.setString(2, event.getDescription());
        statement.setString(3, event.getLocation());
        Timestamp timestamp = new Timestamp(event.getDate().getTime());
        statement.setTimestamp(4, timestamp);
        statement.setInt(5, event.getMaxParticipant());
        statement.setInt(6, event.getPrice());
    }


}
