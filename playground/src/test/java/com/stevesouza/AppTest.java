package com.stevesouza;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

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
    @Parameters({"myfile's.txt","myfile\"s.txt", "myfile*?.+.txt"})
    public void testUrlEncoding(String original) throws Exception {
        String encoded = URLEncoder.encode(original, "UTF-8");
        String decoded = URLDecoder.decode(encoded, "UTF-8");
        assertEquals("The decoded string should equal the original", original, decoded);
    }

    @Test
    @Parameters({
            "myfile's.txt, myfile%27s.txt",
            "myfile\"s.txt, myfile%22s.txt",
            "myfile*?.+.txt, myfile*%3F.%2B.txt"})
    public void testMultipleParameters(String original, String answer) throws Exception {
        String encoded = URLEncoder.encode(original, "UTF-8");
        assertEquals("The answer string should match the encoded value", answer, encoded);
    }

    @Test
    public void testApp()
    {
        assertThat(true).isEqualTo(true);
    }
}
