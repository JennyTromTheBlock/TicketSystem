package DAL.Connectors;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class TestSqlConnector extends AbstractConnector {
    public TestSqlConnector() throws Exception {
        super("db_config/test_sql_config");
    }

    @Override
    public Connection getConnection() throws Exception {
        try {
            return getDataSource().getConnection();
        }
        catch (SQLServerException e) {
            e.printStackTrace();
            throw new Exception("Failed to get connection to the test server", e);
        }
    }
}
