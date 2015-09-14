package com.stevesouza.aspectj.javastyle.nativestyle.structure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by stevesouza on 6/23/15.
 */
public class CustomerClassTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetExtraInfo() throws Exception {
        CustomerClass customer = new CustomerClass();
        assertThat(customer.getName()).isEqualTo("Steve Souza");
    }
}