package DAL.Connectors;

import BE.Event;
import BE.SystemUser;
import BLL.SystemUsers.ISystemUserManager;
import BLL.SystemUsers.SystemUserManager;
import DAL.EventDAO;
import DAL.IEventDAO;
import DAL.SystemUsers.ISystemUserDAO;
import DAL.SystemUsers.SystemUserDAO;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Instructions:
 * Create a new config file named "sql_config" in
 * a "db_config" package in the project root directory.
 *
 * The config file should look like this:
 * Server=insert IP
 * Name=insert database name
 * User=insert username
 * Password=insert password
 */

    public class SqlConnector extends AbstractConnector {

    public SqlConnector() throws Exception {
            super("db_config/sql_config", "Failed to connect the the server");
        }
    }
}
