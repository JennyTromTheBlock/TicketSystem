package DAL.Connectors;

public class SqlConnector extends AbstractConnector {
    public SqlConnector() throws Exception {
        super("db_config/sql_config", "Failed to connect the the server");
    }
}
