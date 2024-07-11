/**
 * Utility class for reading configuration properties from a file.
 * 
 * <p>This class uses a singleton pattern to ensure that the properties are 
 * loaded only once. The properties are loaded from the file located at 
 * "src/main/resources/config.properties".</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * String urlPublicHome = ConfigReader.getProperty("urlPublicHome");
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class is thread-safe and ensures that the properties 
 * are loaded only once, even in a multi-threaded environment.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 * 
 */
package com.taskmanagerplus.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;

    /**
     * Gets the value of the specified property key.
     * 
     * <p>If the properties have not been loaded yet, this method will load them 
     * from the file. Subsequent calls will return the cached property values.</p>
     * 
     * @param key the property key
     * @return the value of the specified property key, or {@code null} if the key is not found
     */
    public static String getProperty(String key) {
        if (properties == null) {
            synchronized (ConfigReader.class) {
                if (properties == null) {
                    try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
                        properties = new Properties();
                        properties.load(fis);
                    } catch (IOException e) {
                        logger.error("Failed to load configuration properties", e);
                    }
                }
            }
        }
        return properties.getProperty(key);
    }
}
