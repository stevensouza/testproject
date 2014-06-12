package com.stevesouza;

import com.google.common.io.Files;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.stevesouza.camel.json.jackson.FileToJsonToPojoRouteBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

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


}
