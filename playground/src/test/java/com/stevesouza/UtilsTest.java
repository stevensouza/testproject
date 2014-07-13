package com.stevesouza;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;


public class UtilsTest {


    @Test
    public void testGetInputDir() throws Exception {
        assertThat(Utils.getInputDir()).isEqualTo("file:/Users/stevesouza/gitrepo/testproject/playground/target/classes/data/in");
    }

    @Test
    public void testGetInput2Dir() throws Exception {
        assertThat(Utils.getInput2Dir()).isEqualTo("file:/Users/stevesouza/gitrepo/testproject/playground/target/classes/data/in2");
    }

    @Test
    public void testGetOutputDir() throws Exception {
        assertThat(Utils.getOutputDir()).isEqualTo("file:/Users/stevesouza/gitrepo/testproject/playground/target/classes/data/out");
    }

    @Test
    public void testLoadProperties() throws Exception {
        Properties properties = Utils.propertyLoader("jamonapi.properties");
        assertThat(properties.getProperty("distributedDataRefreshRateInMinutes")).isEqualTo("5");
        assertThat(properties.getProperty("jamonDataPersister")).isEqualTo("com.jamonapi.distributed.DistributedJamonHazelcastPersister");
        assertThat(properties.getProperty("I_DO_NO_EXIST")).isNull();

        System.out.println(System.getProperty("commandLineProp"));
    }

    @Test
    public void testLoadPropertiesNoFile() throws Exception {
        Properties properties = Utils.propertyLoader("I_DONT_EXIST.properties");

        System.out.println(System.getProperty("commandLineProp"));
    }
}