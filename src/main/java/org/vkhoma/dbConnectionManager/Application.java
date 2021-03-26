package org.vkhoma.dbConnectionManager;

import org.apache.log4j.Logger;
import org.vkhoma.dbConnectionManager.connection.ConnectionManager;
import org.vkhoma.dbConnectionManager.connection.impl.ConnectionManagerImpl;
import org.vkhoma.dbConnectionManager.repository.ElasticLogRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
	private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws SQLException, IOException {
		log.info("Application started");

		// Instantiate connection manager
		ConnectionManager connectionManager = ConnectionManagerImpl.create();

		// Get connection from connection pool
		Connection connection = connectionManager.getConnection();

		// Return connection to connection pool
		connectionManager.releaseConnection(connection);

		// Close connection before application exit
		ElasticLogRepository.getInstance().closeConnection();

		log.info("Application finished");
	}

}
