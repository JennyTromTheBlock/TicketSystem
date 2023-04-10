package DAL.SpecialTicket;

import DAL.Connectors.AbstractConnector;
import DAL.Connectors.SqlConnector;

public class SpecialTicketDAO implements ISpecialTicketDAO {
    AbstractConnector connector;

    public SpecialTicketDAO() throws Exception {
        connector = new SqlConnector();
    }
}
