package DAL;

import BE.Event;
import BE.Note;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;
import DAL.SystemUsers.SystemUserDAO;

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


    private void bindEventInfo(Event event, PreparedStatement statement) throws SQLException {
        statement.setString(1, event.getEventName());
        statement.setString(2, event.getDescription());
        statement.setString(3, event.getLocation());
        Timestamp timestamp = new Timestamp(event.getDate().getTime());
        statement.setTimestamp(4, timestamp);
        statement.setInt(5, event.getMaxParticipant());
        statement.setInt(6, event.getPrice());
    }

    @Override
    public Note createNote(Note note) throws Exception {
        Note newNote = null;
        String sql = "INSERT INTO Notes " +
                "(SenderID, EventID, Message, Time)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, note.getSender().getEmail());
            statement.setInt(2, note.getEvent().getId());
            statement.setString(3, note.getMessage());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(4, timestamp);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                note.setMessageId(id);
                newNote = note;
            }
        }
        catch (Exception e) {
            throw new Exception("Failed to create note", e);
        }
        return newNote;
    }

    @Override
    public List<Note> getAllNotesFromEvent(Event event) throws Exception {

        //todo retrive all notes from event;
        //todo list the in db before getting them.

        ArrayList<Note> allNotesFromEvent = new ArrayList<>();
        String sql = "SELECT * FROM Notes WHERE EventID = ? ORDER BY Time DESC;";

        try (Connection connection = connector.getConnection();
             PreparedStatement s = connection.prepareStatement(sql)) {

            s.setInt(1, event.getId());

            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                String sender = rs.getString("SenderID");
                String message = rs.getString("Message");
                Timestamp timestamp = rs.getTimestamp("Time");
                int id = rs.getInt("ID");

                SystemUserDAO sys = new SystemUserDAO();

                Note note = new Note(sys.getSystemUserById(sender), event, message, timestamp, id);
                System.out.println(note.getMessage());
            }
        } catch (Exception e){
            throw new Exception("Failed to retrieve Notes", e);
        }
        return allNotesFromEvent;

    }


}
