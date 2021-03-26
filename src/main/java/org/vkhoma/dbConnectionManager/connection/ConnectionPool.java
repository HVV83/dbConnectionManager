package org.vkhoma.dbConnectionManager.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vkhoma on 3/26/21.
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    int getSize();

    void shutdown() throws SQLException;

}
