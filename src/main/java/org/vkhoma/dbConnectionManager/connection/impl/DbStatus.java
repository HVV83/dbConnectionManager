package org.vkhoma.dbConnectionManager.connection.impl;

/**
 * Created by vkhoma on 3/26/21.
 */
public enum DbStatus {
    MASTER("master"),
    SLAVE("slave");

    private final String key;

    DbStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
