package org.vkhoma.dbConnectionManager.log.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.vkhoma.dbConnectionManager.repository.ElasticLogRepository;

import java.io.IOException;

/**
 * Created by vkhoma on 3/26/21.
 */
public class ElasticAppender extends AppenderSkeleton {
    private final ElasticLogRepository repository;

    public ElasticAppender() {
        repository = ElasticLogRepository.getInstance();
    }

    @Override
    protected void append(LoggingEvent event) {
        repository.saveLogData(event);
    }

    @Override
    public void close() {
        try {
            repository.closeConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

}
