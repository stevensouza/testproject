package com.stevesouza;

import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(properties.getProperty("hello")).isEqualTo("Encrypted hello#world");
        properties.put("specialchars","special chars hello#!(,!world");
        assertThat(properties.getProperty("specialchars")).isEqualTo("special chars hello#!(,!world");

        System.out.println(System.getProperty("commandLineProp"));
    }

    @Test
    public void testLoadPropertiesNoFile() throws Exception {
        Properties properties = Utils.propertyLoader("I_DONT_EXIST.properties");

        System.out.println(System.getProperty("commandLineProp"));
    }

    @Test
    public void testLoadPropertiesPropsTest() throws Exception {
        Properties properties = Utils.propertyLoader("test.props");

        System.out.println(properties.get("org.automon"));
        System.out.println(properties);
    }

    @Test
    public void testLoadPropertiesPropsTestXml() throws Exception {
        Properties properties = Utils.propertyLoader("test.props.xml");

        System.out.println("org.automon="+properties.get("org.automon"));
        System.out.println("myothervar="+properties.get("myothervar"));

        System.out.println(properties);
    }
    
    @Test
    public void testReadFile() throws Exception {
        String fileName=Utils.getIntput2DirPlain()+"/personsnamexstream.json";
        String contents = Utils.readFile(fileName);
        assertThat(contents).contains("joel","souza");
    }
}
