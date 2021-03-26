package org.vkhoma.dbConnectionManager.util;

import java.util.ResourceBundle;

/**
 * Created by vkhoma on 3/26/21.
 */
public class ConfigurationUtil {
    private static final String PROPERTIES_FILE = "app";

    private ConfigurationUtil() {
    }

    public static String getValue(String key) {
        return ResourceBundle.getBundle(PROPERTIES_FILE).getString(key);
    }

}
