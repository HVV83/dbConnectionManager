package org.vkhoma.dbConnectionManager.connection.impl;

import org.junit.Before;
import org.junit.Test;
import org.vkhoma.dbConnectionManager.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by vkhoma on 3/26/21.
 */
public class ConnectionManagerImplTest {
    private static ConnectionManager connectionManager;

    @Before
    public void setUpConnectionManagerInstance() throws SQLException {
        connectionManager = ConnectionManagerImpl.create();
    }

    @Test
    public void shouldReturnValidConnection() throws SQLException {
        assertTrue(connectionManager.getConnection().isValid(1));
    }

    @Test
    public void shouldReleaseConnection() throws SQLException {
        Connection connection = connectionManager.getConnection();
        assertTrue(connectionManager.releaseConnection(connection));
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenAskedMoreThanMaxConnections() throws SQLException {
        final int MAX_POOL_SIZE = 20;
        for (int i = 0; i < MAX_POOL_SIZE * 2 + 1; i++) {
            connectionManager.getConnection();
        }
    }

}