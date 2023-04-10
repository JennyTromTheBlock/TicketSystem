package DAL.SpecialTicket;

import BE.Event;
import BE.SpecialTicket;
import BE.SpecialTicketType;
import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialTicketDAO implements ISpecialTicketDAO {
    AbstractConnector connector;

    public SpecialTicketDAO() throws Exception {
        connector = new SqlConnector();
    }
}
