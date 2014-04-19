package com.stevesouza;


import org.junit.Test;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@RunWith(JUnitParamsRunner.class)
public class AppTest 
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     *
     * TODO: try paramerterized tests, anyString(), verify, when, junitparams, catchexception library, fest
     */

    /**
     * Rigourous Test :-)
     */

    @Test
    @Parameters({"yes","on","1","true"}) // run the test 4 times with the following parameters
    public void testBooleanFlagWhenRequestingTrue(String booleanFlag) throws Exception {
        assertThat(booleanFlag).isEqualTo(booleanFlag);
   }

    @Test
    public void testApp()
    {
        assertThat(true).isEqualTo(true);
    }
}
