package com.stevesouza.misc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MiscJavaTests {

    @Test
    public void testClassMethods() {
        assertThat(MiscJavaTests.class.getSimpleName()).isEqualTo("MiscJavaTests");
        assertThat(MiscJavaTests.class.getName()).isEqualTo("com.stevesouza.misc.MiscJavaTests");

    }


    @Test
    public void testVarArgs() {
        assertThat(varArgsMethod("Steve ", "Souza")).isEqualTo("Steve Souza");
    }

    private static String varArgsMethod(String... args) {
        String retValue = "";
        for (String str : args) {
            retValue+=str;
        }

        return retValue;
    }


}