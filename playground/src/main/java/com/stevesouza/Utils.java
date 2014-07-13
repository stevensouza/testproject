package com.stevesouza;

import com.google.common.io.Files;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.stevesouza.camel.json.jackson.FileToJsonToPojoRouteBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Set;

/**
 * Created by stevesouza on 5/26/14.
 */
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class);

    public static void displayJamon() {
        for (Monitor mon : MonitorFactory.getRootMonitor().getMonitors()) {
            LOG.info(mon);
        }
    }

    public static String getInputDir() {
        return "file:"+Utils.class.getResource("/data/in").getFile();
    }

    public static String getInput2Dir() {
        return "file:"+Utils.class.getResource("/data/in2").getFile();
    }

    public static String getOutputDir() {
        return "file:"+Utils.class.getResource("/data/out").getFile();
    }

    public static Properties getJamonProperties() throws IOException {
        Properties defaults = getDefaults();
        Properties userProvided = propertyLoader("jamonapi.properties");
        Properties jamonProps = new Properties(defaults);
        jamonProps.putAll(userProvided);
        return jamonProps;
    }

    public static Properties propertyLoader(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = Utils.class.getClassLoader().getResourceAsStream(fileName);
            if (input!=null) {
                properties.load(input);
            }

            properties = replaceWithCommandLineProps(properties);
        } finally{
            if (input!=null) {
                input.close();
            }
        }

        return properties;
    }

    private static Properties replaceWithCommandLineProps(Properties properties) {
        for (Object key : properties.keySet()) {
            String value = System.getProperty(key.toString());
            if (value != null) {
                properties.put(key, value);
            }
        }

        return properties;
    }

    private static Properties getDefaults() {
        Properties defaults = new Properties();
        defaults.put("distributedDataRefreshRateInMinutes", "5");
        defaults.put("jamonDataPersister", "com.jamonapi.distributed.DistributedJamonHazelcastPersister");
        return defaults;
    }


}
