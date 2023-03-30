package DAL.Connectors;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Instructions:
 * Create a new config file in
 * a "db_config" package in the project root directory.
 *
 * The config file should look like this:
 * Server=insert IP
 * Name=insert database name
 * User=insert username
 * Password=insert password
 */
public abstract class AbstractConnector {
    private final String CONFIG;
    private SQLServerDataSource dataSource;

    public AbstractConnector(String configPath) throws Exception {
        this.CONFIG = configPath;

        try {
            connect(getConfig());
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to connect to the server", e);
        }
    }

    private void connect(DatabaseConfig config) {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(config.getServer());
        dataSource.setDatabaseName(config.getName());
        dataSource.setUser(config.getUser());
        dataSource.setPassword(config.getPassword());
        dataSource.setTrustServerCertificate(true);
    }

    private DatabaseConfig getConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(CONFIG));

        String server = properties.getProperty("Server");
        String name = properties.getProperty("Name");
        String user = properties.getProperty("User");
        String password = properties.getProperty("Password");
        return new DatabaseConfig(server, name, user, password);
    }

    public SQLServerDataSource getDataSource() {
        return this.dataSource;
    }

    abstract public Connection getConnection() throws Exception;
}
