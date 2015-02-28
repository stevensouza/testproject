package com.stevesouza.misc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MiscJavaTests {

    @Test
    public void testClassMethods() {
        assertThat(MiscJavaTests.class.getSimpleName()).isEqualTo("MiscJavaTests");
        assertThat(MiscJavaTests.class.getName()).isEqualTo("com.stevesouza.misc.MiscJavaTests");

    }


}