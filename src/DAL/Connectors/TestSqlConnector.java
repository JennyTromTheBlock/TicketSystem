package DAL.Connectors;

public class TestSqlConnector extends AbstractConnector {
    public TestSqlConnector() throws Exception {
        super("db_config/test_sql_config", "Failed to connect to the test server");
    }
}
