package org.vkhoma.dbConnectionManager.dto;

import java.util.Date;

/**
 * Created by vkhoma on 3/26/21.
 */
public class LogDataDto {
    private final String id;
    private final String level;
    private final Date time;
    private final String location;
    private final String message;
    private final String throwable;

    public LogDataDto(String id, String level, Date time, String location, String message, String throwable) {
        this.id = id;
        this.level = level;
        this.time = time;
        this.location = location;
        this.message = message;
        this.throwable = throwable;
    }

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public Date getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public String getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return "LogDataDto{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", message='" + message + '\'' +
                ", throwable='" + throwable + '\'' +
                '}';
    }

}
