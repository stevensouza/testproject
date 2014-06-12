package com.stevesouza;

import org.junit.Test;

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
}