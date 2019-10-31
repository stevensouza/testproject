package com.stevesouza;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return "file:" + Utils.class.getResource("/data/in").getFile();
    }

    public static String getInput2Dir() {
        return "file:" + getIntput2DirPlain();
    }

    public static String getIntput2DirPlain() {
        return Utils.class.getResource("/data/in2").getFile();
    }

    public static String getOutputDir() {
        return "file:" + Utils.class.getResource("/data/out").getFile();
    }

    public static Properties getJamonProperties() throws IOException {
        Properties defaults = getDefaults();
        Properties userProvided = propertyLoader("jamonapi.properties");
        Properties jamonProps = new Properties(defaults);
        jamonProps.putAll(userProvided);
        return jamonProps;
    }

    public static String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Stream<String> lines = Files.lines(path);
        return lines.collect(Collectors.joining()); // converts to a string
    }

    public static Properties propertyLoader(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = Utils.class.getClassLoader().getResourceAsStream(fileName);
            if (input != null) {
                properties.load(input);
            }

            properties = replaceWithCommandLineProps(properties);
        } finally {
            if (input != null) {
                input.close();
            }
        }

        return properties;
    }

    public static InputStream resourceInputStream(String fileName) {
      return Utils.class.getClassLoader().getResourceAsStream(fileName);
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
