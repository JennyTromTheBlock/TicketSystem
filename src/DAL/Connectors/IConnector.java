package DAL.Connectors;

import java.sql.Connection;

public interface IConnector {
    Connection getConnection() throws Exception;
}
