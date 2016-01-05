package be.softwarelab.fsm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigurationManager {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ConfigurationManager.class);
    private static final org.slf4j.Logger CONSOLE = org.slf4j.LoggerFactory.getLogger("Console");

    public static Properties readConfiguration() {
        LOGGER.debug("Configuration: Read");
        CONSOLE.debug("Configuration: Read");
        Properties config = new Properties();
        InputStream input = null;

        try {

            //Default file is available.
            input = new FileInputStream("config.properties");

            // load the configuration file
            config.load(input);

            // Print all properties
            Enumeration<?> e = config.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = config.getProperty(key);
                LOGGER.debug("Key : " + key + ", Value : " + value);
            }

        } catch (IOException io) {
            LOGGER.error("IO Exception: {}", io.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("IO Exception: {}", e.toString());
                }
            }
        }

        validateConfiguration(config);

        return config;
    }

    public static void validateConfiguration(Properties config) {
        LOGGER.debug("Configuration: Validate");
        String providerUrl = config.getProperty("providerUrl");
        String task = config.getProperty("task");
        String name = config.getProperty("name");
        String guid = config.getProperty("guid");
        String frequency = config.getProperty("frequency");

        if (providerUrl == null || providerUrl.isEmpty()) {
            providerUrl = "https://cyberserver.mips.be:443/mq/";
            config.setProperty("providerUrl", providerUrl);
        }
        if (name == null || name.isEmpty()) {
            name = "Anonymous";
            config.setProperty("name", name);
        }
        // Create a new GUID for new clients
        if (guid == null || guid.isEmpty()) {
            guid = java.util.UUID.randomUUID().toString();
            config.setProperty("guid", guid);
        }
        // Default task is polling.
        if (task == null || task.isEmpty()) {
            task = "poll";
            config.setProperty("task", task);
        }
        // Default polling frequency is 1000 milli seconds
        if (frequency == null || frequency.isEmpty()) {
            frequency = "1000";
            config.setProperty("frequency", frequency);
        } else {
            try {
                Long freq = Long.valueOf(frequency);
            } catch (Exception ex) {
                // set default value to 1000 milli seconds if this can not be converterd to Long
                frequency = "1000";
            }
        }

        //TODO: remove later, this is just to test if you can write the file.
        writeConfiguration(config);
    }

    public static void writeConfiguration(Properties config) {
        LOGGER.debug("Configuration: Write");
        OutputStream output = null;

        try {
            output = new FileOutputStream("config.properties");
            // save properties to project root folder
            config.store(output, null);
        } catch (IOException io) {
            LOGGER.error("IO Exception: {}", io.toString());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    LOGGER.error("IO Exception: {}", e.toString());
                }
            }
        }
    }
}
