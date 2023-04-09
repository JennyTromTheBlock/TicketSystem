package DAL.EventNotes;

import BE.Event;
import BE.Note;
import BE.SystemUser;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventNoteDAO implements IEventNoteDAO {
    private AbstractConnector connector;

    public EventNoteDAO() throws Exception {
        connector = new SqlConnector();
    }

    @Override
    public Note createNote(Note note) throws Exception {
        Note newNote = null;

        String sql = "INSERT INTO Notes " +
                "(SenderEmail, EventID, Message, Time)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, note.getSender().getEmail());
            statement.setInt(2, note.getEvent().getId());
            statement.setString(3, note.getMessage());
            Timestamp timestamp = new Timestamp(note.getSendTime().getTime());
            statement.setTimestamp(4, timestamp);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);

                newNote = new Note(id, note);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create note", e);
        }

        return newNote;
    }

    @Override
    public List<Note> getAllNotesFromEvent(Event event) throws Exception {

        List<Note> allNotesFromEvent = new ArrayList<>();

        String sql = "SELECT Notes.ID AS 'Note ID', Notes.[Message] AS 'NoteMessage', Notes.[Time] AS 'NoteSendTime', " +
                "SystemUsers.Email AS 'SystemUserEmail', SystemUsers.FirstName, SystemUsers.LastName " +
                "FROM Notes " +
                "INNER JOIN SystemUsers ON Notes.SenderEmail = SystemUsers.Email " +
                "WHERE Notes.EventID = ? " +
                "ORDER BY Notes.Time ASC;";

        try (Connection connection = connector.getConnection();
             PreparedStatement s = connection.prepareStatement(sql)) {

            s.setInt(1, event.getId());

            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                int noteId = rs.getInt("Note ID");
                String noteMessage = rs.getString("NoteMessage");
                Timestamp noteSendTime = rs.getTimestamp("NoteSendTime");

                String systemUserEmail = rs.getString("SystemUserEmail");
                String systemUserFirstName = rs.getString("FirstName");
                String systemUserLastName = rs.getString("LastName");

                SystemUser systemUser = new SystemUser(systemUserEmail, systemUserFirstName, systemUserLastName);

                Note note = new Note(noteId, systemUser, event, noteMessage, noteSendTime);

                allNotesFromEvent.add(note);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to retrieve Notes", e);
        }

        return allNotesFromEvent;
    }
}
