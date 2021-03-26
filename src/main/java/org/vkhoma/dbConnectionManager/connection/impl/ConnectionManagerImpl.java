package org.vkhoma.dbConnectionManager.connection.impl;

import org.apache.log4j.Logger;
import org.vkhoma.dbConnectionManager.connection.ConnectionManager;
import org.vkhoma.dbConnectionManager.connection.ConnectionPool;
import org.vkhoma.dbConnectionManager.util.ConfigurationUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vkhoma on 3/26/21.
 */
public class ConnectionManagerImpl implements ConnectionManager {
    private static final Logger log = Logger.getLogger(ConnectionManagerImpl.class);
    private static ConnectionPool masterConnectionPool;
    private static ConnectionPool slaveConnectionPool;
    private final Map<Connection, String> usedConnections = new HashMap<>();

    private ConnectionManagerImpl() {
    }

    public static ConnectionManagerImpl create() throws SQLException {
        masterConnectionPool = createConnectionPool(DbStatus.MASTER.getKey());
        slaveConnectionPool = createConnectionPool(DbStatus.SLAVE.getKey());
        return new ConnectionManagerImpl();
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Connection connection = masterConnectionPool.getConnection();
            usedConnections.put(connection, DbStatus.MASTER.getKey());
            log.info("Given connection from master database");
            return connection;
        } catch (SQLException ex) {
            log.error("An error occurred while getting a connection from master database");
            try {
                Connection connection = slaveConnectionPool.getConnection();
                usedConnections.put(connection, DbStatus.SLAVE.getKey());
                log.info("Given connection from slave database");
                return connection;
            } catch (SQLException e) {
                log.error(e);
                throw new SQLException("No database available");
            }
        }
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        String dbStatusKey = usedConnections.get(connection);

        if (DbStatus.MASTER.getKey().equals(dbStatusKey)) {
            usedConnections.remove(connection);
            log.info("A connection was returned to master connection pool");
            return masterConnectionPool.releaseConnection(connection);
        }
        if (DbStatus.SLAVE.getKey().equals(dbStatusKey)) {
            usedConnections.remove(connection);
            log.info("A connection was returned to slave connection pool");
            return slaveConnectionPool.releaseConnection(connection);
        }
        log.warn("A connection wasn't returned to any known connection pool");
        return false;
    }

    private static ConnectionPool createConnectionPool(String dbKey) throws SQLException {
        return ConnectionPoolImpl.create(ConfigurationUtil.getValue(dbKey + ".url"),
                ConfigurationUtil.getValue(dbKey + ".username"),
                ConfigurationUtil.getValue(dbKey + ".password"));
    }

}
