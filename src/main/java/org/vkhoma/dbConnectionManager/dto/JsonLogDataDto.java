package org.vkhoma.dbConnectionManager.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

/**
 * Created by vkhoma on 3/26/21.
 */
public class JsonLogDataDto {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .create();
    private final LogDataDto logDataDto;

    public JsonLogDataDto(LoggingEvent event) {
        this.logDataDto = new LogDataDto(UUID.randomUUID().toString(),
                event.getLevel().toString(),
                new Date(event.getTimeStamp()),
                event.getLocationInformation().getClassName(),
                event.getMessage().toString(),
                parseThrowable(event.getThrowableInformation()));
    }

    private String parseThrowable(ThrowableInformation throwableInformation) {
        if (throwableInformation == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwableInformation.getThrowable().printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public String getJson() {
        return gson.toJson(logDataDto);
    }

}
