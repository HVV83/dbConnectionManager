package org.vkhoma.dbConnectionManager.repository;

import org.apache.http.HttpHost;
import org.apache.log4j.spi.LoggingEvent;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.vkhoma.dbConnectionManager.dto.JsonLogDataDto;
import org.vkhoma.dbConnectionManager.util.ConfigurationUtil;

import java.io.IOException;

/**
 * Created by vkhoma on 3/26/21.
 */
public class ElasticLogRepository {
    private static final String INDEX = "log_data";
    private static RestHighLevelClient restClient;
    private static ElasticLogRepository instance;

    private ElasticLogRepository() {
    }

    public static ElasticLogRepository getInstance() {
        if (instance == null) {
            synchronized (ElasticLogRepository.class) {
                if (instance == null) {
                    initRestClient();
                    instance = new ElasticLogRepository();
                }
            }
        }
        return instance;
    }

    private static void initRestClient() {
        final String host = ConfigurationUtil.getValue("elastic.host");
        final int portFirst = Integer.parseInt(ConfigurationUtil.getValue("elastic.port.first"));
        final int portSecond = Integer.parseInt(ConfigurationUtil.getValue("elastic.port.second"));
        final String scheme = ConfigurationUtil.getValue("elastic.scheme");

        if (restClient == null) {
            restClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(host, portFirst, scheme),
                            new HttpHost(host, portSecond, scheme)));
        }
    }

    public synchronized void closeConnection() throws IOException {
        restClient.close();
        restClient = null;
    }

    public boolean saveLogData(LoggingEvent event) {
        JsonLogDataDto logDataDto = new JsonLogDataDto(event);
        IndexRequest request = new IndexRequest(INDEX);
        request.source(logDataDto.getJson(), XContentType.JSON);
        try {
            IndexResponse response = restClient.index(request, RequestOptions.DEFAULT);
            return RestStatus.CREATED.equals(response.status());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
